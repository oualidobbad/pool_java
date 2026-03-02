public class ThreadTest extends Thread {
	private int count;
	
	public ThreadTest(String name, int count){
		super(name);
		if (count < 0)
			throw new IllegalArgumentException("count must be positive");
		this.count = count;
	}

	@Override
	public void run(){
		for (int i = 0; i < count; i++)
			System.out.println(this.getName());
	}
}
