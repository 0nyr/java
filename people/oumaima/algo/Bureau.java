public class  Bureau extends Membre {
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
	
