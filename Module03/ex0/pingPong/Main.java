public class Main {
	public static void main(String[] args) {
		try {
			Thread t1 = new PingPongThread("Ping", 4);
			Thread t2 = new PingPongThread("Pong", 4);

			t2.start();
			t1.start();
			t2.join();
			t1.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
