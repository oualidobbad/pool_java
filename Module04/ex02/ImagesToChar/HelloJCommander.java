import com.beust.jcommander.Parameter;
import com.beust.jcommander.JCommander;

public class HelloJCommander {
	@Parameter(names = {"--name", "-n"}, description = "Your name")
	String name ;
	@Parameter(names="-age")
	int age ;
	public static void main(String[] args) {
		try {
			HelloJCommander hello = new HelloJCommander();
			JCommander.newBuilder().addObject(hello).build().parse(args);
			hello.println();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
	}

	void println(){
		System.out.println("hi " + this.name + " you are " + this.age);
	}
}