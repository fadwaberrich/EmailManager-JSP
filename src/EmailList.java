import java.io.Serializable;
import java.util.ArrayList;

public class EmailList implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> emailAddresses;

    public EmailList(ArrayList<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public ArrayList<String> getEmailAddresses() {
        return emailAddresses;
    }
}
