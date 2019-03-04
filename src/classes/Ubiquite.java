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
    
    
    
    
    
    // Resolution ////////////////////////////////////////
    
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
    
    public boolean NOPE_HUGOessayerRacourcirPourResoudre() {
    	boolean retour = false;
    	boolean b1=false;
    	boolean b2=false;
    	Chirurgie copie1 = new Chirurgie(chirurgie1);
    	Chirurgie copie2 = new Chirurgie(chirurgie2);
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
    		
    		while (this.jour.conflitOuPas(copie1,copie2)!=null && (copie1.getDuree() > copie1.getChirurgien().getICtempsMoyen().get(0)) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgienPb.getICtempsInteroperatoire().get(0))) {
    			copie1.setFin(copie1.getFin().minusMinutes(1));
    			debEcart = copie1.getFin();
        	}
    		while (this.jour.conflitOuPas(copie1,copie2)!=null && (copie2.getDuree() > copie2.getChirurgien().getICtempsMoyen().get(0)) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgienPb.getICtempsInteroperatoire().get(0))) {
    			copie2.setDebut(copie2.getDebut().plusMinutes(1));
    			finEcart = copie2.getDebut();
    		}
    	}
    	else {
    		debEcart = copie2.getFin();
    		finEcart = copie1.getDebut();

    		while (this.jour.conflitOuPas(copie1,copie2)!=null && (copie2.getDuree() > copie2.getChirurgien().getICtempsMoyen().get(0)) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgienPb.getICtempsInteroperatoire().get(0))) {
    			copie2.setFin(copie2.getFin().minusMinutes(1));
    			debEcart = copie2.getFin();
        	}
    		while (this.jour.conflitOuPas(copie1,copie2)!=null && (copie1.getDuree() > copie1.getChirurgien().getICtempsMoyen().get(0)) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgienPb.getICtempsInteroperatoire().get(0))) {
    			copie1.setDebut(copie1.getDebut().plusMinutes(1));
    			finEcart = copie1.getDebut();
    		}
    		
    	}
    	
    	if (this.jour.conflitOuPas(copie1,copie2)==null && (ChronoUnit.MINUTES.between(debEcart, finEcart)>=chirurgienPb.getICtempsInteroperatoire().get(0)) ) {
    		// Alors on decide d'appliquer ce changement
    		chirurgie1.setDebut(copie1.getDebut());
    		chirurgie1.setFin(copie1.getFin());
    		chirurgie2.setDebut(copie2.getDebut());
    		chirurgie2.setFin(copie2.getFin());
    		retour = true;
    		this.setEtat(true);
    	}
    	return retour;
    }
    
    
    
    
    
    //////////////////////////////////////////////////////
    // Resolutions intelligentes et coherentes
    
    // Methode à tenter en n°1
    public boolean essayerChangementEvidentDeChirurgien(BaseDeDonnees data, double indiceRecouvrementDesire) {
//    	Conditions :
//    	1. Gros chevauchement   2. des chirurgiens sont libres a cet horaire    3. un des blocs est dans avec son chirurgien fort
//		4. Les chirurgiens ne peuvent pas etre choisis en dehors de la journee
    	
    	//On fait toutes les conditions qui font qu'on laisse la main a une autre resolution plus laxe ou plus appropriee
    	
    	if(resolu)
    		return false;
    	
    	if(getIndiceDeRecouvrement()<indiceRecouvrementDesire) {
    		return false;
    	}
    	ArrayList<Chirurgien> chirurgiensLibres2 = getChirurgiensLibres2();
    	ArrayList<Chirurgien> chirurgiensLibres1 = getChirurgiensLibres1();
    	if(chirurgiensLibres1.size()==0 && chirurgiensLibres2.size()==0) { 
    		return false;
    	}
    	
    	Chirurgien chFort1 = chirurgie1.getSalle().getChirurgienFort(jour,2);
    	Chirurgien chFort2 = chirurgie2.getSalle().getChirurgienFort(jour,2);
    	
    	//Si le chirurgien fort d'un des deux blocs est libre, on le bouge sans trop se poser de question
    	
    	
    	if(chirurgiensLibres1.contains(chFort1) && !chirurgiensLibres2.contains(chFort2)) {
    		chirurgie1.setChirurgien(chFort1);
    		resolu = true;
    		
    	}
    	if(!chirurgiensLibres1.contains(chFort1) && chirurgiensLibres2.contains(chFort2)) {
    		chirurgie2.setChirurgien(chFort2);
    		resolu = true;
    	}
    	
    	
    	if(!(chirurgienPb.equals(chFort1) || chirurgienPb.equals(chFort2))) { //Si aucun de blocs  n'est avec son bloc fort
    		return false;
    	}
    	//Ici il y a donc forcement un des deux blocs qui est dans avec Ch fort et il faudra bouger l'autre
    	
    	
    	if(chirurgienPb.equals(chFort1) && !chirurgienPb.equals(chFort2)) {//Si c'est le 1, on essaie de bouger la 2
    		
    		if(chirurgiensLibres2.size()==0) {
    			return false; //Si pas de blocs libres, on laisse la main
    		}
    		else {
    			//Sinon, il va falloir choisir un chirurgien parmis tous les blocs libres
    			// => on va arbitrairement prendre celui ou il y a le plus de chirurgie du mec
    			int max=0;
    			Chirurgien cMax=chirurgiensLibres2.get(0);
    			for(Chirurgien chAevaluer : chirurgiensLibres2) {
    				if(chAevaluer.nombreDeChirurgiesDe(chirurgie2.getSalle(),jour)>max) {
    					 cMax=chAevaluer;
    					 max=chAevaluer.nombreDeChirurgiesDe(chirurgie2.getSalle(), jour);
    				}
    			}
    			chirurgie2.setChirurgien(cMax);
    			resolu = true;
    			return true;
    		}
    	}
    	
    	if(chirurgienPb.equals(chFort2) && !chirurgienPb.equals(chFort1)) {//Si c'est le 2, on essaie de bouger la 1
    		
    		if(chirurgiensLibres1.size()==0) {
    			return false; //Si pas de blocs libres, on laisse la main
    		}
    		else {
    			//Sinon, il va falloir choisir un bloc parmis tous les blocs libres
    			// => on va arbitrairement prendre celui ou il y a le plus de chirurgie du mec
    			int max=0;
    			Chirurgien cMax=chirurgiensLibres1.get(0);
    			for(Chirurgien chAevaluer : chirurgiensLibres1) {
    				if(chAevaluer.nombreDeChirurgiesDe(chirurgie1.getSalle(),jour)>max) {
    					 cMax=chAevaluer;
    					 max=chAevaluer.nombreDeChirurgiesDe(chirurgie1.getSalle(), jour);
    				}
    			}
    			chirurgie1.setChirurgien(cMax);
    			resolu = true;
    			return true;
    		}
    	}
    	//Si les deux sont dans leur bloc fort on laisse la main
    	return false;
    }
    
    // Methode à tenter en n°2
    public boolean essayerRaccourcissementEvident(BaseDeDonnees data) {
    	//Conditions pour un raccourcissement evident : 
    	//1. petit indice de recouvrement   2. pas de superposition  3. Anomalie de longueur sur une des deux chirurgies
    	
    	if(resolu)
    		return false;
    	
    	if(getIndiceDeRecouvrement()>0.25) { //En dessous de 0,25 on va dire qu'on traite le cas
    		return false;
    	}
    	
    	boolean anomalieDuree1=false;
    	if(chirurgie1.getDuree()>chirurgie1.getChirurgien().getICtempsMoyen().get(1)) {
    		anomalieDuree1 = true;
    	}
    	boolean anomalieDuree2=false;
    	if(chirurgie2.getDuree()>chirurgie2.getChirurgien().getICtempsMoyen().get(1)) {
    		anomalieDuree2 = true;
    	}
    	
    	Chirurgien chFort1 = chirurgie1.getSalle().getChirurgienFort(jour, 3);
    	Chirurgien chFort2 = chirurgie2.getSalle().getChirurgienFort(jour, 3);
    	
    	
    	if(!(anomalieDuree1 || anomalieDuree2)) { //Si il y a aucune anomalie on traite pas
    		return false;
    	}
    	if(chFort1!=null) {
    		if(chFort1.nombreDeChirurgiesDe(chirurgie1.getSalle(), jour)>4 && getChirurgiensLibres1().contains(chFort1)) {
	    		//si le chFort est important et libre, on abandonne
	    		return false;
    		}
    	}
    	
    	
    	if(chFort2!=null) {
	    	if(chFort2.nombreDeChirurgiesDe(chirurgie1.getSalle(), jour)>4 && getChirurgiensLibres2().contains(chFort2)) {
	    		//si le chFort est important et libre, on abandonne
	    		return false;
	    	}
    	}
    	
    	//Ici on a donc forcement une anomalie de longueur et un "petit recouvrement" sur un des bouts des chirurgies
    	//On va maintenant tronquer la ou les chirurgies
    	
    	
    	if(anomalieDuree1 && !anomalieDuree2) {
//    		On est dans ce cas :
//    			#######################              <- on va racourcir celle la sur sa droite
//    								###########
    		
    		
    		int cote=1; //de base le cote a couper est le droit
    		if(Chirurgie.superposition(chirurgie1, chirurgie2)) {
        		//Dans ce cas il faut decider de quel cote raccourcir
        		cote = coteAcouper();
        	}
    		
    		if(cote == 1) { //Si c'est le cote droit
    			LocalTime backupHeureFin = chirurgie1.getFin(); //C'est la fin de la chirurgie qu'on va amputer
	    		while(Journee.enMemeTempsOuPas(chirurgie1, chirurgie2)) {//Tant que les deux se touchent on raccourcit
	    			chirurgie1.raccourcirFin(1);
	    			if(chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) {
	    				//Si on depasse la borne inferieure de l'intervalle de confiance on arrete et on remet la valeur de base
	    				chirurgie1.setFin(backupHeureFin);
	    				return false;
	    			}
	    		}
    		
	    		
	    		//Une fois qu'elles ne se touchent plus, il faut voir si on peut laisser le temps minimal de nettoyage de bloc
	    		chirurgie1.raccourcirFin(data.getICtempsInteroperatoire().get(0).intValue());
	    		
	    		if(chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) {
					//Si on depasse la borne inferieure de l'intervalle de confiance on arrete et on remet la valeur de base
					chirurgie1.setFin(backupHeureFin);
					return false;
				}
	    		this.resolu=true;
	    		return true;
    		}
    		if(cote == -1) {
    			LocalTime backupHeureDebut = chirurgie1.getDebut(); //c'est le debut qu'on va amputer
    			while(Journee.enMemeTempsOuPas(chirurgie1, chirurgie2)) {//Tant que les deux se touchent on raccourcit
	    			chirurgie1.raccourcirDebut(1);
	    			if(chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) {
	    				//Si on depasse la borne inferieure de l'intervalle de confiance on arrete et on remet la valeur de base
	    				chirurgie1.setDebut(backupHeureDebut);
	    				return false;
	    			}
	    		}
    		
	    		
	    		//Une fois qu'elles ne se touchent plus, il faut voir si on peut laisser le temps minimal de nettoyage de bloc
	    		chirurgie1.raccourcirDebut(data.getICtempsInteroperatoire().get(0).intValue());
	    		
	    		if(chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) {
					//Si on depasse la borne inferieure de l'intervalle de confiance on arrete et on remet la valeur de base
					chirurgie1.setDebut(backupHeureDebut);
					return false;
				}
	    		this.resolu=true;
	    		return true;
    		}
    	}
    	
    	if(!anomalieDuree1 && anomalieDuree2) {
    		//Dans ce cas la logiquement elles peuvent pas �tre superposees
//    		On est dans ce cas :
//    			#########              
//    				  ##########################   <- on va racourcir celle la sur sa gauche
    		LocalTime backupHeureDebut = chirurgie2.getDebut();
    		
    		while(Journee.enMemeTempsOuPas(chirurgie1, chirurgie2)) {//Tant que les deux se touchent on raccourcit
    			chirurgie2.raccourcirDebut(1);
    			if(chirurgie2.getDuree()<chirurgie2.getChirurgien().getICtempsMoyen().get(0)) {
    				//Si on depasse la borne inferieure de l'intervalle de confiance on arrete et on remet la valeur de base
    				chirurgie2.setDebut(backupHeureDebut);
    				return false;
    			}
    		}
    		
    		//Une fois qu'elles ne se touchent plus, il faut voir si on peut laisser le temps minimal de nettoyage de bloc
    		chirurgie2.raccourcirDebut(data.getICtempsInteroperatoire().get(0).intValue());
    		
    		if(chirurgie2.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) {
				//Si on depasse la borne inferieure de l'intervalle de confiance on arrete et on remet la valeur de base
				chirurgie2.setDebut(backupHeureDebut);
				return false;
			}

    		this.resolu=true;
    		return true;
    	}
    	
    	
    	if(anomalieDuree1 && anomalieDuree2 && !Chirurgie.superposition(chirurgie1, chirurgie2)) {
    		//Si les deux sont en anomalie de temps, on coupe les deux
    		LocalTime backupHeureDebut = chirurgie2.getDebut();
    		LocalTime backupHeureFin = chirurgie1.getFin();
    		
    		LocalTime debEcart = LocalTime.from(chirurgie1.getFin());
			LocalTime finEcart = LocalTime.from(chirurgie2.getDebut());
    		
    		while (Journee.enMemeTempsOuPas(chirurgie1, chirurgie2) && (chirurgie1.getDuree()>chirurgie1.getChirurgien().getICtempsMoyen().get(0)) &&  (chirurgie1.getDuree()>chirurgie1.getChirurgien().getICtempsMoyen().get(0)) ) {
    			if (chirurgie1.getDebut().isBefore(chirurgie2.getDebut()) && !chirurgie1.getFin().isAfter(chirurgie2.getFin())) {
    				chirurgie1.raccourcirFin(1);
    				chirurgie2.raccourcirDebut(1);
    			}
    		}
    		if ( (chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) || (chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) ) {
    			chirurgie1.setFin(backupHeureFin);
    			chirurgie2.setDebut(backupHeureDebut);
    			return false;
    		}
    		else {
    			debEcart = LocalTime.from(chirurgie1.getFin());
    			finEcart = LocalTime.from(chirurgie2.getDebut());
    			while (chirurgie1.getDuree()>chirurgie1.getChirurgien().getICtempsMoyen().get(0) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgie1.getChirurgien().getICtempsInteroperatoire().get(0)) ) {
    				chirurgie1.raccourcirFin(1);
    				debEcart = LocalTime.from(chirurgie1.getFin());
        		}
    			while ( chirurgie2.getDuree()>chirurgie2.getChirurgien().getICtempsMoyen().get(0) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgie2.getChirurgien().getICtempsInteroperatoire().get(0))) {
    				chirurgie2.raccourcirDebut(1);
    				finEcart = LocalTime.from(chirurgie2.getDebut());
    			}
    		}
    		
    		if ( (chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) || (chirurgie2.getDuree()<chirurgie2.getChirurgien().getICtempsMoyen().get(0)) ||  (ChronoUnit.MINUTES.between(debEcart, finEcart)<chirurgie2.getChirurgien().getICtempsInteroperatoire().get(0)) ) {
    			chirurgie1.setFin(backupHeureFin);
    			chirurgie2.setDebut(backupHeureDebut);
    			return false;
    		}

    		this.resolu=true;
    		return true;
    		
    	}
    	return false;
    }
    
    
    // Methode à tenter en n°3
    public boolean essayerChangementChirurgienPresentSousContraintes(BaseDeDonnees database) {
    	boolean result = false;
    	LocalDate auj = this.chirurgie1.getDate();
    	ArrayList<Chirurgien> chirurgiensCandidatsCh1 = new ArrayList<>(this.getChirurgiensLibres1());
    	ArrayList<Chirurgien> copie1 = new ArrayList<>(chirurgiensCandidatsCh1);
    	ArrayList<Chirurgien> chirurgiensCandidatsCh2 = new ArrayList<>(this.getChirurgiensLibres2());
    	ArrayList<Chirurgien> copie2 = new ArrayList<>(chirurgiensCandidatsCh2);
    	
    	// J'ai donc ici une liste de chirurgiens candidats, pour remplacer le ChirurgienProbleme de l'ubiquite
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
    			chirurgiensCandidatsCh1.remove(albert);  // je ne garde ce chirurgien dans ma liste, que s'il a au moins une chirurgie lambda qui n'est pas en conflit
    			System.out.println("ici que tu l'as perdu 1");
    		}
    		
    		//Puis je procède aux éliminations, j'enleve le chirurgien  : 
    		// Si le chirurgien est deja surchargé de travail
    		if (albert.getLesJSurcharges().contains(auj) && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas a ses durées habituelles
    		if (chTest.anomalieDureeChirurgieOuPas()==true && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas à sa plage horaire habituelles
    		if ((albert.getPlagesHorairesPref().get(chTest.indicePlageHoraire()) < 0.2) && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si, en donnant cette chirurgie au chirurgien, on ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    	}
    	
    	for (Chirurgien albert : copie2) {
    		Chirurgie chTest = new Chirurgie(chirurgie2);
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
    		
    		// Si, en donnant cette chirurgie au chirurgien, on ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    	}
    	
    	// On rajoute ici (également) l'idée de chirurgien fort, dans l'hypothese ou il serait le chirurgien qui vérifie toutes les contraintes
    	// alors il serait le résultat évident 
    	// (bien que logiquement, il aurait deja été selectionné lors d'une premiere resolution évidente)
    	
    	Chirurgien chFort1 = this.chirurgie1.getSalle().getChirurgienFort(jour,2);
    	Chirurgien chFort2 = this.chirurgie2.getSalle().getChirurgienFort(jour,2);
    	
    	
    	if (chirurgiensCandidatsCh1.size()!=0 && chirurgiensCandidatsCh2.size()==0) {
    		if (chirurgiensCandidatsCh1.contains(chFort1) && !chFort1.equals(chirurgienPb)) {
    			chirurgie1.setChirurgien(chFort1);
    			chFort1.getChirurgies().add(chirurgie1);
    		}
    		else {
    			chirurgie1.setChirurgien(chirurgiensCandidatsCh1.get(0));
    			chirurgiensCandidatsCh1.get(0).getChirurgies().add(chirurgie1);
    		}
    		this.setEtat(true);
			result = true;
    	}
    	
    	else if (chirurgiensCandidatsCh1.size()==0 && chirurgiensCandidatsCh2.size()!=0) {
    		if (chirurgiensCandidatsCh2.contains(chFort2) && !chFort2.equals(chirurgienPb)) {
    			chirurgie2.setChirurgien(chFort2);
    			chFort2.getChirurgies().add(chirurgie2);
    		}
    		else {
    			chirurgie2.setChirurgien(chirurgiensCandidatsCh2.get(0));
    			chirurgiensCandidatsCh2.get(0).getChirurgies().add(chirurgie2);
    		}
    		this.setEtat(true);
			result = true;
    	}
    	
    	
    	else if (chirurgiensCandidatsCh1.size()!=0 && chirurgiensCandidatsCh2.size()!=0) {
    		
    		if (chirurgiensCandidatsCh1.contains(chFort1) && !chirurgiensCandidatsCh2.contains(chFort2) && !chFort1.equals(chirurgienPb)) {
    			chirurgie1.setChirurgien(chFort1);
    			chFort1.getChirurgies().add(chirurgie1);
    		}
    		else if (!chirurgiensCandidatsCh2.contains(chFort1) && chirurgiensCandidatsCh2.contains(chFort2) && !chFort2.equals(chirurgienPb)) {
    			chirurgie2.setChirurgien(chFort2);
    			chFort2.getChirurgies().add(chirurgie2);
    		}
    		else if (chirurgiensCandidatsCh1.contains(chFort1) && chirurgiensCandidatsCh2.contains(chFort2)) {
    			chirurgie1.setChirurgien(chFort1);
    			chFort1.getChirurgies().add(chirurgie1);
    		}
    		
    		else {
    			chirurgie1.setChirurgien(chirurgiensCandidatsCh1.get(0));
    			chirurgiensCandidatsCh1.get(0).getChirurgies().add(chirurgie1);
    		}
    	
    		this.setEtat(true);
			result = true;
    	}
    	return result;
    	
    }
    
    //////////////////////////////////////////////////////
    
    
    
    
    
    
    //////////////////////////////////////////////////////
    // Resolutions peu académiques, tout de même basées sur des statistiques, mais à ne faire que si volonté de Resoudre a tout prix
    
    public void NOPE_HUGO_essayerChangementChirurgienPresentSecondCouteau(BaseDeDonnees database) {
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
    
    
    public void NOPE_HUGO_00essayerChangementChirurgienAbsentSousContraintes(BaseDeDonnees database) {
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

    
    
    
    // Methode à tenter en n°4
    public boolean essayerChangementChirurgienAbsentSousContraintes(BaseDeDonnees database) {
    	boolean result = false;
    	LocalDate auj = this.chirurgie1.getDate();
    	ArrayList<Chirurgien> chirurgiensCandidatsCh1 = new ArrayList<>(database.getTousChirurgiens());
    	ArrayList<Chirurgien> copie1 = new ArrayList<>(chirurgiensCandidatsCh1);
    	ArrayList<Chirurgien> chirurgiensCandidatsCh2 = new ArrayList<>(database.getTousChirurgiens());
    	ArrayList<Chirurgien> copie2 = new ArrayList<>(chirurgiensCandidatsCh2);
    	
    	// J'ai donc ici une liste de chirurgiens candidats, pour remplacer le ChirurgienProbleme de l'ubiquite
    	// On va procéder par élimination pour trouver qui serait le plus suceptible d'avoir cette chirurgie
    	
    	
    	// ie on va chercher un chirurgien absent, dont la réalisation de cette chirurgie serait dans ses habitudes de travail.
    	 
    	for (Chirurgien albert : copie1) {
    		Chirurgie chTest = new Chirurgie(chirurgie1);
    		
    		//j'enleve le chirurgien  : 
    		
    		// si le chirurgien est présent ce jour là 
    		// Car s'il avait été là, alors il a déjà été candidat dans une précédente méthode, et il n'a pas été retenu pour de bonnes raisons
    		if (jour.getChirurgiensMobilises().contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// S'il n'est pas censé travailler le jour qu'on lui propose 
    		int k = jour.getDate().getDayOfWeek().getValue()-1;
    		if (albert.getProportions().get(k)<0.15) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		
    		// Si la chirurgie ne correspond pas a ses durées habituelles
    		if (chTest.anomalieDureeChirurgieOuPas()==true && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas à sa plage horaire habituelles
    		if ((albert.getPlagesHorairesPref().get(chTest.indicePlageHoraire()) < 0.15) && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    		
    		// Si, en donnant cette chirurgie au chirurgien, on ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh1.remove(albert);
    		}
    	}
    	
    	for (Chirurgien albert : copie2) {
    		Chirurgie chTest = new Chirurgie(chirurgie2);
    		
    		//j'enleve le chirurgien  : 
    		
    		// si le chirurgien est présent ce jour là 
    		// Car s'il avait été là, alors il a déjà été candidat dans une précédente méthode, et il n'a pas été retenu pour de bonnes raisons
    		if (jour.getChirurgiensMobilises().contains(albert)) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// S'il n'est pas censé travailler le jour qu'on lui propose 
    		int k = jour.getDate().getDayOfWeek().getValue()-1;
    		if (albert.getProportions().get(k)<0.15) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas a ses durées habituelles
    		if (chTest.anomalieDureeChirurgieOuPas()==true && chirurgiensCandidatsCh2.contains(albert)) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si la chirurgie ne correspond pas à sa plage horaire habituelles
    		if ((albert.getPlagesHorairesPref().get(chTest.indicePlageHoraire()) < 0.15) && chirurgiensCandidatsCh1.contains(albert)) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    		
    		// Si, en donnant cette chirurgie au chirurgien, on ne vérifie pas les contraintes de durées interopératoires nécessaires
    		chTest.setChirurgien(albert);
    		if (chTest.anomalieDureeInterOpeBlocOuPasChirurgien(database)!=0) {
    			chirurgiensCandidatsCh2.remove(albert);
    		}
    	}
    	
    	
    	// Alors on prend un chirurgien qui a des stats respectables de la liste ch1 et il remplacera le chirurgien sur la ch1
    	if (chirurgiensCandidatsCh1.size()!=0) {
    			chirurgie1.setChirurgien(chirurgiensCandidatsCh1.get(0));
    			chirurgiensCandidatsCh1.get(0).getChirurgies().add(chirurgie1);
    		this.setEtat(true);
			result = true;
    	}
    	
    	
    	// Alors on prend un chirurgien qui a des stats respectables de la liste ch2 et il remplacera le chirurgien sur la ch2
    	else if (chirurgiensCandidatsCh2.size()!=0) {
    		chirurgie2.setChirurgien(chirurgiensCandidatsCh2.get(0));
    		chirurgiensCandidatsCh2.get(0).getChirurgies().add(chirurgie2);
    		this.setEtat(true);
			this.chirurgie1.setEnConflit(false);
			this.chirurgie2.setEnConflit(false);
			result = true;
    	}
    
    	return result;
    	
    }

    
    // Methode à tenter en n°5 / Meme déconseillé d'utiliser car manque de cohérence
    public boolean resoEfficaceMaisPeuCoherente(BaseDeDonnees database) {
    	
    	boolean result = false;
    	LocalDate auj = this.chirurgie1.getDate();
    	ArrayList<Chirurgien> chirurgiensCandidats = new ArrayList<>(database.getTousChirurgiens());
    	ArrayList<Chirurgien> copie1 = new ArrayList<>(chirurgiensCandidats);
    	
    	Chirurgien chFort1 = chirurgie1.getSalle().getChirurgienFort(jour, 2);
    	Chirurgien chFort2 = chirurgie2.getSalle().getChirurgienFort(jour, 2);
    	
    	if (chirurgie1.getChirurgien().equals(chFort1)) {
    		for (Chirurgien albert : copie1) {
        		
        		if (jour.getChirurgiensMobilises().contains(albert)) {
        			chirurgiensCandidats.remove(albert);
        		}
    		}
        	
        	if (chirurgiensCandidats.size()!=0) {
        			chirurgie2.setChirurgien(chirurgiensCandidats.get(0));
        			chirurgiensCandidats.get(0).getChirurgies().add(chirurgie2);
        		this.setEtat(true);
    			result = true;
        	}
    	}
    	
    	
    	else {
    		for (Chirurgien albert : copie1) {
        		
        		if (jour.getChirurgiensMobilises().contains(albert)) {
        			chirurgiensCandidats.remove(albert);
        		}
    		}
        	
        	if (chirurgiensCandidats.size()!=0) {
        			chirurgie1.setChirurgien(chirurgiensCandidats.get(0));
        			chirurgiensCandidats.get(0).getChirurgies().add(chirurgie1);
        		this.setEtat(true);
    			result = true;
        	}
    	}
    
    	return result;
    	
    }
    
    //////////////////////////////////////////////////////
    
    
    
    
    
    
    
    // Accesseurs //
    
    public Chirurgien getChirurgienPb() {
    	return this.chirurgienPb;
    }
}
