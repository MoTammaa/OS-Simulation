package os;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	Queue<Process> readyQueue ;
	

	public Scheduler() {
		this.readyQueue = new LinkedList<>();
	}
	
	public void startSchedule() {
		while(this.readyQueue.size()>0) {
			Process p = (Process) readyQueue.remove();
			// execute 2 instructions	
			p.executeNextInstruction();
			
			if(p.isFinished() == false) {
				p.executeNextInstruction();
			}
			if(p.isFinished() == false) {
				this.readyQueue.add(p);
			}
			
			
			
			
		}			
	}
	
	public static void main(String [] args) {
		Queue q =  new LinkedList<>();
		q.add(2);
		q.add("test");
		q.remove();
		System.out.print(q);
		
	}
		
}