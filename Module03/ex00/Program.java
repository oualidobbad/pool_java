public class Program {

	public static void println(String message) {
        System.out.println(message);
    }
	public static void main(String args[]){
		try {
			if (args.length == 0)
				return ;
			String argSplit[] = args[0].trim().split("=");
			if (!argSplit[0].equals("--count"))
				throw new IllegalArgumentException("argmust found {--count} ") ;
		
		int count = Integer.parseInt(argSplit[1]);
		Thread egg = new ThreadTest("Egg", count);
		Thread hen = new ThreadTest("Hen", count);
		egg.start();
		hen.start();
		egg.join();
		hen.join();
		for (int i = 0; i < count; i++)
			println("Humman");
		}catch (NumberFormatException n) 
		{
			println("Error in number of --count=??");
		}
		catch (Exception e) {
			println(e.getMessage());
		}

	}
}
