package Module01.ex03;

public class Program {
	public static void swap(int a, int b){
		int tmp = a;
		a = b;
		b = tmp;
	}
	public static void main (String[]args){
		int a = 9;
		int b = 8;
		swap(a, b);
		System.out.println("a: " + a + " b: " + b);
	}
}
