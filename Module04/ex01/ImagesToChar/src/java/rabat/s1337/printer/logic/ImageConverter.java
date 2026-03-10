package rabat.s1337.printer.logic;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream; 

public class ImageConverter {
	private final Color WHITE_COLOR = new Color(200, 200, 200);
	private char charOfWhite;
	private char charOfBlack;

	private InputStream pathOfImage;

	public ImageConverter(InputStream pathOfImage, char charOfWhite, char charOfBlack) {
		this.pathOfImage = pathOfImage;
		this.charOfWhite = charOfWhite;
		this.charOfBlack = charOfBlack;
	}

	public void converter() throws Exception {
		BufferedImage image = ImageIO.read(pathOfImage);
		int height = image.getHeight();
		int width = image.getWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int color = image.getRGB(x, y);

				if (color >= WHITE_COLOR.getRGB())
					System.out.print(charOfWhite);
				else 
					System.out.print(charOfBlack);
			}
			System.out.println();
		}
	}

}
