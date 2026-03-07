package com.example.downloaderapp;

// =====================================================================
//  MainActivity.java — Simple Downloader for Android
//  --------------------------------------------------
//  Ported from the terminal-based HttpURLConnection downloader.
//  Downloads any direct-link file and saves it to the Downloads folder.
//
//  HOW IT WORKS:
//  1. User pastes a URL and taps "Download".
//  2. A background Thread opens an HttpURLConnection (just like the
//     original Main.java), reads bytes in a loop, and writes them
//     to a file in the public Downloads directory.
//  3. Progress updates are posted back to the UI thread with
//     runOnUiThread() so the ProgressBar and TextViews update live.
//  4. On API 29+ we use MediaStore (scoped storage) to create the
//     file; on older APIs we write directly to the Downloads folder.
// =====================================================================

import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

	// ── UI references ────────────────────────────────────────────────
	private TextInputEditText editTextUrl;
	private MaterialButton buttonDownload;
	private ProgressBar progressBar;
	private TextView textPercent;
	private TextView textStatus;

	// Flag to prevent multiple simultaneous downloads
	private boolean isDownloading = false;

	// =================================================================
	// onCreate — called once when the Activity is first created.
	// We inflate the layout and wire up the button click listener.
	// =================================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ── Find views by their IDs (must match activity_main.xml) ──
		editTextUrl = findViewById(R.id.editTextUrl);
		buttonDownload = findViewById(R.id.buttonDownload);
		progressBar = findViewById(R.id.progressBar);
		textPercent = findViewById(R.id.textPercent);
		textStatus = findViewById(R.id.textStatus);

		// ── Button click → start download ───────────────────────────
		buttonDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 1. Read the URL from the input field
				String url = editTextUrl.getText() != null
						? editTextUrl.getText().toString().trim()
						: "";

				// 2. Basic validation
				if (url.isEmpty()) {
					Toast.makeText(MainActivity.this,
							"Please enter a URL", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!url.startsWith("http://") && !url.startsWith("https://")) {
					Toast.makeText(MainActivity.this,
							"URL must start with http:// or https://",
							Toast.LENGTH_SHORT).show();
					return;
				}

				// 3. Prevent double-tap
				if (isDownloading) {
					Toast.makeText(MainActivity.this,
							"A download is already in progress",
							Toast.LENGTH_SHORT).show();
					return;
				}

				// 4. Start the download on a background thread
				startDownload(url);
			}
		});
	}

	// =================================================================
	// startDownload — launches a background thread that does the
	// actual HTTP work. This is the Android equivalent of your
	// terminal directDownload() method.
	//
	// WHY a Thread? Android forbids network calls on the main (UI)
	// thread — it would freeze the screen. We use a plain Thread
	// here (simplest approach). You could also use ExecutorService,
	// Coroutines, or WorkManager for production apps.
	// =================================================================
	private void startDownload(final String urlString) {
		isDownloading = true;

		// ── Reset UI ────────────────────────────────────────────────
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(0);
				textPercent.setVisibility(View.VISIBLE);
				textPercent.setText("0%");
				textStatus.setText("Connecting...");
				buttonDownload.setEnabled(false);
			}
		});

		// ── Background thread (network + file I/O) ─────────────────
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				InputStream inputStream = null;
				OutputStream outputStream = null;

				try {
					// ────────────────────────────────────────────────
					// STEP 1: Open HTTP connection (same as your code)
					// ────────────────────────────────────────────────
					URL url = new URL(urlString);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");

					// Some servers reject requests without a User-Agent
					connection.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Android; DownloaderApp)");
					connection.setConnectTimeout(15000); // 15 s connect timeout
					connection.setReadTimeout(15000); // 15 s read timeout
					connection.connect();

					// Check HTTP response code
					int responseCode = connection.getResponseCode();
					if (responseCode != HttpURLConnection.HTTP_OK) {
						postError("Server returned HTTP " + responseCode);
						return;
					}

					// ────────────────────────────────────────────────
					// STEP 2: Determine file name from URL
					// ────────────────────────────────────────────────
					String fileName = extractFileName(urlString);

					// Total file size (-1 if server doesn't tell us)
					int fileLength = connection.getContentLength();

					// ────────────────────────────────────────────────
					// STEP 3: Create output file in Downloads folder
					// ────────────────────────────────────────────────
					String savedPath;

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
						// === API 29+ : Use MediaStore (scoped storage) ===
						// This is the modern way — no WRITE_EXTERNAL_STORAGE
						// permission needed.
						ContentValues values = new ContentValues();
						values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
						values.put(MediaStore.Downloads.MIME_TYPE,
								getMimeType(fileName));
						values.put(MediaStore.Downloads.RELATIVE_PATH,
								Environment.DIRECTORY_DOWNLOADS);

						Uri fileUri = getContentResolver().insert(
								MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

						if (fileUri == null) {
							postError("Could not create file in Downloads");
							return;
						}

						outputStream = getContentResolver()
								.openOutputStream(fileUri);
						savedPath = Environment.DIRECTORY_DOWNLOADS
								+ "/" + fileName;

					} else {
						// === API < 29 : Write directly to Downloads ===
						File downloadsDir = Environment
								.getExternalStoragePublicDirectory(
										Environment.DIRECTORY_DOWNLOADS);
						if (!downloadsDir.exists()) {
							downloadsDir.mkdirs();
						}

						File outFile = new File(downloadsDir, fileName);
						outputStream = new FileOutputStream(outFile);
						savedPath = outFile.getAbsolutePath();
					}

					// ────────────────────────────────────────────────
					// STEP 4: Read bytes in a loop — exactly like
					// your original directDownload() method
					// ────────────────────────────────────────────────
					inputStream = connection.getInputStream();
					byte[] buffer = new byte[4096];
					long totalDownloaded = 0;
					int bytesRead;

					updateStatus("Downloading...");

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
						totalDownloaded += bytesRead;

						// Calculate percentage (only if we know total size)
						if (fileLength > 0) {
							final int percent = (int) (totalDownloaded * 100 / fileLength);
							updateProgress(percent);
						}
					}

					outputStream.flush();

					// ────────────────────────────────────────────────
					// STEP 5: Done! Update the UI
					// ────────────────────────────────────────────────
					updateProgress(100);
					final String finalPath = savedPath;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							textStatus.setText("Download complete!\n"
									+ finalPath);
							buttonDownload.setEnabled(true);
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
					postError("Error: " + e.getMessage());

				} finally {
					// ── Clean up resources ──────────────────────────
					try {
						if (inputStream != null)
							inputStream.close();
					} catch (Exception ignored) {
					}
					try {
						if (outputStream != null)
							outputStream.close();
					} catch (Exception ignored) {
					}
					if (connection != null)
						connection.disconnect();
					isDownloading = false;
				}
			}
		}).start(); // <── don't forget to actually start the thread!
	}

	// =================================================================
	// HELPER: Extract a file name from a URL.
	// "https://example.com/path/file.zip?token=abc" → "file.zip"
	// If we can't figure it out, fall back to "download".
	// =================================================================
	private String extractFileName(String urlString) {
		try {
			// Remove query parameters
			String path = urlString;
			int queryIndex = path.indexOf('?');
			if (queryIndex != -1) {
				path = path.substring(0, queryIndex);
			}

			// Remove fragment
			int fragIndex = path.indexOf('#');
			if (fragIndex != -1) {
				path = path.substring(0, fragIndex);
			}

			// Get last path segment
			int lastSlash = path.lastIndexOf('/');
			if (lastSlash != -1 && lastSlash < path.length() - 1) {
				String name = path.substring(lastSlash + 1);
				// URL-decode common sequences
				name = java.net.URLDecoder.decode(name, "UTF-8");
				if (!name.isEmpty()) {
					return name;
				}
			}
		} catch (Exception ignored) {
		}

		return "download";
	}

	// =================================================================
	// HELPER: Guess MIME type from file extension.
	// Used by MediaStore so the system indexes the file correctly.
	// =================================================================
	private String getMimeType(String fileName) {
		String ext = "";
		int dot = fileName.lastIndexOf('.');
		if (dot != -1) {
			ext = fileName.substring(dot + 1).toLowerCase();
		}
		String mime = MimeTypeMap.getSingleton()
				.getMimeTypeFromExtension(ext);
		return (mime != null) ? mime : "application/octet-stream";
	}

	// =================================================================
	// HELPER: Post progress update to the UI thread.
	// Runs on the UI thread because only the main thread can
	// touch Android Views (ProgressBar, TextView, etc.).
	// =================================================================
	private void updateProgress(final int percent) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progressBar.setProgress(percent);
				textPercent.setText(percent + "%");
			}
		});
	}

	// =================================================================
	// HELPER: Post a status message to the UI thread.
	// =================================================================
	private void updateStatus(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				textStatus.setText(message);
			}
		});
	}

	// =================================================================
	// HELPER: Post an error message and re-enable the button.
	// =================================================================
	private void postError(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				textStatus.setText(message);
				progressBar.setVisibility(View.GONE);
				textPercent.setVisibility(View.GONE);
				buttonDownload.setEnabled(true);
			}
		});
	}
}
