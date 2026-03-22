package rabat.s1337.printer.app;
import rabat.s1337.printer.logic.ImageConverter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.JCommander;
import java.awt.Color;
import java.io.FileInputStream;

public class Main {

	@Parameters(separators = "=")
	static class Argument{
		@Parameter(names = "--white", required = true, converter = StringToColor.class, description = "Color to reprsent white color")
		public Color white;
		@Parameter(names = "--black", converter = StringToColor.class, required = true, description = "Color of represent black color")
		public Color black;

	}

	public static void main(String[] args) {
		try {
			Argument arg = new Argument();
			JCommander jc = new JCommander(arg);
			jc.parse(args);

//			System.out.println(arg.white);
//			System.out.println(arg.black);
			 ImageConverter imageConverter = new ImageConverter(new FileInputStream("/home/oualid/Documents/pool_java/Module04/ex02/ImagesToChar/src/resources/it_black.bmp"), arg.white, arg.black);
			 imageConverter.converter();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}
}
