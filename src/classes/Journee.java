package classes;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.math.*;

public class Journee {
    private LocalDate date;
    private ArrayList<Conflit> conflitsDuJour;
    private ArrayList<Chirurgie> chirurgiesDuJour;
    private ArrayList<Chirurgien> chirurgiensMobilises;
    private ArrayList<Bloc> sallesOccupeesduJour;
    private ArrayList<Anomalie> anomaliesDuJour;
    
    
    //CONSTRUCTEURS 
    
    public Journee() {
    	conflitsDuJour = new ArrayList<Conflit>();
    	chirurgiesDuJour = new ArrayList<Chirurgie>();
    	chirurgiensMobilises = new ArrayList<Chirurgien>();
    	sallesOccupeesduJour = new ArrayList<Bloc>();
    }
    
    
    
    
    public Journee(Chirurgie c) { //Instancie une journee a partir des parametres de c
    	date = c.getDate();
    	
    	conflitsDuJour = new ArrayList<Conflit>();
    	
    	chirurgiesDuJour = new ArrayList<Chirurgie>();
    	chirurgiesDuJour.add(c);
    	
    	chirurgiensMobilises = new ArrayList<Chirurgien>();
    	chirurgiensMobilises.add(c.getChirurgien());
    	
    	sallesOccupeesduJour = new ArrayList<Bloc>();
    	sallesOccupeesduJour.add(c.getSalle());
    }
    
    
    
    
    public void importerInfoChirurgie(Chirurgie c) { //recupere les infos d'une chirurgie et les ajoute dans la journee 
    											  //SANS creer de doublons
    	if(!chirurgiesDuJour.contains(c)) {
    		chirurgiesDuJour.add(c);
    		if(!chirurgiensMobilises.contains(c.getChirurgien()))
    			chirurgiensMobilises.add(c.getChirurgien());
    		if(!sallesOccupeesduJour.contains(c.getSalle()))
    			sallesOccupeesduJour.add(c.getSalle());
    	}
    	
    }
    
    
    // 1. Affichage des chirurgies de la journee - 2 plannings afin de bien voir les conflits : 1 par Blocs, 1 par Chirurgiens
    
    //AUXILIAIRES POUR AFFICHAGE
    
    private String reductionNomChirurgienPourAffichage(Chirurgien albert) {
    	String[] separation = albert.getName().split(" ");
    	char[] caracteres = new char[1];
    	separation[0].getChars(0, 1, caracteres, 0);
    	
    	String nomDeFamReduit = separation[1].substring(0, 3);
    	return caracteres[0]+"."+nomDeFamReduit; 
    }   
    private int cbdecaracteresNecessaires(Chirurgie c){
        // va prendre la valeur de dizaines de minutes entre le debut et la fin d'une chirurgie
        double i = ChronoUnit.MINUTES.between(c.getDebut(), c.getFin());
        int j = (int) i/5 ;
        return j;
    }
    private int cbdecaracteresAvant(Chirurgie c){
        LocalTime debutJournee = LocalTime.of(0, 0);
        double i = ChronoUnit.MINUTES.between(debutJournee, c.getDebut());
        int j= (int) i/5;
        @SuppressWarnings("deprecation")
		int nbCarac = new Integer(c.getID()).toString().length();
        return j+15-nbCarac;
    }

    
    //TRIS
    
    public static ArrayList<Chirurgie> triParBlocs(ArrayList<Chirurgie> liste) {
    	@SuppressWarnings("unchecked") //Warning relou
		Comparator<Chirurgie> PAR_BLOC = Comparator.comparing(Chirurgie::getSalle);
    	
    	Collections.sort(liste, PAR_BLOC);
    	return liste;
    }
    public static ArrayList<Chirurgie> triParChirurgien(ArrayList<Chirurgie> liste){
    	@SuppressWarnings("unchecked") //Warning relou
		Comparator<Chirurgie> PAR_CHIRURGIEN = Comparator.comparing(Chirurgie::getChirurgien);
    	Collections.sort(liste, PAR_CHIRURGIEN);
    	return liste;
    }
    
    
    
    //AFFICHAGES
    
    public void planningJourneeParBloc(){
        System.out.println("\n\n\n");
        System.out.println("                                                     PLANNING DU "+date+"\n\n");
        System.out.println("Salle          0h   0h30  1h    1h30  2h   2h30   3h    3h30  4h   4h30   5h   5h30   6h   6h30   7h   7h30   8h   8h30    9h   9h30  10h  10h30  11h  11h30  12h  12h30  13h  13h30  14h  14h30  15h  15h30  16h  16h30  17h  17h30  18h  18h30  19h  19h30  20h  20h30  21h  21h30  22h  22h30  23h  23h30  00h");
        //  A gauche du '.' c'est 8h,   A droite du '.' c'est 8h05
        System.out.println("               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .");
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = triParBlocs(chirurgiesDuJour);
        Bloc blocActuel = null;
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            if(!c.getSalle().equals(blocActuel)) {
            	System.out.println("\n\n               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .");
            	System.out.println(c.getSalle().getName()+" :"); //On affiche que s'il y a un changement de bloc
            }
            
            System.out.print(c.getID());
            for (int compteur=0;compteur<cbAvant;compteur++){
                System.out.print(" ");
            }
            String nouvNom = reductionNomChirurgienPourAffichage(c.getChirurgien());
            System.out.print(nouvNom);
            int compteur = nouvNom.length();
            while (compteur<=combien){
                System.out.print("#");
                compteur++;
            }
            System.out.println("");
            blocActuel = c.getSalle();
        }
    }
    
    public void planningJourneeParChirurgien(){
        System.out.println("\n\n\n");
        System.out.println("                                                      PLANNING DU "+date+"\n\n");
        System.out.println("Chirurgien     0h   0h30  1h    1h30  2h   2h30   3h    3h30  4h   4h30   5h   5h30   6h   6h30   7h   7h30   8h  8h30    9h   9h30  10h  10h30  11h  11h30  12h  12h30  13h  13h30  14h  14h30  15h  15h30  16h  16h30  17h  17h30  18h  18h30  19h  19h30  20h  20h30  21h  21h30  22h  22h30  23h  23h30  00h");
        //  A� gauche du . c'est 8h, A� droite du . c'est 8h05
        System.out.println("               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .");
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = triParChirurgien(chirurgiesDuJour);
        
        Chirurgien chirurgienActuel = null;
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            if(!c.getChirurgien().equals(chirurgienActuel)) {
            	System.out.println("\n\n               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .");
            	
            	System.out.println(c.getChirurgien().getName()+" :"); //On affiche que s'il y a un changement de chirurgien
            }
            System.out.print(c.getID());
            for (int compteur=0;compteur<cbAvant;compteur++){
                System.out.print(" ");
            }
            System.out.print(c.getSalle().getName());
            int compteur = c.getSalle().getName().length();
            while (compteur<combien){
                System.out.print("#");
                compteur++;
            }
            System.out.println("");
            chirurgienActuel = c.getChirurgien();
        }
        
    }
    
    
    
    
    
    
    // 2. Detection des conflits d'une journee = on etudie si 2 chirurgies sont en conflit (selon les 3 definitions) puis on dresse la liste des conflits du jour
    
    
    public boolean ubiquiteOuPas(Chirurgie x, Chirurgie y){
        boolean b=false;
        if ((x.getDate().isEqual(y.getDate())) && ((x.getDebut().isBefore(y.getDebut())) || x.getDebut().equals(y.getDebut()))){
            if (x.getFin().isAfter(y.getDebut()) || x.getFin().equals(y.getDebut())){
                if (x.getChirurgien()==y.getChirurgien()){
                    b=true;
                }
            }
        }
        else if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isAfter(y.getDebut()) || x.getDebut().equals(y.getDebut()))){
            if (y.getFin().isAfter(x.getDebut()) || y.getFin().equals(x.getDebut())){
                if (x.getChirurgien()==y.getChirurgien()){
                    b=true;
                }
        }
        }
        return b;
    }
    

    public boolean interferenceOuPas(Chirurgie x, Chirurgie y){
        boolean b=false;
        if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isBefore(y.getDebut()) || x.getDebut().equals(y.getDebut()))){
            if (x.getFin().isAfter(y.getDebut()) || x.getFin().equals(y.getDebut())){
                if (x.getSalle()==y.getSalle()){
                    b=true;
                }
            }
        }
        else if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isAfter(y.getDebut()) || x.getDebut().equals(y.getDebut()))){
            if (y.getFin().isAfter(x.getDebut()) || y.getFin().equals(x.getDebut())){
                if (x.getSalle()==y.getSalle()){
                    b=true;
                }
            }
        }
        return b;
    }
    
    
    
    public Conflit conflitOuPas(Chirurgie x, Chirurgie y){
        Conflit c = null;
        boolean uBool,iBool = false;
        LocalTime debConflit,finConflit = null;
        uBool=ubiquiteOuPas(x,y);
        iBool=interferenceOuPas(x,y);
        if ((uBool==true) && (iBool==false)){
            if (x.getDebut().isBefore(y.getDebut())){
                debConflit=y.getDebut();
            }
            else { debConflit=x.getDebut();}
            
            if (x.getFin().isBefore(y.getFin())){
                finConflit=x.getFin();
            }
            else { finConflit=y.getFin();}
            
            c = new Ubiquite(x,y);
            
        }
        
        else if ((uBool==false) && (iBool==true)){
            if (x.getDebut().isBefore(y.getDebut()) || x.getDebut().equals(y.getDebut())){
                debConflit=y.getDebut();
            }
            else { debConflit=x.getDebut();}
            
            if (x.getFin().isBefore(y.getFin()) || x.getFin().equals(y.getFin())){
                finConflit=x.getFin();
            }
            else { finConflit=y.getFin();}
            
            c = new Interference(x,y);
        }
        
        else if ((uBool==true) && (iBool==true)) {
            if (x.getDebut().isBefore(y.getDebut()) || x.getDebut().equals(y.getDebut())){
                debConflit=y.getDebut();
            }
            else { debConflit=x.getDebut();}
            if (x.getFin().isBefore(y.getFin()) || x.getFin().equals(y.getFin())){
                finConflit=x.getFin();
            }
            else { finConflit=y.getFin();}
            c = new Chevauchement(x,y);
        }
        return c;
    }
    
   
    
    
   // Cette methode ne fait que mettre dans conflitsDuJour les conflits encore presents
    public void detectionConflit(){
        Conflit conf = null;
        conflitsDuJour = new ArrayList<Conflit>();
        for (Chirurgie c1 : this.chirurgiesDuJour){
            for (Chirurgie c2 : this.chirurgiesDuJour){
                if (!c1.equals(c2)){
                    conf=conflitOuPas(c1,c2);
                }
                if ((conflitsDuJour.size()!=0) && conf!=null) {
                	if (!conflitsDuJour.contains(conf)) {
                		conflitsDuJour.add(conf);
                		c2.setEnConflit(true);
                		c1.setEnConflit(true);
                	}
                }
                else if (conflitsDuJour.size()==0) {
                	if (conf!=null) {
                		conflitsDuJour.add(conf);
                		c1.setEnConflit(true);
                		c2.setEnConflit(true);
                	}
                }
            }
        }
    }
    
    
    
    
    // 3. Detection d'anomalies parmis les chirurgies qui sont en conflits
    
    
    // On définit ici des méthodes qui s'applique à toute chirurgie, mais on ne regardera QUE pour des chirurgies en conflit
    
    
    // Ici, pour un chirurgien donné, la chirurgie devient une anomalie si : 
    //   pour un seuil fixé, la probas pour qu'il travaille ce jour, alors que la chirurgie est en conflit, est trop basse pour le seuil
    
    public boolean anomalieJourOuPas(Chirurgie x, double seuil) {
    	boolean b = false;
    	String leJour = null;
    	leJour = x.getDate().getDayOfWeek().toString();
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
		
		if (x.getChirurgien().getProportions().get(numero)<seuil) {
			b=true;
		}
		return b;
    }
    
    
    // Pour une chirurgie (en conflit) donnée, et pour un certain seuil, la chirurgie passe en anomalie si : 
    //    il y a une trop grande différence entre ses temps de chirurgies habituels et celui ci (selon un certain seuil de tolérance)
    public boolean anomalieDureeChirurgieOuPas(Chirurgie x, double seuil) {
    	boolean b = false;
    	double time = ChronoUnit.MINUTES.between(x.getDebut(), x.getFin());
    	if (Math.abs(x.getChirurgien().getTempsMoyen()-time)>seuil) {
    		b=true;
    	}
    	return b;
    }
    
    
    public boolean anomalieSurchageChirurgienOuPas(Chirurgie x, double seuilTemps, int seuilNb) {
    	boolean b = false;
    	double sum=0;
    	int nombreCh = 0;
    	for (Chirurgie c : x.getChirurgien().getChirurgies()) {
    		sum += ChronoUnit.MINUTES.between(c.getDebut(), c.getFin());
    		nombreCh ++ ;
    	}
    	if ((sum > seuilTemps) || (nombreCh > seuilNb))  {
    		b = true;
    	}
    	return b;
    }
    
    
    
    public boolean anomalieDureeInterOpeBlocOuPas(Chirurgie x, double seuil) {
    	boolean b = false;
    	return b;
    }
    
    
    // Methode qui va passer en attribut d'une instance Journée, toutes les anomalies de chirurgie en conflit
    public void detectionsAnomalie() {
    	boolean b1=false, b2=false, b3=false, b4=false;
    	for (Conflit conf : conflitsDuJour) {
    		
    	}
    }
    
    
    
    
    
    // 4. Methodes de base de resolution de conflits
    
    // Ici resolution facile, trouver une salle disponible totalement disponible sur le creneau : ie ne genere aucun conflit / cout = 0
    // Dans ce cas (de maniere innocente) on va choisir la premiere dispo qui ne genere aucun conflit
    // Voir la 1ere qui n'est pas occupee de la Journee entiere
    
    
    
    
    public void raccourcirChirurgieDebut(Chirurgie ch, long nbMin) {
    	ch.setDebut(ch.getDebut().plusMinutes(nbMin));
    }
    
    public void raccourcirChirurgieFin(Chirurgie ch, long nbMin) {
    	ch.setFin(ch.getFin().minusMinutes(nbMin));
    }
    
    public void deplacerChirurgieAvant(Chirurgie ch, long nbMin) {
    	ch.setDebut(ch.getDebut().minusMinutes(nbMin));
    	ch.setFin(ch.getFin().minusMinutes(nbMin));
    }
    
    public void deplacerChirurgieApres(Chirurgie ch, long nbMin) {
    	ch.setDebut(ch.getDebut().plusMinutes(nbMin));
    	ch.setFin(ch.getFin().plusMinutes(nbMin));
    }
    
    
    
    
    
    
    // RESOLUTION INTERFERENCE
    ///////////////////////////////////////////
    
    public void resoudreInterferenceCout0(BaseDeDonnees database, Interference i){
    	Bloc sallePb = i.getSallePb();
    	Bloc uneSalle = null;
    	LocalTime debLimite = LocalTime.of(8,00);
    	LocalTime finLimite = LocalTime.of(20,00);
    	
    	int compteur = 0, lg = database.getTousBlocs().size();
    	if (i.getCh1().getDebut().isBefore(debLimite) || (i.getCh1().getFin().isAfter(finLimite))) {
    		while ((compteur < lg) && (database.getTousBlocs().get(compteur).getID()<4)){ // Ici je veux juste arriver a une salle Urgence, tout en restant dans la liste des blocs
    				compteur++;
    			}
    		if (compteur < lg) {
    			uneSalle = database.getTousBlocs().get(compteur);
    			if (!this.sallesOccupeesduJour.contains(uneSalle) && !uneSalle.equals(sallePb)) {
    				i.getCh1().setSalle(uneSalle);
    				i.setEtat(true);
    			}
    		}
    	}
    	
    	else { // Cas ou les horaires sont normaux on l'envoie dans une salle normale
    		while ((compteur < lg) && (database.getTousBlocs().get(compteur).getID()>4)) {
    			compteur++;
    		}
    		if (compteur < lg) {
    			uneSalle = database.getTousBlocs().get(compteur);
    			if (!this.sallesOccupeesduJour.contains(uneSalle) && !uneSalle.equals(sallePb)) {
    				i.getCh1().setSalle(uneSalle);
    				i.setEtat(true);
    			}
    		}
    	}
    	if (i.getEtat()==false) {
			System.out.println("Interference non resolue");
				}
    	else { System.out.println("Interference resolue");}
    	
    }
    
    
    
    
    
    // RESOLUTION D'UBIQUITE 
    /////////////////////////////////////////////
    
    public void resolutionUbiquite(BaseDeDonnees database, Ubiquite u) {
    	// METHODE FINALE DE RESOLV D'u, qui appellera toutes les eventuelles résolutions
    }
    
    
    public void resoudreUbiquiteCout0(BaseDeDonnees database, Ubiquite u) {
    	Chirurgien chirurgienPb = u.getChirurgienPb();
    	Chirurgien unChirurgien = null ;
    	int compteur = 0, lg = database.getTousChirurgiens().size();
    	while (compteur < lg) {
    		unChirurgien = database.getTousChirurgiens().get(compteur);
    		if (!chirurgiensMobilises.contains(unChirurgien) ) {
				u.getCh1().setChirurgien(unChirurgien);
				u.setEtat(true);
				compteur = lg;
			}
    		compteur ++ ; // Ci dessus, j'ai simplement test s'il y avait des Chirurgiens NON UTILISES toute la journee car le choisir = cout 0
    	}
    	
    	if (u.getEtat()==false) {
			System.out.println("Ubiquite non resolue");
				}
    	else { System.out.println("Ubiquite resolue");}
    		
    	 
    }
    
    
    
    // DABORD ALLER CHERCHER LES CHIRURGIENS QUI ONT 0 CONFLITS, car les AUTRES ON EST SUR QU'ils ne sont pas la
    
    // Si dans la journée, parmi ceux qui travaillent, est ce qu'il y en a un qui bosse pas a ce moment là, et qui provoque 0 conflit
    public void resoudreUbiquite0(BaseDeDonnees database, Ubiquite u) {
    	Chirurgien chirurgienPb = u.getChirurgienPb();
    	Chirurgien unChirurgien = null;
    	Chirurgie chirurgieTest = null;
    	
    	int compteur = 0, lg = database.getTousChirurgiens().size();
    	int nbConflitsGeneres = 0;

    	while (compteur < lg) {
    		unChirurgien = database.getTousChirurgiens().get(compteur);
        	chirurgieTest = u.getCh1();
        	chirurgieTest.setChirurgien(unChirurgien);
        	for (Chirurgie compteuse : chirurgiesDuJour) {
        		if (conflitOuPas(chirurgieTest, compteuse)!=null) {
        			nbConflitsGeneres++;
        		}
        		
        	}
        	if (chirurgiensMobilises.contains(unChirurgien) && conflitOuPas(chirurgieTest,u.getCh2())==null && nbConflitsGeneres==0) {
    			u.getCh1().setChirurgien(unChirurgien);
    			u.setEtat(true);
    			compteur = lg;
        	}
    	}
    	
    	
    	if (u.getEtat()==false) {
    		compteur = 0;
    		lg = database.getTousChirurgiens().size();
    		nbConflitsGeneres =0;

        	while (compteur < lg) {
        		unChirurgien = database.getTousChirurgiens().get(compteur);
            	chirurgieTest = u.getCh2();
            	chirurgieTest.setChirurgien(unChirurgien);
            	for (Chirurgie compteuse : chirurgiesDuJour) {
            		if (conflitOuPas(chirurgieTest, compteuse)!=null) {
            			nbConflitsGeneres++;
            		}
            		
            	}
            	if (chirurgiensMobilises.contains(unChirurgien) && conflitOuPas(chirurgieTest,u.getCh1())==null && nbConflitsGeneres==0 ) {
        			u.getCh2().setChirurgien(unChirurgien);
        			u.setEtat(true);
        			compteur = lg;
            	}
        	}
    	}
        		
    		
    }
    
    
    // Si celle au dessus n'a pas marché, alors aller chercher parmis ceux qui bossent pas de la journée (ex : ceux qui bossent pas le mardi15)
    // lesquels ont la plus grosse probas de bosser un mardi
    
    public void resoudreUbiquite1(BaseDeDonnees database, Ubiquite u) {
    	Chirurgien chirurgienPb = u.getChirurgienPb();
    	Chirurgien unChirurgien = null;
    	Chirurgie chirurgieTest = null;
    	
    	int compteur = 0, lg = database.getTousChirurgiens().size();
    	float probasMax=0;
    	
    	String leJour = null;
    	leJour = u.getDate().getDayOfWeek().toString();
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
    	
    	
		Chirurgien theSurgeon = null;
		int nbChirurgiensComptabilisés=0;
    	
    	while (compteur < lg) {
    		unChirurgien = database.getTousChirurgiens().get(compteur);
    		
    		if (!chirurgiensMobilises.contains(unChirurgien) ) {
    			
    			if (unChirurgien.getProportions().get(numero)>probasMax) {
    				theSurgeon=unChirurgien;
    			}
    			nbChirurgiensComptabilisés++;
			}
    		compteur ++ ; // Ci dessus, j'ai simplement test s'il y avait des Chirurgiens NON UTILISES toute la journee car le choisir = cout 0
    	}
    	
    	if (nbChirurgiensComptabilisés!=0 && !theSurgeon.equals(chirurgienPb) && !chirurgiensMobilises.contains(theSurgeon)) {
    		u.getCh1().setChirurgien(theSurgeon);
    		u.setEtat(true);
    	}
		
    }
    
    
    
    
    
    // Ubiquite classique + cas où le chirurgien travaille un jour ou il ne devrait pas
    public void resoudreUbiquite2(BaseDeDonnees database, Ubiquite u) {
    	
    }
    
    
    
    
    
    
    
    
    
    // RESOLUTION CHEVAUCHEMENT 
    ///////////////////////////////////////////////
    
    public void resoudreChevauchementCout0(BaseDeDonnees database, Chevauchement c) {
    	Chirurgien chirugienPb = c.getChirurgienPb();
    	Chirurgien unChirurgien = null ;
    	Bloc sallePb = c.getSallePb();
    	Bloc uneSalle = null;
    	boolean a=false, b=false;
    	
    	// Resolution de l'ubiquité
    	int compteur = 0, lg = database.getTousChirurgiens().size();
    	
    	while (compteur < lg) {
    		unChirurgien = database.getTousChirurgiens().get(compteur);
    		if (!this.sallesOccupeesduJour.contains(unChirurgien)) {
				c.getCh1().setChirurgien(unChirurgien);
				a = true;
				compteur = lg;
			}
    		compteur ++ ;
    	}
    	
    	// Resolution de l'interference
    	compteur = 0;
    	lg = database.getTousBlocs().size();
    	while (compteur < lg) {
    		uneSalle = database.getTousBlocs().get(compteur);
    		if (!this.sallesOccupeesduJour.contains(uneSalle)) {
				c.getCh1().setSalle(uneSalle);
				b = true;
				compteur = lg;
			}
    		compteur ++ ;
    	}
    	
    	
    	if (a==true && b==true) {
    		c.setEtat(true);
			System.out.println("Chevauchement resolu");
				}
    	else { 
    		c.setEtat(false);
    		System.out.println("Chevauchement non resolu");}
    }
    
    
    
    public void resoudreConflitCout0(BaseDeDonnees database, Conflit c) {
    	if (c instanceof Interference){
    		Interference cBis = (Interference) c;
    		resoudreInterferenceCout0(database, cBis);
    	}
    	if (c instanceof Ubiquite) {
    		Ubiquite cBis = (Ubiquite) c;
    		resoudreUbiquiteCout0(database, cBis);
    	}
    	if (c instanceof Chevauchement) {
    		Chevauchement cBis = (Chevauchement) c;
    		resoudreChevauchementCout0(database, cBis);
    	}
    }
    
    
    
    
    
   
    
    
    ////////////////////////////////////////////////////////
    
    
    // ACCESSEURS //
    public ArrayList<Chirurgie> getChirurgieJour(){
        return this.chirurgiesDuJour;
    }
    public ArrayList<Conflit> getConflits(){
    	return this.conflitsDuJour;
    }
    public LocalDate getDate() {
    	return date;
    }
    public ArrayList<Chirurgien> getChirurgiensMobilises(){
    	return chirurgiensMobilises;
    }
    
           
        
    
}
