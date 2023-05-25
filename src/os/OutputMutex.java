
package os;

import java.util.LinkedList;
import java.util.Queue;

public class OutputMutex extends Mutex {
     public static Queue<Integer> blockedQueue = new LinkedList<Integer>();
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
        @Override
    public Queue<Integer> getBlockedQueue() {
        return blockedQueue;
    }
    @Override
    public void setBlockedQueue(Queue<Integer> blockedQueue) {
        InputMutex.blockedQueue = blockedQueue;
    }
}
