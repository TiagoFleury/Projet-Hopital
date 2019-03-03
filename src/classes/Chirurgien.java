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
    private ArrayList<Float> plagesHorairesHabituelles;
    private ArrayList<LocalDate> joursSurchargesDeTravail;
    
    
  
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
    
    
    

    
    
    
   
    public ArrayList<Chirurgie> recupChirurgiesDuJour(Journee j){
    	ArrayList<Chirurgie> chDuJ = new ArrayList<>();
    	for (Chirurgie c : j.getChirurgiesJour()) {
    			if (c.getChirurgien().equals(this)) {
    				chDuJ.add(c);
    			}
    	}
    	return chDuJ;
    }
    


	public int nombreDeChirurgiesDe(Bloc room, Journee jour) {
		int compteur = 0;
		for(Chirurgie c : recupChirurgiesDuJour(jour)) {
			if(c.getSalle().equals(room)) {
				compteur++;
			}
		}
		return compteur;
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

	public ArrayList<LocalDate> getLesJSurcharges(){
		return this.joursSurchargesDeTravail;
	}
	
	public ArrayList<Float> getPlagesHorairesPref(){
		return this.plagesHorairesHabituelles;
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
	
	public void setPlagesHorairesHabituelles(ArrayList<Float> prop) {
		this.plagesHorairesHabituelles=prop;
	}


	public void setJSurchargeTravail(ArrayList<LocalDate> lesj) {
		this.joursSurchargesDeTravail=lesj;
	}

	public Bloc getBlocFort(Journee jour, int seuil) { //Si il n'y a pas de bloc particulierement fort, retourne null
		
		Bloc bMax=null;
		int max=0;
		int compte;
		int tempsMax=0;
		ArrayList<Chirurgie> liste = new ArrayList<>();
		for(Bloc b : jour.getBlocs()) {
			compte=0;
			for(Chirurgie c : jour.getChirurgiesJour()) {
				if(c.getChirurgien().equals(this) && c.getSalle().equals(b)) {
					compte++;
					liste.add(c);
				}
				
			}
			if(compte>max) {
				bMax=b;
				max = compte;
				tempsMax=0; //On reset le temps max
				for(Chirurgie c : liste) {
					tempsMax+=c.getDuree();
				}
				
			}
			else if(compte==max & max>0) {//Si c'est le meme nombre, on compare le temps
				int somme=0;
				for(Chirurgie c : liste) {
					somme+=c.getDuree();
				}
				if(somme>tempsMax) {
					bMax=b;
					tempsMax=somme;
				}
			}
			liste.clear();
			
			
		}
		

		if(max >= seuil) { //A partir de 3 chirurgies, on va dire que c'est son temps fort
			return bMax;
		}
		return null;
	}



}
