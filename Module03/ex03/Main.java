
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Queue;
import java.net.URL;

public class Main {
	static Queue<URL> parseFileUrls() {
		
		Queue<URL> queue = new ArrayDeque<>();
		try (
			BufferedReader in = new BufferedReader(new FileReader("files_urls.txt")))
			{
			String line;
			while ((line = in.readLine()) != null) {
				queue.add(URI.create(line).toURL());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return queue;
	}

	public static void main(String[] args) {
		try {
			if (args.length == 0 || !args[0].split("=")[0].equals("--threadsCount"))
				throw new IllegalArgumentException("args must be {--threadsCount=nb}");

			int N = Integer.parseInt(args[0].split("=")[1].trim());
			Thread [] threads = new Thread[N];
			for (int i = 0; i < N; i++){
				threads[i] = new DownloaderTasks();
				threads[i].start();
			}
			for (int i = 0; i < N; i++){
				threads[i].join();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}