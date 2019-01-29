package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;

public class Conflit {
    private LocalDate date;
    private LocalTime debut;
    private LocalTime fin;
    private Chirurgie chirurgie1;
    private Chirurgie chirurgie2;
    private boolean resolu;
    
    
    public Conflit(LocalDate jour, LocalTime hDeb, LocalTime hFin, Chirurgie ch1, Chirurgie ch2, boolean etat){
        this.date=jour;
        this.debut=hDeb;
        this.fin=hFin;
        this.chirurgie1=ch1;
        this.chirurgie2=ch2;
        this.resolu=etat;
        
    }
}
