import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Encadreur extends Personne {
 public Encadreur(String nom, String prenom,int matricule) { super(nom,prenom,matricule);}
 
    public boolean enseignantExisteDeja(Connection connection) throws SQLException {

        String query = "SELECT COUNT(*) FROM Enseignant WHERE idEnseignant = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, getMatricule());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next(); // Aller à la première ligne
        int count = resultSet.getInt(1); // Récupérer le résultat COUNT(*)
        preparedStatement.close();
        return count > 0;
    }
    public void ajouterEnseignant() throws SQLException,EtudiantDejaPresentException{
        Connection connection = DatabaseConnector.getConnection() ; 
        if  (!enseignantExisteDeja(connection)) {
        String query = "INSERT INTO Enseignant  VALUES (?,?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, getMatricule());
            // Définir les valeurs des paramètres de la requête
            preparedStatement.setString(2, getNom());
            preparedStatement.setString(3, getPrenom());

            // Exécuter la requête pour ajouter l'étudiant à la table Etudiant
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Énseignant ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout de l'enseignant.");
            }
            
            // Fermer la PreparedStatement
            preparedStatement.close();}else{
                throw  new EtudiantDejaPresentException("L'enseignant est déjà présent dans la base de données!");
               
        }
    }
   
 }


