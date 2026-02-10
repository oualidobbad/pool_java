package ex4;

import java.util.Scanner;

public class Program {

    static int max(int[] arr, int[] charac, int index) {
        int max = 0;
        int j = 0;

        for (int i = 0; i < 65535; i++) {
            if (arr[i] != 0 && max < arr[i]) {
                charac[index] = i;
                max = arr[i];
                j = i;
            }
        }
        arr[j] = 0;
        return max;
    }

    public static void main(String[] args) {
        int[] arr = new int[65535];
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        char[] characters = line.toCharArray();

        int[] maxes = new int[10];
        int[] charac = new int[10];

        for (char c : characters) {
            arr[c]++;
        }

        for (int i = 0; i < 10; i++) {
            maxes[i] = max(arr, charac, i);
        }

        if (maxes[0] == 0) {
            scanner.close();
            return;
        }
		for (int i = 0; i < 10; i++)
		{
			if (maxes[0] == maxes[i])
				System.out.print(maxes[i] + " ");
			else
			{
				System.out.println();
				break;
			}
		}
        int hash = 10;
        for (int i = 1; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                int height = (maxes[j] * 10) / maxes[0];

                if (i == 11) {
                    System.out.print((char) charac[j] + " ");
                    continue;
                }

                if (height >= hash) {
                    System.out.print("# ");
                } else if (height == hash - 1) {
					if (maxes[j] != 0)
                    	System.out.print(maxes[j] + " ");
                }
				else
                   break;
            }
            System.out.println();
            hash--;
        }

        scanner.close();
    }
}