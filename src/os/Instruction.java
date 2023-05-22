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

                return Kernel.readFromDisk((String) args[0]);
            case print:
                String name = (String) args[0];
                name = name.substring(4, name.length() - 1);
                System.out.println("NAME : "+name);
                System.out.println("START : "+start+5);
                System.out.println("END : "+(end-12));
                Object b =  Kernel.readFromMemory(name, start+5, end-12);
                Kernel.print(b);
                return null;
            case assign:
                if (args[1] == null) {
                    // take input
                    String in = Kernel.askTheUserForInput();
                    args[1] = in;
                    this.execute(start, end);
                } else { // a 5
                    int x = -1;
                    int y = -1;
                    if (start == 0) { // 0 1 2 3 4 5 6 7
                        x = 5;
                        y = 7;
                    } else { // 20 21 22 23 24 25 26 27
                        x = 25;
                        y = 27;
                    }
                   // System.out.println("THE ARGS : "+args[0] + " " + args[1] );
                    Kernel.writeToMemory(((String) args[0]), ((String) args[1]), x, y);
                }

                return null;
            case printFromTo:
               // System.out.println((String)args[0]+"    "+(String)args[1]);
               String val1 = (String) Kernel.readFromMemory(   ((String)args[0])    , start, end);
               val1 = val1.substring(4,val1.length()-1);
               
               String val2 = (String) Kernel.readFromMemory(   ((String)args[1])    , start, end);
                val2 = val2.substring(4,val2.length()-1);
               Kernel.printFromTo( Integer.parseInt(val1), Integer.parseInt(val2));
               // Kernel.printFromTo( Integer.parseInt(val1), Integer.parseInt(val2);
                return null;
                //int a = 5
                //int b = 7
                // printFrom(a,b)

        }
        return null;
    }
    @Override
    public String toString() {
        if(type == InstType.readFile) {
            return type + " " + args[0];
        }

        return type + " " + String.join(" ", args.toString());
    }
}
