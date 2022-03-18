public class DataElt implements java.io.Serializable{
	
	String value;
	
	public DataElt(String s) {
		this.value=s;
	}

	//Sert pour tous les éléments qui vont extends cette classe -> pas besoin de mettre aucun String comme ça
    public DataElt() {}

    public String toString(){
		return value;
	}
	
}