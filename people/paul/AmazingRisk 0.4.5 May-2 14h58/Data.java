import java.util.*;

public class Data implements java.io.Serializable{
	
	LinkedList<DataElt> list;
	long time;
	
	public Data(){
		this.list=new LinkedList<>();
		time = System.currentTimeMillis();
	}

	public Data(LinkedList<DataElt> l){
		this.list=l;
	}
	
	public LinkedList<DataElt> getList(){
		return list;
	}
	
	public void addElt(DataElt d){
		list.add(d);
	}
	
	public String toString(){
		StringBuilder s= new StringBuilder();
		for(DataElt d: list)
			s.append(d.toString()).append(" ");
		return s.toString();
	}
	
	public long getTime(){
		return time;
	}
	
	public boolean equals(Object o){
		if (!(o == null)&&(o instanceof Data)){
			return (list.equals(((Data)o).getList())&&time==((Data)o).getTime());
		}
		return false;
	}
	public void clear() {
		list.clear();
	}

}

