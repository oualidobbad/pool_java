public class Main {
	public static void main(String[] args) {
		

		try {
			if (args.length == 0)
				throw new IllegalArgumentException("args must {name Program} {--count=nb} ") ;
			String argSplit[] = args[0].trim().split("=");
			if (!argSplit[0].equals("--count"))
				throw new IllegalArgumentException("argmust found {--count} ") ;
			Thread t1 = new Thread(new RunnableDemo("Egg", Integer.parseInt(argSplit[1])));
			Thread t2 = new Thread(new RunnableDemo("Hen", Integer.parseInt(argSplit[1])));
	
			t1.start();
			t2.start();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		catch(NumberFormatException n){
			System.err.println("Error: invalid number of count");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

	}
}
