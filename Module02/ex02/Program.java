
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
    public static void main(String [] args)
    {
        Path current = Path.of("").toAbsolutePath();
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println(current);
			String arg = sc.nextLine();
			if (arg.equals("42"))
				return ;
			current = changeDir(current, arg);
		}
    }
}
// /home/