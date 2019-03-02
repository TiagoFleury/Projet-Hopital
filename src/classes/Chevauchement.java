package classes;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;

public class Chevauchement extends Conflit {
    private Bloc sallePb;
    private Chirurgien chirurgienPb;
    
    public Chevauchement(Chirurgie ch1, Chirurgie ch2, Journee j){
        super(ch1,ch2,j);
        this.sallePb = ch1.getSalle();
        this.chirurgienPb=ch1.getChirurgien();
    }
    
    
    
    
    
    
    // RESOLUTION CHEVAUCHEMENT 
    ///////////////////////////////////////////////
    
    public void resoudreChevauchementCout0(BaseDeDonnees database, Chevauchement c) {
    	Chirurgien chirugienPb = c.getChirurgienPb();
    	Chirurgien unChirurgien = null ;
    	Bloc sallePb = c.getSallePb();
    	Bloc uneSalle = null;
    	boolean a=false, b=false;
    	
    	// Resolution de l'ubiquité
    	int compteur = 0, lg = database.getTousChirurgiens().size();
    	
    	while (compteur < lg) {
    		unChirurgien = database.getTousChirurgiens().get(compteur);
    		if (!this.sallesOccupeesduJour.contains(unChirurgien)) {
				c.getCh1().setChirurgien(unChirurgien);
				a = true;
				compteur = lg;
			}
    		compteur ++ ;
    	}
    	
    	// Resolution de l'interference
    	compteur = 0;
    	lg = database.getTousBlocs().size();
    	while (compteur < lg) {
    		uneSalle = database.getTousBlocs().get(compteur);
    		if (!this.sallesOccupeesduJour.contains(uneSalle)) {
				c.getCh1().setSalle(uneSalle);
				b = true;
				compteur = lg;
			}
    		compteur ++ ;
    	}
    	
    	
    	if (a==true && b==true) {
    		c.setEtat(true);
			System.out.println("Chevauchement resolu");
				}
    	else { 
    		c.setEtat(false);
    		System.out.println("Chevauchement non resolu");}
    }
    
    // ACCESSEURS // 
    
    public Bloc getSallePb() {
    	return this.sallePb;
    }
    public Chirurgien getChirurgienPb() {
    	return this.chirurgienPb;
    }
    
    
    
}
