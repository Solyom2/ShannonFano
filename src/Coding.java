import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Coding {

    public static void main(String[] args) throws IOException {

        File file = new File("raw.txt");
        File codeAbcFile = new File("codeabc.txt");
        Scanner sc = new Scanner(codeAbcFile);
        String[] codeabc = new String[1];

        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            codeabc = line.split(",");
        }

        ArrayList<CodeCharacter> characters = new ArrayList<>();
        int codeLength = 0;

        sc = new Scanner(file);
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            codeLength += line.length();

            for(int i = 0; i < line.length(); i++) {
                char c = Character.toLowerCase(line.charAt(i));
                boolean existing = false;

                for(CodeCharacter codec : characters) {
                    if(codec.getCharacter() == c) {
                        codec.increaseOccurances();
                        existing = true;
                        break;
                    }
                }
                if(!existing) {
                    CodeCharacter codeCharacter = new CodeCharacter(c);
                    characters.add(codeCharacter);
                }
            }

        }

        for(CodeCharacter codec : characters) {
            codec.calculateFrequency(codeLength);
        }

        Collections.sort(characters, Collections.reverseOrder());

        characters.get(0).setXvalue(0);
        for(int i = 1; i < characters.size(); i++) {
            double xval = 0;
            for(int j = 0; j < i; j++) {
                xval += characters.get(j).getFrequency();
            }
            characters.get(i).setXvalue(xval);
        }

        double intervalLength = 1.0/codeabc.length;
        calculateCodewords(characters, codeabc, 0, 1, intervalLength);

        for(CodeCharacter c : characters) {
            System.out.println(c);
        }

        double enthropy = 0;
        double avgCodewordLength = 0;
        double effiency = 0;
        for(CodeCharacter cc : characters) {
            enthropy += -1 * cc.getFrequency() *(log2(cc.getFrequency()));
            avgCodewordLength += cc.getFrequency() * cc.getCodeword().length();
        }
        effiency = enthropy/(avgCodewordLength * log2(codeabc.length));

        System.out.println("Enthropy: " + enthropy);
        System.out.println("Average codeword legth: " + avgCodewordLength);
        System.out.println("Encoding effiency: " + effiency);


        sc = new Scanner(file);
        FileWriter writer = new FileWriter("encoded.txt");
        while (sc.hasNextLine()) {
           String line = sc.nextLine();
           line = line.toLowerCase();
           for(int i = 0; i < line.length(); i++) {
               char rawchar = line.charAt(i);
               for(CodeCharacter cc : characters) {
                   if(cc.getCharacter() == rawchar) {
                       writer.write(cc.getCodeword());
                       break;
                   }
               }
           }
           writer.write("\r\n");
        }
        writer.close();

    }

    public static int calculateCodewords(ArrayList<CodeCharacter> characters, String[] codeabc, double intervalStart, double intervalEnd, double intervalLength) {
        int charactersInInterval = 0;
        for(double i = intervalStart, intervalNo = 0; i < intervalEnd && intervalNo < codeabc.length; i += intervalLength, intervalNo++) {
            charactersInInterval = 0;
            int auxChar = 0;

            for(int j = 0; j < characters.size(); j++) {
                auxChar = j;
                if(characters.get(j).getXvalue() >= i && characters.get(j).getXvalue() < i + intervalLength) {
                    charactersInInterval++;
                    characters.get(j).setCodeword(characters.get(j).getCodeword() + codeabc[(int)intervalNo]);
                }
                else if(characters.get(j).getXvalue() < i || characters.get(j).getXvalue() >= i + intervalLength || j == characters.size() - 1) {
                    if(charactersInInterval > 1) {
                        double newIntervalLength = intervalLength/codeabc.length;
                        charactersInInterval = calculateCodewords(characters, codeabc, i, i+ intervalLength, newIntervalLength);
                        break;
                    }
                    else if (charactersInInterval == 1) {
                        break;
                    }
                }
            }
            if(charactersInInterval > 1 && auxChar == characters.size() - 1) {
                double newIntervalLength = intervalLength/codeabc.length;
                calculateCodewords(characters, codeabc, i, i+ intervalLength, newIntervalLength);
            }
        }
        return charactersInInterval;
    }

    public static double log2(double x) {
        return (Math.log(x) / Math.log(2) + 1e-10);
    }

}
