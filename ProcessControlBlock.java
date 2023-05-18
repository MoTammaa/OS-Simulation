package defaultPackage;

public class ProcessControlBlock {
	int processID ;
	String processState ; 
	int programCounter ;
	int startMemoryBoundary ;
	int endMemoryBoundary ;
	
	public ProcessControlBlock (int processID , String processState , int programCounter , int startMemoryBoundary,int endMemoryBoundary) {
		this.processID = processID;
		this.processState = processState;
		this.programCounter = programCounter;
		this.startMemoryBoundary = startMemoryBoundary;
		this.endMemoryBoundary = endMemoryBoundary;
			
	}
}
