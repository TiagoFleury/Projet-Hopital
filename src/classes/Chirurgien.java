package classes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("rawtypes")
public class Chirurgien implements Comparable{
    private String nom;
    private ArrayList<Chirurgie> sesChirurgies;
    private ArrayList<Double> proportionsJoursTravail;
    private ArrayList<LocalDate> joursDeTravail ;
    private double[] statsTempsMoyenDeChirurgie; // [temps, borne1 IC, borne2 IC]
    private double[] statsTempsDeTravail;        //  [temps, borne1 IC, borne2 IC]
    public double[] statsTempsInteroperatoire;  // [temps, borne1 IC, borne2 IC]
    public int nbChirurgies;
    private ArrayList<Double> tempsDesChirurgies;
    
    
  
    public Chirurgien(String nom) {
		this.nom = nom;
		this.proportionsJoursTravail=null;
		this.statsTempsMoyenDeChirurgie = new double[3];
		this.statsTempsInteroperatoire = new double[3];
		this.statsTempsDeTravail = new double[3];
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
    
    
    

    
    
    
   
    
    

    // Detection d'anomalies de Chirurgien
	public ArrayList<LocalDate> anomaliesSurchargeChirurgienOuPas(BaseDeDonnees database) {
		double sum=0;
		Journee j = null;
		ArrayList<LocalDate> lesJSurcharges = new ArrayList<>();
		for (int i = 0 ; i < database.listeJournees.size() ; i++) {
			sum=0;
			j=database.getJournee(i);
			for (Chirurgie ch : j.getChirurgiesJour()) {
				if (ch.estEnConflit()==false) {
					if (ch.getChirurgien().equals(this)) {
						sum+= ch.getDuree();
					}
				}
			}
			if ( sum > this.getICtempsTravailParJour().get(2) ) {
				lesJSurcharges.add(j.getDate());
			}
		}
		return lesJSurcharges;
	}
		
	// Avec cette methode, quand je voudrai savoir si je peux lui refiler une chirurgie (via correction d'un conflit)
	// alors je verifierai que ce chirurgien n'est pas deja en train d'etre surchagé de travail ce jour là (via contains).
		
	
    
    
    
    
    
    


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
		return statsTempsMoyenDeChirurgie[0];
	}
	public ArrayList<Double> getICtempsMoyen(){
		ArrayList<Double> retour = new ArrayList<Double>();
		retour.add(statsTempsMoyenDeChirurgie[1]);
		retour.add(statsTempsMoyenDeChirurgie[2]);
		
		return retour;
	}
	


	public double getTempsTravailMoyenParJour() {
		return statsTempsDeTravail[0];
	}
	
	public ArrayList<Double> getICtempsTravailParJour(){
		ArrayList<Double> retour = new ArrayList<Double>();
		retour.add(statsTempsDeTravail[1]);
		retour.add(statsTempsDeTravail[2]);
		
		return retour;
	}

	
	public double getTempsInteroperatoireMoyen() {
		return statsTempsInteroperatoire[0];
	}
	public ArrayList<Double> getICtempsInteroperatoire(){
		ArrayList<Double> retour = new ArrayList<Double>();
		retour.add(statsTempsInteroperatoire[1]);
		retour.add(statsTempsInteroperatoire[2]);
		
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
		statsTempsMoyenDeChirurgie[0] = moyenne;
	}

	public void setICtempsMoyen(ArrayList<Double> IC95) {
		statsTempsMoyenDeChirurgie[1] = IC95.get(0);
		statsTempsMoyenDeChirurgie[2] = IC95.get(1);
	}
	
	public void setTempsInteroperatoireMoyen(double moyenne) {
		statsTempsInteroperatoire[0] = moyenne;
	}
	
	public void setICtempsInteroperatoire(ArrayList<Double> IC95) {
		statsTempsInteroperatoire[1] = IC95.get(0);
		statsTempsInteroperatoire[2] = IC95.get(1);
	}
	
	
	public void setMoyenneJours(ArrayList<Double> listemoyennes) {
		proportionsJoursTravail=listemoyennes;
	}
	
	public void setChirurgies(ArrayList<Chirurgie> liste) {
		sesChirurgies = liste;
	}




	public void setTempsTravailMoyenParJour(double moyenne) {
		statsTempsDeTravail[0] = moyenne;
	}
	
	public void setICtempsTravailParJour(ArrayList<Double> IC95) {
		statsTempsDeTravail[1] = IC95.get(0);
		statsTempsDeTravail[2] = IC95.get(1);
	}



}
