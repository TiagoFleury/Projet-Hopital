import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;
import java.util.Date;


public class Chirurgie {
    private int id;
    private LocalDate date;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private Bloc salle;
    private Chirurgien chirurgien;
    
    
    // ACCESSEURS //
    public Chirurgien getChirurgien(){
        return this.chirurgien;
    }
    public Bloc getSalle(){
        return this.salle;
    }
    public LocalDateTime getDebut(){
        return this.heureDebut;
    }
    public LocalDateTime getFin(){
        return this.heureFin;
    }
    public LocalDate getDate(){
        return this.date;
    }
    
    
}
