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
    
    public static boolean enMemeTempsOuPas(Chirurgie x, Chirurgie y) {
    	boolean bool=false;
    	if ((x.getDate().isEqual(y.getDate())) && ((x.getDebut().isBefore(y.getDebut())) || x.getDebut().equals(y.getDebut()))){
    		if (x.getFin().isAfter(y.getDebut()) || x.getFin().equals(y.getDebut())){
            	bool = true;
            }
    	}
    	else if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isAfter(y.getDebut()) || x.getDebut().equals(y.getDebut()))){
    		if (y.getFin().isAfter(x.getDebut()) || y.getFin().equals(x.getDebut())){
            	bool = true;
            }
    	}
        return bool; 
    }
    
    
    public boolean ubiquiteOuPas(Chirurgie x, Chirurgie y){
        boolean b=false;
        if (enMemeTempsOuPas(x,y)==true && x.getChirurgien().equals(y.getChirurgien())) {
        	b=true;
        }
        return b;
    }
    
    

    public boolean interferenceOuPas(Chirurgie x, Chirurgie y){
        boolean b=false;
        if (enMemeTempsOuPas(x,y)==true && x.getSalle().equals(y.getSalle())) {
        	b=true;
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
            
            c = new Ubiquite(x,y,this);
            
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
            
            c = new Interference(x,y, this);
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
            c = new Chevauchement(x,y,this);
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
    
    
  
    
    
    

    
    
    
    // 4. Methodes de base de resolution de conflits
    
    // Ici resolution facile, trouver une salle disponible totalement disponible sur le creneau : ie ne genere aucun conflit / cout = 0
    // Dans ce cas (de maniere innocente) on va choisir la premiere dispo qui ne genere aucun conflit
    // Voir la 1ere qui n'est pas occupee de la Journee entiere
    
    
    

    
    
    
    
    
    
    
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
    
    
    
    

    
    // Resolution de conflit
    /////////////////////////////////////////////////////////
    
    public void resolutionConflit(BaseDeDonnees database, Conflit c) {
    	// 1ere etape, penser a checker que si, pr les 2 chirurgies en conflits, leur durees n'est pas du tout coherente
    	// ie intervalle de confiance 95%  && qu'en plus, cela résoud le conflit  -- alors on la raccourcie direct
    	
    	// puis resoudre conflit
    }
    
    
   
    
    
    
    
    
    
    ////////////////////////////////////////////////////////
    
    
    // ACCESSEURS //
    public ArrayList<Chirurgie> getChirurgiesJour(){
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
    public ArrayList<Bloc> getBlocs(){
    	return sallesOccupeesduJour;
    }
    
           
        
    
}
