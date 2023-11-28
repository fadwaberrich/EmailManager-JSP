import java.io.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EmailManagerServlet")
public class EmailManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "adresses.txt";

    @Override
    public void init() throws ServletException {
        // Obtenez le chemin absolu du fichier adresses.txt dans le répertoire src
        String realPath = getServletContext().getRealPath("/" + FILE_NAME);
        // Stockez le chemin absolu dans une constante pour une utilisation ultérieure
        FILE_PATH = realPath;
    }

    private static String FILE_PATH;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("Servlet doGet method called.");
    	 ArrayList<String> emailList = readEmailAddresses();
        // Initialiser la liste des membres (même si elle est vide au début)
       

    	 request.setAttribute("emailList", emailList);

    	    // Redirige vers la page JSP
    	    String string = "ListEmail.jsp";
    	    request.getRequestDispatcher(string).forward(request, response);
    	   
    }
    
 
    
    
    
    private String generateEmailListHtml(ArrayList<String> emailList) {
        StringBuilder html = new StringBuilder();
        for (String email : emailList) {
            html.append("<li>").append(email).append("</li>");
        }
        return html.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("email");

        if ("ajouter".equals(action)) {
        	if (email != null && !email.isEmpty()) {
                addEmailAddress(email);
                request.setAttribute("notificationMessage", "L'adresse " + email + " est inscrite");
            } else {
            	 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Veuillez saisir une adresse e-mail pour l'ajout.");
                 return;
            }
        }
        else if ("supprimer".equals(action)) {
            if (email != null && !email.isEmpty()) {
                boolean removed = removeEmailAddress(email);
                if (removed) {
                	  request.setAttribute("notificationMessage", "L'adresse " + email + " est supprimée");
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "L'adresse e-mail spécifiée n'existe pas pour la suppression.");
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Aucune adresse e-mail spécifiée pour la suppression.");
                return;
            }
        } 
       

        // Sauvegarde la nouvelle liste dans le fichier
        ArrayList<String> updatedEmailList = readEmailAddresses();
        request.setAttribute("emailList", updatedEmailList);
        String string = "ListEmail.jsp";
		request.getRequestDispatcher(string).forward(request, response);
    }

    private ArrayList<String> readEmailAddresses() {
        ArrayList<String> emailList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            emailList = (ArrayList<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Gérer l'exception (créer le fichier si absent, gérer les erreurs de désérialisation, etc.)
            e.printStackTrace();
        }

        return emailList;
    }

    private void addEmailAddress(String email) {
        ArrayList<String> emailList = readEmailAddresses();
        emailList.add(email);
        writeEmailAddresses(emailList);
    }

    private boolean removeEmailAddress(String email) {
        ArrayList<String> emailList = readEmailAddresses();
        boolean removed = emailList.remove(email);
        if (removed) {
            writeEmailAddresses(emailList);
        }
        return removed;
    }


    private void writeEmailAddresses(ArrayList<String> emailList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(emailList);
        } catch (IOException e) {
            // Gérer l'exception (créer le fichier si absent, etc.)
            e.printStackTrace();
        }
    }}

