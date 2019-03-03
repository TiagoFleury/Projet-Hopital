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
//    	Conditions :
//    	1. Gros chevauchement   2. des blocs sont libres a cet horaire    3. un des chirurgiens est dans son bloc fort
    	
    	//On fait toutes les conditions qui font qu'on laisse la main a une autre resolution plus severe ou plus appropriee
    	
    	if(getIndiceDeRecouvrement()<0.6) {
    		return;
    	}
    	if(getBlocsLibres().size()==0) { //Si il n'y a aucun bloc libre a cet horaire
    		return;
    	}
    	
    	if(chirurgie1.getChirurgien().getBlocFort(jour).equals(this.sallePb)) { //Si le chirurgien1 est sur son bloc fort
    		
    	}
    	
    }

}
