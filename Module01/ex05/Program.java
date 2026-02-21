public class Program {
	public static void main(String[] args) {
		Menu menu = new Menu();
		try{

			menu.lancerMenu();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
