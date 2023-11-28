<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<head>
    <title>List Manager</title>
   <!DOCTYPE html>
<html>
<head>
    <title>Email Manager</title>
     <style>
        .notification {
            display: none;
            padding: 15px;
            background-color: #4CAF50;
            color: white;
            margin-bottom: 15px;
        }
    </style>
    <script>
        function showNotification(message) {
            var notificationDiv = document.getElementById("notification");
            notificationDiv.innerHTML = message;
            notificationDiv.style.display = "block";

            // Masquer la notification après quelques secondes
            setTimeout(function() {
                notificationDiv.style.display = "none";
            }, 3000); // 3000 millisecondes = 3 secondes
        }

        // Votre fonction pour récupérer la réponse du serveur
        function handleResponse(response) {
            console.log("Handling server response:", response);
            showNotification(response);
            
           
        }
    </script>
</head>
<body>
    <h3>Membres</h3>
    
   
    <ul>
         <% 
            // Récupérer et afficher la liste des membres
            ArrayList<String> emailList = (ArrayList<String>)request.getAttribute("emailList");
            if (emailList != null) {
                for (String email : emailList) {
                    out.println("<li>" + email + "</li>");
                }
            }
      
        %>
    </ul>

    
    <form action="EmailManagerServlet" method="post">
        <label for="email"> Entrer votre adresse mail:</label>
        <input type="text" id="email" name="email"placeholder="Entrez votre adresse e-mail" >
       <button type="submit" name="action" value="ajouter" style="background-color: blue; color: white;border-radius: 5px;">Subscribe</button>
<button type="submit" name="action" value="supprimer" style="background-color: #f44336; color: white;border-radius: 5px;">Unsubscribe</button>

    </form>
    <!-- Ajout de la notification -->
   <div id="notification" class="notification" style="margin-top: 20px;"></div>
     <script>
        // Appel à handleResponse après la soumission du formulaire
        document.addEventListener("DOMContentLoaded", function() {
            handleResponse("<%= request.getAttribute("notificationMessage")%>");
        });
    </script>
    <!-- Ajout du lien vers la page de gestion des emails -->
   

   
</body>
 
</html>