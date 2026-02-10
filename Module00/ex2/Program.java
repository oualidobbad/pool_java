package ex2;

import java.util.Scanner;

class Program {

	static boolean isPrime(int n)
	{
		if (n <= 1)
			return false;
		for (int i = 2; i * i <= n; i++)
		{
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public static void main (String []args)
	{
		Scanner sc = new Scanner(System.in);
		int count = 0;

		while (true) {
			int number = sc.nextInt();
			int sum = 0;

			if (number == 42)
				break;
			while (number > 0) {
				sum += number % 10;
				number /= 10;
			}
			if (isPrime(sum))
				count++;
		}
		System.out.println("Count of coffee-request : " + count);
		sc.close();
	}
}
