package os;

public class ProcessControlBlock  {
    int processID ;
	State processState ; 
	int programCounter ;
	int startMemoryBoundary ;
	int endMemoryBoundary ;
	
	public ProcessControlBlock (int processID , int programCounter , int startMemoryBoundary,int endMemoryBoundary) {
		this.processID = processID;
		this.processState = State.READY;
		this.programCounter = programCounter;
		this.startMemoryBoundary = startMemoryBoundary;
		this.endMemoryBoundary = endMemoryBoundary;
			
	}
	public ProcessControlBlock (int processID ,State s  ,int programCounter , int startMemoryBoundary,int endMemoryBoundary) {
		this.processID = processID;
		this.processState = s;
		this.programCounter = programCounter;
		this.startMemoryBoundary = startMemoryBoundary;
		this.endMemoryBoundary = endMemoryBoundary;
			
	}

	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public State getProcessState() {
		return processState;
	}

	public void setProcessState(State processState) {
		this.processState = processState;
	}

	public int getProgramCounter() {
		return programCounter;
	}

	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}

	public int getStartMemoryBoundary() {
		return startMemoryBoundary;
	}

	public void setStartMemoryBoundary(int startMemoryBoundary) {
		this.startMemoryBoundary = startMemoryBoundary;
	}

	public int getEndMemoryBoundary() {
		return endMemoryBoundary;
	}

	public void setEndMemoryBoundary(int endMemoryBoundary) {
		this.endMemoryBoundary = endMemoryBoundary;
	}
	
}
