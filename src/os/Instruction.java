package os;

public class Instruction {
    InstType type;
    Object[] args;


    public Instruction(InstType type, Object[] args) {
        this.type = type;
        this.args = args;
    }

    public Object execute(int start, int end, int pc) {
        switch (type) {
            case readFile:
                readFile(start, end, pc); return null;

            case print:
                print(start, end); return null;

            case assign:
                assign(start, end, pc); return null;

            case printFromTo:
                printFromTo(start, end); return null;

            case writeFile:
                writeFile(start, end, pc); return null;
            case input:
                input(pc); return null;
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
        // name = name.substring(4, name.length() - 1);// System.out.println("NAME : "+name);// System.out.println("START : "+start+5);// System.out.println("END : "+(end-12));
        System.out.println("THE NAME OF var: "+name);
        Object b =  Kernel.readFromMemory(name, start+5, end-12);
        Kernel.print(b);
    }
    private void printFromTo(int start, int end){
        System.out.println((String)args[0]+"    "+(String)args[1]);
        String one = (String)args[0];
        String two = (String)args[1];
        if(one.endsWith(" ") || one.endsWith("\n") || one.endsWith("\t") || one.endsWith("\r")){
            one = one.substring(0,one.length()-1);
        }
        if(two.endsWith(" ") || two.endsWith("\n") || two.endsWith("\t") || two.endsWith("\r")){
            two = two.substring(0,two.length()-1);

        }
        String val1 = (String) Kernel.readFromMemory(   one    , start, end);
        //val1 = val1.substring(4,val1.length()-1);

        String val2 = (String) Kernel.readFromMemory(   two    , start, end);
        //val2 = val2.substring(4,val2.length()-1);
        Kernel.printFromTo( Integer.parseInt(val1), Integer.parseInt(val2));
        // Kernel.printFromTo( Integer.parseInt(val1), Integer.parseInt(val2);

        //int a = 5
        //int b = 7
        // printFrom(a,b)
    }
    private  void assign(int start, int end, int pc){
//        if (args[1] == null) {
//            // take input
//            String in = Kernel.askTheUserForInput();
//            args[1] = in;
//            this.execute(start, end);
//        } else { // a 5
            int x = start + 5;
            int y = start + 7;
        Object argument0, argument1;
//        if(args[0] instanceof Instruction)
//            argument0 = (Instruction)((Instruction) args[0]).execute(start,end);
//        else
            argument0 = args[0];
        if(args[1] instanceof Instruction){
            Interpreter.printMemory();
            //get the data from input or readFile instruction just above me
            Instruction k = (Instruction) args[1];
//            System.out.println(k.toString());
            for(int h = 0 ;h<k.args.length;h++){
                System.out.println(k.args[h]);
            }

            argument1 = MemoryManager.memory[pc-1][1] /*OLD>>((Instruction) args[1]).execute(start,end)*/;

                
        }
        else
            argument1 = args[1];


            // System.out.println("THE ARGS : "+args[0] + " " + args[1] );
        Kernel.writeToMemory(argument0.toString(), argument1.toString(), x, y);
//        }
    }
    private void input(int pc){
        //replace instruction with the actual input value
        MemoryManager.memory[pc][1] = Kernel.askTheUserForInput();
    }
    private void writeFile(int start, int end, int pc){
        Object argument0, argument1;
//        if(args[0] instanceof Instruction)
//            argument0 = ((Instruction) args[0]).execute(start,end);
//        else
            argument0 = args[0];
        if(args[1] instanceof Instruction)
            argument1 = MemoryManager.memory[pc-1][1]/*OLD>>((Instruction) args[1]).execute(start,end)*/;
        else
            argument1 = args[1];

        Object filename = Kernel.readFromMemory(argument0.toString(), start,end);
        Object data = Kernel.readFromMemory(argument1.toString(), start,end);
        if(data == null || filename == null){
            System.out.println("Writing nulls or to nulls Not supported");
            return;
        }
        Kernel.writeToDisk( data.toString(), filename.toString());
    }
    private void readFile(int start, int end, int pc){
        Object argument0;
        if(args[0] instanceof Instruction)
            //although yasmine said this won't happen
            argument0 = MemoryManager.memory[pc-1][1]/*OLD>>((Instruction) args[0]).execute(start,end)*/;
        else
            argument0 = args[0];
        //if arg is "input" --->   readFile input
        //then execute the input first, get the return value, then use it as an argument for readFile
        // System.out.println("argument0 : "+argument0);
        // System.out.println("argument0.toString() : "+argument0.toString());
        // System.out.println(args.length);
        // String s = argument0.toString();

//replace instruction with the actual value
        MemoryManager.memory[pc][1] = Kernel.readFromDisk(checkIfVar(argument0.toString(),start,end));
    }
    private String checkIfVar(String string, int start, int end) {
       Object r =  Kernel.readFromMemory(string, start+5,start+7);

        return r == null ? string : r.toString();
    }

    private void semWait(int start, int end) {
        Mutex MtobeUsed = (args[0].equals("userInput"))? OS.inpMutex : (args[0].equals("userOutput"))? OS.outpMutex :
                (args[0].equals("file"))? OS.filMutex : null;

        if (MtobeUsed == null) {
            System.out.println("Mutex not supported. Please enter a valid mutex!" );
            return;
        }

        Kernel.semWait(MtobeUsed, (Integer)MemoryManager.memory[start][1]);
    }
    private void semSignal(int start, int end){
        Mutex MtobeUsed = (args[0].equals("userInput"))? OS.inpMutex : (args[0].equals("userOutput"))? OS.outpMutex :
                (args[0].equals("file"))? OS.filMutex : null;

        if (MtobeUsed == null) {
            System.out.println("Mutex not supported. Please enter a valid mutex!" );
            return;
        }

        Kernel.semSignal(MtobeUsed, (Integer)MemoryManager.memory[start][1]);
    }
    @Override
    public String toString() {
//        if(type == InstType.readFile) {
//            return type + "( " + args[0]+" )";
//        }
        StringBuilder ret = new StringBuilder(type + "( " );
        for ( Object o : args) {
            ret.append(o.toString()).append(", ");
        }
        return (ret.charAt(ret.length()-2) != '(')? ret.substring(0, ret.length() - 2).concat(" )"): ret.toString().concat(")");
        //return type + "( " + String.join(" ", args.toString()) + " )";
    }
}
