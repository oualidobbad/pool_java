public class RunnableDemo implements Runnable {
	static int a = 0;
	static final Object LOCK = new Object();

	@Override
	public void run() {
		for (int i = 0; i < 1_000_000; i++) {
			synchronized (LOCK) {
				a++;
			}
		}
		System.out.println(a);
	}
}
