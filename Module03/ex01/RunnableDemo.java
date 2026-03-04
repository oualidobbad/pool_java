public class RunnableDemo implements Runnable {

	static final Object LOCK = new Object();
	String threadName;
	int count;
	static boolean turn = true;


	public RunnableDemo(String name, int count){
		this.threadName = name;
		if (count <= 0)
			throw new IllegalArgumentException("count must be positive");
		this.count = count;
	}
	private void println(String x){
		System.out.println(x);
	}

	@Override
	public void run() {
		boolean myTurn = threadName.equals("Hen") ? false : true;
		
		for (int i = 0; i < count; i++) {
			synchronized(LOCK) 
			{
				while (turn != myTurn) {
					try {
						LOCK.wait();
					} catch(InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
				println(threadName);
				turn = !turn;
				LOCK.notifyAll();
			}
		}
	}
}
