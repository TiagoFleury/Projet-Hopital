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
    
    
    

     // Detection d'anomalies
    
    public int anomalieChirurgienDureeInterOpeBlocOuPas(BaseDeDonnees database, Journee j, Chirurgie x) {
    	int retour = 0;
    	// On va retourner 0 s'il n'y pas d'anomalie, -1 sil y en a une avec la chirurgie juste avant, et 1 si cest avec celle d'apres, 2 si en anomalie avec celle d'avant et apres
    	
    	
    	ArrayList<Chirurgie> sesChirurgiesAuj = new ArrayList<>();
    	for (Chirurgie c : j.getChirurgiesJour()) {
    		if (c.getChirurgien().equals(this)) {
    			sesChirurgiesAuj.add(c);
    		}
    	}
    	Collections.sort(sesChirurgiesAuj, Chirurgie.CHRONOLOGIQUE);
    	int i = sesChirurgiesAuj.indexOf(this);
    	// On traite le cas de la chirurgie d'avant
    	boolean b1 = false, b2 = false;
    	if (i>0) {
    		if ( ( ChronoUnit.MINUTES.between(sesChirurgiesAuj.get(i-1).getFin(), x.getDebut()) < this.getICtempsInteroperatoire().get(1) ) ||  j.enMemeTempsOuPas(x,sesChirurgiesAuj.get(i-1)) )  {
        		b1=true;
        	}
    	}
    	
    	if (i<sesChirurgiesAuj.size()-1) {
    		if (ChronoUnit.MINUTES.between(x.getFin(), sesChirurgiesAuj.get(i+1).getDebut()) < this.getICtempsInteroperatoire().get(1)  ||  j.enMemeTempsOuPas(x,sesChirurgiesAuj.get(i+1)) ) {
    			b2=true;
    		}
    	}

    	if (b1==true && b2==false) {
    		retour=-1;
    	}
    	if (b1==false && b2==true) {
    		retour = 1;
    	}
    	if (b1==true && b2==true) {
    		retour = 2;
    	}
    	return retour;
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
