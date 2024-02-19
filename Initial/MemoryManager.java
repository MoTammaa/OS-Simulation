import java.io.*;

public class MemoryManager {
    private int size = 40;
    private Process[] memory;
    private int nextAvailableIndex;

    public MemoryManager() {
        this.memory = new Process[size];
        this.nextAvailableIndex = 0;
    }

    public boolean isMemoryAvailable(Process process) {
        int processSize = process.getSize();
        int availableSpace = size - nextAvailableIndex;
        return availableSpace >= processSize;
    }

    public void allocateMemory(Process process) {
        if (!isMemoryAvailable(process)) {
            System.out.println("Insufficient memory space to allocate the process.");
        }

        int processSize = process.getSize();
        int memoryStart = nextAvailableIndex;
        int memoryEnd = nextAvailableIndex + processSize - 1; //3shan ana bbda2 mn 0 fl array

        // bamla el blocks of mem with the process given
        for (int i = memoryStart; i <= memoryEnd; i++) {
            memory[i] = process;
        }

        nextAvailableIndex = memoryEnd + 1;
    }

    public void freeMemory(Process process) {
        int memoryStart = process.getMemoryLowerBound();
        int memoryEnd = process.getMemoryUpperBound();

        // fady makn w sheel el process(es) w 7ot mkanhom null
        for (int i = memoryStart; i <= memoryEnd; i++) {
            memory[i] = null;
        }

        // for fresh start mn el awel
        if (memoryStart < nextAvailableIndex) {
            nextAvailableIndex = memoryStart;
        }
    }

    public void swapProcessToDisk(Process process) {
        process.setState(State.BLOCKED);

        // hna b5ly el state blocked 3shan msh hst3mlha w hfdy mkan fe el mem array
        //hwa ana a3ml array tany esmo disk w el process elly 3mltaha free fel mem tro7lo wla ehh???
        String processData = process.getInstructions(); // Assuming instructions represent the process's data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("process_data.txt"))) {
            writer.write(processData);
        } catch (IOException e) {
            System.out.println("Error saving process data to disk: " + e.getMessage());
        }
        freeMemory(process);

    }

    public void swapProcessToMemory(Process process) {
        process.setState(State.READY);
        try (BufferedReader reader = new BufferedReader(new FileReader("process_data.txt"))) {
            StringBuilder processData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                processData.append(line).append("\n");
            }

            // Set the loaded data to the process
            process.setInstructions(processData.toString());
        } catch (IOException e) {
            System.err.println("Error reading process data from disk: " + e.getMessage());
            // Perform any necessary error handling or recovery actions
        }

        allocateMemory(process);
        // hna b5ly el state ready 3shan a3rf ast3mlha w ad5lha fe el mem array
        //zy method swap bas bl3ks

    }


}
