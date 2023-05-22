package os;

import java.util.LinkedList;
import java.util.Queue;
//    static String [][] memory = new String[40][2]; 

public class OS {
    private static Queue<Integer> readyQueue;
    private static Queue<Integer> blockedQueue;

    private static OutputMutex outpMutex;
    private static InputMutex inpMutex;
    private static FileMutex filMutex;

    

    public OS(Queue<Integer> readyQueue, Queue<Integer> blockedQueue) {
        this.readyQueue = readyQueue;
        this.blockedQueue = blockedQueue;
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



    public static void main(String[]args){
    //     //Test Mutex is updated correctly 
    //     OS os = new OS();
    //     PCB pcb1 = new PCB(1, "null", 1,  new int []{1});
    //     PCB pcb2 = new PCB(2, "null", 2,  new int []{2});
    //     PCB pcb3 = new PCB(3, "null", 3,  new int []{3});
    //     Process p1 = new Process(pcb1);
    //     Process p2 = new Process(pcb2);
    //     Process p3 = new Process(pcb3);

    //     p1.semWait(outpMutex);
    //     p2.semWait(inpMutex);
    //     p3.semWait(filMutex);

    //     // System.out.println("First semwait with no collision");
    //     // System.out.println("Output Mutex");
    //     // outpMutex.printMutex();
    //     // System.out.println("Input Mutex");
    //     // inpMutex.printMutex();
    //     // System.out.println("File Mutex");
    //     // filMutex.printMutex();



    //     p1.semWait(inpMutex);
    //     p2.semWait(filMutex);
    //     p3.semWait(outpMutex);



    //     // System.out.println("First semwait with after collision");
    //     // System.out.println("Output Mutex");
    //     // outpMutex.printMutex();
    //     // System.out.println("Input Mutex");
    //     // inpMutex.printMutex();
    //     // System.out.println("File Mutex");
    //     // filMutex.printMutex();

    //     // System.out.println("The General Blocked Queue: ");
    //     // System.out.println("Blocked Queue: " + blockedQueue);

    //     // System.out.println("Releasing the Output Mutex first time : ");
    //      p1.semSignal(outpMutex);
    //     // System.out.println("Output Mutex");
    //     // outpMutex.printMutex();
    //     // System.out.println("Input Mutex");
    //     // inpMutex.printMutex();
    //     // System.out.println("File Mutex");
    //     // filMutex.printMutex();

    //     // System.out.println("The General Blocked Queue: ");
    //     // System.out.println("Blocked Queue: " + blockedQueue);


    //     System.out.println("Releasing the Output Mutex second time : ");
    //     p1.semSignal(outpMutex);
    //     System.out.println("Output Mutex");
    //    outpMutex.printMutex();
    //     System.out.println("Input Mutex");
    //     inpMutex.printMutex();
    //     System.out.println("File Mutex");
    //     filMutex.printMutex();



    // }
    }

}
