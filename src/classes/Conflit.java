package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;

public class Conflit {
    protected Journee jour;
    protected LocalTime debut;
    protected LocalTime fin;
    protected Chirurgie chirurgie1;
    protected Chirurgie chirurgie2;
    protected boolean resolu;
    protected double nbMinutes;
    
   
    public Conflit(Chirurgie ch1, Chirurgie ch2, Journee j) {
    	this.chirurgie1=ch1;
    	this.chirurgie2=ch2;
    	this.resolu=false;
    	
    	
    	this.jour=j;
    	if (ch1.getDebut().isBefore(ch2.getDebut())){
    		this.debut=ch2.getDebut();
    	}
    	else { this.debut=ch1.getDebut();}
    	if (ch1.getFin().isBefore(ch2.getFin())){
    		this.fin=ch1.getFin();
    	}
    	else {this.fin=ch2.getFin();}
    	
    	nbMinutes = ChronoUnit.MINUTES.between(debut,fin);
		if(nbMinutes<0) { //C'est le cas ou une chirurgie est sur deux jours
			nbMinutes = ChronoUnit.MINUTES.between(LocalDateTime.of(jour.getDate(), debut),LocalDateTime.of(jour.getDate(), fin).plusDays(1));
		}
    	
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj==null) {
    		return false;
    	}
    	if (this==obj) {
    		return true;
    	}
    	if (this.getClass()!=obj.getClass()) {
    		return false;
    	}
    	Conflit objConf = (Conflit) obj;
    	if ( (this.getCh1().equals(objConf.getCh1()) && this.getCh2().equals(objConf.getCh2()))  ||   (this.getCh1().equals(objConf.getCh2()) && this.getCh2().equals(objConf.getCh1()))  )  {
    		return true;
    	}
    	return false;
    }
    
    
    
    
    public ArrayList<Chirurgien> getChirurgiensLibres1(){ //Retourne les chirurgiens libres sur la chirurgie 1 (mais qui sont utilises dans la journee)
    	ArrayList<Chirurgien> liste = new ArrayList<>();
    	ArrayList<Chirurgie> listeChir;
    	for(Chirurgien albert : jour.getChirurgiensMobilises()) { //Pour chaque chirurgiens du jour
    		boolean ajout=true;
    		//Il faut tester si la plage horaire est occupee par une chirurgie ou pas
    		listeChir = albert.recupChirurgiesDuJour(jour);
    		if(listeChir.size()==0)
    			ajout=false;
    		else {
	    		for(Chirurgie x : listeChir) {
	    			if(Journee.enMemeTempsOuPas(x,chirurgie1)) { //Si il y en a un qui est en meme temps, c'est que le chirurgien n'est pas libre sur l'horaire
	    				ajout = false;
	    			}
	    		}
    		}
    		if(ajout)
    			liste.add(albert);
    	}
    	
    	return liste;
    }
    
    public ArrayList<Chirurgien> getChirurgiensLibres2(){ //Retourne les chirurgiens libres sur la chirurgie 1 (mais qui sont utilises dans la journee)
    	ArrayList<Chirurgien> liste = new ArrayList<>();
    	ArrayList<Chirurgie> listeChir;
    	for(Chirurgien albert : jour.getChirurgiensMobilises()) { //Pour chaque chirurgiens du jour
    		boolean ajout=true;
    		//Il faut tester si la plage horaire est occupee par une chirurgie ou pas
    		listeChir = albert.recupChirurgiesDuJour(jour);
    		if(listeChir.size()==0)
    			ajout=false;
    		else {
	    		for(Chirurgie x : listeChir) {
	    			if(Journee.enMemeTempsOuPas(x,chirurgie2)) { //Si il y en a un qui est en meme temps, c'est que le chirurgien n'est pas libre sur l'horaire
	    				ajout = false;
	    			}
	    		}
    		}
    		if(ajout)
    			liste.add(albert);
    	}
    	
    	return liste;
    }
    
    

    
    // ACCESSEURS //
    public String toString() {
    	return "(c"+chirurgie1.getID()+",c"+chirurgie2.getID()+")";
    }
    
    public Chirurgie getCh1() {
    	return this.chirurgie1;
    }
    public Chirurgie getCh2() {
    	return this.chirurgie2;
    }
    public LocalDate getDate() {
    	return jour.getDate();
    }
    public Journee getJournee() {
    	return jour;
    }
    public boolean getEtat() {
    	return this.resolu;
    }
    
    public double getDuree() {
    	return this.nbMinutes;
    }
    
    
    
    
    // MUTATEURS //
    public void setEtat(boolean b) {
    	this.resolu=b;
    }
    
}
