package os;

import java.io.*;
public class MemoryManager {
    private int size = 40;
    static Object [][] memory = new Object[40][2];
    private int nextAvailableIndex;

    public MemoryManager() {
        this.memory = new Object[size][2];
        this.nextAvailableIndex = 0;
    }

    public boolean isMemoryAvailable() {
        // int processSize = 20;
        // int availableSpace = size - nextAvailableIndex;
        // return availableSpace >= processSize;

        if(memory[0][0] == null || memory[20][0] == null){
            return true;
        }
        else{
            return false;
        }  

    }

    public void allocateMemory(Process process) {
        if (!isMemoryAvailable()) {
            System.out.println("Insufficient memory space to allocate the process.");
         
        }
        else{
            int memoryStart;
            int memoryEnd;
            int processSize = 20;
        
            if(memory[0][1] == null){
                 memoryStart = 0;
                 memoryEnd = 19;

            }
            else{
                memoryStart = 20;
                memoryEnd = 39;
            }
            String hardDisk = Kernel.readFromDisk("hardDisk.txt");
            String[] lines = hardDisk.split("\n");
           for(int i = 0 ;i<lines.length;i++){
              String[] attributes = lines[i].split(",");
              int id = Integer.parseInt(attributes[1]);
              if(id == process.getPcb().getProcessID()){
                
                 for(int j = 0;j<attributes.length-1;j+=1){
                   Kernel.writeToMemory(attributes[j], attributes[j+1], memoryStart, memoryEnd);
                 }
                 return;

              }

           }

      
            

        }








        // int processSize = 20;
        // int memoryStart = nextAvailableIndex;
        // int memoryEnd = nextAvailableIndex + processSize - 1; //3shan ana bbda2 mn 0 fl array

        // // bamla el blocks of mem with the process given
        // for (int i = memoryStart; i <= memoryEnd; i++) {
        //     memory[i] = process;
        // }

        // nextAvailableIndex = memoryEnd + 1;
    }

    public static void freeMemory(int start,int end) {
       String text = "";
        for(int h = 0 ;h<MemoryManager.memory.length;h++){

            if(MemoryManager.memory[h][1] instanceof String){
                String s = (String)MemoryManager.memory[h][1];
                if(s.endsWith(" ") || s.endsWith("\n") || s.endsWith("\t") ||
                 s.endsWith("\r") || s.endsWith("\f") || s.endsWith("\b") ){
                    MemoryManager.memory[h][1] = s.substring(0,s.length()-1);
                }
            }

            if(      MemoryManager.memory[h][1] == null  ||    MemoryManager.memory[h][1].equals("null")   ){
                MemoryManager.memory[h][0] = null;
                MemoryManager.memory[h][1] = null;
            }
            
            /*if(mem[i][1] == null){
				mem[i][0] = null;
			} */

        }
        // fady makn w sheel el process(es) w 7ot mkanhom null
        for (int i = start; i <= end; i++) {
            text += (memory[i][0]+"") + " " + (memory[i][1]+"") + "\n";
           
            memory[i][0] = null;
            memory[i][1] = null;

        }
         Kernel.overrideDisk(text, "hardDisk.txt");
       
    }
    public static void clearMemory(int start, int end) {
    for (int i = start; i <= end; i++) {
        memory[i][0] = null;
        memory[i][1] = null;
    }
}

    public void swapProcessToDisk(Process process) {
        process.getPcb().setProcessState(State.READY);

        // hna b5ly el state blocked 3shan msh hst3mlha w hfdy mkan fe el mem array
        //hwa ana a3ml array tany esmo disk w el process elly 3mltaha free fel mem tro7lo wla ehh???
        String processData = "";
        for(int i = process.getPcb().getStartMemoryBoundary();i<=process.getPcb().getEndMemoryBoundary() && memory[i][0] != null;i++){
          processData+=memory[i][0] +": "+memory[i][1]+",";
        }
        processData = processData.substring(0,processData.length()-1);
         // Assuming instructions represent the process's data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("process_data.txt"))) {
            writer.write(processData);
        } catch (IOException e) {
            System.out.println("Error saving process data to disk: " + e.getMessage());
        }
       // freeMemory(process);

    }

    // public void swapProcessToMemory(Process process) {
    //     process.setState(State.READY);
    //     try (BufferedReader reader = new BufferedReader(new FileReader("process_data.txt"))) {
    //         StringBuilder processData = new StringBuilder();
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             processData.append(line).append("\n");

    //         }

    //         // Set the loaded data to the process
    //         process.setInstructions(processData.toString());
    //     } catch (IOException e) {
    //         System.err.println("Error reading process data from disk: " + e.getMessage());
    //         // Perform any necessary error handling or recovery actions
    //     }

    //     allocateMemory(process);
    //     // hna b5ly el state ready 3shan a3rf ast3mlha w ad5lha fe el mem array
    //     //zy method swap bas bl3ks

    // }


}
