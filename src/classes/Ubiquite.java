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

public class Ubiquite extends Conflit {
    private Chirurgien chirurgienPb;
    private ArrayList<Bloc> salles;
    
    
    public Ubiquite(Chirurgie ch1, Chirurgie ch2){
        super(ch1, ch2);
        this.chirurgienPb=ch1.getChirurgien();
        ArrayList<Bloc> blocs = new ArrayList<Bloc>();
        blocs.add(ch1.getSalle());
        blocs.add(ch2.getSalle());
        this.salles=blocs;
    }
    
    
    // Accesseurs //
    
    public Chirurgien getChirurgienPb() {
    	return this.chirurgienPb;
    }
}
