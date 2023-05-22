package os;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Interpeter {
    public Interpeter() {

    }

    public ArrayList<String> readFile(String fileName) {
        BufferedReader br;
        try {
            br = new BufferedReader(new java.io.FileReader(fileName));
            ArrayList<String> lines = new ArrayList<String>();
            String line = br.readLine();

            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            return lines;
        } catch (IOException ie){
            System.out.println("Error reading file: " + ie.getMessage());
        }

        return null;
    }


}
