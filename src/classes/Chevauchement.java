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
    
    
    
    // ACCESSEURS // 
    
    public Bloc getSallePb() {
    	return this.sallePb;
    }
    public Chirurgien getChirurgienPb() {
    	return this.chirurgienPb;
    }
    
    
    
}
