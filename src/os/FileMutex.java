package os;

import java.util.LinkedList;
import java.util.Queue;

public class FileMutex extends Mutex {
     public static Queue<Integer> blockedQueue = new  LinkedList<Integer>();
    public FileMutex() {
        super();
    }
    @Override
    public Queue<Integer> getBlockedQueue() {
        return blockedQueue;
    }
    @Override
    public void setBlockedQueue(Queue<Integer> blockedQueue) {
        InputMutex.blockedQueue = blockedQueue;
    }

}