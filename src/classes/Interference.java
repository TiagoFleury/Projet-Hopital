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

public class Interference extends Conflit {
    private Bloc sallePb;
    private ArrayList<Chirurgien> chirurgiens;
    
    
    public Interference(Chirurgie ch1, Chirurgie ch2, Journee j){
        super(ch1, ch2, j);
        this.sallePb=ch1.getSalle();
        ArrayList<Chirurgien> chirurgiensPb = new ArrayList<Chirurgien>();
        chirurgiensPb.add(ch1.getChirurgien());
        chirurgiensPb.add(ch2.getChirurgien());
        this.chirurgiens=chirurgiensPb;
    }
    
    public Bloc getSallePb() {
    	return this.sallePb;
    }
    
    private void essayerChangementDeSalleEvident(BaseDeDonnees data) {
    	//Conditions :
    	//1. Gros chevauchement   2. 
//    	if(pourcentageChevauchement(chirurgie1, chirurgie2)) {
//    		
//    	}
    }
}
