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
    private ArrayList<Bloc> sallesPb;
    private ArrayList<Chirurgien> chirurgiensPb;
    
    public Chevauchement(Chirurgie ch1, Chirurgie ch2){
        super(ch1,ch2);
        ArrayList<Bloc> lesSallesPb = new ArrayList<Bloc>();
        ArrayList<Chirurgien> lesChirurgiensPb = new ArrayList<Chirurgien>();
        lesSallesPb.add(ch1.getSalle());
        lesSallesPb.add(ch2.getSalle());
        this.sallesPb=lesSallesPb;
        lesChirurgiensPb.add(ch1.getChirurgien());
        lesChirurgiensPb.add(ch2.getChirurgien());
        this.chirurgiensPb=lesChirurgiensPb;
    }
}
