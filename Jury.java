import java.util.ArrayList;

public class Jury {
    President president;
    Rapporteur rapporteur;
    Examinateur  examinateur;
    ArrayList<Encadreur> encadreurs;
    public Jury(President president, Rapporteur rapporteur, Examinateur examinateur, ArrayList<Encadreur> encadreurs) {
        this.president = president;
        this.rapporteur = rapporteur;
        this.examinateur = examinateur;
        this.encadreurs = encadreurs;
    }
    


}
