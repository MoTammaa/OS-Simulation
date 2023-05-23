package os;

public class InstructionParser {
    public static  Instruction parseInstruction(String instruction) {
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
                for (int i = 1; i < tokens.length; i++) {
                    args[0] += tokens[i] + (i>= tokens.length-1? "":" ");
                }

                return  new Instruction(InstType.print, args);

            case "assign":
                Object[] args2 = new Object[2];
                args2[0] = tokens[1]; // the variable
                for (int i = 2; i < tokens.length; i++) {
                    args2[1] += tokens[i] + (i>= tokens.length-1? "":" ");
                }
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

                for (int i = 2; i < tokens.length; i++) {
                    args5[1] += tokens[i] + (i>= tokens.length-1? "":" ");
                }
                Instruction check2 = parseInstruction((String) args5[1]);
                args5[1] = check2 == null ? args5[1] : check2; // value or instruction

                return new Instruction(InstType.writeFile, args5);

            case "semWait":
                Object[] args7 = new Object[1];
                args7[0] = tokens[1];
                return new Instruction(InstType.semWait, args7);

            case "semSignal":
                Object[] args6 = new Object[1];
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
