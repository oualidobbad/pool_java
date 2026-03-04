public class CounterThread extends Thread {
	
    static int turn = 0;
    final int N = 9;
    static int counter = 1;
    static final Object lock = new Object();
    static int t = 0;
    int myTurn;

    public CounterThread() {
        myTurn = t++;
    }

   @Override
public void run() {
    while (true) {
        synchronized (lock) {
            while (turn != myTurn) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (counter > N) {
                turn = (turn + 1) % t;
                lock.notifyAll();
                return;
            }
            System.out.println(getName() + ": " + counter++);
            turn = (turn + 1) % t;
            lock.notifyAll();
        }
    }
}
}