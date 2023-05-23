package os;

import java.util.LinkedList;
import java.util.Queue;
//    static String [][] memory = new String[40][2]; 

public class OS {
    private static Queue<Integer> readyQueue;
    private static Queue<Integer> blockedQueue;

     static OutputMutex outpMutex;
     static InputMutex inpMutex;
     static FileMutex filMutex;

    

    public OS(Queue<Integer> readyQueue, Queue<Integer> blockedQueue) {
        this.readyQueue = readyQueue;
        this.blockedQueue = blockedQueue;
    }
    public static boolean isBlocked(int pid) {
    	return blockedQueue.contains(pid);
    }

    
    public OS(){
        this.readyQueue = new LinkedList<Integer>();
        this.blockedQueue = new LinkedList<Integer>();
         outpMutex = new OutputMutex();
         inpMutex = new InputMutex();
         filMutex = new FileMutex();

        
        
       
    }


    

 
    public static void addToReadyQueue(int processID) {
        readyQueue.add(processID);
    }
    
    public static void addToBlockedQueue(int processID) {
        blockedQueue.add(processID);
    }

    public static void removeFromReadyQueue(int processID) {
        readyQueue.remove(processID);
    }

    public static void removeFromBlockedQueue(int processID) {
        blockedQueue.remove(processID);
    }



    public static Queue<Integer> getReadyQueue() {
        return readyQueue;
    }


    public static void main(String[]args){
   
    



    OS os = new OS();

    Kernel kernel = new Kernel();

    MemoryManager memoryManager = new MemoryManager();
    Object [][] memory = memoryManager.memory;

    Interpeter interpeter = new Interpeter("Program_2.txt");
    interpeter = new Interpeter("Program_3.txt");
    //interpeter = new Interpeter("Program_1.txt");
  //  System.out.println("State of p1 alone : "+((String)memory[1][0])+" "+((State)memory[1][1]));

  //  System.out.println("First Check:");
   // System.out.println("Memory:"+memoryManager.memory);
  //  System.out.println("Ready Queue:"+readyQueue);
    //System.out.println("Ready Queue:"+readyQueue);
    
    // interpeter = new Interpeter("Program_2.txt");


    //  System.out.println("State of p1  : "+((String)memory[1][0])+" "+((State)memory[1][1]));
    //   System.out.println("State of p2 : "+((String)memory[21][0])+" "+((State)memory[21][1]));

    // interpeter = new Interpeter("Program_3.txt");
   // System.out.println(kernel.readFromDisk("hardDisk.txt"));
       
    
    





    }
}
