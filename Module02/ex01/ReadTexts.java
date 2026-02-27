package ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

public class ReadTexts {
    TreeSet <String> dictionary = new TreeSet<>();
    Vector <Integer> countInFileA = new Vector<>();
    Vector <Integer> countInFileB = new Vector<>();

    double similarity = 0.0;

    public void ReadTexts(String textA, String textB) throws Exception{

        BufferedReader fileA = new BufferedReader(new FileReader(textA));
        BufferedReader fileB = new BufferedReader(new FileReader(textB));
        HashMap <String, Integer> mapFileA = new HashMap<>();
        HashMap <String, Integer> mapFileB = new HashMap<>();


        String line;
        while ((line = fileA.readLine()) != null) {
            String [] words = line.split("[^A-Za-z]");
            for (int i = 0; i < words.length; i++)
                mapFileA.put(words[i], mapFileA.getOrDefault(words[i], 0) + 1);
        }
        while ((line = fileB.readLine()) != null) {
            String [] words = line.split("[^A-Za-z]");
            for (int i = 0; i < words.length; i++)
                mapFileB.put(words[i], mapFileB.getOrDefault(words[i], 0) + 1);
        }

        dictionary.addAll(mapFileA.keySet());
        dictionary.addAll(mapFileB.keySet());

        for (String word : dictionary){
            countInFileA.add(mapFileA.getOrDefault(word, 0));
            countInFileB.add(mapFileB.getOrDefault(word, 0));
        }

        fileA.close();
        fileB.close();

        calculateSumilarity();
        result();
    }

    private void calculateSumilarity(){
        Integer numerator = 0;
        double Denominator = 0.0;
        Integer a = 0;
        Integer b = 0;

        for (int i = 0; i < countInFileA.size(); i++){
            numerator += countInFileA.get(i) * countInFileB.get(i);
            a += countInFileA.get(i) * countInFileA.get(i);
            b += countInFileB.get(i) * countInFileB.get(i);
        }
        Denominator = Math.sqrt(a) * Math.sqrt(b);

        if (Denominator != 0)
            similarity += numerator / Denominator;
    }
    
    private void result() throws Exception {
        try (Writer dictionaryTxt = new FileWriter("dictionary.txt")) {
            for (String word : dictionary) {
                dictionaryTxt.write(word + ", ");
            }
        }

        System.out.println("similarity: " + String.format("%.2f", similarity));
    }

}
