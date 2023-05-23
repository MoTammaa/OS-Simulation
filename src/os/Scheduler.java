package os;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	
	

	public Scheduler() {
		
	}
	
	public void startSchedule() {
		while(OS.getReadyQueue().size()>0) {
			System.out.println("WE STARTED");
			int x = OS.getReadyQueue().remove();
			// execute 2 instructions
			Process p  = getPBID(x);
			p.getPcb().setProcessState(State.RUNNING);
			p.executeNextInstruction();
			if(OS.isBlocked(p.getPcb().getProcessID())) {
				p.getPcb().setProcessState(State.BLOCKED);
				continue;
			}
			
			if(p.isFinished() == false) {
				p.executeNextInstruction();
				if(OS.isBlocked(p.getPcb().getProcessID())) {
				p.getPcb().setProcessState(State.BLOCKED);
				continue;
			}
			}
			if(p.isFinished() == false) {
				OS.getReadyQueue().add(p.getPcb().getProcessID());
				p.getPcb().setProcessState(State.READY);
				

			}
			
			
			
			
		}			
	}
	
	private Process getPBID(int x) {
		Object [] [] mem = MemoryManager.memory;
		if(mem[0][1].equals(x)) {
			int pid = x;
			State s =(State) mem[1][1];
			int pc = (int) mem[2][1];
			int start = (int) mem[3][1];
			int end = (int) mem[4][1];
			return new Process(new ProcessControlBlock(pid, s, pc, start, end));
		}
		if(mem[20][1].equals(x)) {
			int pid = x;
			State s =(State) mem[21][1];
			int pc = (int) mem[22][1];
			int start = (int) mem[23][1];
			int end = (int) mem[24][1];
			return new Process(new ProcessControlBlock(pid, s, pc, start, end));
		}

		return goGetItFromHD(x);
	}
	//PCB
// 1- int processID ;
// 2-	State state ; 
// 3-	int pc ;
// 4-	int startBoundary ;
// 5- int endBoundary ;
// 6- var 1
// 7- var 2
// 8- var 3
// 9- instruction 1 ...


	private Process goGetItFromHD(int x) {
		String hd =  Kernel.readFromDisk("hardDisk.txt");
		//text += (memory[i][0]+"") + " " + (memory[i][1]+"") + "\n";
		String [] lines = hd.split("\n");
		for(int i = 0; i<lines.length;i++) {
			String [] attributes = lines[i].split(" ");
			if(attributes[0].equals("processID")){
				int y =  Integer.parseInt(attributes[1]);
				if (x==y){
					int pid = y ;
					State s = stringToState(lines[i+1].split(" ")[1]);
					int pc = Integer.parseInt(lines[i+2].split(" ")[1]);
					int start = Integer.parseInt(lines[i+3].split(" ")[1]);
					int end = Integer.parseInt(lines[i+4].split(" ")[1]);
					String a = lines[i+5].split(" ")[0];
					String aVal = lines[i+5].split(" ")[1];
					String b = lines[i+6].split(" ")[0];
					String bVal = lines[i+6].split(" ")[1];
					String c = lines[i+7].split(" ")[0];
					String cVal = lines[i+7].split(" ")[1];
					ArrayList<String> vars = new ArrayList<>();
					ArrayList <String> names = new ArrayList<>(); 
					for(int j =8;j<20;j++){
						if( (i+j)<=lines.length-1  &&   !lines[i+j].split(" ")[0].equals("processID")){
							names.add(lines[i+j].split(" ")[0]);
							vars.add(lines[i+j].split(" ")[1]);
						}
					}
					System.out.println("pid : "+pid);
					System.out.println("state : "+s);
					System.out.println("pc : "+pc);
					System.out.println("start : "+start);
					System.out.println("end : "+end);
					System.out.println("a : "+a);
					System.out.println("aVal : "+aVal);
					System.out.println("b : "+b);
					System.out.println("bVal : "+bVal);
					System.out.println("c : "+c);
					System.out.println("cVal : "+cVal);
					System.out.println("vars : "+vars);
					System.out.println("names : "+names);
					
					return new Process(new ProcessControlBlock(pid, s, pc, start, end));
				}
			}
		}
		return null;
	}
	private State stringToState(String string) {
		switch(string) {
			case "READY":
				return State.READY;
			case "RUNNING":
				return State.RUNNING;
			case "BLOCKED":
				return State.BLOCKED;
		}
        return null;
    }

    public static void main(String [] args) {
		Queue q =  new LinkedList<>();
		q.add(2);
		q.add("test");
		q.remove();
		System.out.print(q);
		
	}
		
}