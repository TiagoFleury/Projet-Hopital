package classes;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.Period;

public class Interference extends Conflit {
    private Bloc sallePb;
    private ArrayList<Chirurgien> chirurgiens;
    
    
    public Interference(Chirurgie ch1, Chirurgie ch2, Journee j){
        super(ch1, ch2, j);
        this.sallePb=ch1.getSalle();
        ArrayList<Chirurgien> chirurgiensPb = new ArrayList<Chirurgien>();
        chirurgiensPb.add(ch1.getChirurgien());
        chirurgiensPb.add(ch2.getChirurgien());
        this.chirurgiens=chirurgiensPb;
    }
    
    public Bloc getSallePb() {
    	return this.sallePb;
    }
    
    
    
    public boolean essayerChangementDeSalleEvident(BaseDeDonnees data) {
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
    	
    	Bloc blocFort1 = chirurgie1.getChirurgien().getBlocFort(jour);
    	Bloc blocFort2 = chirurgie2.getChirurgien().getBlocFort(jour);
    	
    	//Si le bloc fort d'un des deux blocs est libre, on le bouge sans trop se poser de question
    	
    	
    	if(blocsLibres1.contains(blocFort1) && !blocsLibres2.contains(blocFort2)) {
    		chirurgie1.setSalle(blocFort1);
    		chirurgie1.setEnConflit(false);
    		chirurgie2.setEnConflit(false);
    		resolu = true;
    		
    	}
    	if(!blocsLibres1.contains(blocFort1) && blocsLibres2.contains(blocFort2)) {
    		chirurgie2.setSalle(blocFort2);
    		chirurgie1.setEnConflit(false);
    		chirurgie2.setEnConflit(false);
    		resolu = true;
    		
    		
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
    			chirurgie2.setSalle(bMax);
    			chirurgie2.setEnConflit(false);
    			chirurgie1.setEnConflit(false);
    			resolu = true;
    			return true;
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
    			chirurgie1.setSalle(bMax);
    			chirurgie1.setEnConflit(false);
    			chirurgie2.setEnConflit(false);
    			resolu = true;
    			return true;
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
    	
    	System.out.println("on est la");
    	if(getIndiceDeRecouvrement()>0.2) { //En dessous de 0,2 on va dire qu'on traite le cas
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
    	
    	Bloc blocFort1 = chirurgie1.getChirurgien().getBlocFort(jour);
    	Bloc blocFort2 = chirurgie2.getChirurgien().getBlocFort(jour);
    	
    	
    	if(!(anomalieDuree1 || anomalieDuree2)) { //Si il y a aucune anomalie on traite pas
    		return false;
    	}
    	System.out.println("on est la");
    	if(blocFort1!=null) {
    		if(blocFort1.nombreDeChirurgiesDe(chirurgie1.getChirurgien(), jour)>4 && getBlocsLibres1().contains(blocFort1)) {
	    		//si le blocFort est important et libre, on abandonne
    			System.out.println("sorti ici");
	    		return false;
    		}
    	}
    	if(blocFort2!=null) {
	    	if(blocFort2.nombreDeChirurgiesDe(chirurgie1.getChirurgien(), jour)>4 && getBlocsLibres2().contains(blocFort2)) {
	    		System.out.println("sorti icioiu");
	    		//si le blocFort est important et libre, on abandonne
	    		return false;
	    	}
    	}
    	
    	//Ici on a donc forcement une anomalie de longueur et un "petit recouvrement" sur un des bouts des chirurgies
    	//On va maintenant tronquer la chirurgie qui est le plus en anomalie de longueur
    	if(anomalieDuree1 && !anomalieDuree2) {
    		System.out.println("ANOMALIE DUREE 1");
//    		On est dans ce cas :
//    			#######################              <- on va racourcir celle la sur sa droite
//    								###########
    		LocalTime backupHeureFin = chirurgie1.getFin();
    		
    		while(Journee.enMemeTempsOuPas(chirurgie1, chirurgie2)) {//Tant que les deux se touchent on raccourcit
    			chirurgie1.raccourcirFin(1);
    			if(chirurgie1.getDuree()<chirurgie1.getChirurgien().getICtempsMoyen().get(0)) {
    				//Si on depasse la borne inferieure de l'intervalle de confiance on arrete et on remet la valeur de base
    				chirurgie1.setFin(backupHeureFin);
    				System.out.println("ca a depasse la borne minimale");
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
    	
    	if(!anomalieDuree1 && anomalieDuree2) {
    		System.out.println("ANOMALIE DUREE 2");
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
    	if(anomalieDuree1 && anomalieDuree2) {
    		//Si les deux sont en anomalie de temps, on coupe celui qui est le plus loin de sa borne maxd
    		Chirurgie chirAcouper;
    		System.out.println("les deux");
    	}
    	return false;
    	
    }
}
