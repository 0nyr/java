public class Membre extends Personne {
    private int id ; 
    private int compteur ;
    
    public Membre (String nom , String prenom , String dateNaissance, String mail , int id ){
        super ( nom , prenom , dateNaissance, mail);
        this.id=id ; 
        
    }
    
    public String toString(){
        return super.toString()+" Identifiant:"+ id ;     
    }
    
    public int getId(){ 
        return id ; 
    }
    
    
}

