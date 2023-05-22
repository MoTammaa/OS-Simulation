package os;

public class InstructionParser {
    public Instruction parseInstruction(String instruction) {
        String[] tokens = instruction.split(" ");
        String type = tokens[0];

        switch (type){
            case "readFile":
                Object[] args3 = new Object[1];
                args3[0] = tokens[1];
                return new Instruction(InstType.readFile, args3);
            case "print":
                Object[] args = new Object[1];
                for (int i = 1; i < tokens.length; i++) {
                    args[0] += tokens[i] + " ";
                }
                return  new Instruction(InstType.print, args);

            case "assign":
                Object[] args2 = new Object[2];
                args2[0] = tokens[1];
                for (int i = 2; i < tokens.length; i++) {
                    args2[1] += tokens[i] + " ";
                }
                Instruction check = parseInstruction((String) args2[1]);
                args2[1] = check == null ? args2[1] : check;

                return new Instruction(InstType.assign, args2);

            default:
                return null;
        }

    }

}
