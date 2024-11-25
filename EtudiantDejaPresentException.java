
public class EtudiantDejaPresentException extends Exception{
    /*
     * Classe d'exception pour signaler que l'etudiant est deja present dans la liste des etudiants.
     */

    public EtudiantDejaPresentException(String message){
        super(message);
    }

}
