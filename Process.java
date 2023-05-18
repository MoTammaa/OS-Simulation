import java.util.ArrayList;

public class Process {
   private PCB pcb;

  private  ArrayList<Mutex> mutexes;

    public Process(PCB pcb) {
        this.pcb = pcb;
        this.mutexes = new ArrayList<Mutex>();
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }

    public ArrayList<Mutex> getMutexes() {
        return mutexes;
    }

    public void setMutexes(ArrayList<Mutex> mutexes) {
        this.mutexes = mutexes;
    }







    public String toString(){
        return pcb.getProcessID()+"";
    }

















    //Mutex Part start here



    public void semWait(Mutex mutex) {
        if (mutex.isIslocked()) {
            mutex.getBlockedQueue().add(this);
            OS.addToBlockedQueue(this);
        } else {
            mutex.setIslocked(true);
            mutex.setProcess(this);
            this.getMutexes().add(mutex);
        }
    }

    public void semSignal(Mutex mutex) {
        if (mutex.getBlockedQueue().isEmpty()) {
            mutex.setIslocked(false);
            mutex.setProcess(null);
            
        } else {
            Process x = mutex.getBlockedQueue().remove();
            mutex.setProcess(x);
            x.getMutexes().add(mutex);
            OS.removeFromBlockedQueue(x);
        }


        this.getMutexes().remove(mutex);

    }




}
