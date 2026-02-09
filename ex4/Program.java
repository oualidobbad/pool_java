package ex4;

import java.util.Scanner;

public class Program {

	static int max(int[] arr, int charac[], int index){
		int max = 0;
		int j = 0;

		for (int i = 0; i < 65535; i++)
		{
			if (arr[i] != 0 && max < arr[i])
			{
				charac[index] = i;
				max = arr[i];
				j = i;
			}
		}
		arr[j] = 0;
		return max;
	}

	public static void main (String[]args){
		int[]arr = new int[65535];
		int [][] matrix = new int[12][10];
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		char [] caracaters = line.toCharArray();
		int maxes[] = new int[10];
		int charac[] = new int [10];
		// int max;

		for (char c : caracaters)
			arr[c]++;
		for (int i = 0; i < 10; i++)
			maxes[i] = max(arr, charac, i);
		for (int i = 0; i < 10; i++)
		{
			if (maxes[0] == maxes[i])
				matrix[0][i] = maxes[0];
			else
				break;
		}
		
		for (int i = 0; i < 10; i++)
			System.out.println((char)charac[i]);

		scanner.close();
	}
}
