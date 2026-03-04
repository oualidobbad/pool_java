public class Main {
	public static void main(String[] args) {
		try {
			Thread t1 = new CounterThread();
			Thread t2 = new CounterThread();
			Thread t3 = new CounterThread();

			t1.start();
			t2.start();
			t3.start();

			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
