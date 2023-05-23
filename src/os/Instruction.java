package os;

public class Instruction {
    InstType type;
    Object[] args;


    public Instruction(InstType type, Object[] args) {
        this.type = type;
        this.args = args;
    }

    public Object execute(int start, int end) {
        switch (type) {
            case readFile:
                readFile(start, end); return null;

            case print:
                print(start, end); return null;

            case assign:
                assign(start, end); return null;

            case printFromTo:
                printFromTo(start, end); return null;

            case writeFile:
                writeFile(start, end); return null;
            case input:
                input(); return null;
            case semSignal:
                semSignal(start, end); return null;
            case semWait:
                semWait(start, end); return null;
            default:
                return null;
        }
    }

    private void print(int start, int end){
        String name = (String) args[0];
        name = name.substring(4, name.length() - 1);
        System.out.println("NAME : "+name);
        System.out.println("START : "+start+5);
        System.out.println("END : "+(end-12));
        Object b =  Kernel.readFromMemory(name, start+5, end-12);
        Kernel.print(b);
    }
    private void printFromTo(int start, int end){
        // System.out.println((String)args[0]+"    "+(String)args[1]);
        String val1 = (String) Kernel.readFromMemory(   ((String)args[0])    , start, end);
        val1 = val1.substring(4,val1.length()-1);

        String val2 = (String) Kernel.readFromMemory(   ((String)args[1])    , start, end);
        val2 = val2.substring(4,val2.length()-1);
        Kernel.printFromTo( Integer.parseInt(val1), Integer.parseInt(val2));
        // Kernel.printFromTo( Integer.parseInt(val1), Integer.parseInt(val2);

        //int a = 5
        //int b = 7
        // printFrom(a,b)
    }
    private  void assign(int start, int end){
//        if (args[1] == null) {
//            // take input
//            String in = Kernel.askTheUserForInput();
//            args[1] = in;
//            this.execute(start, end);
//        } else { // a 5
            int x = start + 5;
            int y = start + 7;
//            if (start == 0) { // 0 1 2 3 4 5 6 7
//                x = 5;
//                y = 7;
//            } else { // 20 21 22 23 24 25 26 27
//                x = 25;
//                y = 27;
//            }
        Object argument0, argument1;
        if(args[0] instanceof Instruction)
            argument0 = (Instruction)((Instruction) args[0]).execute(start,end);
        else
            argument0 = args[0];
        if(args[1] instanceof Instruction)
            argument1 = (Instruction)((Instruction) args[1]).execute(start,end);
        else
            argument1 = args[1];


            // System.out.println("THE ARGS : "+args[0] + " " + args[1] );
        Kernel.writeToMemory(((String) args[0]), ((String) args[1]), x, y);
//        }
    }
    private Object input(){
        return Kernel.askTheUserForInput();
    }
    private void writeFile(int start, int end){
        Object argument0, argument1;
        if(args[0] instanceof Instruction)
            argument0 = (Instruction)((Instruction) args[0]).execute(start,end);
        else
            argument0 = args[0];
        if(args[1] instanceof Instruction)
            argument1 = (Instruction)((Instruction) args[1]).execute(start,end);
        else
            argument1 = args[1];

        Kernel.writeToDisk(argument0.toString(), argument1.toString());
    }
    private String readFile(int start, int end){
        Object argument0;
        if(args[0] instanceof Instruction)
            argument0 = (Instruction)((Instruction) args[0]).execute(start,end);
        else
            argument0 = args[0];
        //if arg is "input" --->   readFile input
        //then execute the input first, get the return value, then use it as an argument for readFile
        return Kernel.readFromDisk(argument0.toString());
    }
    private void semWait(int start, int end) {
        Mutex MtobeUsed = (args[0].equals("userInput"))? OS.inpMutex : (args[0].equals("userOutput"))? OS.outpMutex :
                (args[0].equals("file"))? OS.filMutex : null;

        if (MtobeUsed == null) {
            System.out.println("Mutex not supported. Please enter a valid mutex!" );
            return;
        }

        Kernel.semWait(MtobeUsed, (Integer)MemoryManager.memory[start][0]);
    }
    private void semSignal(int start, int end){
        Mutex MtobeUsed = (args[0].equals("userInput"))? OS.inpMutex : (args[0].equals("userOutput"))? OS.outpMutex :
                (args[0].equals("file"))? OS.filMutex : null;

        if (MtobeUsed == null) {
            System.out.println("Mutex not supported. Please enter a valid mutex!" );
            return;
        }

        Kernel.semSignal(MtobeUsed, (Integer)MemoryManager.memory[start][0]);
    }
    @Override
    public String toString() {
        if(type == InstType.readFile) {
            return type + " " + args[0];
        }

        return type + " " + String.join(" ", args.toString());
    }
}
