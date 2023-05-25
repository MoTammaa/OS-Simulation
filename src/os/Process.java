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

 






    //public String toString(){
//        return pcb.getProcessID()+"";
//    }




    public void executeNextInstruction() {
        int id = this.getPcb().getProcessID();

        int one =(int) MemoryManager.memory[0][1];
        int pc = pcb.getProgramCounter();
//        if(pcb.processID == 1 ){
//            System.out.println((String) MemoryManager.memory[pc][1]);
//        }

        pcb.setProgramCounter(pc+1);
       // Interpeter.printMemory();
//        System.out.println("Intruction ::"+MemoryManager.memory[pc][1]);
        Instruction instruction = InstructionParser.parseInstruction((String) MemoryManager.memory[pc][1]);

        System.out.print("Executing Instruction (process>'" + pcb.processID  +"'):  => "+ (instruction == null?"null": instruction.toString()));
        Kernel.showOutput();

        if(instruction != null && (instruction.type == InstType.input || instruction.type == InstType.readFile)){
            System.out.println("  /------> HINT::::[next instruction]:::: " + InstructionParser.parseInstruction((String) MemoryManager.memory[pc+1][1]));
        } else System.out.println();

        // if(instruction != null && instruction.toString().equals("semSignal( userInput )")){
        //     System.out.println("here");
        // }
         
        if(id == one){
            MemoryManager.memory[2][1] = pcb.getProgramCounter();
            instruction.execute(0 , 19, pcb.getProgramCounter() - 1);
        }
        else{
             MemoryManager.memory[22][1] = pcb.getProgramCounter();
            instruction.execute(20 , 39, pcb.getProgramCounter() - 1);
        }

		
	}
    /// inst 1 
    // inst 2 
    // inst 3 
    // inst 4 
    // inst 5 
    // nulllll <----pc
	
	public boolean isFinished() {
        //System.out.println(MemoryManager.memory[pcb.getProgramCounter()][1]);
        // if(pcb.getProcessID() == 1 && pcb.getProgramCounter() == 17){
        //     Interpeter.printMemory();
        // // }
        // System.out.println(MemoryManager.memory[pcb.getProgramCounter()][1]);
        // System.out.println(MemoryManager.memory[pcb.getProgramCounter()][1] == null);
        // System.out.println(MemoryManager.memory[pcb.getProgramCounter()][1] instanceof String);
        if(MemoryManager.memory[pcb.getProgramCounter()][1] instanceof String
        &&( ((String)MemoryManager.memory[pcb.getProgramCounter()][1]).endsWith(" ")
        || ((String)MemoryManager.memory[pcb.getProgramCounter()][1]).endsWith("\n")
        || ((String)MemoryManager.memory[pcb.getProgramCounter()][1]).endsWith("\r")
        || ((String)MemoryManager.memory[pcb.getProgramCounter()][1]).endsWith("\t")
        )){
            // System.out.println("here");
            String s = (String)MemoryManager.memory[pcb.getProgramCounter()][1];
            MemoryManager.memory[pcb.getProgramCounter()][1] = s.substring(0, s.length()-1);
        }
            // READ THIS : why pc +1 ? 
            //bec when he execute the instruction he increment the pc by 1 1st then excute the instruction at pc+1

        return pcb.getProgramCounter() > this.getPcb().getEndMemoryBoundary() 
            || MemoryManager.memory[pcb.getProgramCounter()][1] == null
            || MemoryManager.memory[pcb.getProgramCounter()][1].equals("null");
		
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

    public String toString() {
        StringBuilder sb = new StringBuilder( "Process'" + this.getPcb().getProcessID()+"' (");
        sb.append("state="+this.getPcb().getProcessState().toString()+", ")
            .append("pc="+this.getPcb().getProgramCounter()+", ")
                .append("memory=["+this.getPcb().getStartMemoryBoundary()+","+this.getPcb().getEndMemoryBoundary()+"]")
                    .append(")");


        return sb.toString();
    }



}
