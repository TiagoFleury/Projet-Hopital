package classes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Comparator;
public class Journee {
    private Date date;
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
    
    
    public int cbdecaracteresNecessaires(Chirurgie c){
        // va prendre la valeur de dizaines de minutes entre le début et la fin d'une chirurgie
        double i = ChronoUnit.MINUTES.between(c.getDebut(), c.getFin());
        int j = (int) i/10 ;
        return j;
    }
    public int cbdecaracteresAvant(Chirurgie c){
        LocalDateTime debutJournee = LocalDateTime.of(c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDayOfMonth(),8,0);
        double i = ChronoUnit.MINUTES.between(debutJournee, c.getDebut());
        int j= (int) i/10;
        return j+15;
    }
    
    
    
        // A FAIRE
    // Methode qui recupere ce que Tiago envoie dans la classe Planning ie =
    // Il retourne une arrayList<Chirurgie>
    // la recevoir via 2 tris = 1er tri qui va selectionner les chirurgies des memes jours, et creer des objets Journees
    // via des constructeurs
    
    // parmis chacunes de ces journees, creer une methode qui return ArrayList<chirurgie> mais triee par blocs
    // public ArrayList<Chirurgie> triParBlocs()
    
    // on aura alors une liste de journees, et chacune de ses journees peut avoir une liste de chirurgies triees par blocs
    

    
    public ArrayList<Chirurgie> triParBlocs(ArrayList<Chirurgie> liste) {
    	Comparator<Chirurgie> PAR_BLOC = Comparator.comparing(Chirurgie::getSalle);
    	
    	Collections.sort(liste, PAR_BLOC);
    	return liste;
    }
    public static ArrayList<Chirurgie> triParChirurgien(ArrayList<Chirurgie> liste){
    	Comparator<Chirurgie> PAR_CHIRURGIEN = Comparator.comparing(Chirurgie::getChirurgien);
    	Collections.sort(liste, PAR_CHIRURGIEN);
    	return liste;
    }
    
    
    public void planningJourneeParBloc(){
        System.out.println("\n");
        System.out.println("Salle          8h  8h30   9h   9h30   10h   10h30   11h   11h30   12h   12h30   13h   13h30   14h   14h30   15h   15h30   16h   16h30   17h   17h30   18h   18h30   19h   19h30   20h   20h30   21h   21h30   22h   22h30   23h   23h30   00h");
        System.out.println("\n");
        
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = triParBlocs(chirurgiesDuJour);
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            System.out.println(c.getSalle().getName());
            for (int compteur=0;compteur<cbAvant;compteur++){
                System.out.print(" ");
            }
            System.out.print(c.getChirurgien().getName());
            int compteur = c.getChirurgien().getName().length();
            while (compteur<combien){
                System.out.print("#");
                compteur++;
            }
            System.out.println("");
        }
    }
    
    public void planningJourneeParChirurgien(){
        System.out.println("\n");
        System.out.println("Chirurgien     8h   8h30   9h   9h30   10h   10h30   11h   11h30   12h   12h30   13h   13h30   14h   14h30   15h   15h30   16h   16h30   17h   17h30   18h   18h30   19h   19h30   20h   20h30   21h   21h30   22h   22h30   23h   23h30   00h");
        System.out.println("\n");
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = triParChirurgien(chirurgiesDuJour);
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            System.out.println(c.getChirurgien().getName());
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
        }
        
    }
    
    
    
    
    
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
            c = new Ubiquite(x.getDate(),debConflit,finConflit,x,y,false);
            
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
            c = new Interference(x.getDate(),debConflit,finConflit,x,y,false);
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
            c = new Chevauchement(x.getDate(),debConflit,finConflit,x,y,false);
        }
        return c;
    }
    
    

    
    public ArrayList<Conflit> detectionConflit(){
        Conflit conf = null;
        ArrayList<Conflit> conflitsDuJour = new ArrayList<Conflit>();
        for (Chirurgie c1 : this.chirurgiesDuJour){
            for (Chirurgie c2 : this.chirurgiesDuJour){
                if (c1!=c2){
                    conf=conflitOuPas(c1,c2);
                }
                if (conf!=null){
                    conflitsDuJour.add(conf);
                }
            }
        }
        return conflitsDuJour;
    }
    
    
    
    
    // ACCESSEURS //
    public ArrayList<Chirurgie> getChirurgieJour(){
        return this.chirurgiesDuJour;
    }
    
           
        
    
}
