public class PCB {
    private int PID;
    private State state;
    private int PC;
    private int priority;
    private int memoryUpperBound;
    private int memoryLowerBound;
    // Other attributes to store relevant process information

    public PCB(int PID, State state, int PC, int priority, int memoryUpperBound, int memoryLowerBound) {
        this.PID = PID;
        this.state = state;
        this.PC = PC;
        this.priority = priority;
        this.memoryUpperBound = memoryUpperBound;
        this.memoryLowerBound = memoryLowerBound;
        // msh 3arf feh tany wla laa
    }

    public int getPID() {
        return PID;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMemoryUpperBound() {
        return memoryUpperBound;
    }

    public void setMemoryUpperBound(int memoryUpperBound) {
        this.memoryUpperBound = memoryUpperBound;
    }

    public int getMemoryLowerBound() {
        return memoryLowerBound;
    }

    public void setMemoryLowerBound(int memoryLowerBound) {
        this.memoryLowerBound = memoryLowerBound;
    }

}
