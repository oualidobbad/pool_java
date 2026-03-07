import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class Main {

    // Detect if URL needs yt-dlp or direct download
    static boolean isComplexURL(String url) {
        return url.contains("youtube.com") || 
               url.contains("youtu.be") ||
               url.contains("instagram.com") ||
               url.contains("twitter.com") ||
               url.contains("facebook.com") ||
               url.contains("tiktok.com");
    }

    // Direct download (your Level 2 code)
    static void directDownload(String url) throws Exception {
        int indexLastSlash = url.lastIndexOf('/');
        String nameFile = url.substring(indexLastSlash + 1);

        URI uri = URI.create(url);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        try (InputStream in = conn.getInputStream();
             OutputStream outStream = new FileOutputStream(nameFile)) {

            int bytesRead;
            byte[] buffer = new byte[4096];
            long totalDownloaded = 0;

            while ((bytesRead = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
                totalDownloaded += bytesRead;
                double percentage = (totalDownloaded * 100) / (double) conn.getContentLength();
                int filled = (int) (percentage / 100 * 20);
                int empty = 20 - filled;
                String bar = "█".repeat(filled) + "░".repeat(empty);
                System.out.print("\rDownloading... [" + bar + "] " + (int) percentage + "%");
            }
        }

        System.out.println("\nDownload complete! -> " + nameFile);
        conn.disconnect();
    }

    // yt-dlp download (Level 3)
    static void ytDlpDownload(String url) throws Exception {
        System.out.println("Detected complex URL, using yt-dlp...");

        ProcessBuilder pb = new ProcessBuilder("sh", "yt-dlp/yt-dlp.sh", url);
        pb.redirectErrorStream(true);

        Process process = pb.start();

        // Read and show yt-dlp output
        InputStream in = process.getInputStream();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }

        process.waitFor();
        System.out.println("Download complete!");
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("-> ");
            String line = scanner.nextLine();

            // Detect URL type and choose download method
            if (isComplexURL(line)) {
                ytDlpDownload(line);
            } else {
                directDownload(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}