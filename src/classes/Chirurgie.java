package classes;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Locale;


public class Chirurgie {
	
	private int id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Bloc salle;
    private Chirurgien chirurgien;
    private boolean estEnConflit;
    private double nbMinutes ;
    
    
    
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
		
		nbMinutes = ChronoUnit.MINUTES.between(heureDebut,heureFin);
		if(nbMinutes<0) { //C'est le cas ou une chirurgie est sur deux jours
			nbMinutes = ChronoUnit.MINUTES.between(LocalDateTime.of(date, heureDebut),LocalDateTime.of(date, heureFin).plusDays(1));
		}
	}
    
    public Chirurgie(Chirurgie ch) {
    	id = ch.getID();
    	date = ch.getDate();
    	heureDebut = ch.getDebut();
    	heureFin = ch.getFin();
    	salle = ch.getSalle();
    	chirurgien = ch.getChirurgien();
    	estEnConflit = ch.estEnConflit();
    	nbMinutes = ch.getDuree();
    	
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
    
    
    
    
    public void raccourcirChirurgieDebut(long nbMin) {
    	this.heureDebut.plusMinutes(nbMin);
    }
    
    public void raccourcirChirurgieFin(Chirurgie ch, long nbMin) {
    	this.heureFin.minusMinutes(nbMin);
    }
    
    public void deplacerChirurgieAvant(Chirurgie ch, long nbMin) {
    	this.heureDebut.minusMinutes(nbMin);
    	this.heureFin.minusMinutes(nbMin);
    }
    
    public void deplacerChirurgieApres(Chirurgie ch, long nbMin) {
    	this.heureDebut.plusMinutes(nbMin);
    	this.heureFin.plusMinutes(nbMin);
    }
    
    public static boolean superposition(Chirurgie ch1, Chirurgie ch2) { //Faut passer en LocalDateTime sinon c'est la demer
    	LocalDateTime heureDebut1;
    	LocalDateTime heureFin1;
    	LocalDateTime heureDebut2;
    	LocalDateTime heureFin2;
    	
    	if(ch1.sur2jours()) {
    		heureDebut1 = LocalDateTime.of(ch1.getDate(), ch1.getDebut());
    		heureFin1 = LocalDateTime.of(ch1.getDate().plusDays(1), ch1.getFin());
    	}
    	else {
    		heureDebut1 = LocalDateTime.of(ch1.getDate(), ch1.getDebut());
    		heureFin1 = LocalDateTime.of(ch1.getDate(), ch1.getFin());
    	}
    	if(ch2.sur2jours()) {
    		heureDebut2 = LocalDateTime.of(ch2.getDate(), ch2.getDebut());
    		heureFin2 = LocalDateTime.of(ch2.getDate().plusDays(1), ch2.getFin());
    	}
    	else {
    		heureDebut2 = LocalDateTime.of(ch2.getDate(), ch2.getDebut());
    		heureFin2 = LocalDateTime.of(ch2.getDate(), ch2.getFin());
    	}
    	
    	
    	if( (heureDebut1.isBefore(heureDebut2)||heureDebut1.equals(heureDebut2)) && (heureFin1.isAfter(heureFin2)||heureFin1.equals(heureFin2))) {
    		return true;
    	}
    	else if((heureDebut2.isBefore(heureDebut1)||heureDebut1.equals(heureDebut2)) && (heureFin2.isAfter(heureFin1)||heureFin1.equals(heureFin2)))
    		return true;
    	return false;
    }
    
    

    
    public int indicePlageHoraire() {
    	LocalTime huit = LocalTime.of(8,00);
		LocalTime quatorze = LocalTime.of(14, 00);
    	LocalTime vingt = LocalTime.of(20,00);
    	LocalTime deux = LocalTime.of(2,00);
    	int a =0;
    	
    	if ( (heureDebut.isAfter(huit) || (heureDebut.equals(huit))) && (heureDebut.isBefore(quatorze) ) ) {
    		a=0;
		}
		if ( (heureDebut.isAfter(quatorze) || (heureDebut.equals(quatorze))) && (heureDebut.isBefore(vingt) )) {
			a=1;
		}
		if ( (heureDebut.isAfter(vingt) || (heureDebut.equals(vingt))) && (heureDebut.isBefore(deux) )) {
			a=2;
		}
		if ( (heureDebut.isAfter(deux) || (heureDebut.equals(deux))) && (heureDebut.isBefore(huit) )) {
			a=3;
		}
		return a;
    }
    
    
    
    
    public boolean sur2jours() {
    	if(heureDebut.isAfter(heureFin))
    		return true;
		return false;
	}

	// Detection d'anomalies
    
    
    // Ici, pour un chirurgien donné, la chirurgie devient une anomalie si : 
    //   pour un seuil fixé, la probas pour qu'il travaille ce jour, alors que la chirurgie est en conflit, est trop basse pour le seuil
  
    public boolean anomalieJourOuPas(double seuil) {
    	boolean b = false;
    	String leJour = null;
    	leJour = date.getDayOfWeek().toString();
    	DateFormatSymbols dfsEN = new DateFormatSymbols(Locale.ENGLISH);
		String[] joursSemaine = dfsEN.getWeekdays(); // Je creee un [jour de la semaine 1=Sunday, 7=Saturday]
		
    	int numero = 0;
    	
    	if (leJour.equals(joursSemaine[1].toString().toUpperCase())) {
			numero = 0; //Dimanche
		}
		if (leJour.equals(joursSemaine[2].toString().toUpperCase())) {
			numero = 1; //Lundi
		}
		if (leJour.equals(joursSemaine[3].toString().toUpperCase())) {
			numero = 2; //Mardi
		}
		if (leJour.equals(joursSemaine[4].toString().toUpperCase())) {
			numero = 3; //Mercredi
		}
		if (leJour.equals(joursSemaine[5].toString().toUpperCase())) {
			numero = 4; //Jeudi
		}
		if (leJour.equals(joursSemaine[6].toString().toUpperCase())) {
			numero = 5; //Vendredi
		}
		if (leJour.equals(joursSemaine[7].toString().toUpperCase())) {
			numero = 6; //Samedi
		}
		
		if (chirurgien.getProportions().get(numero)<seuil) {
			b=true;
		}
		return b;
    }
    
    
    
    
    

    // Pour une chirurgie (en conflit) donnee, la chirurgie passe en anomalie si : 
    //    Pour le chirurgien de la chirurgie : 
    // 				il y a une trop grande difference entre ses temps de chirurgies habituels et celui ci
    public boolean anomalieDureeChirurgieOuPas() {
    	boolean b = false;
    	double time = nbMinutes;
    	ArrayList<Double> interv = chirurgien.getICtempsMoyen();
    	if ( (time<chirurgien.getICtempsMoyen().get(0)) || (time>chirurgien.getICtempsMoyen().get(1))){
    		b=true;
    	}
    	return b;
    }
    
   
    
    
    // Detection d'anomalies
    
    // C'est en quelque sorte une detection en deux temps
    // Ici j'essaye de savoir si 
    //    - en ajoutant la chirurgie this, cela provoque une anomalie de durée interopératoire.
    //    - si jamais elle appartient deja a la liste des chirurgies du jour, alors on verifie si elle est initialement en anomalie
    
    public int anomalieDureeInterOpeBlocOuPasChirurgien(BaseDeDonnees database) {
    	int retour = 0;
    	
    	// On va retourner 0 s'il n'y pas d'anomalie, -1 sil y en a une avec la chirurgie juste avant, et 1 si cest avec celle d'apres, 2 si en anomalie avec celle d'avant et apres
    	Chirurgien theC = this.chirurgien;
    	ArrayList<Chirurgie> sesChirurgiesAuj = theC.recupChirurgiesDuJour(database.getJournee(date));
    	
    	
    	if (!sesChirurgiesAuj.contains(this)) {
    		ArrayList<Chirurgie> copie = new ArrayList<Chirurgie>(sesChirurgiesAuj);
    		copie.add(this);
    		Collections.sort(copie, Chirurgie.CHRONOLOGIQUE);
    		int i = copie.indexOf(this); // Avec cette ligne, je signifie que pr tester l'anomalie, il faut que la chirurgie soit dans la liste des ch du Chirurgien
        	
    		
        	// On traite le cas de la chirurgie d'avant
        	boolean b1 = false, b2 = false;
        	if (i>0) {
        		if ( ( ChronoUnit.MINUTES.between(sesChirurgiesAuj.get(i-1).getFin(),heureDebut) < theC.getICtempsInteroperatoire().get(1) ) ||  Journee.enMemeTempsOuPas(this,sesChirurgiesAuj.get(i-1)) )  {
            		b1=true;
            	}
        	}
        	
        	if (i<sesChirurgiesAuj.size()-1) {
        		if (ChronoUnit.MINUTES.between(heureFin, sesChirurgiesAuj.get(i+1).getDebut()) < theC.getICtempsInteroperatoire().get(1)  ||  Journee.enMemeTempsOuPas(this,sesChirurgiesAuj.get(i+1)) ) {
        			b2=true;
        		}
        	}

        	if (b1==true && b2==false) {
        		retour= -1;
        	}
        	if (b1==false && b2==true) {
        		retour = 1;
        	}
        	if (b1==true && b2==true) {
        		retour = 2;
        	}
        	return retour;
    	}
    	
    	
    	else { // Cas ou la chirurgie est deja dans la liste des chirurgies du jour
    		int i = sesChirurgiesAuj.indexOf(this); 
        	
        	// On traite le cas de la chirurgie d'avant
        	boolean b1 = false, b2 = false;
        	if (i>0) {
        		if ( ( ChronoUnit.MINUTES.between(sesChirurgiesAuj.get(i-1).getFin(),heureDebut) < theC.getICtempsInteroperatoire().get(1) ) ||  Journee.enMemeTempsOuPas(this,sesChirurgiesAuj.get(i-1)) )  {
            		b1=true;
            	}
        	}
        	
        	// cas de la chirurgie d'apres
        	if (i<sesChirurgiesAuj.size()-1) {
        		if (ChronoUnit.MINUTES.between(heureFin, sesChirurgiesAuj.get(i+1).getDebut()) < theC.getICtempsInteroperatoire().get(1)  ||  Journee.enMemeTempsOuPas(this,sesChirurgiesAuj.get(i+1)) ) {
        			b2=true;
        		}
        	}

        	
        	if (b1==true && b2==false) {
        		retour= -1;
        	}
        	if (b1==false && b2==true) {
        		retour = 1;
        	}
        	if (b1==true && b2==true) {
        		retour = 2;
        	}
        	return retour;
    	}
    	
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
    public double getDuree() {
    	return nbMinutes;
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
