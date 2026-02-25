import java.io.File;
import java.io.IOException;
import java.text.FieldPosition;
import java.util.Scanner;

public class Program {
    public static void main(String [] args){

		ReadInputUsrFile usrin;
		Scanner sc = new Scanner(System.in);
		String line = "";

		try 
		{
			usrin = new ReadInputUsrFile();
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
			return ;
		}
		while (true) {
			try
			{
				System.out.print("-> ");
				line = sc.nextLine().trim();
				if (line.equals("42"))
					return ;
				File file = new File(line);
				if (!file.isAbsolute())
				{
					System.out.println("enter full path:");
					continue ;
				}
				usrin.typeSignature(line);
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
    }
}
