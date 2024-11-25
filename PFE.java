import java.time.LocalDateTime;
import java.util.ArrayList;

public class PFE {
    private String titre;
    private LocalDateTime date;
    private String lieu;
    private String note;
    private String statut;
    Jury jury;
    ArrayList<Etudiant> etudiants ;
    public PFE(String titre, LocalDateTime date, String lieu, String note, Jury jury) {
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.note = note;
        this.jury = jury;
        this.etudiants = new ArrayList<>();
        this.statut = "En attente";
    }

    public PFE(String titre, LocalDateTime date, String lieu, String note, Jury jury, ArrayList<Etudiant> etudiants, String statut) {
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.note = note;
        this.jury = jury;
        this.etudiants = etudiants;
        this.statut = statut;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getLocale() {
        return lieu;
    }
    public void setLocale(String locale) {
        this.lieu = locale;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Jury getJury() {
        return jury;
    }
    public void setJury(Jury jury) {
        this.jury = jury;
    }
    public ArrayList<Etudiant> getEtudiants() {
        return etudiants;
    }
    public void setEtudiants(ArrayList<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    
}
