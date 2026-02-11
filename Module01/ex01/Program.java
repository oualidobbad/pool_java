package Module01.ex01;

public class Program {
	public static void main(String[] args) {
		User user1 = new User("oualid", 50);
		System.out.println("========== user1 ==========");
		user1.printInfo();
		User user2 = new User("obbad", 100);
		System.out.println("========== user2 ==========");
		user2.printInfo();
	}
}
