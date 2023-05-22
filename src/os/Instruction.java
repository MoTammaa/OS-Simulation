package os;

public class Instruction {
    InstType type;
    Object[] args;


    public Instruction(InstType type, Object[] args) {
        this.type = type;
        this.args = args;
    }

    public Object execute(int start , int end) {
    switch (type){
        case readFile:

            return Kernel.readFromDisk((String) args[0]);
        case print:
           Kernel.print((String) args[0]);
            return null;
        case assign:
        if(args[1]==null){
            // take input
            String in =  Kernel.askTheUserForInput();
            args[1] = in; 
            this.execute(start, end);
        }
        else{ // a 5 
            int x = -1;
            int y = -1;
            if(start == 0){  // 0 1 2 3 4 5 6 7 
                x = 5;
                y = 7;
            }
            else{ // 20 21 22 23 24 25 26 27
                x = 25;
                y = 27;
            }
            Kernel.writeToMemory(((String) args[0]), ( (String) args[1]), x, y);
        }

            return null;
        case printFromTo:
            Kernel.printFromTo(Integer.parseInt((String) args[0]),Integer.parseInt ((String) args[1]));
            return null;

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
