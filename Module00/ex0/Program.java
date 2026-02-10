package ex0;

public class Program {
	public static void main(String []args)
	{
		int num = 479598;
		int count = 0;
		int sum = 0;

		
		while (num > 0){
			count++;
			sum += num % 10;
			num /= 10;
		}
		if (count != 6)
			System.out.println("Error: the number not 6 numbers");
		else
			System.out.println(sum);
	}
}
