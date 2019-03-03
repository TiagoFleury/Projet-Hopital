package classes;

import java.util.ArrayList;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.Period;

public class Ubiquite extends Conflit {
    private Chirurgien chirurgienPb;
    private ArrayList<Bloc> salles;
    
    
    public Ubiquite(Chirurgie ch1, Chirurgie ch2, Journee j){
        super(ch1, ch2, j);
        this.chirurgienPb=ch1.getChirurgien();
        ArrayList<Bloc> blocs = new ArrayList<Bloc>();
        blocs.add(ch1.getSalle());
        blocs.add(ch2.getSalle());
        this.salles=blocs;
    }
    
    
    
    
    
    // Resolution ///////////////////////////////
    
    public void resolutionUbiquite() {
    	// appeler resolution evidente = essayerChangementTrèsEvidentChirurgien()
    	
    	// appeler essayerChangementPresqueEvidentChirurgien()
    	
    	// appeler resolution un peu moins évidente, mais précise car spécifique
    	//       essayerChangementChirurgienPresentSousContraintes()
    	
    	// appeler essayerRacourcirPourResoudre()
    	
    	
    	// appeler essayerChangementChirurgienPresentPeuPrecis()
    	
    	// appeler essayerChangementChirurgienAbsentSousContraintes()
    	
    	// appeler essayerChangementChirurgienExterieur()
    	
    	
    }
    
    
    
    
    // Cette résolution va s'occuper d'un cas spécifique où : 
    //    une des deux chirurgies est anormalement longue, et dans ce cas on peut envisager de la/les raccourcir
    //    le cas où une chirurgie est apres le début ET avant la fin n'est pas pris en compte, et sera résolue d'une autre manière
    
    // integrer un temps inter opératoire
    
    public void essayerRacourcirPourResoudre() {
    	boolean b1=false;
    	boolean b2=false;
    	Chirurgie copie1 = chirurgie1;
    	Chirurgie copie2 = chirurgie1;
    	b1=copie1.anomalieDureeChirurgieOuPas();
    	b2=copie2.anomalieDureeChirurgieOuPas();
    	
    	if (b1==true && b2==false) {
    		// Racourcir ch1 jusqu'a ce que ce ne soit plus un conflit
    		if (copie1.getDebut().isBefore(copie2.getDebut()) && !copie1.getFin().isAfter(copie2.getFin())) {
    			while (copie1.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie1.setFin(copie1.getFin().minusMinutes(1));
    			}
    			
    		}
    		if (copie1.getFin().isAfter(copie2.getFin()) && !copie1.getDebut().isBefore(copie2.getDebut())) {
    			while (copie1.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie1.setDebut(copie1.getDebut().plusMinutes(1));
    				
    			}
    		}
    	}
    	
    	if (b1==false && b2==true) {
    		// Racourcir ch2 jusqu'a ce que ne soit plus un conflit
    		if (copie2.getDebut().isBefore(copie1.getDebut()) && !copie2.getFin().isAfter(copie1.getFin())) {
    			while (copie2.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie2.setFin(copie2.getFin().minusMinutes(1));
    			}
    		}
    		if (copie2.getFin().isAfter(copie1.getFin()) && !copie2.getDebut().isBefore(copie1.getDebut())) {
    			while (copie2.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie2.setDebut(copie2.getDebut().plusMinutes(1));
    			}
    		}
    	}
    	if (b1==true && b2==true) {
    		if (copie1.getDebut().isBefore(copie2.getDebut()) && !copie1.getFin().isAfter(copie2.getFin())) {
    			while (copie1.anomalieDureeChirurgieOuPas()==true && copie2.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie1.setFin(copie1.getFin().minusMinutes(1));
    				copie2.setDebut(copie2.getDebut().plusMinutes(1));
    			}
    			while (copie1.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie1.setFin(copie1.getFin().minusMinutes(1));
    			}
    			while (copie2.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie2.setDebut(copie2.getDebut().plusMinutes(1));
    			}
    		}
    		
    		
    		if (copie2.getDebut().isBefore(copie1.getDebut()) && !copie2.getFin().isAfter(copie1.getFin())) {
    			while (copie1.anomalieDureeChirurgieOuPas()==true && copie2.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie2.setFin(copie2.getFin().minusMinutes(1));
    				copie1.setDebut(copie1.getDebut().plusMinutes(1));
    			}
    			while (copie1.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie1.setDebut(copie1.getDebut().plusMinutes(1));
    			}
    			while (copie2.anomalieDureeChirurgieOuPas()==true && this.jour.conflitOuPas(copie1,copie2)!=null) {
    				copie2.setFin(copie2.getFin().minusMinutes(1));
    			}
    		}
    	}
    	LocalTime debEcart = null;
    	LocalTime finEcart = null;
    	
    	if (copie1.getDebut().isBefore(copie2.getDebut())) {
    		debEcart = copie1.getFin();
    		finEcart = copie2.getDebut();
    	}
    	else {
    		debEcart = copie2.getFin();
    		finEcart = copie1.getDebut();
    	}
    	
    	
    	if (this.jour.conflitOuPas(copie1,copie2)==null && ChronoUnit.MINUTES.between(debEcart, finEcart)>chirurgienPb.getICtempsInteroperatoire().get(1) && ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgienPb.getICtempsInteroperatoire().get(2) ) {
    		// Alors on decide d'appliquer ce changement
    		chirurgie1.setDebut(copie1.getDebut());
    		chirurgie1.setFin(copie1.getFin());
    		chirurgie2.setDebut(copie2.getDebut());
    		chirurgie2.setFin(copie2.getFin());
    	}
    	
    }
    
    
    
    
    public void essayerChangementEvidentDeChirurgien() {
    	// faire la même qu'au dessus, mais via indice de recouvrement, alors direct on part sur un changement de truc
    	
    	// partir du meme principe de candidats (ie les mecs présents), faire des le début ChirurgienFort, voir s'il est là et qu'en plus
    	// il y a un fort indice de recouvrement alors go changement.
    }
    
    
    
    
    
    public boolean essayerChangementChirurgienPresentSousContraintes(BaseDeDonnees database) {
    	boolean result = false;
    	LocalDate auj = this.chirurgie1.getDate();
    	ArrayList<Chirurgien> chirurgiensCandidatsCh1 = new ArrayList<>(this.getChirurgiensLibres1());
    	ArrayList<Chirurgien> copie1 = new ArrayList<>(chirurgiensCandidatsCh1);
    	ArrayList<Chirurgien> chirurgiensCandidatsCh2 = new ArrayList<>(this.getChirurgiensLibres2());
    	ArrayList<Chirurgien> copie2 = new ArrayList<>(chirurgiensCandidatsCh2);
    	
    	// J'ai donc ici une liste de chirurgiens candidats, pour remplacer le Chirurgien Probleme de l'ubiquite
    	// On va procéder par élimination pour trouver qui serait le plus suceptible d'avoir cette chirurgie
    	
    	
    	// ie on va chercher un chirurgien présent, dont la réalisation de cette chirurgie serait dans ses habitudes de travail.
    	 
    	for (Chirurgien albert : copie1) {
    		Chirurgie chTest = new Chirurgie(chirurgie1);
    		int nbNonConflitsJour =0;
    		
    		for (Chirurgie x : albert.recupChirurgiesDuJour(jour)) {
    			if (x.estEnConflit()==false) {
    				nbNonConflitsJour++;
    			}
    		}
    		if (nbNonConflitsJour==0) {
    			chirurgiensCandidatsCh1.remove(albert);
    			System.out.println("ici que tu l'as perdu 1");
    		}
    		
    		// Si le chirurgien est deja surchargé de travail
    		if (albert.getLesJSurcharges().contains(auj) && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    			System.out.println("ici que tu l'as perdu 2");
    		}
    		
    		// Si la chirurgie ne correspond pas a ses durées habituelles
    		if (chTest.anomalieDureeChirurgieOuPas()==true && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    			System.out.println("ici que tu l'as perdu 3");
    		}
    		
    		// Si la chirurgie ne correspond pas à sa plage horaire habituelles
    		if ((albert.getPlagesHorairesPref().get(chTest.indicePlageHoraire()) < 0.3) && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    			System.out.println("ici que tu l'as perdu 4");
    		}
    		
    		
    		// Si la chirurgie ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    	}
    	
    	for (Chirurgien albert : copie2) {
    		Chirurgie chTest = new Chirurgie(chirurgie2);
    		Chirurgien chirurgienTest=albert;
    		int nbNonConflitsJour =0;
    		
    		for (Chirurgie x : albert.recupChirurgiesDuJour(jour)) {
    			if (x.estEnConflit()==false) {
    				nbNonConflitsJour++;
    			}
    		}
    		if (nbNonConflitsJour==0) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si le C est deja surchargé de travail
    		if (albert.getLesJSurcharges().contains(auj) && chirurgiensCandidatsCh2.contains(albert)) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas a ses durées habituelles
    		if (chTest.anomalieDureeChirurgieOuPas()==true && chirurgiensCandidatsCh2.contains(albert)) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas à sa plage horaire habituelles
    		if ((albert.getPlagesHorairesPref().get(chTest.indicePlageHoraire()) < 0.2) && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		
    		// Si la chirurgie ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    	}
    	
    	// On rajoute ici (également) l'idée de chirurgien fort, dans l'hypothese ou il serait le chirurgien qui vérifie toutes les contraintes
    	// alors il serait le résultat évident 
    	// (bien que logiquement, il aurait deja été selectionné lors d'une premiere resolution évidente)
    	
    	Chirurgien chFort1 = this.chirurgie1.getSalle().getChirurgienFort(jour);
    	Chirurgien chFort2 = this.chirurgie2.getSalle().getChirurgienFort(jour);
    	
    	if (chirurgiensCandidatsCh1.size()!=0 && chirurgiensCandidatsCh2.size()==0) {
    		if (chirurgiensCandidatsCh1.contains(chFort1) && !chFort1.equals(chirurgienPb)) {
    			chirurgie1.setChirurgien(chFort1);
    			chFort1.getChirurgies().add(chirurgie1);
    			System.out.println("ca a changé 1");
    		}
    		else {
    			chirurgie1.setChirurgien(chirurgiensCandidatsCh1.get(0));
    			chirurgiensCandidatsCh1.get(0).getChirurgies().add(chirurgie1);
    			System.out.println("ca a changé 2");
    		}
    		this.setEtat(true);
			this.chirurgie1.setEnConflit(false);
			this.chirurgie2.setEnConflit(false);
			result = true;
    	}
    	
    	else if (chirurgiensCandidatsCh1.size()==0 && chirurgiensCandidatsCh2.size()!=0) {
    		if (chirurgiensCandidatsCh2.contains(chFort2) && !chFort2.equals(chirurgienPb)) {
    			chirurgie2.setChirurgien(chFort2);
    			chFort2.getChirurgies().add(chirurgie2);
    			System.out.println("ca a changé 3");
    		}
    		else {
    			chirurgie2.setChirurgien(chirurgiensCandidatsCh2.get(0));
    			chirurgiensCandidatsCh2.get(0).getChirurgies().add(chirurgie2);
    			System.out.println("ca a changé 4");
    		}
    		this.setEtat(true);
			this.chirurgie1.setEnConflit(false);
			this.chirurgie2.setEnConflit(false);
			result = true;
    	}
    	
    	
    	else if (chirurgiensCandidatsCh1.size()!=0 && chirurgiensCandidatsCh2.size()!=0) {
    		if (chirurgiensCandidatsCh1.contains(chFort1) && !chFort1.equals(chirurgienPb)) {
    			chirurgie1.setChirurgien(chFort1);
    			chFort1.getChirurgies().add(chirurgie1);
    			System.out.println("ca a changé 5");
    		}
    		else {
    			if (chirurgiensCandidatsCh2.contains(chFort2) && !chFort2.equals(chirurgienPb)) {
    				this.chirurgie2.setChirurgien(chFort2);
    				chFort2.getChirurgies().add(chirurgie2);
    				System.out.println("ca a changé 6");
    			}
    			else {
    				chirurgie1.setChirurgien(chirurgiensCandidatsCh1.get(0));
    				chirurgiensCandidatsCh1.get(0).getChirurgies().add(chirurgie1);
    				System.out.println("ca a changé 7");
    				}
    			}
    		this.setEtat(true);
			this.chirurgie1.setEnConflit(false);
			this.chirurgie2.setEnConflit(false);
			result = true;
    	}
    	return result;
    	
    }
    
    
    

    
    
    
    public void essayerChangementChirurgienPresentPeuPrecis(BaseDeDonnees database) {
    	Chirurgien chirurgienP = chirurgienPb;
    	Chirurgien unChirurgien = null;
    	Chirurgie chirurgieTest = null;
    	
    	int compteur = 0, lg = database.getTousChirurgiens().size();
    	int nbConflitsGeneres = 0;

    	while (compteur < lg) {
    		unChirurgien = database.getTousChirurgiens().get(compteur);
        	chirurgieTest = chirurgie1;
        	chirurgieTest.setChirurgien(unChirurgien);
        	for (Chirurgie compteuse : this.jour.getChirurgiesJour()) {
        		if (this.jour.conflitOuPas(chirurgieTest, compteuse)!=null) {
        			nbConflitsGeneres++;
        		}
        		
        	}
        	if (this.jour.getChirurgiensMobilises().contains(unChirurgien) && this.jour.conflitOuPas(chirurgieTest,chirurgie2)==null && nbConflitsGeneres==0) {
    			chirurgie1.setChirurgien(unChirurgien);
    			this.resolu=true;
    			compteur = lg;
        	}
    	}
    	
    	
    	if (this.resolu==false) {
    		compteur = 0;
    		lg = database.getTousChirurgiens().size();
    		nbConflitsGeneres =0;

        	while (compteur < lg) {
        		unChirurgien = database.getTousChirurgiens().get(compteur);
            	chirurgieTest = chirurgie2;
            	chirurgieTest.setChirurgien(unChirurgien);
            	for (Chirurgie compteuse : this.jour.getChirurgiesJour()) {
            		if (this.jour.conflitOuPas(chirurgieTest, compteuse)!=null) {
            			nbConflitsGeneres++;
            		}
            		
            	}
            	if (this.jour.getChirurgiensMobilises().contains(unChirurgien) && this.jour.conflitOuPas(chirurgieTest,chirurgie1)==null && nbConflitsGeneres==0 ) {
        			chirurgie2.setChirurgien(unChirurgien);
        			this.resolu=true;
        			compteur = lg;
            	}
        	}
    	}
        		
    		
    }
    
    
    
    
    
    // Si celle au dessus n'a pas marché, alors aller chercher parmis ceux qui bossent pas de la journée (ex : ceux qui bossent pas le mardi15)
    // lesquels ont la plus grosse probas de bosser un mardi
    
    public void essayerChangementChirurgienAbsentSousContraintes(BaseDeDonnees database, Ubiquite u) {
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
		int nbChirurgiensComptabilises=0;
    	
    	while (compteur < lg) {
    		unChirurgien = database.getTousChirurgiens().get(compteur);
    		
    		if (!this.jour.getChirurgiensMobilises().contains(unChirurgien) ) {
    			
    			if (unChirurgien.getProportions().get(numero)>probasMax) {
    				theSurgeon=unChirurgien;
    			}
    			nbChirurgiensComptabilises++;
			}
    		compteur ++ ; // Ci dessus, j'ai simplement test s'il y avait des Chirurgiens NON UTILISES toute la journee car le choisir = cout 0
    	}
    	
    	if (nbChirurgiensComptabilises!=0 && !theSurgeon.equals(chirurgienPb) && !this.jour.getChirurgiensMobilises().contains(theSurgeon)) {
    		u.getCh1().setChirurgien(theSurgeon);
    		u.setEtat(true);
    	}
		
    }
    
    
    /////////////////////////////////////
    
    
    
    
    
    
    // Accesseurs //
    
    public Chirurgien getChirurgienPb() {
    	return this.chirurgienPb;
    }
}
