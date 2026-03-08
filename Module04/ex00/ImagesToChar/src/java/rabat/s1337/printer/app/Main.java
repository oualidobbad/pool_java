package rabat.s1337.printer.app;
import rabat.s1337.printer.logic.ImageConverter;
// "/home/oobbad/Documents/pool_java/Module04/ex00/ImagesToChar/src/resources/it_black.bmp"
public class Main {
	public static void main(String[] args) {
		try {
			if (args.length != 3 || (args[0].length() != 1 || args[1].length() != 1))
				throw new IllegalArgumentException("args must {[char Of White Color] [char Of Black Color] [PathName of Image.bmp]}");
			// h
			ImageConverter imageConverter = new ImageConverter(args[2], args[0].charAt(0), args[1].charAt(0));
			imageConverter.converter();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
}
