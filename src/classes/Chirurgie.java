package classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;


public class Chirurgie {
	
	private int id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Bloc salle;
    private Chirurgien chirurgien;
    private boolean estEnConflit;
    
    
    
    public static Comparator<Chirurgie> CHRONOLOGIQUE = new Comparator<Chirurgie>(){
    	
    	public int compare(Chirurgie ch1, Chirurgie ch2) {
    		if(ch1.getDate().equals(ch2.getDate()))
    			return ch1.getDebut().compareTo(ch2.getDebut());
    		
    		return ch1.getDate().compareTo(ch2.getDate());
    		
    		
    	}
    };
        
    @SuppressWarnings("unchecked")
	public static Comparator<Chirurgie> PAR_BLOC = Comparator.comparing(Chirurgie::getSalle);
    @SuppressWarnings("unchecked")
	public static Comparator<Chirurgie> PAR_CHIRURGIEN = Comparator.comparing(Chirurgie::getChirurgien);
    
    

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
    //Deux chirurgies sont egales si elles ont les m�mes valeurs dans tous leurs attributs sauf ID
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
    public boolean estEnConflit() {
		return estEnConflit;
	}
    
    
    //MUTATEURS
   
    public void setSalle(Bloc b) {
    	salle = b;
    }

	public void setChirurgien(Chirurgien surgeon) {
		chirurgien = surgeon;
	}

	public void setEnConflit(boolean b) {
		estEnConflit = b;
	}
	
	public void setDebut(LocalTime deb) {
		heureDebut=deb;
	}
	public void setFin(LocalTime fin) {
		heureFin=fin;
	}

	
}
