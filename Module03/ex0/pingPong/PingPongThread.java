public class PingPongThread extends Thread{
	int count;
	static boolean turn = false;
	static final Object lock = new Object();

	public PingPongThread(String name, int count){
		super(name);
		this.count = count;
	}

	@Override
	public void run(){
		boolean myTurn = getName().equals("Ping") ? false : true;

		for (int i = 0; i < count; i++){
			synchronized(lock){
				while (myTurn != turn) {
					try {
						lock.wait();
					} catch (Exception e) {
						Thread.currentThread().interrupt();
						return ; 
					}
				}
				System.out.println(getName());
				turn = !turn;
				lock.notify();
				
			}
		}
	}
}
