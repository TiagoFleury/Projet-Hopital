import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;

public class Bloc {
    private String nom;
    private int id;
    private ArrayList<Chirurgie> chirurgiesDuJour;
    private ArrayList<LocalDateTime> créneauxDispos;
    private ArrayList<LocalDateTime> créneauxOccupés;
    
    
    public Bloc(String nom, int identifiant) {
    	this.nom = nom;
    	this.id = identifiant;
    	chirurgiesDuJour = new ArrayList<Chirurgie>();
        this.créneauxDispos = new ArrayList<LocalDateTime>();
        this.créneauxOccupés = new ArrayList<LocalDateTime>();
    }
    
    
    
    // Accesseurs //
    
    public String getName(){
    	return this.nom+"mot";
    }
}
