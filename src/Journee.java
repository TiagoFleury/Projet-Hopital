
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;

public class Journee {
    private Date date;
    private ArrayList<Conflit> conflitsDuJour;
    private ArrayList<Chirurgie> chirurgieduJour;
    private ArrayList<Chirurgien> chirurgiensMobilises;
    private ArrayList<Bloc> sallesOccupees;
    
}
