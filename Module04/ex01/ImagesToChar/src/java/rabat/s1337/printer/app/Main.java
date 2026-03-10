package rabat.s1337.printer.app;
import rabat.s1337.printer.logic.ImageConverter;

public class Main {
	public static void main(String[] args) {
		try {
			if (args.length != 2 || (args[0].length() != 1 || args[1].length() != 1))
				throw new IllegalArgumentException("args must {[char Of White Color] [char Of Black Color] }");
			
			ImageConverter imageConverter = new ImageConverter(Main.class.getResourceAsStream("/resources/it_black.bmp"), args[0].charAt(0), args[1].charAt(0));
			imageConverter.converter();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
}
