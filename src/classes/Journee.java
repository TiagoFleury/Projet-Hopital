package classes;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.IOException;

public class Journee {
    private LocalDate date;
    private ArrayList<Conflit> conflitsDuJour;
    private ArrayList<Chirurgie> chirurgiesDuJour;
    private ArrayList<Chirurgien> chirurgiensMobilises;
    private ArrayList<Bloc> sallesOccupeesduJour;
    
    
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

    
    
    
    //AFFICHAGES
    
    public String planningJourneeParBloc(){
    	String str="";
        str+="\n\n\n\n";
        str+="                                                     PLANNING DU "+date+"\n\n";
        str+="Salle          0h   0h30  1h    1h30  2h   2h30   3h    3h30  4h   4h30   5h   5h30   6h   6h30   7h   7h30   8h   8h30    9h   9h30  10h  10h30  11h  11h30  12h  12h30  13h  13h30  14h  14h30  15h  15h30  16h  16h30  17h  17h30  18h  18h30  19h  19h30  20h  20h30  21h  21h30  22h  22h30  23h  23h30  00h\n";
        //  A gauche du '.' c'est 8h,   A droite du '.' c'est 8h05
        str+="               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .\n";
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = chirurgiesDuJour;
        Collections.sort(chirurgiesJourTriees, Chirurgie.PAR_BLOC);
        Bloc blocActuel = null;
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            if(!c.getSalle().equals(blocActuel)) {
            	str+="\n\n               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .\n";
            	str+=c.getSalle().getName()+" :\n"; //On affiche que s'il y a un changement de bloc
            }
            
            str+=c.getID();
            for (int compteur=0;compteur<cbAvant;compteur++){
                str+=" ";
            }
            String nouvNom = reductionNomChirurgienPourAffichage(c.getChirurgien());
            str+=nouvNom;
            int compteur = nouvNom.length();
            while (compteur<=combien){
                str+="#";
                compteur++;
            }
            str+="\n";
            blocActuel = c.getSalle();
        }
        return str;
    }
    
    public String planningJourneeParChirurgien(){
    	String str = "";
        str+="\n\n\n\n";
        str+="                                                      PLANNING DU "+date+"\n\n\n";
        str+="Chirurgien     0h   0h30  1h    1h30  2h   2h30   3h    3h30  4h   4h30   5h   5h30   6h   6h30   7h   7h30   8h  8h30    9h   9h30  10h  10h30  11h  11h30  12h  12h30  13h  13h30  14h  14h30  15h  15h30  16h  16h30  17h  17h30  18h  18h30  19h  19h30  20h  20h30  21h  21h30  22h  22h30  23h  23h30  00h";
        //  A gauche du . c'est 8h, A droite du . c'est 8h05
        str+="               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .\n";
        ArrayList<Chirurgie> chirurgiesJourTriees = new ArrayList<Chirurgie>();
        chirurgiesJourTriees = chirurgiesDuJour;
        Collections.sort(chirurgiesJourTriees, Chirurgie.PAR_CHIRURGIEN);
        
        Chirurgien chirurgienActuel = null;
        for(Chirurgie c : chirurgiesJourTriees){
            int cbAvant = cbdecaracteresAvant(c);
            int combien=cbdecaracteresNecessaires(c);
            if(!c.getChirurgien().equals(chirurgienActuel)) {
            	str+="\n\n               .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .     .    .\n";
            	
            	str+=c.getChirurgien().getName()+" :\n"; //On affiche que s'il y a un changement de chirurgien
            }
            str+=c.getID();
            for (int compteur=0;compteur<cbAvant;compteur++){
                str+=" ";
            }
            str+=c.getSalle().getName();
            int compteur = c.getSalle().getName().length();
            while (compteur<combien){
                str+="#";
                compteur++;
            }
            str+="\n";
            chirurgienActuel = c.getChirurgien();
        }
        return str;
        
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
    
   
    public void purgerConflit() {
    	for(Conflit c : conflitsDuJour) {
    		if(!c.getEtat()) {//si le conflit est en resolu, on l'enleve de la liste
    			conflitsDuJour.remove(c);
    		}
    	}
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
                	}
                }
                else if (conflitsDuJour.size()==0) {
                	if (conf!=null) {
                		conflitsDuJour.add(conf);
                	}
                }
            }
        }
        for(Chirurgie c : chirurgiesDuJour) {
        	c.setEnConflit(false);
        	for(Conflit conflit : conflitsDuJour) {
        		if(c.equals(conflit.getCh1()) || c.equals(conflit.getCh2())) {
        			c.setEnConflit(true);
        		}
        	}
        }
    }
    
    
    public int[] resoudreConflits(BufferedWriter writer, BaseDeDonnees data, int degre) {
    	detectionConflit();
    	//D'abord les chevauchements evidents
    	int evident=0;
    	int moyen=0;
    	int shetan=0;
    	int[] tab = new int[3];
    	
    	String planning;
    	
    	
    	if(conflitsDuJour.size()==0) {
    		return tab;
    	}
    		
    	
    	boolean changementEffectue=true;//Pour entrer dans les boucles
    	
    	try {
    		
	    	
	    	while(changementEffectue) {
		    	
		    	
		    	while(changementEffectue) {
		    		//Quand on a fait une resolution compliquee, on reessaie les resolutions simples avec le reste
		    		
			    	while(changementEffectue) {//Tant que des changements se font, on fait des resolutions simples
			    		detectionConflit();
			    		changementEffectue=false;
				    	for(Conflit c : conflitsDuJour) { //On va faire d'abord tous les chevauchements simples
				    		if(c instanceof Chevauchement ) {
				    			Chevauchement chevauchement = (Chevauchement) c;
				    			planning=planningJourneeParBloc();
				    			if(chevauchement.essayerDecalageEvident()) {
				    				changementEffectue=true;
				    				evident++;
				    				writer.write("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();
				    				System.out.print("------------------------------------------------------------------------------------------\n");
				    				System.out.println(planning);
				    				writer.write("\nLe chevauchement "+c+" a ete corrige par un deplacement evident\n");
				    				writer.newLine();
				    				System.out.println("\nLe chevauchement "+c+" a ete corrige par un deplacement evident\n");
				    				System.out.println("Nouveau planning : \n");
				    				
				    				System.out.println(planningJourneeParBloc());
				    				writer.flush();
				    				
				    			}
				    		}
				    	}
				    	//Ici on aura fait tous les chevauchements evidents
				    	
				    	for(Conflit c : conflitsDuJour) {
				    		if(c instanceof Interference) {
				    			Interference interf = (Interference) c;
				    			planning = planningJourneeParBloc();
				    			if(interf.essayerChangementDeSalleEvident()){
				    				changementEffectue=true;
				    				evident++;
				    				writer.write("------------------------------------------------------------------------------------------\n");

				    				System.out.println("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();
				    				System.out.println(planning);
				    				writer.write("\nL'interference "+c+" a ete corrige par un changement de bloc evident\n");
				    				writer.newLine();

				    				System.out.println("\nL'interference "+c+" a ete corrige par un changement de bloc evident\n");
				    				System.out.println("Nouveau planning : \n");
				    				System.out.println(planningJourneeParBloc());
				    				writer.flush();
				    				
				    			}
				    			else if(interf.essayerRaccourcissementEvident(data)) {
				    				changementEffectue=true;
				    				evident++;
				    				writer.write("------------------------------------------------------------------------------------------\n");

				    				System.out.println("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();
				    				System.out.println(planning);
				    				writer.write("\nL'interference "+c+" a ete corrige par un raccourcissement evident\n");
				    				writer.newLine();

				    				System.out.println("\nL'interference "+c+" a ete corrige par un raccourcissement evident\n");
				    				System.out.println("Nouveau planning : \n");
				    				System.out.println(planningJourneeParBloc());
				    				writer.flush();
				    			}
				    		}
				    	}
				    	
				    	//Ici on aura fait toutes les interferences evidentes
				    	
				    	for(Conflit c : conflitsDuJour) {
				    		if(c instanceof Ubiquite) {
				    			Ubiquite ubiq = (Ubiquite)c;
				    			planning = planningJourneeParChirurgien();
				    			if(ubiq.essayerChangementEvidentDeChirurgien(data, 0.2)) {
				    				changementEffectue=true;
				    				evident++;
				    				writer.write("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();

				    				System.out.println("------------------------------------------------------------------------------------------\n");
				    				System.out.println(planning);
				    				writer.write("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien evident\n");

				    				System.out.println("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien evident\n");
				    				writer.newLine();
				    				System.out.println("Nouveau planning : \n");
				    				System.out.println(planningJourneeParChirurgien());
				    				writer.flush();
				    			}
				    			else if(ubiq.essayerRaccourcissementEvident(data)) {
				    				changementEffectue=true;
				    				evident++;
				    				writer.write("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();
				    				System.out.println("------------------------------------------------------------------------------------------\n");
				    				System.out.println(planning);
				    				writer.write("\nL'ubiquite "+c+" a ete corrigee par un raccourcissement evident\n");

				    				System.out.println("\nL'ubiquite "+c+" a ete corrigee par un raccourcissement evident\n");
				    				writer.newLine();
				    				System.out.println("Nouveau planning : \n");
				    				System.out.println(planningJourneeParChirurgien());
				    				writer.flush();
				    			}
				    		}
				    	}
			    	} //Fin premier while
			    	
			    	//Ici il n'y a plus de resolutions simples a faire normalement
			    	if(degre>1) {
				    	for(Conflit c : conflitsDuJour) {
				    		if(c instanceof Interference) {
				    			Interference interf = (Interference) c;
				    			planning = planningJourneeParBloc();
				    			if(interf.essayerDeplacementDeForce(data)){
				    				changementEffectue=true;
				    				moyen++;
				    				writer.write("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();
				    				System.out.println("------------------------------------------------------------------------------------------\n");
				    				System.out.println(planning);
				    				writer.write("\nL'interference "+c+" a ete corrige par un deplacement de bloc force\n");

				    				System.out.println("\nL'interference "+c+" a ete corrige par un deplacement de bloc force\n");
				    				writer.newLine();
				    				System.out.println("Nouveau planning : \n");
				    				System.out.println(planningJourneeParBloc());
				    				writer.flush();
				    				break;
				    			}
				    		}
				    		
				    		
			    			if(c instanceof Ubiquite) {
				    			Ubiquite ubiq = (Ubiquite)c;
				    			planning = planningJourneeParChirurgien();
				    		
				    			if(ubiq.essayerChangementPresqueEvidentDeChirurgien(data)) {
				    				changementEffectue=true;
				    				moyen++;
				    				writer.write("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();
				    				System.out.println("------------------------------------------------------------------------------------------\n");
				    				System.out.println(planning);
				    				writer.write("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien force\n");

				    				System.out.println("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien force\n");
				    				writer.newLine();
				    				System.out.println("Nouveau planning : \n");
				    				System.out.println(planningJourneeParChirurgien());
				    				writer.flush();
				    				break;
				    			}
				    			else if(ubiq.essayerChangementChirurgienPresentSousContraintes(data)) {
				    				changementEffectue=true;
				    				moyen++;
				    				writer.write("------------------------------------------------------------------------------------------\n");
				    				writer.newLine();
				    				System.out.println("------------------------------------------------------------------------------------------\n");
				    				System.out.println(planning);
				    				
				    				writer.write("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien sous contraintes\n");
				    				writer.newLine();
				    				System.out.println("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien sous contraintes\n");
				    				System.out.println("Nouveau planning : \n");
				    				System.out.println(planningJourneeParChirurgien());
				    				writer.flush();
				    				break;
				    			}
				    		}
			    		
				    	}
			    	}
			    	
			    	
		    	}//Fin deuxieme while
		    	
		    	//Ici il n'y a eu aucune modifications, ni avec les resolutions evidentes, ni avec les resolutions moyennes
		    	
		    	
		    	if(degre>2) {
		    		for(Conflit c : conflitsDuJour) {
			    		if(c instanceof Interference) {
			    			Interference interf = (Interference) c;
			    			planning = planningJourneeParBloc();
			    			if(interf.vendreSonAmeAuShetan(data)){
			    				changementEffectue=true;
			    				shetan++;
			    				writer.write("------------------------------------------------------------------------------------------\n");

			    				System.out.println("------------------------------------------------------------------------------------------\n");
			    				writer.newLine();
			    				System.out.println(planning);
			    				writer.write("\nL'interference "+c+" a ete corrige par un deplacement peu coherent\n");

			    				System.out.println("\nL'interference "+c+" a ete corrige par un deplacement peu coherent\n");
			    				writer.newLine();
			    				System.out.println("\nL'interference "+c+" a ete corrigee par un deplacement peu coherent\n");
			    				System.out.println("Nouveau planning : \n");
			    				System.out.println(planningJourneeParBloc());
			    				writer.flush();
			    				break;
			    			}
			    		}
			    		
		    			if(c instanceof Ubiquite) {
			    			Ubiquite ubiq = (Ubiquite)c;
			    			planning = planningJourneeParChirurgien();
			    			if(ubiq.essayerChangementChirurgienAbsentSousContraintes(data)) {
			    				changementEffectue=true;
			    				shetan++;
			    				writer.write("------------------------------------------------------------------------------------------\n");
			    				System.out.println("------------------------------------------------------------------------------------------\n");
			    				writer.newLine();
			    				System.out.println(planning);
			    				writer.write("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien sous contraintes mais absent cette journee\n");
			    				writer.newLine();

			    				System.out.println("\nL'ubiquite "+c+" a ete corrigee par un changement de chirurgien sous contraintes mais absent cette journee\n");
			    				System.out.println("Nouveau planning : \n");
			    				System.out.println(planningJourneeParChirurgien());
			    				writer.flush();
			    				break;
			    			}
			    			else if(ubiq.resoEfficaceMaisPeuCoherente(data)) {
			    				changementEffectue=true;
			    				shetan++;
			    				writer.write("------------------------------------------------------------------------------------------\n");
			    				System.out.println("------------------------------------------------------------------------------------------\n");
			    				writer.newLine();
			    				System.out.println(planning);
			    				writer.write("\nL'ubiquite "+c+" a ete corrigee par un resolution peu coherente\n");

			    				System.out.println("\nL'ubiquite "+c+" a ete corrigee par un resolution peu coherente\n");
			    				writer.newLine();
			    				System.out.println("Nouveau planning : \n");
			    				System.out.println(planningJourneeParChirurgien());
			    				writer.flush();
			    				break;
			    			}
			    		}
		    		
			    	}
		    	}
		    	
		    	
		    	
		    	
	    	} //Fin troisieme while
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    	tab[0]=evident;
    	tab[1]=moyen;
    	tab[2]=shetan;
    	
    	return tab;
    	
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
