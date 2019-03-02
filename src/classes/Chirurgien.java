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
    private ArrayList<LocalDate> joursDeTravail ;
    private double[] StatsTempsMoyenDeChirurgie; // [temps, borne1 IC, borne2 IC]
    private double[] StatsTempsDeTravail;        //  [temps, borne1 IC, borne2 IC]
    public double[] StatsTempsInteroperatoire;  // [temps, borne1 IC, borne2 IC]
    public int nbChirurgies;
    private ArrayList<Double> tempsDesChirurgies;
    
    
  
    public Chirurgien(String nom) {
		this.nom = nom;
		this.proportionsJoursTravail=null;
		this.StatsTempsMoyenDeChirurgie = new double[3];
		this.StatsTempsInteroperatoire = new double[3];
		this.StatsTempsDeTravail = new double[3];
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
    
    
    

    // Detection d'anomalies
	public boolean anomalieSurchargeChirurgienOuPas() {
    	boolean b = false;
    	double sum=0;
    	int nombreCh = 0;
    	for (Chirurgie c : chirurgiesDuJour) {
    		if (c.getChirurgien().equals(albert)) {
        		sum += c.getDuree();
        		nombreCh ++ ;
    		}
    	}
    	if ( sum > albert.getICtempsTravailParJour().get(2) ) {
    		b = true;
    	}
    	return b;
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
		return StatsTempsMoyenDeChirurgie[0];
	}
	public ArrayList<Double> getICtempsMoyen(){
		ArrayList<Double> retour = new ArrayList<Double>();
		retour.add(StatsTempsMoyenDeChirurgie[1]);
		retour.add(StatsTempsMoyenDeChirurgie[2]);
		
		return retour;
	}
	


	public double getTempsTravailMoyenParJour() {
		return StatsTempsDeTravail[0];
	}
	
	public ArrayList<Double> getICtempsTravailParJour(){
		ArrayList<Double> retour = new ArrayList<Double>();
		retour.add(StatsTempsDeTravail[1]);
		retour.add(StatsTempsDeTravail[2]);
		
		return retour;
	}

	
	public double getTempsInteroperatoireMoyen() {
		return StatsTempsInteroperatoire[0];
	}
	public ArrayList<Double> getICtempsInteroperatoire(){
		ArrayList<Double> retour = new ArrayList<Double>();
		retour.add(StatsTempsInteroperatoire[1]);
		retour.add(StatsTempsInteroperatoire[2]);
		
		return retour;
	}
	
	public ArrayList<Double> getProportions(){
		return proportionsJoursTravail;
	}
	
	public ArrayList<Double> getLesTempsdeChirurgies(){
		return tempsDesChirurgies;
	}

	
	//MUTATEURS
	public void setTempsMoyenChirurgie(double moyenne) {
		StatsTempsMoyenDeChirurgie[0] = moyenne;
	}

	public void setICtempsMoyen(ArrayList<Double> IC95) {
		StatsTempsMoyenDeChirurgie[1] = IC95.get(0);
		StatsTempsMoyenDeChirurgie[2] = IC95.get(1);
	}
	
	public void setTempsInteroperatoireMoyen(double moyenne) {
		StatsTempsInteroperatoire[0] = moyenne;
	}
	
	public void setICtempsInteroperatoire(ArrayList<Double> IC95) {
		StatsTempsInteroperatoire[1] = IC95.get(0);
		StatsTempsInteroperatoire[2] = IC95.get(1);
	}
	
	
	public void setMoyenneJours(ArrayList<Double> listemoyennes) {
		proportionsJoursTravail=listemoyennes;
	}
	
	public void setChirurgies(ArrayList<Chirurgie> liste) {
		sesChirurgies = liste;
	}




	public void setTempsTravailMoyenParJour(double moyenne) {
		StatsTempsDeTravail[0] = moyenne;
	}
	
	public void setICtempsTravailParJour(ArrayList<Double> IC95) {
		StatsTempsDeTravail[1] = IC95.get(0);
		StatsTempsDeTravail[2] = IC95.get(1);
	}



}
