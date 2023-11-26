import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializeEmailList {
    public static void main(String[] args) {
        
        ArrayList<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("email1@example.com");
        emailAddresses.add("email2@example.com");

      
        EmailList emailList = new EmailList(emailAddresses);

        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("C:\\Users\\ASUS\\eclipse-workspace\\EmailManager\\WebContent\\adresses.txt"))) {
            out.writeObject(emailList);
            System.out.println("Objet sérialisé avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
