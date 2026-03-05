// ==================optimazed and simple code ===============
public class RealMultithreading extends Thread {
	
	private static Integer threadsCount;
	private static int globalSum = 0;
	private Integer [] arr;
	private int sizeOfEachOne;
	private int myIndex;
	private int start;
	private int end;
	private static Object lockSum = new Object();

	public RealMultithreading(int myIndex, Integer NbThreads, Integer arr[]){
		this.arr = arr;
		threadsCount = NbThreads;
		sizeOfEachOne = arr.length/NbThreads;
		this.myIndex = myIndex;
	}

	@Override
	public void run(){
		start = myIndex * sizeOfEachOne;
		end = (myIndex == threadsCount -1) ? arr.length : start + sizeOfEachOne;
		int sum = 0;
		for (int i = start; i < end; i++){
			sum += arr[i];
		}
		synchronized(lockSum){
			globalSum +=sum;
		}

		System.out.println(getName() + ": from " + start + " to " + (end - 1) + " sum is " + sum);
	}
	public static int getGlobalSum() {
		return globalSum;
	}

}




// ================== my fist Code ====================

// public class RealMultithreading extends Thread {
	
// 	private static Integer threadsCount;
// 	private static int indexOrder = 0;
// 	private static int newStart = 0;
// 	private static int shunkVar = 0;
// 	private static int globalSum = 0;
// 	private Integer [] arr;
// 	private Integer size;
// 	private int sizeOfEachOne;
// 	private int myIndex;
// 	private int start;
// 	private int end;
// 	private static Object lock = new Object();


// 	public RealMultithreading(int myIndex, Integer size, Integer NbThreads, Integer arr[]){
// 		this.size = size;
// 		this.arr = arr;
// 		threadsCount = NbThreads;
// 		sizeOfEachOne = size/NbThreads;
// 		this.myIndex = myIndex;
// 	}
// 	private void shunkArr(){
// 		synchronized(lock){
// 			while (indexOrder != myIndex) {
// 				try {
// 					lock.wait();
// 				} catch (Exception e) {
// 					Thread.currentThread().interrupt();
// 					return ;
// 				}
// 			}
// 			start = newStart;
// 			if (indexOrder == threadsCount - 1){
// 				end = size;
// 				indexOrder = -1;
// 			}
// 			else{
// 				end = shunkVar + sizeOfEachOne;
// 			}
// 			newStart = shunkVar + sizeOfEachOne;
// 			shunkVar += sizeOfEachOne ;
// 			indexOrder++;
// 			lock.notifyAll();
// 		}
// 	}

// 	public static void resetSharedState() {
// 		indexOrder = 0;
// 		newStart = 0;
// 		shunkVar = 0;
// 		globalSum = 0;
// 	}
// 	@Override
// 	public void run(){
// 		shunkArr();
// 		int sum = 0;
// 		for (int i = start; i < end; i++){
// 			sum += arr[i];
// 		}
// 		synchronized(lock){
// 			globalSum +=sum;
// 		}
// 		System.out.println(getName() + ": from " + start + " to " + (end - 1) + " sum is " + sum);
// 	}
// 	public static int getGlobalSum() {
// 		return globalSum;
// 	}

// }