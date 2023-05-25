package os;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Scheduler {
	
	private static int timeCycle = 0;
	private int proccessEntered =0, MAX_PROCESSES = 3;

	private static final int MAX_INSTRUCTIONS_PER_SLICE = 3;

	public static PriorityQueue<Pair> processTiming;

	public Scheduler() {
		goGetProcessEntranceTimingCycle();
	}
	
	public void startSchedule() {
		while(proccessEntered < MAX_PROCESSES || OS.getReadyQueue().size() > 0) {
			System.out.println("\n************************************");
			System.out.println("------------------We are in the scheduler Time Slice: "+ timeCycle++);
			cleanTheMEM();
			// if(timeCycle == 11){
			// 	System.out.println("Time Slice 10 reached");
				
			// }
			if( !checkForNewProcesses()){
				if(OS.getReadyQueue().size() == 0) {
					System.out.println("\nNO Process Currently in the Kernel!!");
					continue;
				}
			}

			int x = OS.getReadyQueue().remove();
			// execute 2 instructions
			Process p  = getPBID(x);
			cleanTheMEM();
			p.getPcb().setProcessState(State.RUNNING);
			p.executeNextInstruction();
			if(OS.isBlocked(p.getPcb().getProcessID())) {
				p.getPcb().setProcessState(State.BLOCKED);
				System.out.println("## Process " + p.getPcb().getProcessID() + " is blocked over the requested resource ##");
				continue;
			}
			int instructionsExecuted = 1;
			while (instructionsExecuted++ < MAX_INSTRUCTIONS_PER_SLICE && !p.isFinished()) {


				if (p.isFinished() == false) {
					p.executeNextInstruction();
					if (OS.isBlocked(p.getPcb().getProcessID())) {
						p.getPcb().setProcessState(State.BLOCKED);
						System.out.println("## Process " + p.getPcb().getProcessID() + " is blocked over the requested resource ##");
						continue;
					}
				}

			}
			if (p.isFinished() == false) {
				OS.getReadyQueue().add(p.getPcb().getProcessID());
				p.getPcb().setProcessState(State.READY);


			}
		}			
	}

	private void cleanTheMEM() {
		// System.out.println("MEM BEFORE CLEANING : " );
		// System.out.println();
		// Interpeter.printMemory();
		Object[][] mem = MemoryManager.memory;
		for(int i = 0; i < 20; i++) {
			if(mem[i][0] instanceof String){
				String s = (String) mem[i][0];
				if(s.endsWith(" ") || s.endsWith("\n") || s.endsWith("\t") || s.endsWith("\r")){
					mem[i][0] = s.substring(0, s.length()-1);

				}
			}
			if(mem[i][1] instanceof String){
				String s= (String) mem[i][1];
				if(s.endsWith(" ") || s.endsWith("\n") || s.endsWith("\t") || s.endsWith("\r")){
					mem[i][1] = s.substring(0, s.length()-1);

				}
			}
			if(mem[i][1] == null || mem[i][1].equals("null")){
				mem[i][0] = null;
			}
		}
		// System.out.println("MEM After CLEANING : " );
		// System.out.println();
		// Interpeter.printMemory();
	}

	private boolean checkForNewProcesses(){
		if(processTiming == null || processTiming.size() == 0 ) return false;
		// System.out.println(processTiming.peek().cycle);
		// System.out.println(processTiming.toString());
		if(processTiming.peek().cycle == timeCycle-1) {
			Interpreter in = new Interpreter(processTiming.poll().program);
			proccessEntered++;
			int size = processTiming.size();
			for (int i = 0; i < size; i++) {
				if(processTiming.peek().cycle == timeCycle-1) {
					Interpreter inr = new Interpreter(processTiming.poll().program);
					proccessEntered++;
				} else {
					break;
				}
			}

			return true;
		}

		return false;
	}

	private void goGetProcessEntranceTimingCycle() {
		processTiming = new PriorityQueue<Pair>();

		BufferedReader br;
		try{
			br = new BufferedReader(new java.io.FileReader("processes.txt"));
			String line;
			while((line = br.readLine()) != null){
				if(line.length() == 0) continue;
				if(line.equalsIgnoreCase("HALT") || line.equalsIgnoreCase("EXIT")) break;
				// else kol 7aga tamam
				Pair p = new Pair( Integer.parseInt( br.readLine()), line);
				processTiming.add(p);
			}
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("Processes File error!");
		}
	}
	
	private Process getPBID(int x) {
		Interpreter.printMemory();
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
	//	Interpeter.printMemory();
		Process p = goGetItFromHD(x);
		//Interpeter.printMemory();
		return p;
	}
	//PCB
// 0- int processID ;
// 1-	State state ;
// 2-	int pc ;
// 3-	int startBoundary ;
// 4- int endBoundary ;
// 5- var 1
// 6- var 2
// 7- var 3
// 8- instruction 1 ...


	private Process goGetItFromHD(int x) {
		String hd =  Kernel.readFromDisk("hardDisk.txt");
		System.out.println("Getting From HardDisk: \n" + hd);
		//text += (memory[i][0]+"") + " " + (memory[i][1]+"") + "\n";
		String [] lines = hd.split("\n");
		for(int i = 0; i<lines.length;i++) {
			String [] attributes = lines[i].split(" ");
			if(attributes[0].equals("processID")){
				int y =  Integer.parseInt(attributes[1].substring(0, attributes[1].length()-1));
				if (x==y){
					int pid = y ;
					String one = lines[i+1].split(" ")[1];
					one = one.substring(0, one.length()-1);
					String two = lines[i+2].split(" ")[1];
					two = two.substring(0, two.length()-1);
					String three = lines[i+3].split(" ")[1];
					three = three.substring(0, three.length()-1);
					String four = lines[i+4].split(" ")[1];
					four = four.substring(0, four.length()-1);
					String five = lines[i+5].split(" ")[1];
					five = five.substring(0, five.length()-1);
					String six = lines[i+6].split(" ")[1];
					six = six.substring(0, six.length()-1);
					String seven = lines[i+7].split(" ")[1];
					seven = seven.substring(0, seven.length()-1);
					State s = stringToState(one);
					int pc = Integer.parseInt(two);
					int start = Integer.parseInt(three);
					int end = Integer.parseInt(four);
					String a = lines[i+5].split(" ")[0];
					String aVal = five;
					String b = lines[i+6].split(" ")[0];
					String bVal =six;
					String c = lines[i+7].split(" ")[0];
					String cVal = seven;
					ArrayList<String> vars = new ArrayList<>();
					ArrayList <String> names = new ArrayList<>(); 
					for(int j =8;j<20;j++){
						if( (i+j)<=lines.length-1  &&   !lines[i+j].split(" ")[0].equals("processID")){
							names.add(lines[i+j].split(" ")[0]);
							String l = "";
							for(int k = 1;k<lines[i+j].split(" ").length-1;k++){
								l+=lines[i+j].split(" ")[k]+" ";
								
							}
							l+=lines[i+j].split(" ")[lines[i+j].split(" ").length-1];
							vars.add(l);
							
						}
					}
					// System.out.println("pid : "+pid);
					// System.out.println("state : "+s);
					// System.out.println("pc : "+pc);
					// System.out.println("start : "+start);
					// System.out.println("end : "+end);
					// System.out.println("a : "+a);
					// System.out.println("aVal : "+aVal);
					// System.out.println("b : "+b);
					// System.out.println("bVal : "+bVal);
					// System.out.println("c : "+c);
					// System.out.println("cVal : "+cVal);
					// System.out.println("vars : "+vars);
					// System.out.println("names : "+names);



					int memoryStart=-1,memoryEnd=-1;
					Object [][] memory = MemoryManager.memory;
				
					if(memory[0][1] == null){
							memoryStart = 0;
							memoryEnd = 19;
							MemoryManager.clearMemory(memoryStart, memoryEnd);
						//   return;

						}
					else if(memory[20][1] == null){
							memoryStart = 20;
							memoryEnd = 39;
							MemoryManager.clearMemory(memoryStart, memoryEnd);
						//  return;
						}
					else{
					if(((State)memory[1][1]).equals( State.RUNNING)){
						memoryStart = 20;
						memoryEnd = 39;
					}
					else{
						memoryStart = 0;
						memoryEnd = 19;
					}
					MemoryManager.freeMemory(memoryStart,memoryEnd);
					}
					ProcessControlBlock pcb = new ProcessControlBlock(pid, s, pc, start, end);
					Interpreter.writeProcessToMem(memoryStart, end, pcb, vars);
					MemoryManager.memory[memoryStart+5][0]=a;
					MemoryManager.memory[memoryStart+5][1]=aVal;
					MemoryManager.memory[memoryStart+6][0]=b;
					MemoryManager.memory[memoryStart+6][1]=bVal;
					MemoryManager.memory[memoryStart+7][0]=c;
					MemoryManager.memory[memoryStart+7][1]=cVal;


					
					return new Process(pcb);
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

    




	static class Pair implements Comparable<Pair>{
		int cycle;
		String program;

		public Pair(int cycle, String filenameProg){
			this.cycle = cycle;
			program = filenameProg.endsWith(".txt")? filenameProg : filenameProg + ".txt";
		}

		@Override
		public int compareTo(Pair o) {
			return this.cycle - o.cycle;
		}

		@Override
		public String toString() {
			return "Pair [cycle=" + cycle + ", program=" + program + "]";
		}
		
	}
}