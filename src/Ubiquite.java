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
    // On aurait pu mettre 2 salles en attribut uniquement, mais je pense qu'il est probable qu'on ait des Mecs sur 3-4 salles
    // en meme temps
    // Et aussi il arrivera parfois qu'en enlevant des ubiquites, cela en rajoute
    
    public Ubiquite(LocalDate jour, LocalDateTime hDeb, LocalDateTime hFin, Chirurgie ch1, Chirurgie ch2, boolean etat,Chirurgien chPb){
        super(jour, hDeb, hFin, ch1, ch2, etat);
        this.chirurgienPb=chPb;
        ArrayList<Bloc> blocs = new ArrayList<Bloc>();
        blocs.add(ch1.getSalle());
        blocs.add(ch2.getSalle());
        this.salles=blocs;
    }
}
