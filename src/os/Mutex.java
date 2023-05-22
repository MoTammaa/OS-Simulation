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
    

    public  void printMutex(){

        System.out.println("Mutex is locked: " + this.islocked);
        System.out.println("Mutex blocked queue: " + this.blockedQueue);
        if(processID == -1){
            System.out.println("Mutex process: " + "null");
            return;   

        }
        System.out.println("Mutex process: " + this.processID);   

    }


    
       
}