package ex01;

import java.util.*;

public class Program {
    public static void main(String [] args)
    {
        ReadTexts rd = new ReadTexts();
        try {
            rd.ReadTexts(args[0], args[1]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
