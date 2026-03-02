public class Main {
	public static void main(String[] args) {
		RunnableDemo r = new RunnableDemo();
		RunnableDemo r1 = new RunnableDemo();

		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r1);

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
