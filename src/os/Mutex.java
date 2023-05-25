package os;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Mutex{

   

    private boolean islocked;
    private int processID;




    public int getProcessID() {
        return processID;
    }
    public void setProcessID(int p) {
        processID = p;
    }
  



   abstract public  Queue<Integer> getBlockedQueue() ;
    abstract public  void setBlockedQueue(Queue<Integer> blockedQueue) ;
    public boolean isIslocked() {
        return islocked;
    }
    public void setIslocked(boolean islocked) {
        this.islocked = islocked;
    }
    public Mutex() {
        //why there is error here
        // if(this.getBlockedQueue() == null){
        //     this.setBlockedQueue(  new LinkedList<Integer>());
        // }
        
        this.islocked = false;
        this.processID = -1;
    }

    public void semWait(int pid){
        if(  islocked ){
            this.getBlockedQueue().add(pid);
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
            if(this.getBlockedQueue().isEmpty()){
                islocked = false;
                processID = -1;
            }
            else{
                processID = this.getBlockedQueue().poll();
                OS.removeFromBlockedQueue(processID);
                this.getBlockedQueue().remove(processID);
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
        System.out.println("Mutex blocked queue: " + this.getBlockedQueue());
        if(processID == -1){
            System.out.println("Mutex process: " + "null");
            return;   

        }
        System.out.println("Mutex process: " + this.processID);   

    }
    



    
       
}
