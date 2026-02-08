package ex4;

import java.util.Scanner;

public class Program {

	static int max(int[] arr){
		int max = 0;

		for (int i = 0; i < 65535; i++)
		{
			if (arr[i] != 0 && max < arr[i])
			{
				max = arr[i];
				arr[i] = 0;
			}
		}
		return max;
	}

	public static void main (String[]args){
		int[]arr = new int[65535];
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		char [] caracaters = line.toCharArray();
		int barr = 10;
		int maxCount;
		int max;

		for (char c : caracaters)
			arr[c]++;
		maxCount = max(arr);
		max = maxCount;
		for (int i = 0; i < 9; i++)
		{
			int scaledHeight = (max * 10) / maxCount;
			for (int j = 10; j > 0; j--)
			{
				if (scaledHeight >= j)
					System.out.print("#");
				else
					System.out.print(" ");
			}
			System.out.println();
			max = max(arr);
		}
		scanner.close();
	}
}
