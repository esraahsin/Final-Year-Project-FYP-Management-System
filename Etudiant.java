import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Etudiant extends Personne{
    Connection connection;
    private String specialite ;
    public Etudiant(String n,String p,int  m, String s){
        super(n, p, m);
        this.specialite = s;
    }
    public String getSpecialite(){
        return specialite;
    }
    public void setSpecialite(String s)
    {
        specialite=s;
    }
    public boolean etudiantExisteDeja(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM Etudiant WHERE idEtudiant = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, getMatricule());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next(); // Aller à la première ligne
        int count = resultSet.getInt(1); // Récupérer le résultat COUNT(*)
        preparedStatement.close();
        return count > 0;
    }
    public void ajouterEtudiant() throws SQLException,EtudiantDejaPresentException{
        connection = DatabaseConnector.getConnection();
        if  (!etudiantExisteDeja(connection)) {
        String query = "INSERT INTO Etudiant  VALUES (?,?, ?,? )";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, getMatricule());
            // Définir les valeurs des paramètres de la requête
            preparedStatement.setString(2, getNom());
            preparedStatement.setString(3, getPrenom());
            preparedStatement.setString(4, specialite);

            // Exécuter la requête pour ajouter l'étudiant à la table Etudiant
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Étudiant ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout de l'étudiant.");
            }
            
            // Fermer la PreparedStatement
            preparedStatement.close();}else{
                throw  new EtudiantDejaPresentException("L'étudiant est déjà présent dans la base de données!");
               
        }
    }
    
    

}
