import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine();

            URI uri = URI.create(line);
			HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
			conn.setRequestMethod("GET");
            try (InputStream in = uri.toURL().openStream();
				OutputStream outStream = new FileOutputStream("here.mp4")) 
				{
					int bytesRead;
					byte buffer[] = new byte[4096];

					while ((bytesRead = in.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}
				}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
}