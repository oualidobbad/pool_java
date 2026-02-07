package ex3;

import java.util.Scanner;

class Program {
	public static void main(String []args){
		Scanner sc = new Scanner(System.in);
		int iteration;
		String week;
		int minOfEachWeek = 10;
		int number = 0;
		long statistic = 0;
		long statisticReverse = 0;

		for (iteration = 1; iteration <= 18; iteration++){
			week = sc.nextLine();
			if (week.equals("42"))
				break ;
			if(!week.equals("week " + iteration))
			{
				System.out.println("IllegalArgument");
				System.exit(-1);
			}
			minOfEachWeek = 10;
			for (int i = 0; i < 5; i++)
			{
				number = sc.nextInt();
				if (number > 9 || number <= 0)
				{
					System.out.println("IllegalNumber");
					System.exit(-1);
				}
				if (number < minOfEachWeek)
					minOfEachWeek = number;
			}
			sc.nextLine();
			statistic = (statistic + minOfEachWeek) * 10L;
		}
		statistic /= 10L;
		long nb = 0;
		while (statistic > 0) {
			statisticReverse += statistic % 10;
			statistic /= 10;
			statisticReverse *= 10;
		}
		statisticReverse /= 10;
		iteration = 1;
		while (statisticReverse > 0) {
			System.err.print("week " + iteration++);
			nb = statisticReverse % 10;
			while (nb > 0) {
				System.out.print("=");
				nb--;
			}
			System.out.println(">");
			statisticReverse /= 10;
		}
		sc.close();
	}
}
