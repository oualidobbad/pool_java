public class RealMultithreading extends Thread {
	private Integer [] arr;
	private static Integer threadsCount;


	public RealMultithreading(Integer size, Integer NbThreads){
		if (NbThreads <= 0)
			throw new IllegalArgumentException("The number of threads must be greater than one.");
		threadsCount = NbThreads;
	}
	@Override
	public void run(){
		
	}

	static void setThreadsCount(Integer nbCount){
		threadsCount = nbCount;
	}
	static Integer getThreadsCount(){
		return threadsCount;
	}
}