package os;

import java.io.*;
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
			throw new RuntimeException("Filename error! Check spelling and extension.");

			
		}
}
   public static void writeToDisk(String text, String filename) {
		if(!filename.endsWith(".txt")) filename =  filename.concat(".txt");
    try {
        FileWriter fw = new FileWriter(filename, true);
        fw.write(text + "\n"); // add newline character
        fw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static void overrideDisk(String text, String filename) {
		if(!filename.endsWith(".txt")) filename =  filename.concat(".txt");
    try {
        FileWriter fw = new FileWriter(filename);
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
    System.out.println("Enter input: ");
    String input = scanner.nextLine();
    //scanner.close();
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
			if (MemoryManager.memory[i][0] == null || MemoryManager.memory[i][0].equals(varName)/* overwrite a variable */
					|| MemoryManager.memory[i][0].equals("null")) {
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
	public static void clearOutputStream(){
		try {
			new FileWriter(new File("OutputStream.txt"), false).close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void out_println(Object obj) {
		try {
			File file = new File("OutputStream.txt");
			FileWriter fr = new FileWriter(file, true);
			PrintWriter printWriter = new PrintWriter(fr);
			StringBuilder stringBuilder = new StringBuilder(obj.toString());
			stringBuilder.append("\n");
			printWriter.write(stringBuilder.toString());
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
//		System.out.println(obj);
	}
	
public static void showOutput() {
	/*• Queues should be printed after every scheduling event, i.e. when a process
is chosen, blocked, or finished.
• Which process is currently executing.
• The instruction that is currently executing
• Time slice is subject to change, i.e. you might be asked to change it to x
instructions per time slice.
• Order in which the processes are scheduled are subject to change.
• The timings in which processes arrive are subject to change
• The memory shown every clock cycle in a human readable format.
• The ID of any process whenever it is swapped in or out of disk.
• The format of the memory stored on Disk. */
Kernel.out_println("***********************************");
Kernel.out_println("*          Queues:                *");
Kernel.out_println("*                                 *");
Kernel.out_println("* Ready Queue:                    *");
Kernel.out_println("* " + OS.getReadyQueue().toString() + " *");
Kernel.out_println("*                                 *");
Kernel.out_println("* Blocked Queue:                  *");
Kernel.out_println("* " + OS.getBlockedQueue().toString() + " *");
Kernel.out_println("*                                 *");
Kernel.out_println("* Queues of Mutexes:              *");
Kernel.out_println("*                                 *");
Kernel.out_println("* FileMutex Blocked Queue :       *");
Kernel.out_println("* " + new FileMutex().getBlockedQueue().toString() + " *");
Kernel.out_println("*                                 *");
Kernel.out_println("* InputMutex Blocked Queue :      *");
Kernel.out_println("* " + new InputMutex().getBlockedQueue().toString() + " *");
Kernel.out_println("*                                 *");
Kernel.out_println("* OutputMutex Blocked Queue :     *");
Kernel.out_println("* " + new OutputMutex().getBlockedQueue().toString() + " *");
Kernel.out_println("*                                 *");
Kernel.out_println("* Process Timing Queue :          *");
Kernel.out_println("* " + Scheduler.processTiming.toString() + " *");
Kernel.out_println("*                                 *");
Kernel.out_println("\n* The Memory :                    *");
Kernel.out_println("*                                 *");
Interpreter.printMemory();
Kernel.out_println("*                                 *");
Kernel.out_println("\n* The Hard Disk :                 *");
Kernel.out_println("* " + readFromDisk("hardDisk.txt") + " *");
Kernel.out_println("***********************************");





	

}
}
