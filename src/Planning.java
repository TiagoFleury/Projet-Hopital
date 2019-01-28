import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

public class Planning {
    private ArrayList<Journee> listeJours;
    
    public Planning(ArrayList<Journee> listDesJours){
        this.listeJours=listDesJours;
    }
    
}
