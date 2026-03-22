package app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;
import java.awt.Color;

public class Main {
    @Parameters(separators = "=")
    static class Arguments {
        @Parameter(names = "--white", required = true, converter = StringToColor.class)
        private Color white;
        @Parameter(names = "--black", required = true, converter = StringToColor.class)
        private Color black;
    }
    public static void main(String[] args) {

        Arguments arguments = new Arguments();
        JCommander jc = new JCommander(arguments);
        try{
            jc.parse(args);
            System.out.println(arguments.white);
            System.out.println(arguments.black);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
