public class Process {
    private PCB pcb;
    private String instructions; //hya hna m7taga tkon arraylist of instructions wla laa?

    public Process(int processId, String instructions, int priority, int memoryUpperBound, int memoryLowerBound) {
        this.pcb = new PCB(processId, State.READY, 0, priority, memoryUpperBound, memoryLowerBound);
        this.instructions = instructions;
    }

    public int getProcessID() {
        return pcb.getPID();
    }

    public State getState() {
        return pcb.getState();
    }

    public void setState(State state) {
        pcb.setState(state);
    }

    public int getProgramCounter() {
        return pcb.getPC();
    }

    public void setProgramCounter(int programCounter) {
        pcb.setPC(programCounter);
    }

    public int getPriority() {
        return pcb.getPriority();
    }

    public void setPriority(int priority) {
        pcb.setPriority(priority);
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getSize() {
        // el mfrood byrg3 size of el instructions elly mwgooda
        return instructions.length();
    }

    public int getMemoryUpperBound() {
        return pcb.getMemoryUpperBound();
    }

    public int getMemoryLowerBound() {
        return pcb.getMemoryLowerBound();
    }
}
