import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ReadInputUsrFile {

	private Map<String, String> map = new HashMap<>();
	String file = "Signature.txt";
	private boolean isPArseSignature = false;
	private OutputStream result;
	private int signatureMaxLen = 0;

	public ReadInputUsrFile() throws IOException{
		result = new FileOutputStream("result.txt") ;
	}

	public void parseSignature() throws IOException, IndexOutOfBoundsException 
	{
		if (isPArseSignature)
			return ;
		BufferedReader fileReader = new BufferedReader(new FileReader(file));
		String line;
		String lineSpl[];
		while ((line = fileReader.readLine()) != null) {
			lineSpl = line.split(",");

			if (lineSpl[1].length() > signatureMaxLen)
				signatureMaxLen = lineSpl[1].length();
			map.put(lineSpl[1].trim(), lineSpl[0].trim());
		}
		isPArseSignature = true;
	}

	public void typeSignature(String fileUsrInput) throws Exception {

		InputStream in = new FileInputStream(fileUsrInput);
		byte[] buffer;
		String sig;

		parseSignature();
		buffer = new byte[signatureMaxLen];
		in.read(buffer);
		sig = null;

		for (Map.Entry<String, String> entry : map.entrySet() )
		{
			String [] key = entry.getKey().split(" ");
			boolean isFound = true;

			for (int i = 0; i < key.length; i++) {
				if (!key[i].equals(String.format("%02X ", buffer[i]).trim())){
					isFound = false;
					break ;
				}
			}
			if (isFound)
			{
				sig = entry.getValue();
				break ;
			}
		}
		if (sig == null)
			throw new KeyNotFound("UNDEFINED");
		result.write(sig.getBytes());
		result.write("\n".getBytes());
		System.out.println("PROCESSED");
	}
}

