import java.util.Arrays;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		try {
			if (args.length != 2)
				throw new IllegalArgumentException("you must enter 2 Args => [ --arraySize=NB] [--threadsCount=NB]");

			Integer sizeArr;
			Integer threadsCount;
			Integer[] arr;
			Random random;

			sizeArr = Integer.parseInt(args[0].trim().split("=")[1]);
			threadsCount = Integer.parseInt(args[1].trim().split("=")[1]);
			if (threadsCount <= 0 || threadsCount > sizeArr)
				throw new IllegalArgumentException(
						"The number of threads must be greater than one and not greater than size of array.");
			if (sizeArr <= 0 || sizeArr >= 2_000_000)
				throw new IllegalArgumentException("The size of Array must be greater than one and not greater than 2000000.");
			random = new Random();
			arr = new Integer[sizeArr];
			for (int i = 0; i < sizeArr; i++){
				arr[i] = random.nextInt(2000);
			}
			int sum = 0;
			for (int i = 0; i < sizeArr; i++) {
				sum += arr[i];
			}
			System.out.println("Sum: " + sum);
			Thread threads[] = new RealMultithreading[threadsCount];

			for (int i = 0; i < threadsCount; i++)
				threads[i] = new RealMultithreading(i,threadsCount, arr);
			for (int i = 0; i < threadsCount; i++)
				threads[i].start();
			for (int i = 0; i < threadsCount; i++)
				threads[i].join();
			System.out.println("Sum by threads: " + RealMultithreading.getGlobalSum());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: check args");
		} catch (NumberFormatException e) {
			System.out.println("Error: check number of each arg");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
