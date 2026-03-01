
import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

public class Program {

    static Path changeDir(Path current, String arg) {

        Path target = current.resolve(arg).normalize();
		File file = new File(target.toString());

        if (!file.exists()) {
            System.out.println("no such file or directory: " + arg);
            return current;
        }
        if (!file.isDirectory()) {
            System.out.println("not a directory: " + arg);
            return current;
        }
        return target;
    }
	static void listFiles(Path current){
		Files.
	}
	// static void getLine(Scanner sc)
	// {

	// }
    public static void main(String [] args)
    {
        Path current = Path.of("").toAbsolutePath();
		Scanner sc = new Scanner(System.in);

		while (true) {
			try{
				System.out.println(current);
				String line[] = sc.nextLine().split(" ");
				if (line[0].trim().equals("42"))
					break ;
				else if (line[0].trim().equals("cd"))
					current = changeDir(current, line[1].trim());
				else if (line[0].trim().equals("ls"))
					break ;
				else if (line[0].trim().equals("mv"))
					break ;

			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		sc.close();
    }
}
// /home/