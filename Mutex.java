import java.util.LinkedList;
import java.util.Queue;

public abstract class Mutex{

    private Queue<Process> blockedQueue;

    private boolean islocked;
    private Process Process;




    public Process getProcess() {
        return Process;
    }
    public void setProcess(Process p) {
        Process = p;
    }
  



    public Queue<Process> getBlockedQueue() {
        return blockedQueue;
    }
    public void setBlockedQueue(Queue<Process> blockedQueue) {
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
        this.blockedQueue = new LinkedList<Process>();
        this.islocked = false;
        this.Process = null;
    }
    

    public  void printMutex(){

        System.out.println("Mutex is locked: " + this.islocked);
        System.out.println("Mutex blocked queue: " + this.blockedQueue);
        if(Process == null){
            System.out.println("Mutex process: " + "null");
            return;   

        }
        System.out.println("Mutex process: " + this.Process.getPcb().getProcessID());   

    }


    
       
}