import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Queue;


public class DownloaderTasks extends Thread {
	private static final Queue<URL> queue = Main.parseFileUrls();
	private static final Object LOCK = new Object();
	static int i = 0;

	@Override
	public void run(){
		URL url;
		String outPutFileName;

		int indx;
		while (true) {
			synchronized(LOCK){
				if (queue.isEmpty())
					return ;
				url = queue.poll();
				outPutFileName = url.toString().substring(url.toString().lastIndexOf('/') + 1);
				indx = ++i;
			}
			try(InputStream in = url.openStream();
				OutputStream outFile = new FileOutputStream(outPutFileName))
				{
					byte[] buffer = new byte[4096];
					int bytRead;
					System.out.println(getName() + " start download file number " + indx);
					while ((bytRead = in.read(buffer)) != -1) {
						outFile.write(buffer, 0, bytRead);
					}
					System.out.println(getName() + " finish download file number " + indx);
				}
				catch (Exception e)
				{
					System.err.println(e.getMessage());
				}
		}
	}
}
