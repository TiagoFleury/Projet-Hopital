package classes;
import java.util.Map;

public class Bloc implements Comparable{
    private String nom;
    private int id;
    

    public Bloc(String leNom) {
    	id = Integer.parseInt(leNom.substring(6));
    	nom = leNom;
    }
    
    
    
    
    @Override
    public String toString() {
    	return nom+"  id:"+id;
    }
    // Accesseurs //
    
    public String getName(){
    	return this.nom;
    }
    public int getID() {
    	return id;
    }




	@Override
	public int compareTo(Object o) {
		if(o instanceof Bloc) {
			Bloc b = (Bloc)o;
			return this.id - b.id;
		}
		return 0;
	}
}
