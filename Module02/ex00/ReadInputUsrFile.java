import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadInputUsrFile {

	public void typeSignature(String fileUsrInput) throws Exception {

		InputStream in = new FileInputStream(fileUsrInput);

		byte[] buffer = new byte[8];
		int bytesRead = in.read(buffer);

		String eightByte = "";
		for (int i = 0; i < bytesRead; i++) {
			eightByte += String.format("%02X ", buffer[i]);
		}
		System.out.println(eightByte);
	}
}

