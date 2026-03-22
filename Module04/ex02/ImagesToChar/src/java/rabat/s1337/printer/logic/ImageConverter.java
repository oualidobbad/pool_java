package rabat.s1337.printer.logic;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import com.diogonunes.jcdp.color.*;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;

public class ImageConverter {
	private final Color WHITE_COLOR = new Color(200, 200, 200);
	private Color colorOfWhite;
	private Color colorOfBlack;

	private InputStream pathOfImage;

	public ImageConverter(InputStream pathOfImage,  Color colorOfWhite, Color colorOfBlack) {
		this.pathOfImage = pathOfImage;
		this.colorOfBlack = colorOfBlack;
		this.colorOfWhite = colorOfWhite;
	}

	public void converter() throws Exception {
		BufferedImage image = ImageIO.read(pathOfImage);
		ColoredPrinter cp = new ColoredPrinter();

		int height = image.getHeight();
		int width = image.getWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int color = image.getRGB(x, y);

				if (color >= WHITE_COLOR.getRGB())
					cp.print(" ", Attribute.NONE, FColor.NONE, ColorMapping.toBColor(colorOfWhite));
				else
					cp.print(" ", Attribute.NONE, FColor.NONE, ColorMapping.toBColor(colorOfBlack));
			}
			System.out.println();
		}
	}

}
