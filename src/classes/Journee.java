package classes;

import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.Comparator;
public class Journee {
    private LocalDate date;
    private ArrayList<Conflit> conflitsDuJour;
    private ArrayList<Chirurgie> chirurgiesDuJour;
    private ArrayList<Chirurgien> chirurgiensMobilises;
    private ArrayList<Bloc> sallesOccupees;
    
    public Journee() {
    	conflitsDuJour = new ArrayList<Conflit>();
    	chirurgiesDuJour = new ArrayList<Chirurgie>();
    	chirurgiensMobilises = new ArrayList<Chirurgien>();
    	sallesOccupees = new ArrayList<Bloc>();
    }
    public Journee(Chirurgie c) { //Instancie une journee a partir des parametres de c
    	date = c.getDate();
    	
    	conflitsDuJour = new ArrayList<Conflit>();
    	
    	chirurgiesDuJour = new ArrayList<Chirurgie>();
    	chirurgiesDuJour.add(c);
    	
    	chirurgiensMobilises = new ArrayList<Chirurgien>();
    	chirurgiensMobilises.add(c.getChirurgien());
    	
    	sallesOccupees = new ArrayList<Bloc>();
    	sallesOccupees.add(c.getSalle());
    }
    
    
    public void importerInfoChirurgie(Chirurgie c) { //recupere les infos d'une chirurgie et les ajoute dans la journee 
    											  //SANS creer de doublons
    	if(!chirurgiesDuJour.contains(c)) {
    		chirurgiesDuJour.add(c);
    		if(chirurgiensMobilises.contains(c.getChirurgien()))
    			chirurgiensMobilises.add(c.getChirurgien());
    		if(sallesOccupees.contains(c.getSalle()))
    			sallesOccupees.add(c.getSalle());
    	}
    	
    }
    
    
    // 1. Affichage des chirurgies de la journee - 2 plannings envisages afin de bien voir les conflits : 1 par Bloc, 1 par Chirurgien
    
    public String reductionNomChirurgienPourAffichage(Chirurgien albert) {
    	String[] separation = albert.getName().split(" ");
    	char[] caracteres = new char[1];
    	separation[0].getChars(0, 1, caracteres, 0);
    	
    	String nomDeFamReduit = separation[1].substring(0, 3);
    	return caracteres[0]+"."+nomDeFamReduit; 
    }   
    
    public int cbdecaracteresNecessaires(Chirurgie c){
        // va prendre la valeur de dizaines de minutes entre le debut et la fin d'une chirurgie
        double i = ChronoUnit.MINUTES.between(c.getDebut(), c.getFin());
        int j = (int) i/5 ;
        return j;
    }
    public int cbdecaracteresAvant(Chirurgie c){
        LocalTime debutJournee = LocalTime.of(8, 0);
        double i = ChronoUnit.MINUTES.between(debutJournee, c.getDebut());
        int j= (int) i/5;
        @SuppressWarnings("deprecation")
		int nbCarac = new Integer(c.getID()).toString().length();
        return j+15-nbCarac;
    }    

    
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
    
    
    
    public void planningJourneeParBloc(){
        System.out.println("\n\n\n");
        System.out.println("                                                     PLANNING DU "+date+"\n\n");
        System.out.println("Salle          8h  8h30    9h   9h30  10h  10h30  11h  11h30  12h  12h30  13h  13h30  14h  14h30  15h  15h30  16h  16h30  17h  17h30  18h  18h30  19h  19h30  20h  20h30  21h  21h30  22h  22h30  23h  23h30  00h");
        //  A  gauche du . c'est 8h, A  droite du . c'est 8h05
        System.out.println("               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .");
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = triParBlocs(chirurgiesDuJour);
        Bloc blocActuel = null;
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            if(!c.getSalle().equals(blocActuel)) {
            	System.out.println("\n\n               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .");
            	System.out.println(c.getSalle().getName()+" :"); //On affiche que s'il y a un changement de bloc
            }
            
            System.out.print(c.getID());
            for (int compteur=0;compteur<cbAvant;compteur++){
                System.out.print(" ");
            }
            String nouvNom = reductionNomChirurgienPourAffichage(c.getChirurgien());
            System.out.print(nouvNom);
            int compteur = nouvNom.length();
            while (compteur<combien){
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
        System.out.println("Chirurgien     8h  8h30    9h   9h30  10h  10h30  11h  11h30  12h  12h30  13h  13h30  14h  14h30  15h  15h30  16h  16h30  17h  17h30  18h  18h30  19h  19h30  20h  20h30  21h  21h30  22h  22h30  23h  23h30  00h");
        //  A  gauche du . c'est 8h, A  droite du . c'est 8h05
        System.out.println("               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .");
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = triParChirurgien(chirurgiesDuJour);
        
        Chirurgien chirurgienActuel = null;
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            if(!c.getChirurgien().equals(chirurgienActuel)) {
            	System.out.println("\n\n               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .");
            	
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
        if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isBefore(y.getDebut()))){
            if (x.getFin().isAfter(y.getDebut())){
                if (x.getChirurgien()==y.getChirurgien()){
                    b=true;
                }
            }
        }
        else if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isAfter(y.getDebut()))){
            if (y.getFin().isAfter(x.getDebut())){
                if (x.getChirurgien()==y.getChirurgien()){
                    b=true;
                }
        }
        }
        return b;
    }
    

    public boolean interferenceOuPas(Chirurgie x, Chirurgie y){
        boolean b=false;
        if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isBefore(y.getDebut()))){
            if (x.getFin().isAfter(y.getDebut())){
                if (x.getSalle()==y.getSalle()){
                    b=true;
                }
            }
        }
        else if ((x.getDate().isEqual(y.getDate())) && (x.getDebut().isAfter(y.getDebut()))){
            if (y.getFin().isAfter(x.getDebut())){
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
            if (x.getDebut().isBefore(y.getDebut())){
                debConflit=y.getDebut();
            }
            else { debConflit=x.getDebut();}
            if (x.getFin().isBefore(y.getFin())){
                finConflit=x.getFin();
            }
            else { finConflit=y.getFin();}
            c = new Interference(x,y);
        }
        else if ((uBool==true) && (iBool==true)){
            if (x.getDebut().isBefore(y.getDebut())){
                debConflit=y.getDebut();
            }
            else { debConflit=x.getDebut();}
            if (x.getFin().isBefore(y.getFin())){
                finConflit=x.getFin();
            }
            else { finConflit=y.getFin();}
            c = new Chevauchement(x,y);
        }
        return c;
    }
    
    

    
    public ArrayList<Conflit> detectionConflit(){
        Conflit conf = null;
        boolean b = false;
        int compteur = 0;
        ArrayList<Conflit> conflitsDuJour = new ArrayList<Conflit>();
        for (Chirurgie c1 : this.chirurgiesDuJour){
            for (Chirurgie c2 : this.chirurgiesDuJour){
                if (c1!=c2){
                    conf=conflitOuPas(c1,c2);
                }
                compteur=0;
                if ((conflitsDuJour.size()!=0)&&(conf!=null)) {
                	for (Conflit unConflit : conflitsDuJour) {
                    	b=conf.equals(unConflit);
                    	if (b==true) {
                    		compteur+=1;
                    	}
                    }
                    if ((conf!=null)&&(compteur==0)){
                    	conflitsDuJour.add(conf);
                    }
                }
                else if (conflitsDuJour.size()==0) {
                	if (conf!=null) {
                		conflitsDuJour.add(conf);
                	}
                }
                
            }
        }
        return conflitsDuJour;
    }
    
    
    
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
    
    
           
        
    
}
