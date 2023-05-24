package os;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;

import os.MemoryManager;
public class Interpeter {
    static int lastID = 1 ;
    public Interpeter(String filename) {

        addProgram(filename);
        if(lastID == 1){
            Scheduler s = new Scheduler();
            s.startSchedule();
        }
        lastID++;
    }

    private void addProgram(String filename) {
        textToProcess(readFile(filename));
    }

    public Interpeter(String filename1 , String filename2) {
        boolean start = false;
        addProgram(filename1);
        if(lastID == 1){
           start = true;
        }
        lastID++;
        addProgram(filename2);
        if(start){
            Scheduler s = new Scheduler();
            s.startSchedule();
        }
        lastID++;
    }

    public Interpeter() {
    }

    public ArrayList<String> readFile(String fileName) {
        BufferedReader br;
        try {
            br = new BufferedReader(new java.io.FileReader(fileName));
            ArrayList<String> lines = new ArrayList<String>();
            String line = br.readLine();

            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            return lines;
        } catch (IOException ie){
            System.out.println("Error reading file: " + ie.getMessage());
        }

        return null;
    }
    public void textToProcess (ArrayList<String> lines ){
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
// Write in Memory and add to the ready queue
    ProcessControlBlock pcb = new ProcessControlBlock(lastID,8,memoryStart,memoryEnd);
    writeProcessToMem(memoryStart, memoryEnd, pcb, lines);
    OS.addToReadyQueue(lastID);

        
    }
    public static void printMemory(){
        Object [][] memory = MemoryManager.memory;
        for(int i = 0 ; i < 40 ; i++){
            System.out.println("Memory["+i+"][0] = "+memory[i][0]+" Memory["+i+"][1] = "+memory[i][1]);
        }
    }




    public static void writeProcessToMem(int memStart,int memEnd,ProcessControlBlock Pcb,ArrayList<String>lines){
        Object [][] memory = MemoryManager.memory;

        memory[memStart][0] = "processID";
        memory[memStart][1] = Pcb.getProcessID();
        

        memory[memStart+1][0] = "processState";
        memory[memStart+1][1] = Pcb.getProcessState();

        memory[memStart+2][0] = "programCounter";
        memory[memStart+2][1] = Pcb.getProgramCounter();

        memory[memStart+3][0] = "startMemoryBoundary";
        memory[memStart+3][1] = Pcb.getStartMemoryBoundary();


        memory[memStart+4][0] = "endMemoryBoundary";
        memory[memStart+4][1] = Pcb.getEndMemoryBoundary();

        memory[memStart+5][0] = null;
        memory[memStart+5][1] = null;
        
        memory[memStart+6][0] = null;
        memory[memStart+6][1] = null;

        memory[memStart+7][0] = null;
        memory[memStart+7][1] =null;
       
        //5,6,7 left empty for variables
        int j = 0 ;
        for(int i = memStart+8;(i<memEnd && !lines.isEmpty());i++){
            memory[i][0] = "Instruction"+j;
            memory[i][1] = lines.remove(0);
            j++;
        }
       

        // if(!lines.isEmpty()){
        //     throw new RuntimeException("Not enough memory to store the process");
        // }



    }
    

}
