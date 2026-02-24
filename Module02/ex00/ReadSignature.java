import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadSignature {
	private Map<String, String> map = new HashMap<>();
	String file = "Signature.txt";


	public void parseSignature() throws IOException, IndexOutOfBoundsException {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			String line;
			String lineSpl[];
			while ((line = fileReader.readLine()) != null) {
				lineSpl = line.split(",");
				map.put(lineSpl[1].trim(), lineSpl[0].trim());
			}
	}
	public String getValue(String key) {
		return map.get(key);
	}
}
