package classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class Chirurgie {
	
	private int id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Bloc salle;
    private Chirurgien chirurgien;
    

    public Chirurgie(String[] champs) {  //Instancie avec Heure
		id = Integer.parseInt(champs[0]);
		String[] champsDate = champs[1].split("/");
		
		date = LocalDate.of(Integer.parseInt(champsDate[2]),Integer.parseInt(champsDate[1]), Integer.parseInt(champsDate[0]));
		
		heureDebut = LocalTime.parse(champs[2]);
		heureFin = LocalTime.parse(champs[3]);
		salle = null;
		chirurgien = null;
	}
    
    @Override
    //Deux chirurgies sont egales si elles ont les mêmes valeurs dans tous leurs attributs sauf ID
    public boolean equals(Object o) {
    	if(this == o) { //C'est la meme adresse memoire
    		return true;
    	}
    	if(o instanceof Chirurgie) {
    		Chirurgie c = (Chirurgie) o;
    		if(c.getDate().toString().equals(date.toString()) && c.getChirurgien().equals(chirurgien) && c.getDebut().toString().equals(heureDebut.toString()) && c.getFin().toString().equals(heureFin.toString()) && c.getChirurgien().equals(chirurgien) ) {
    			return true;
    		}
    	}
    	
    	
    	return false;
    }
    
    
    
    //AFFICHAGES
    @Override
    public String toString() {
    	String str = "chir"+id+" E"+salle.getID()+" - "+chirurgien+"  ["+heureDebut+" -- "+heureFin+"]"+" -- "+date;
    	return str;
    }
    
    public static String afficherListe(ArrayList<Chirurgie> liste) {
    	
    	String str = "";
    	for(Chirurgie c : liste) {
    		str+="chir"+c.id+"\n";
    	}
    	
    	return str;
    }
    
    
    
    
    // ACCESSEURS //
    public Chirurgien getChirurgien(){
        return this.chirurgien;
    }
    public Bloc getSalle(){
        return this.salle;
    }
    public LocalTime getDebut(){
        return this.heureDebut;
    }
    public LocalTime getFin(){
        return this.heureFin;
    }
    public LocalDate getDate(){
        return this.date;
    }
    public int getID() {
    	return id;
    }
    
    
    
    //MUTATEURS
   
    public void setSalle(Bloc b) {
    	salle = b;
    }

	public void setChirurgien(Chirurgien surgeon) {
		chirurgien = surgeon;
		
	}
}
