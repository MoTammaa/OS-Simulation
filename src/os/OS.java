import java.util.LinkedList;
import java.util.Queue;

public class OS {
    private static Queue<Process> readyQueue;
    private static Queue<Process> blockedQueue;

    private static OutputMutex outpMutex;
    private static InputMutex inpMutex;
    private static FileMutex filMutex;

    

    public OS(Queue<Process> readyQueue, Queue<Process> blockedQueue) {
        this.readyQueue = readyQueue;
        this.blockedQueue = blockedQueue;
    }

    
    public OS(){
        this.readyQueue = new LinkedList<Process>();
        this.blockedQueue = new LinkedList<Process>();
         outpMutex = new OutputMutex();
         inpMutex = new InputMutex();
         filMutex = new FileMutex();

        
        
       
    }


    

 
    public static void addToReadyQueue(Process process) {
        readyQueue.add(process);
    }
    
    public static void addToBlockedQueue(Process process) {
        blockedQueue.add(process);
    }

    public static void removeFromReadyQueue(Process process) {
        readyQueue.remove(process);
    }

    public static void removeFromBlockedQueue(Process process) {
        blockedQueue.remove(process);
    }



    public static void main(String[]args){
        //Test Mutex is updated correctly 
        OS os = new OS();
        PCB pcb1 = new PCB(1, "null", 1,  new int []{1});
        PCB pcb2 = new PCB(2, "null", 2,  new int []{2});
        PCB pcb3 = new PCB(3, "null", 3,  new int []{3});
        Process p1 = new Process(pcb1);
        Process p2 = new Process(pcb2);
        Process p3 = new Process(pcb3);

        p1.semWait(outpMutex);
        p2.semWait(inpMutex);
        p3.semWait(filMutex);

        // System.out.println("First semwait with no collision");
        // System.out.println("Output Mutex");
        // outpMutex.printMutex();
        // System.out.println("Input Mutex");
        // inpMutex.printMutex();
        // System.out.println("File Mutex");
        // filMutex.printMutex();



        p1.semWait(inpMutex);
        p2.semWait(filMutex);
        p3.semWait(outpMutex);



        // System.out.println("First semwait with after collision");
        // System.out.println("Output Mutex");
        // outpMutex.printMutex();
        // System.out.println("Input Mutex");
        // inpMutex.printMutex();
        // System.out.println("File Mutex");
        // filMutex.printMutex();

        // System.out.println("The General Blocked Queue: ");
        // System.out.println("Blocked Queue: " + blockedQueue);

        // System.out.println("Releasing the Output Mutex first time : ");
         p1.semSignal(outpMutex);
        // System.out.println("Output Mutex");
        // outpMutex.printMutex();
        // System.out.println("Input Mutex");
        // inpMutex.printMutex();
        // System.out.println("File Mutex");
        // filMutex.printMutex();

        // System.out.println("The General Blocked Queue: ");
        // System.out.println("Blocked Queue: " + blockedQueue);


        System.out.println("Releasing the Output Mutex second time : ");
        p1.semSignal(outpMutex);
        System.out.println("Output Mutex");
       outpMutex.printMutex();
        System.out.println("Input Mutex");
        inpMutex.printMutex();
        System.out.println("File Mutex");
        filMutex.printMutex();



    }

}
