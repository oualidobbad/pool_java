public class Main {
	public static void main(String[] args) {
		try {
			Integer sizeArr;
			Integer threadsCount;

			if (args.length != 2)
				throw new IllegalArgumentException("you must enter 2 Args => [ --arraySize=NB] [--threadsCount=NB]");
			sizeArr = Integer.parseInt(args[0].trim().split("=")[1]);
			threadsCount =  Integer.parseInt(args[1].trim().split("=")[1]);

			System.out.println(sizeArr);
			System.out.println(threadsCount);
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.err.println("Error: check args");
		}
		catch (NumberFormatException e) {
			System.out.println("Error: check number of each arg");

		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
}
