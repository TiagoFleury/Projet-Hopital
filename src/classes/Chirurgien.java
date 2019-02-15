package classes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Chirurgien implements Comparable{
    private String nom;
<<<<<<< HEAD
    private ArrayList<Chirurgie> sesChirurgies;
    private ArrayList<Float> moyennesJours;
    
=======
    private ArrayList<LocalDate> joursDeTravail ;
    private double tempsMoyenChirurgie;
>>>>>>> Branche-Tiago
    
    
  
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

	//ACCESSEURS
	public double getTempsMoyen() {
		return tempsMoyenChirurgie;
	}
	

	//MUTATEURS
	public void setTempsMoyenChirurgie(double moyenne) {
		tempsMoyenChirurgie = moyenne;
		
	}
}
