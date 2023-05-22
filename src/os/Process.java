package os;

import java.util.ArrayList;

public class Process implements Comparable<Process>{
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

        int one =(int) MemoryManager.memory[0][1];
        int pc = pcb.getProgramCounter();
        pcb.setProgramCounter(pc+1);

        Instruction instruction = InstructionParser.parseInstruction((String) MemoryManager.memory[pc][1]);
         
        if(id == one){
            MemoryManager.memory[2][1] = pcb.getProgramCounter();
            instruction.execute(0 , 19);
        }
        else{
             MemoryManager.memory[22][1] = pcb.getProgramCounter();
            instruction.execute(20 , 39);
        }

		
	}
    /// inst 1 
    // inst 2 
    // inst 3 
    // inst 4 
    // inst 5 
    // nulllll <----pc
	
	public boolean isFinished() {
        System.out.println(MemoryManager.memory[pcb.getProgramCounter()][1]);
        return pcb.getProgramCounter() > this.getPcb().getEndMemoryBoundary() 
            || MemoryManager.memory[pcb.getProgramCounter()][0] == null;
		
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

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Process))  return false;
        Process p = (Process) obj;
        return this.getPcb().getProcessID() == p.getPcb().getProcessID();
    }

	@Override
	public int compareTo(Process o) {
		// TODO Auto-generated method stub
		return this.pcb.getProcessID() - o.getPcb().getProcessID();
	}




}
