import java.io.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EmailServlet")
public class EmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> emailAddresses;
    private String filePath;

    @Override
    public void init() throws ServletException {
        filePath = getInitParameter("filePath");

        if (filePath == null || filePath.isEmpty()) {
            throw new ServletException("File path parameter not specified");
        }

        try {
            loadEmailAddresses();
        } catch (IOException | ClassNotFoundException e) {
            throw new ServletException("Error loading email addresses", e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   
        request.setAttribute("emailAddresses", emailAddresses);

        
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("email");

        try {
            if ("Subscribe".equals(action)) {
                addEmail(email);
            } else if ("Delete".equals(action)) {
                deleteEmail(email);
            }

            // Display the updated list of email addresses
            request.setAttribute("emailAddresses", emailAddresses);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    private void loadEmailAddresses() throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            
            EmailList emailList = (EmailList) in.readObject();
            emailAddresses = emailList.getEmailAddresses();
        } catch (FileNotFoundException e) {
        
            emailAddresses = new ArrayList<>();
        }
    }
    private void addEmail(String email) throws IOException {
        // Perform validation if needed
        emailAddresses.add(email);
        saveEmailAddresses();
    }

    private void deleteEmail(String email) throws IOException {
        // Perform validation if needed
        emailAddresses.remove(email);
        saveEmailAddresses();
    }

    private void saveEmailAddresses() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(emailAddresses);
        }
    }
}
