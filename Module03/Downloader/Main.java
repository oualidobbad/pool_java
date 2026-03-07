import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class Main {

    static boolean isComplexURL(String url) {
        return url.contains("youtube.com") || 
               url.contains("youtu.be") ||
               url.contains("instagram.com") ||
               url.contains("twitter.com") ||
               url.contains("facebook.com") ||
               url.contains("tiktok.com");
    }
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
                System.out.print("\r" + (int) percentage + "%");
            }
        }

        System.out.println("\nDownload complete! -> " + nameFile);
        conn.disconnect();
    }

    static void ytDlpDownload(String url) throws Exception {

        ProcessBuilder pb = new ProcessBuilder("yt-dlp", url);
        Process process = pb.start();

        InputStream in = process.getInputStream();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            System.out.print("\r" + scanner.nextLine());
        }

        process.waitFor();
        System.out.println("\nDownload complete!");
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("-> ");
            String line = scanner.nextLine();

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