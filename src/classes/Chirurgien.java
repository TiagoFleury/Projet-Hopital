package classes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Chirurgien implements Comparable{
    private String nom;
    private ArrayList<Chirurgie> sesChirurgies;
    private ArrayList<Float> moyennesJours;
    
    
    
  
    public Chirurgien(String nom) {
		this.nom = nom;
		this.moyennesJours=null;
	}
    
    
    
    
    @Override
    public boolean equals(Object o) {
    	if (o==null) {
    		return false;
    	}
    	if (this==o) {
    		return true;
    	}
    	if (this.getClass()!=o.getClass()) {
    		return false;
    	}
    	Chirurgien other = (Chirurgien) o;
    	
    	if (!other.getName().equals(this.nom)) {
    		return false;
    	}
    	return true;
    }
    
    
    
    
    public void remplirAttributsChirurgieEtMoyenne(BaseDeDonnees database) {
    	int lundi = 0, mardi = 0, mercredi = 0, jeudi = 0, vendredi =0, samedi =0, dimanche = 0;
    	
    	for (Chirurgie c : database.getTousChirurgies()) {
    		if (c.getChirurgien().equals(this)){
    			sesChirurgies.add(c);
    			if (c.getDate().getDayOfWeek().equals("Monday")){
    				lundi+=1;
    			}
    			else if ()
    		}
    	}
    }
    


    @Override
    public String toString() {
    	return nom;
    }

	//ACCESSSEURS
    public String getName() {
    	return nom;
    }
    public ArrayList<Chirurgie> getChirurgies(){
    	return sesChirurgies;
    }


	@Override
	public int compareTo(Object o) {
		return this.nom.compareTo(((Chirurgien)o).getName());
	}
}
