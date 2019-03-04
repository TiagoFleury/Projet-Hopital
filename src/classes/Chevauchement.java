package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Chevauchement extends Conflit {
    private Bloc sallePb;
    private Chirurgien chirurgienPb;
    
    public Chevauchement(Chirurgie ch1, Chirurgie ch2, Journee j){
        super(ch1,ch2,j);
        this.sallePb = ch1.getSalle();
        this.chirurgienPb=ch1.getChirurgien();
    }
    
    
    
    
    
    
    // RESOLUTION CHEVAUCHEMENT 
    ///////////////////////////////////////////////
    
    
    public boolean essayerDecalageEvident() {
    	if(resolu)
    		return false;
    	if(getIndiceDeRecouvrement()>0.9) {
    		return false;
    	}
    	ArrayList<Chirurgie> chirurgiesDuBloc = sallePb.recupererChirurgies(jour);
    	
    	Collections.sort(chirurgiesDuBloc,Chirurgie.CHRONOLOGIQUE);
    	
    	//Maintenant qu'elles sont dans l'ordre chronologique, on va tester si il y a la place entre ch1 et la prochaine du meme chirurgien.
    	Chirurgie prochaineDuMemeChirurgien = null;
    	Chirurgie precedenteDuMemeChirurgien = null;
    	for(int i = chirurgiesDuBloc.indexOf(chirurgie2)+1;i<chirurgiesDuBloc.size();i++) {
    		if(chirurgiesDuBloc.get(i).getChirurgien().equals(chirurgienPb)) {
    			prochaineDuMemeChirurgien = chirurgiesDuBloc.get(i);
    			break;
    		}
    		
    	}
    	for(int i = chirurgiesDuBloc.indexOf(chirurgie1)-1;i>=0;i--) {
    		if(chirurgiesDuBloc.get(i).getChirurgien().equals(chirurgienPb)) {
    			precedenteDuMemeChirurgien = chirurgiesDuBloc.get(i);
    			break;
    		}
    		
    	}
    	//Si il n'y en a pas de suivantes, ca va juste rester a null
    	if(prochaineDuMemeChirurgien==null) {
    		Chirurgie prochaineDuBloc;
    		try {
    			prochaineDuBloc = chirurgiesDuBloc.get(chirurgiesDuBloc.indexOf(chirurgie2)+1);
    		}
    		catch(IndexOutOfBoundsException e) {
    			prochaineDuBloc=null;
    		}
    		Chirurgie chirurgieAdecaler;
			Chirurgie chirurgieQuiReste;
			if(chirurgie1.getFin().isAfter(chirurgie2.getFin())) {
				chirurgieAdecaler=chirurgie1;
				chirurgieQuiReste=chirurgie2;
			}
			else {
				chirurgieAdecaler=chirurgie2;
				chirurgieQuiReste=chirurgie1;
			}
			
    		if(prochaineDuBloc==null) {//Si derniere chirurgie de la journee, on la decale
    			 
    			
    			chirurgieAdecaler.decalerVersDroite(ChronoUnit.MINUTES.between(chirurgieAdecaler.getDebut(), chirurgieQuiReste.getFin())+15);
    			resolu=true;
    			return true;
    		}
    		else {//Si il y a une autre chirurgie apres mais d'un autre chirurgien, on essaie de le caler entre
    			LocalTime backupHeureDebut = chirurgieAdecaler.getDebut();
    			LocalTime backupHeureFin = chirurgieAdecaler.getFin();
    			chirurgieAdecaler.decalerVersDroite(ChronoUnit.MINUTES.between(chirurgieAdecaler.getDebut(), chirurgieQuiReste.getFin())+10);
    			
				if(Journee.enMemeTempsOuPas(chirurgieAdecaler, prochaineDuBloc)) { //Si ca se chevauche on remet tout
					chirurgieAdecaler.setDebut(backupHeureDebut);
					chirurgieAdecaler.setFin(backupHeureFin);
				}
				else {
					resolu=true;
					return true;
				}
    		}
    		
    		
    	}
    	if(precedenteDuMemeChirurgien==null){
    		Chirurgie precedenteDuBloc;
    		try {
    			precedenteDuBloc = chirurgiesDuBloc.get(chirurgiesDuBloc.indexOf(chirurgie1)-1);
    		}
    		catch(IndexOutOfBoundsException e) {
    			precedenteDuBloc=null;
    		}
    		Chirurgie chirurgieAdecaler;
			Chirurgie chirurgieQuiReste;
			if(chirurgie1.getDebut().isBefore(chirurgie2.getFin())) {
				chirurgieAdecaler=chirurgie1;
				chirurgieQuiReste=chirurgie2;
			}
			else {
				chirurgieAdecaler=chirurgie2;
				chirurgieQuiReste=chirurgie1;
			}
			
    		if(precedenteDuBloc==null) {//Si premiere chirurgie de la journee, on la decale
    			 
    			
    			chirurgieAdecaler.decalerVersGauche(ChronoUnit.MINUTES.between(chirurgieQuiReste.getDebut(), chirurgieAdecaler.getFin())+15);
    			resolu=true;
    			return true;
    		}
    		else {//Si il y a une autre chirurgie avant mais d'un autre chirurgien, on essaie de le caler entre
    			LocalTime backupHeureDebut = chirurgieAdecaler.getDebut();
    			LocalTime backupHeureFin = chirurgieAdecaler.getFin();
    			chirurgieAdecaler.decalerVersGauche(ChronoUnit.MINUTES.between(chirurgieQuiReste.getDebut(), chirurgieAdecaler.getFin())+10);
    			
				if(Journee.enMemeTempsOuPas(chirurgieAdecaler, precedenteDuBloc)) { //Si ca se chevauche on remet tout
					chirurgieAdecaler.setDebut(backupHeureDebut);
					chirurgieAdecaler.setFin(backupHeureFin);
				}
				else {
					resolu=true;
					return true;
				}
    		}
    		
    	}
    	
    	
    	
    	if(prochaineDuMemeChirurgien!=null) {
	    	if(chirurgie2.rentrePileEntre(chirurgie1,prochaineDuMemeChirurgien)) {
	    		chirurgie2.deplacerEntre(chirurgie1,prochaineDuMemeChirurgien);
	    		return true;
	    	}
    	}
    	
    	if(precedenteDuMemeChirurgien!=null) {
	    	if(chirurgie1.rentrePileEntre(precedenteDuMemeChirurgien, chirurgie2)) {
	    		chirurgie1.deplacerEntre(precedenteDuMemeChirurgien, chirurgie2);
	    		return true;
	    	}
    	}
    	return false;
    }
    
    
    // ACCESSEURS // 
    
    public Bloc getSallePb() {
    	return this.sallePb;
    }
    public Chirurgien getChirurgienPb() {
    	return this.chirurgienPb;
    }
    
    
    
}
