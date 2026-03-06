
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            URI uri = URI.create("https://cdn.pixabay.com/video/2026/02/15/334716_large.mp4");

            try (InputStream in = uri.toURL().openStream();
				OutputStream outStream = new FileOutputStream("here.mp4")) 
				{
					byte buffer[] = new byte[4096];
					int bytesRead;
					while ((bytesRead = in.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}
				}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
}