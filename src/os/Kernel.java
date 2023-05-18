package os;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class Kernel {
	/*1. ‘Kernel’:
	/*Functions:
1. readFromDisk(String filename); Read the data of any file from the disk.
2.writeToDisk(String text, String filename); Write text output to a file in the disk.
3. print(Object obj); Print data on the screen. //integer or string or anything
4. input(); Take text input from the user.
5. ??: Reading data from memory.
6. ??: Writing data to memory.
//not sure of 5,6 */
    public static String readFromDisk(String filename) {
    try {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

   public static void writeToDisk(String text, String filename) {
    try {
        FileWriter fw = new FileWriter(filename, true);
        fw.write(text + "\n"); // add newline character
        fw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public static void print(Object obj) {
		
    }

    public static String input() {
        return null;
    }

	public static void main(String[] args) {
		
}

	private static void tryWriteToDisk() {
		String filename = "output.txt";
		String text = "third line";
		writeToDisk(text, filename);
	}

	private static void tryReadFromDisk() {
		String filename = "Program_1.txt";
		String contents = readFromDisk(filename);
		if (contents != null) {
		    System.out.println("Contents of " + filename + ":");
		    System.out.println(contents);
		} else {
		    System.out.println("Failed to read " + filename);
		}
	}

}
