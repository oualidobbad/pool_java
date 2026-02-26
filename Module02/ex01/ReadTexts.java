package ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashSet;

public class ReadTexts {
    private HashSet<String> set = new HashSet<>();

    public void ReadTexts(String textA, String textB) throws Exception{
        BufferedReader fileA = new BufferedReader(new FileReader(textA));
        BufferedReader fileB = new BufferedReader(new FileReader(textB));
        Reader file = new FileReader(textA);

        System.out.println((char)file.read());

        
    }   
}
