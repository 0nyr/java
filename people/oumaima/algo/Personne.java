
public class Personne {
    private String nom ;
    private String prenom ; 
    private String dateNaissance ; 
    private String mail ;
    
    //constructeur 
    
    public Personne ( String nom , String prenom , String dateNaissance, String mail ){
        this.nom=nom ; 
        this.prenom=prenom ; 
        this.dateNaissance=dateNaissance;
        this.mail=mail ; 
    }
    
    //methodes 
    
    public String getNom() {
        return nom ;
    }
 
    public String getPrenom() {
        return prenom ;
    }
    
    public String getDateNaissance(){
        return dateNaissance ; 
    }
    
    public String getMail(){
        return mail ;
    }
    
    public String toString(){
        String s = "Nom:"+nom+ " Prénom:"+prenom+ " Date de naissance:"+dateNaissance+ " Adresse Mail:"+mail ;
        return s ; 
    }
    
    
}



/*public class  Bureau extends Membre {
    private String role ;
    
    public Bureau ( String nom , String prenom , String dateNaissance, String mail , int id , String role ){
        super(nom , prenom , dateNaissance, mail, id);
        this.role=role ;
    }
    
    public String toString(){
        return super.toString()+ " role:"+role ;
    }
    
    public String getRole(){
        return role ;
    } 
    
}
*/

