import java.nio.file.Path;

public class Program {

    public static void main(String [] args)
    {
        Path current = Path.of("/home/oualid");

       current = current.resolve("Document");
       current = current.resolve("obbad/../../HELLO");
       current = current.normalize();
       System.out.println(current);
       System.out.println(current.getParent());
       System.out.println(current.normalize());
       System.out.println(current.getFileName());
       System.out.println(current.isAbsolute());
    }
}