package os;

import java.util.ArrayList;

public class Process {
   private ProcessControlBlock pcb;


    public Process(ProcessControlBlock pcb) {
        this.pcb = pcb;
    }

    public ProcessControlBlock getPcb() {
        return pcb;
    }

    public void setPcb(ProcessControlBlock pcb) {
        this.pcb = pcb;
    }

 






    public String toString(){
        return pcb.getProcessID()+"";
    }




    public void executeNextInstruction() {
        int id = this.getPcb().getProcessID();
        int one = Integer.parseInt((String) MemoryManager.memory[0][1]);
         int pc = pcb.getProgramCounter();
        Instruction instruction = (Instruction) MemoryManager.memory[pc][1];
        if(id == one){
            instruction.execute(0 , 19);
        }
        else{
            instruction.execute(20 , 39);
        }

		
	}
	
	public boolean isFinished() {
		return true;
	}












    //Mutex Part start here



    public void semWait(Mutex mutex) {
        if (mutex.isIslocked()) {
            if(mutex.getProcessID()== this.getPcb().getProcessID()){
                return;
            }
            mutex.getBlockedQueue().add(this.getPcb().getProcessID());
            OS.addToBlockedQueue(this.getPcb().getProcessID());
            this.getPcb().setProcessState(State.BLOCKED);
        } else {
            mutex.setIslocked(true);
            mutex.setProcessID(this.getPcb().getProcessID());
          
        }
    }

    public void semSignal(Mutex mutex) {
        if (mutex.getBlockedQueue().isEmpty()) {
            mutex.setIslocked(false);
            mutex.setProcessID(-1);
            
        } else {
            int x = mutex.getBlockedQueue().remove();
            mutex.setProcessID(x);
            this.getPcb().setProcessState(State.BLOCKED);
            OS.removeFromBlockedQueue(x);
        }


      

    }




}
