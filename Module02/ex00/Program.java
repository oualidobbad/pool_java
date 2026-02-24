import java.io.FileInputStream;
import java.io.InputStream;

public class Program {
    public static void main(String [] args){
        try{
            InputStream st = new FileInputStream("file.txt");
            int data;
            while ((data = st.read()) != -1) {
                System.out.print((char)data);
            }

        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
