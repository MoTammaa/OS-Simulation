package os;

public class OutputMutex extends Mutex {
    
    public OutputMutex() {
        super();
    }
    
    // public void semWait(Process process) {
    //     if (this.isIslocked()) {
    //         this.getBlockedQueue().add(process);
    //     } else {
    //         setIslocked(true);
    //         process.get
    //     }
    // }
    
    // public void semSignal(Process process) {
    //     if (this.getBlockedQueue().isEmpty()) {
    //         setIslocked(false);
    //     } else {
    //         this.getBlockedQueue().remove();
    //     }
    // }

}
