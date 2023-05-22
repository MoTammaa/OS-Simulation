package os;

public class Instruction {
    InstType type;
    Object[] args;


    public Instruction(InstType type, Object[] args) {
        this.type = type;
        this.args = args;
    }

    public Object execute() {
    //todo: implement
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
