package Game.BigFlip;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class taskBoard {

    boolean found_flag = false;
    ArrayList<Integer> solution = new ArrayList<>();
    Queue<Message> board = new LinkedList<>();
    ReadWriteLock wRLock = new ReentrantReadWriteLock();
    Lock lock = new ReentrantLock();

    public taskBoard(){

    }
    public void pushTask(Message taskM){
        board.add(taskM);
    }
    public Message pullTask(){
        Message pull = board.peek();
        board.remove(pull);
        assert pull != null;
        return pull;
    }
    public Message peekTask(){
        assert board.peek() != null;
        return board.peek();
    }

    public void setFound_flag(boolean found_flag) {
        try {wRLock.writeLock().lock();
            this.found_flag = found_flag;
        }finally {
            wRLock.writeLock().unlock();
        }
    }

    public boolean getFound_flag() {
        try {wRLock.readLock().lock();
            return found_flag;
        }finally {
            wRLock.readLock().unlock();
        }
    }

    public void setSolution(Message solution) {
        try {lock.lock();
            if(!found_flag){
                this.solution = solution.getMoves();
                setFound_flag(true);
            }
        }finally {
            lock.unlock();
        }
    }
}
