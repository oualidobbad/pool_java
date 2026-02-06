package ex1;
import java.util.Scanner;

public class  Program {
	public static void main(String [] args){
		
		Scanner sc = new Scanner(System.in);
		int number = sc.nextInt();
		int i;

		if (number <= 1){
			System.err.println("IllegalArgument");
			System.exit (-1);
		}
		for (i = 2; i * i <= number; i++)
		{
			if (number % i == 0)
			{
				System.out.println("False " + (i - 1));
				System.exit(0);
			}
		}
		System.out.println("True " +  (i - 1));
	}
}
