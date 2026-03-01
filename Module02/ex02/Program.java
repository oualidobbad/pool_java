
import java.io.File;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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

	static void listFiles(Path current) throws Exception{
        File file = new File(current.toString());

        File [] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++)
            System.out.println(listFiles[i].getName() + " " + listFiles[i].length()/1024 + "KB");

	}

    static void moveOrRenameTo(Path current, String arg1, String arg2) throws Exception{

        Path what = current.resolve(arg1).normalize();
        Path where = current.resolve(arg2).normalize();

        if (!Files.exists(what))
            throw new Exception("mv: " + what.getFileName() + ": No such file or directory");
        if (Files.exists(where) && Files.isDirectory(where)) {
            where = where.resolve(what.getFileName()).normalize();
        }  
        Files.move(what, where);
    }  

    static String getLine(Scanner sc){
        System.out.print("-> ");
        if (!sc.hasNextLine())
            throw new ExitException("good luck");
        return sc.nextLine().trim();
    }
    
    
    public static void main(String [] args)
    {
        Path current = Path.of("").toAbsolutePath();
		Scanner sc = new Scanner(System.in);

		while (true) {
			try{
				System.out.println(current);
				String input = getLine(sc);
                String line[] = input.split(" ");
				if (line[0].trim().equals("exit"))
					break ;
				else if (line[0].trim().equals("cd") && line.length == 2)
					current = changeDir(current, line[1].trim());
				else if (line[0].trim().equals("ls") && line.length == 1)
					listFiles(current);
				else if (line[0].trim().equals("mv")&& line.length == 3)
					moveOrRenameTo(current, line[1], line[2]);
                else
                    System.out.println(input + ": Command Not Found");

			}
            catch(ExitException e)
            {
                System.out.println(e.getMessage());
                return ;
            }
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		sc.close();
    }
}