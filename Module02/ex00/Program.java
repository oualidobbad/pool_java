
public class Program {
    public static void main(String [] args){
		ReadSignature signature= new ReadSignature();
		try
		{
			signature.parseSignature();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
    }
}
