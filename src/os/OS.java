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
         Kernel.writeToDisk("", "hardDisk.txt");
        this.readyQueue = new LinkedList<Integer>();
        this.blockedQueue = new LinkedList<Integer>();
         outpMutex = new OutputMutex();
         inpMutex = new InputMutex();
         filMutex = new FileMutex();

        
        
       
    }


    

 
    public static Queue<Integer> getBlockedQueue() {
        return blockedQueue;
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
    Kernel.clearOutputStream();

    Object [][] memory = MemoryManager.memory;
    
    
    Scheduler sch = new Scheduler();
    sch.startSchedule();




    }
}
