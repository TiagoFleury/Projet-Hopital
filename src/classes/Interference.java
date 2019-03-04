package classes;

import java.util.ArrayList;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Interference extends Conflit {
    private Bloc sallePb;
    
    
    public Interference(Chirurgie ch1, Chirurgie ch2, Journee j){
        super(ch1, ch2, j);
        this.sallePb=ch1.getSalle();
        ArrayList<Chirurgien> chirurgiensPb = new ArrayList<Chirurgien>();
        chirurgiensPb.add(ch1.getChirurgien());
        chirurgiensPb.add(ch2.getChirurgien());
    }
    
    public Bloc getSallePb() {
    	return this.sallePb;
    }
    
    
    
    public boolean essayerChangementDeSalleEvident() {
//    	Conditions :
//    	1. Gros chevauchement   2. des blocs sont libres a cet horaire    3. un des chirurgiens est dans son bloc fort
//		4. Les blocs E peuvent pas etre choisis en dehors de la journee
    	
    	//On fait toutes les conditions qui font qu'on laisse la main a une autre resolution plus laxe ou plus appropriee
    	
    	if(resolu)
    		return false;
    	
    	if(getIndiceDeRecouvrement()<0.6) {
    		return false;
    	}
    	ArrayList<Bloc> blocsLibres2 = getBlocsLibres2();
    	ArrayList<Bloc> blocsLibres1 = getBlocsLibres1();
    	if(blocsLibres1.size()==0 && blocsLibres2.size()==0) { //Si il n'y a aucun bloc libre a cet horaire
    		return false;
    	}
    	
    	Bloc blocFort1 = chirurgie1.getChirurgien().getBlocFort(jour, 4);
    	Bloc blocFort2 = chirurgie2.getChirurgien().getBlocFort(jour, 4);
    	
    	//Si le bloc fort d'un des deux chirurgiens est libre, on le bouge sans trop se poser de question
    	
    	
    	if(blocsLibres1.contains(blocFort1) && !blocsLibres2.contains(blocFort2)) {
    		if(!(blocFort1.getID()<=3 && chirurgie1.estLaNuit())) {
	    		chirurgie1.setSalle(blocFort1);
	    		resolu = true;
    		}
    	}
    	if(!blocsLibres1.contains(blocFort1) && blocsLibres2.contains(blocFort2)) {
    		if(!(blocFort2.getID()<=3 && chirurgie2.estLaNuit())) {	
    			chirurgie2.setSalle(blocFort2);
	    		resolu = true;
    		}
    		
    	}
    	
    	
    	if(blocFort1==null || blocFort2==null){
    		return false;
    	}
    	
    	
    	if(!(blocFort1.equals(this.sallePb) || blocFort2.equals(this.sallePb))) { //Si aucun des chirurgiens  n'est sur son bloc fort
    		return false;
    	}
    	//Ici il y a donc forcement un des deux chirurgiens qui est dans son bloc fort et il faudra bouger l'autre
    	
    	if(blocFort1.equals(this.sallePb) && !blocFort2.equals(this.sallePb)) {//Si c'est le 1, on essaie de bouger la 2
    		
    		if(blocsLibres2.size()==0) {
    			return false; //Si pas de blocs libres, on laisse la main
    		}
    		else {
    			//Sinon, il va falloir choisir un bloc parmis tous les blocs libres
    			// => on va arbitrairement prendre celui ou il y a le plus de chirurgie du mec
    			int max=0;
    			Bloc bMax=blocsLibres2.get(0);
    			for(Bloc blocAevaluer : blocsLibres2) {
    				if(blocAevaluer.nombreDeChirurgiesDe(chirurgie2.getChirurgien(),jour)>max) {
    					 bMax=blocAevaluer;
    					 max=blocAevaluer.nombreDeChirurgiesDe(chirurgie2.getChirurgien(), jour);
    				}
    			}
    			if(!(bMax.getID()<=3 && chirurgie2.estLaNuit())) {
	    			chirurgie2.setSalle(bMax);
	    			resolu = true;
	    			return true;
    			}
    		}
    	}
    	
    	if(blocFort2.equals(this.sallePb) && !blocFort1.equals(this.sallePb)) {//Si c'est le 2, on essaie de bouger la 1
    		
    		if(blocsLibres1.size()==0) {
    			return false; //Si pas de blocs libres, on laisse la main
    		}
    		else {
    			//Sinon, il va falloir choisir un bloc parmis tous les blocs libres
    			// => on va arbitrairement prendre celui ou il y a le plus de chirurgie du mec
    			int max=0;
    			Bloc bMax=blocsLibres1.get(0);
    			for(Bloc blocAevaluer : blocsLibres1) {
    				if(blocAevaluer.nombreDeChirurgiesDe(chirurgie1.getChirurgien(),jour)>max) {
    					 bMax=blocAevaluer;
    					 max=blocAevaluer.nombreDeChirurgiesDe(chirurgie1.getChirurgien(), jour);
    				}
    			}
    			if(!(bMax.getID()<=3 && chirurgie1.estLaNuit())) {
	    			chirurgie1.setSalle(bMax);
	    			resolu = true;
	    			return true;
    			}
    			
    		}
    	}
    	//Si les deux sont dans leur bloc fort on laisse la main
    	return false;
    	
    	
    }
    
    

    
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
    	System.out.println("Borne : "+chirurgie2.getChirurgien().getICtempsMoyen().get(1)+"  duree ch"+chirurgie2.getID()+"  : "+chirurgie2.getDuree());
    	if(chirurgie2.getDuree()>chirurgie2.getChirurgien().getICtempsMoyen().get(1)) {
    		anomalieDuree2 = true;
    	}
    	
    	Bloc blocFort1 = chirurgie1.getChirurgien().getBlocFort(jour, 4);
    	Bloc blocFort2 = chirurgie2.getChirurgien().getBlocFort(jour, 4);
    	
    	
    	if(!(anomalieDuree1 || anomalieDuree2)) { //Si il y a aucune anomalie on traite pas
    		return false;
    	}
    	if(blocFort1!=null) {
    		if(blocFort1.nombreDeChirurgiesDe(chirurgie1.getChirurgien(), jour)>4 && getBlocsLibres1().contains(blocFort1)) {
	    		//si le blocFort est important et libre, on abandonne
	    		return false;
    		}
    	}
    	
    	
    	
    	if(blocFort2!=null) {
	    	if(blocFort2.nombreDeChirurgiesDe(chirurgie1.getChirurgien(), jour)>4 && getBlocsLibres2().contains(blocFort2)) {
	    		//si le blocFort est important et libre, on abandonne
	    		return false;
	    	}
    	}
    	
    	//Ici on a donc forcement une anomalie de longueur et un "petit recouvrement" sur un des bouts des chirurgies
    	//On va maintenant tronquer la chirurgie qui est le plus en anomalie de longueur
    	
    	
    	
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
    			while (chirurgie1.getDuree()>chirurgie1.getChirurgien().getICtempsMoyen().get(0) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<data.getICtempsInteroperatoire().get(0)) ) {
    				chirurgie1.raccourcirFin(1);
    				debEcart = LocalTime.from(chirurgie1.getFin());
        		}
    			while ( chirurgie2.getDuree()>chirurgie2.getChirurgien().getICtempsMoyen().get(0) && (ChronoUnit.MINUTES.between(debEcart, finEcart)<data.getICtempsInteroperatoire().get(0))) {
    				chirurgie2.raccourcirDebut(1);
    				finEcart = LocalTime.from(chirurgie2.getDebut());
    			}
    		}
    		
    		if ( (chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) || (chirurgie2.getDuree()<chirurgie2.getChirurgien().getICtempsMoyen().get(0)) ||  (ChronoUnit.MINUTES.between(debEcart, finEcart)<data.getICtempsInteroperatoire().get(0)) ) {
    			chirurgie1.setFin(backupHeureFin);
    			chirurgie2.setDebut(backupHeureDebut);
    			return false;
    		}

    		this.resolu=true;
    		return true;
    		
    	}
    	return false;
    	
    }
    
    
    public boolean essayerDeplacementDeForce(BaseDeDonnees data) {
    	
    	if(resolu)
    		return false;
    	ArrayList<Bloc> blocsLibres2 = getBlocsLibres2();
    	ArrayList<Bloc> blocsLibres1 = getBlocsLibres1();
    	
    	Bloc blocFort1 = chirurgie1.getChirurgien().getBlocFort(jour, 1);
    	Bloc blocFort2 = chirurgie2.getChirurgien().getBlocFort(jour, 1);
    	
    	if(sallePb.nombreDeChirurgiesDe(chirurgie1.getChirurgien(), jour)>sallePb.nombreDeChirurgiesDe(chirurgie2.getChirurgien(), jour)) {
    		//La on va plutot essayer de bouger chirurgie2 parce que son chirurgien est moins dans cette salle
    		if(blocsLibres2.contains(blocFort2)) {
        		//Si son bloc fort est libre on la met direct
    			if(!(blocFort2.getID()<=3 && chirurgie2.estLaNuit())) {
	    			chirurgie2.setSalle(blocFort2);
	    			resolu = true;
	    			return true;
    			}
        	}
    	}
    	else if(sallePb.nombreDeChirurgiesDe(chirurgie1.getChirurgien(), jour)<sallePb.nombreDeChirurgiesDe(chirurgie2.getChirurgien(), jour)) {
    		//La on va essayer de bouger la chirurgie1
    		if(blocsLibres1.contains(blocFort1)) {
        		//Si son bloc fort est libre on la met direct
    			if(!(blocFort1.getID()<=3 && chirurgie1.estLaNuit())) {
	    			chirurgie1.setSalle(blocFort1);
	    			resolu = true;
	    			return true;
    			}
        	}
    	}
	
		//si les deux sont egaux, on bouge celui qu'on peut comme on peut
		if(blocsLibres1.contains(blocFort1)) {
			if(!(blocFort1.getID()<=3 && chirurgie1.estLaNuit())) {
    			chirurgie1.setSalle(blocFort1);
    			resolu = true;
    			return true;
			}
		}
		if(blocsLibres2.contains(blocFort2)) {
			if(!(blocFort2.getID()<=3 && chirurgie2.estLaNuit())) {
    			chirurgie2.setSalle(blocFort2);
    			resolu = true;
    			return true;
			}
		}
    	return false;
    	
    }
    public boolean vendreSonAmeAuShetan(BaseDeDonnees data) {
    	//Ici on va resoudre le conflit dans tous les cas par un deplacement de bloc
    	//on va privilegier quand meme de bouger la chirurgie qui est le moins a sa place et essayer de la mettre dans un bloc existant
    	if(resolu)
    		return false;
    	ArrayList<Bloc> blocsLibres2 = getBlocsLibres2();
    	ArrayList<Bloc> blocsLibres1 = getBlocsLibres1();
    	
    	Bloc blocFort1 = chirurgie1.getChirurgien().getBlocFort(jour, 1);
    	Bloc blocFort2 = chirurgie2.getChirurgien().getBlocFort(jour, 1);
    	
    	if(sallePb.equals(blocFort1)) {
    		//On tej la 2
    		if(blocsLibres2.size()>0) {
    			for(Bloc b : blocsLibres2) {
    				if(!(b.getID()<=3 && chirurgie2.estLaNuit())) {
    					chirurgie2.setSalle(b);
    					this.resolu=true;
    					return true;
    				}
    			}
    		}
    		else {
    			//ajouter un nouveau bloc
    			for(Bloc b : data.blocsExistants) {
    				if(!jour.getBlocs().contains(b) && !(b.getID()<=3 && chirurgie2.estLaNuit())) {
    					chirurgie2.setSalle(b);
    					this.resolu=true;
    					return true;
    				}
    			}
    		}
    	}
    	

    	if(sallePb.equals(blocFort2)) {
    		//On tej la 2
    		if(blocsLibres1.size()>0) {
    			for(Bloc b : blocsLibres1) {
    				if(!(b.getID()<=3 && chirurgie1.estLaNuit())) {
    					chirurgie1.setSalle(b);
    					this.resolu=true;
    					return true;
    				}
    			}
    		}
    		else {
    			//ajouter un nouveau bloc
    			for(Bloc b : data.blocsExistants) {
    				if(!jour.getBlocs().contains(b) && !(b.getID()<=3 && chirurgie1.estLaNuit())) {
    					chirurgie1.setSalle(b);
    					this.resolu=true;
    					return true;
    				}
    			}
    		}
    	}
    	
    	if(blocsLibres2.size()>0) {
			for(Bloc b : blocsLibres2) {
				if(!(b.getID()<=3 && chirurgie2.estLaNuit())) {
					chirurgie2.setSalle(b);
					this.resolu=true;
					return true;
				}
			}
		}
    	else if(blocsLibres1.size()>0) {
			for(Bloc b : blocsLibres1) {
				if(!(b.getID()<=3 && chirurgie1.estLaNuit())) {
					chirurgie1.setSalle(b);
					this.resolu=true;
					return true;
				}
			}
		}
		else {
			//ajouter un nouveau bloc
			for(Bloc b : data.blocsExistants) {
				if(!jour.getBlocs().contains(b) && !(b.getID()<=3 && chirurgie2.estLaNuit())) {
					chirurgie2.setSalle(b);
					this.resolu=true;
					return true;
				}
			}
		}
    	
    	
    	return false;
    	
    	
    }
    
    
}
