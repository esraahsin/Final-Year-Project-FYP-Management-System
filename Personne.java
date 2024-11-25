public abstract class Personne {
    private String nom,prenom; int matricule ;
    public Personne(String n , String p , int m){
        nom=n;
        prenom=p;
        matricule=m;
    }
    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;

    }
    public int getMatricule(){
        return matricule;
    }
    public void setNom(String n){
        nom=n;
    }
    public void setPrenom(String p){
        prenom=p;
    }
    public void setMatricule(int m){
        matricule=m;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Personne other = (Personne) obj;
        if (matricule != other.matricule)
            return false;
        return true;
    }
    
    
    
    

}