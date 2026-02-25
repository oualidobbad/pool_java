import java.util.Scanner;

public class Program {
    public static void main(String [] args){
		ReadSignature signature= new ReadSignature();

		try {
			signature.parseSignature();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		ReadInputUsrFile usrin = new ReadInputUsrFile();
		Scanner sc = new Scanner(System.in);
		String line = "";
		while (true) {
			try
			{
				System.out.print("-> ");
				line = sc.nextLine().trim();
				if (line.equals("42"))
					return ;
				usrin.typeSignature(line);
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
    }
}
