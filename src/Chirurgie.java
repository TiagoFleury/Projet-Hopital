
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;



public class Chirurgie {
	
	private int id;
    private LocalDate date;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private Bloc salle;
    private Chirurgien chirurgien;
    

    public Chirurgie(String[] champs) {  //Instancie avec Heure
		id = Integer.parseInt(champs[0]);
		String[] champsDate = champs[1].split("/");
		
		date = LocalDate.of(Integer.parseInt(champsDate[2]),Integer.parseInt(champsDate[1]), Integer.parseInt(champsDate[0]));
		
		heureDebut = LocalDateTime.of(date, LocalTime.parse(champs[2]));
		heureFin = LocalDateTime.of(date, LocalTime.parse(champs[3]));
		salle = null;
		chirurgien = null;
	}
    
    
    
    @Override
    public String toString() {
    	String str = "chir"+id+"   Bloc : E"+salle.getID()+"     - "+chirurgien;
    	return str;
    }
    
    
    
    
    //MUTATEURS
   
    public void setSalle(Bloc b) {
    	salle = b;
    }

	public void setChirurgien(Chirurgien surgeon) {
		chirurgien = surgeon;
		
	}

}
