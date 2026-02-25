import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.StringConcatException;
import java.util.HashMap;
import java.util.Map;

public class ReadInputUsrFile {

	private Map<String, String> map = new HashMap<>();
	String file = "Signature.txt";
	private boolean isPArseSignature = false;
	private OutputStream result;

	public ReadInputUsrFile() throws IOException{
		result = new FileOutputStream("result.txt") ;
	}

	public void parseSignature() throws IOException, IndexOutOfBoundsException {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String line;
			String lineSpl[];
			while ((line = fileReader.readLine()) != null) {
				lineSpl = line.split(",");
				map.put(lineSpl[1].trim(), lineSpl[0].trim());
			}
	}

	public void typeSignature(String fileUsrInput) throws Exception {

		InputStream in = new FileInputStream(fileUsrInput);
		byte[] buffer = new byte[8];
		int bytesRead = in.read(buffer);
		String eightByte = "";

		if (!isPArseSignature)
		{
			parseSignature();
			isPArseSignature = true;
		}
		for (int i = 0; i < bytesRead; i++) {
			eightByte += String.format("%02X ", buffer[i]);
		}
		System.out.println(eightByte);
		String value = map.get(eightByte.trim());
		if (value == null)
			throw new KeyNotFound("UNDEFINED");
		result.write(value.getBytes());
		result.write("\n".getBytes());
		System.out.println("PROCESSED");
	}
}

