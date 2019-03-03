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
    
    
    
    
    
    // Resolution
    
    public void resolutionUbiquite() {
    	// METHODE FINALE DE RESOLV D'u, qui appellera toutes les eventuelles résolutions
    }
    
    
    
    // Cette résolution va s'occuper d'un cas spécifique où : 
    //    une des deux chirurgies est anormalement longue, et dans ce cas on peut envisager de la/les raccourcir
    //    le cas où une chirurgie est apres le début ET avant la fin n'est pas pris en compte, et sera résolue d'une autre manière
    
    
    
    public void resoudreUbiquite0() {
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
    
    
    
    
    
    
// DABORD ALLER CHERCHER LES CHIRURGIENS QUI ONT au moins 1 ch en non conflit, car les AUTRES ON EST SUR QU'ils ne sont pas la
    
    // Puis parmis ces memes chirurgiens qui ne sont pas en FULL conflits, il faut vérifier qu'en leurs ajoutant cette chirurgie,
    // cela reste dans IC95 de leurs temps de chirurgies moyens par jour.
    // ie -- qu'on ne provoque pas une anomalieSurchageTravail()
    
    // du coup avant, faire une methode qui envoie dans Chirurgien son IC95 de temps de travail par jour, et redefinir bien anomalie
    
    
    
    // Si dans la journée, parmi ceux qui travaillent, est ce qu'il y en a un qui bosse pas a ce moment là, et qui provoque 0 conflit
    public void resoudreUbiquite11(BaseDeDonnees database) {
    	Chirurgien chirurgienP = chirurgienPb;
    	Chirurgien unChirurgien = null;
    	
    	Chirurgie chirurgieTest = null;
    	
    	int compteur = 0, lg = database.getTousChirurgiens().size();
    	int nbConflitsGeneres = 0;
    	int nbConflitsJourChirurgien;
    	int nbChirurgies = 0;
    	ArrayList<Chirurgien> chCandidats = new ArrayList<>();
    	while (compteur < lg) {
    		nbConflitsJourChirurgien = 0;
    		unChirurgien = database.getTousChirurgiens().get(compteur);
    		for (Chirurgie c : unChirurgien.getChirurgies()) {
    			if (chirurgie2.getDate().equals(c.getDate())) {
    				if (c.estEnConflit()==true) {
    					nbConflitsJourChirurgien++;
    				}
    				nbChirurgies ++ ;
    			}
    		}
    		if (nbConflitsJourChirurgien<=(nbChirurgies-1) &&  nbChirurgies>=2) {
    			chCandidats.add(unChirurgien);
    		}
    		
    		// checker si pas d'anomalie de surcharge de taff
    		// on prend le chirurgien tel que quand on lui rajoute la chirurgie, la durée de sa journée ressemble a sa durée moyenne de journée
    		// et aussi tel que la durée de chirurgie ressemble de ouf a ses durées habituelles de chirurgies
    		
    		// et bien sur tel que ca ne génère pas de conflit
    	}
    	boolean bool = false;
    	Chirurgie chirugieTest = null;
    	ArrayList<Chirurgien> chCandidats2 = new ArrayList<>();
    	for (Chirurgien ch : chCandidats) {
    		bool = ch.anomaliesSurchargeChirurgienOuPas(database);
    		chirurgieTest = chirurgie1;
			chirurgieTest.setChirurgien(ch);
    		if (bool==false) {
    			bool = chirurgieTest.anomalieDureeChirurgieOuPas();
    			if (bool = false ) {
    				if (chirurgieTest.anomaliesChirurgienDureeInterOpeBlocOuPas()!=0) {
    					bool=true;
    				}
    			}
    		}
    		if (bool==false) {
    			chCandidats2.add(ch);
    		}
    	}
    	
    }
    
    
    
    
    
    public void resoudreUbiquite1(BaseDeDonnees database) {
    	LocalDate auj = this.chirurgie1.getDate();
    	ArrayList<Chirurgien> chirurgiensCandidatsCh1 = this.getChirurgiensLibres1();
    	ArrayList<Chirurgien> chirurgiensCandidatsCh2 = this.getChirurgiensLibres2();
    	
    	// J'ai donc ici une liste de chirurgiens candidats, pour remplacer le ChProbleme de l'ubiquite
    	// On va procéder par élimination pour trouver qui serait le plus suceptible d'avoir cette chirurgie normalement
    	
    	
    	for (Chirurgien albert : chirurgiensCandidatsCh1) {
    		Chirurgie chTest = this.chirurgie1;
    		Chirurgien chirurgienTest=albert;
    		int nbNonConflitsJour =0;
    		
    		for (Chirurgie x : albert.recupChirurgiesDuJour(jour)) {
    			if (x.estEnConflit()==false) {
    				nbNonConflitsJour++;
    			}
    		}
    		if (nbNonConflitsJour==0) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si le C est deja surchargé de travail
    		if (albert.getLesJSurchages().contains(auj)) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas a ses durées habituelles
    		if (chTest.anomalieDureeChirurgieOuPas()==true) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas à sa plage horaire habituelles
    		if ()
    		
    		
    		
    		// Si la chirurgie ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		chirurgienTest.recupChirurgiesDuJour(jour).add(chTest);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    	}
    	
    	
    	
    	
    	for (Chirurgien albert : chirurgiensCandidatsCh2) {
    		Chirurgie chTest = this.chirurgie2;
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
    		if (albert.getLesJSurchages().contains(auj)) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas a ses durées habituelles
    		if (chTest.anomalieDureeChirurgieOuPas()==true) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas à sa plage horaire habituelles
    		
    		
    		
    		
    		// Si la chirurgie ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		chirurgienTest.recupChirurgiesDuJour(jour).add(chTest);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    	}
    	
    	
    	
    }
    
    
    
    
    
    public void resoudreUbiquiteee0(BaseDeDonnees database) {
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
    
    public void resoudreUbiquite2(BaseDeDonnees database, Ubiquite u) {
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
    
    
    
    
    
    // Ubiquite classique + cas où le chirurgien travaille un jour ou il ne devrait pas
    public void resoudreUbiquite3(BaseDeDonnees database, Ubiquite u) {
    	
    	// Cest une application classique de changement de chirurgien, il suffit d'appeller Ubiquite0 ????
    }
    
    
    
    
    
    
    
    
    
    // Accesseurs //
    
    public Chirurgien getChirurgienPb() {
    	return this.chirurgienPb;
    }
}
