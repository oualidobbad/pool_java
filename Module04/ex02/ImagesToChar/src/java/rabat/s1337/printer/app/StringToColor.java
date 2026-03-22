package rabat.s1337.printer.app;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.awt.Color;
import java.util.Map;

public class StringToColor implements IStringConverter<Color>{
    private Map<String, Color> colors = Map.ofEntries(
            Map.entry("white", Color.WHITE),
            Map.entry("black", Color.BLACK),
            Map.entry("red", Color.RED),
            Map.entry("green", Color.GREEN),
            Map.entry("blue", Color.BLUE),
            Map.entry("yellow", Color.YELLOW),
            Map.entry("cyan", Color.CYAN),
            Map.entry("magenta", Color.MAGENTA),
            Map.entry("gray", Color.GRAY),
            Map.entry("orange", Color.ORANGE),
            Map.entry("pink", Color.PINK),
            Map.entry("lightGray", Color.LIGHT_GRAY)
    );
	@Override
    public Color convert(String value){

        String key = value.trim().toLowerCase();
        Color color = colors.get(key);

        if (color == null){
            throw new ParameterException(value + ": Color not found");
        }
        return color;
    }
}
