package os;

public class InstructionParser {
    public static  Instruction parseInstruction(String instruction) {
        if(instruction.endsWith(" ") || instruction.endsWith("\n") || instruction.endsWith("\r") || instruction.endsWith("\t") || instruction.endsWith("\f") || instruction.endsWith("\b")){
            instruction = instruction.substring(0,instruction.length()-1);
        }
        String[] tokens = instruction.split(" ");
        String type = tokens[0];

        switch (type){
            case "readFile":
                Object[] args3 = new Object[1];

                Instruction checkarg = parseInstruction(tokens[1]); //to handle if arg is an instruction input

                args3[0] = checkarg == null ? tokens[1] : checkarg; // value or instruction

                return new Instruction(InstType.readFile, args3);

            case "input":
                return new Instruction(InstType.input, new Object[0]);

            case "print":
                Object[] args = new Object[1];

                args[0] = "";
                for (int i = 1; i < tokens.length; i++) {
                    args[0] += tokens[i] + (i>= tokens.length-1? "":" ");
                }

                return  new Instruction(InstType.print, args);

            case "assign":
                Object[] args2 = new Object[2];
                args2[0] = tokens[1]; // the variable
                args2[1] = "";
                for (int i = 2; i < tokens.length; i++) {
                    args2[1] += tokens[i] + (i>= tokens.length-1? "":" ");
                }
//                System.out.print("Object arr >>");
//                for (Object o: args2) {
//                    System.out.print(o.toString() + "  ");
//                } System.out.println();
                Instruction check = parseInstruction((String) args2[1]);
                args2[1] = check == null ? args2[1] : check; // value or instruction

                return new Instruction(InstType.assign, args2);

            case "printFromTo" :
                Object [] args4 = new Object[2];

                Instruction checkarg0 = parseInstruction(tokens[1]); //to handle if arg is an instruction input
                Instruction checkarg1 = parseInstruction(tokens[2]);

                args4[0] = checkarg0 == null ? tokens[1] : checkarg0;
                args4[1] = checkarg1 == null ? tokens[2] : checkarg1;

                return new Instruction(InstType.printFromTo, args4);

            case "writeFile":
                Object[] args5 = new Object[2];

                Instruction ch = parseInstruction(tokens[1]);
                args5[0] = ch == null? tokens[1]: ch; // the variable

                args5[1] = "";
                for (int i = 2; i < tokens.length; i++) {
                    args5[1] += tokens[i] + (i>= tokens.length-1? "":" ");
                }
                Instruction check2 = parseInstruction((String) args5[1]);
                args5[1] = check2 == null ? args5[1] : check2; // value or instruction

                return new Instruction(InstType.writeFile, args5);

            case "semWait":
                Object[] args7 = new Object[1];
                if(tokens.length == 1){
                    System.out.println("here");
                    System.out.println(tokens[0]);
                    Interpreter.printMemory();

                }
                if(tokens[1].endsWith(" ")||tokens[1].endsWith("\n")||tokens[1].endsWith("\r")||tokens[1].endsWith("\t")){
                    tokens[1] = tokens[1].substring(0,tokens[1].length()-1);
                }
                args7[0] = tokens[1];
                return new Instruction(InstType.semWait, args7);

            case "semSignal":
                Object[] args6 = new Object[1];
                if(tokens[1].endsWith(" ")||tokens[1].endsWith("\n")||tokens[1].endsWith("\r")||tokens[1].endsWith("\t")){
                    tokens[1] = tokens[1].substring(0,tokens[1].length()-1);
                }
                args6[0] = tokens[1];
                return new Instruction(InstType.semSignal, args6);

            default:
                return null;
        }

    }
    public static void main(String[] args) {
        InstructionParser ip = new InstructionParser();
        Instruction i = ip.parseInstruction("printFromTo a b");
        System.out.println(i);
    }

}
