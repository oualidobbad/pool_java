package rabat.s1337.printer.logic;
import java.awt.Color;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import com.diogonunes.jcdp.color.api.Ansi.BColor; // if you ever need backgrounds

public final class ColorMapping {
    private ColorMapping() {}

    public static BColor toBColor(Color c) {
        if (c == null) return BColor.NONE;

        if (c.equals(Color.BLACK))   return BColor.BLACK;
        if (c.equals(Color.BLUE))    return BColor.BLUE;
        if (c.equals(Color.CYAN))    return BColor.CYAN;
        if (c.equals(Color.DARK_GRAY)) return BColor.BLACK; // best fit
        if (c.equals(Color.GRAY))    return BColor.WHITE;   // best fit
        if (c.equals(Color.GREEN))   return BColor.GREEN;
        if (c.equals(Color.LIGHT_GRAY)) return BColor.WHITE;
        if (c.equals(Color.MAGENTA)) return BColor.MAGENTA;
        if (c.equals(Color.ORANGE))  return BColor.YELLOW;  // nearest ANSI
        if (c.equals(Color.PINK))    return BColor.MAGENTA; // nearest ANSI
        if (c.equals(Color.RED))     return BColor.RED;
        if (c.equals(Color.WHITE))   return BColor.WHITE;
        if (c.equals(Color.YELLOW))  return BColor.YELLOW;

        return BColor.BLACK;
    }
}