package classes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Chirurgien implements Comparable{
    private String nom;
    private ArrayList<Chirurgie> sesChirurgies;
    private ArrayList<Double> proportionsJoursTravail;
    public double tempsMoyenEntreDeuxChirurgies;
    private ArrayList<LocalDate> joursDeTravail ;
    private double tempsMoyenChirurgie;
    public int nbChirurgies;
    private ArrayList<Double> tempsDesChirurgies;
    
    
  
    public Chirurgien(String nom) {
		this.nom = nom;
		this.proportionsJoursTravail=null;
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
    
    
    
    
    public float proportionTempsdeChirurgieEnDureeInterOpe() {
    	float result = 0;
    	for (Chirurgie c : sesChirurgies) {
    		
    	}
    	return result;
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
	
	public ArrayList<Double> getProportions(){
		return proportionsJoursTravail;
	}
	
	public ArrayList<Double> getLesTempsdeChirurgies(){
		return tempsDesChirurgies;
	}

	
	//MUTATEURS
	public void setTempsMoyenChirurgie(double moyenne) {
		tempsMoyenChirurgie = moyenne;
	}
	
	public void setMoyenneJours(ArrayList<Double> listemoyennes) {
		proportionsJoursTravail=listemoyennes;
	}
	
	public void setChirurgies(ArrayList<Chirurgie> liste) {
		sesChirurgies = liste;
	}
}
