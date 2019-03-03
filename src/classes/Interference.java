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
    	
    	//On fait toutes les conditions qui font qu'on laisse la main a une autre resolution plus laxe ou plus appropriee
    	
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
    	
    }
}
