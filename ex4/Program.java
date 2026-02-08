package ex4;

import java.util.Scanner;

public class Program {
	public static void main (String[]args){
		int[]arr = new int[65535];
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		char [] caracaters = line.toCharArray();

		for (char c : caracaters)
		{
			arr[c]++;
		}
		for (int i = 0; i < 65535; i++)
		{
			if (arr[i] != 0)
			{
				System.out.print((char)i + " ");
				System.out.println(arr[i]);
			}
		}
		scanner.close();
	}
}
