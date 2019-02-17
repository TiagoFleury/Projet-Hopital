package classes;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Bloc implements Comparable{
    private String nom;
    private int id;
    private ArrayList<Double> moyennesJours;
    private ArrayList<Chirurgie> chirurgies;

    public Bloc(String leNom) {
    	nom = leNom;
    	
    	id = Integer.parseInt(leNom.substring(6));
    	
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

    public ArrayList<Chirurgie> getChirurgies(){
    	return chirurgies;
    }

    @Override
    public boolean equals(Object o) {
    	if(this == o) {
    		return true;
    	}
    	if(o instanceof Bloc) {
    		Bloc b = (Bloc) o;
    		if(b.id == id)
    			return true;
    	}
    	return false;
    }

	@Override
	public int compareTo(Object o) {
		if(o instanceof Bloc) {
			Bloc b = (Bloc)o;
			return this.id - b.id;
		}
		return 0;
	}
	
	
	
	// Mutateurs //
	public void setMoyennesJours(ArrayList<Double> listemoyennes) {
		moyennesJours=listemoyennes;
	}
	public void setChirurgies(ArrayList<Chirurgie> liste) {
		chirurgies = liste;
	}
}
