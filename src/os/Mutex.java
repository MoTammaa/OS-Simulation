package os;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Mutex{

    private Queue<Integer> blockedQueue;

    private boolean islocked;
    private int processID;




    public int getProcessID() {
        return processID;
    }
    public void setProcessID(int p) {
        processID = p;
    }
  



    public Queue<Integer> getBlockedQueue() {
        return blockedQueue;
    }
    public void setBlockedQueue(Queue<Integer> blockedQueue) {
        this.blockedQueue = blockedQueue;
    }
    public boolean isIslocked() {
        return islocked;
    }
    public void setIslocked(boolean islocked) {
        this.islocked = islocked;
    }
    public Mutex() {
        //why there is error here
        this.blockedQueue = new LinkedList<Integer>();
        this.islocked = false;
        this.processID = -1;
    }

    public void semWait(int pid){
        if(islocked){
            blockedQueue.add(pid);
            OS.addToBlockedQueue(pid);
            
        }
        else{
            islocked = true;
            processID = pid;
        }
        //logic todo
    }
    public void semSignal(int pid){
        if(processID == pid){
            if(blockedQueue.isEmpty()){
                islocked = false;
                processID = -1;
            }
            else{
                processID = blockedQueue.poll();
                OS.removeFromBlockedQueue(processID);
                changeProcessState(processID);
                OS.addToReadyQueue(processID);
            }
        }
        //logic todo
    }
    private void changeProcessState(int processID2) {
        Object [][] memory = MemoryManager.memory;
        if(memory[1][1] != null ){
            Kernel.writeToMemory((String)memory[1][0], State.BLOCKED, 0, 19);
        }
        else{
            Kernel.writeToMemory((String)memory[21][0], State.BLOCKED, 20, 39);
        }

    }
    public  void printMutex(){

        System.out.println("Mutex is locked: " + this.islocked);
        System.out.println("Mutex blocked queue: " + this.blockedQueue);
        if(processID == -1){
            System.out.println("Mutex process: " + "null");
            return;   

        }
        System.out.println("Mutex process: " + this.processID);   

    }
    
public static void main(String[] args) {
    Mutex mutex = new InputMutex();
   
    mutex.printMutex();
    System.out.println();
    mutex.semWait(1);
    mutex.printMutex();
    System.out.println();
    mutex.semWait(2);
    mutex.printMutex();
    System.out.println();
    mutex.semWait(3);
    mutex.printMutex();
    System.out.println();
    mutex.semSignal(1);
    mutex.printMutex();
    System.out.println();
    mutex.semSignal(2);
    mutex.printMutex();
    System.out.println();
    mutex.semSignal(3);
    mutex.printMutex();
}


    
       
}