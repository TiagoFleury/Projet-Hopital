package classes;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Bloc implements Comparable{
    private String nom;
    private int id;
    private ArrayList<Double> moyennesJours;
    public static double tempsMoyenEntreDeuxChirurgies;

    public Bloc(String leNom) {
    	nom = leNom;
    	
    	id = Integer.parseInt(leNom.substring(6));
    	
    }
    
    
    
    
    @Override
    public String toString() {
    	return nom+"  id:"+id;
    }
    
    
    
    
    
    
    
    // Accesseurs //
    
    public String getName(){
    	return this.nom;
    }
    public int getID() {
    	return id;
    }


    @Override
    public boolean equals(Object o) {
    	if(o==null) {
    		return false;
    	}
    	if(this == o) {
    		return true;
    	}
    	if(o instanceof Bloc) {
    		Bloc b = (Bloc) o;
    		if(b.id == id)
    			return true;
    	}
    	return false;
    }

	@Override
	public int compareTo(Object o) {
		if(o instanceof Bloc) {
			Bloc b = (Bloc)o;
			return this.id - b.id;
		}
		return 0;
	}
	
	
	
	// Mutateurs //
	public void setMoyennesJours(ArrayList<Double> listemoyennes) {
		moyennesJours=listemoyennes;
	}



	public ArrayList<Chirurgie> recupererChirurgies(Journee jour) { //retourne une liste vide si il y a 0 chirurgies dans ce bloc dans cette journee
		ArrayList<Chirurgie> liste = new ArrayList<Chirurgie>();
		for(Chirurgie c : jour.getChirurgiesJour()) {
			if(c.getSalle().equals(this)) {
				liste.add(c);
			}
		}
		return liste;
	}
	
	public int nombreDeChirurgiesDe(Chirurgien albert, Journee jour) {
		int compteur = 0;
		for(Chirurgie c : recupererChirurgies(jour)) {
			if(c.getChirurgien().equals(albert)) {
				compteur++;
			}
		}
		return compteur;
	}
}
