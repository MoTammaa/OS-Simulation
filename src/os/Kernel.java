package os;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Kernel {
	/*1. ‘Kernel’:
	/*Functions:
1. readFromDisk(String filename); Read the data of any file from the disk.                               DONE
2.writeToDisk(String text, String filename); Write text output to a file in the disk.            		 DONE
3. print(Object obj); Print data on the screen. //integer or string or anything else.                    DONE
4. input(); Take text input from the user. 										                         DONE
5. ??: Reading data from memory. 											                             DONE
6. ??: Writing data to memory. 											                                 DONE
7. Ask the user for input in a nice way and input it.					                                 DONE
8. Print from To. 																						 DONE
//not sure of 5,6 */
    public static String readFromDisk(String filename) {
    
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
		System.out.println(obj);
    }

	public static String askTheUserForInput() {
		print("Please enter a value");
		return input();
	}

	public static String input() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter input: ");
    String input = scanner.nextLine();
    scanner.close();
    return input;
}
	public static void printFromTo(int from, int to) {
		for (int i = from; i <= to; i++) {
			System.out.print(i+" ");
		}
		System.out.println();
		System.out.println();
		System.out.println();
}
	public static Object readFromMemory(String varName, int start,int end) {
		for (int i = start; i < end; i++) {
			if (MemoryManager.memory[i][0].equals(varName)) {
				return MemoryManager.memory[i][1];
			}
		}
		return null;
	}
	public static void writeToMemory(String varName, Object value,int start,int end) {
		for (int i = start; i < end; i++) {
			if (MemoryManager.memory[i][0] == null || MemoryManager.memory[i][0].equals(varName)/* overwrite a variable */) {
				// if(i <= start+5 && i <= start+7){
				// 	if(!varName.startsWith("Instruction")){
				// 		continue;
				// 	}
				// }
				// if(!varName.equals("processID") &&  !varName.equals("programCounter") && !varName.equals("processState")
				// 	&& !varName.equals("startMemoryBoundary") && !varName.equals("endMemoryBoundary") && !varName.startsWith("instruction")){
				// 		//it's a variable
				// 	}
				MemoryManager.memory[i][0] = varName;
				MemoryManager.memory[i][1] = value;
				return;
			}
		}
		throw new RuntimeException("Memory is full");
	}

	public static void semWait(Mutex m, int pid){
		m.semWait(pid);

	}
	public static void semSignal(Mutex m, int pid){
		m.semSignal(pid);

	}
	public static void main(String[] args) {
  System.out.println(readFromDisk("Program_1.txt"));
}


	

}
