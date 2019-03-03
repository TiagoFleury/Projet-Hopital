package classes;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Bloc implements Comparable{
    private String nom;
    private int id;
    private ArrayList<Double> moyennesJours;
    private ArrayList<Chirurgie> chirurgies;
    public static double tempsMoyenEntreDeuxChirurgies;

    public Bloc(String leNom) {
    	nom = leNom;
    	
    	id = Integer.parseInt(leNom.substring(6));
    	
    }
    
    
    
    
    @Override
    public String toString() {
    	return nom+"  id:"+id;
    }
    
    
    
    
	public Chirurgien getChirurgienFort(Journee jour) { //Si il n'y a pas de chirurgien particulierement fort, retourne null
		
		Chirurgien cMax=null;
		int max=0;
		int compte;
		int tempsMax=0;
		ArrayList<Chirurgie> liste = new ArrayList<>();
		for(Chirurgien albert : jour.getChirurgiensMobilises()) {
			compte=0;
			for(Chirurgie c : jour.getChirurgiesJour()) {
				if(c.getSalle().equals(this) && c.getChirurgien().equals(albert)) {
					compte++;
					liste.add(c);
				}
				
			}
			if(compte>max) {
				cMax=albert;
				max = compte;
				tempsMax=0; //On reset le temps max
				for(Chirurgie c : liste) {
					tempsMax+=c.getDuree();
				}
				
			}
			else if(compte==max & max>0) {//Si c'est le meme nombre, on compare le temps
				int somme=0;
				for(Chirurgie c : liste) {
					somme+=c.getDuree();
				}
				if(somme>tempsMax) {
					cMax=albert;
					tempsMax=somme;
				}
			}
			liste.clear();
		}
		

		if(max >= 2) { //A partir de 3 chirurgies, on va dire que c'est son temps fort
			return cMax;
		}
		return null;
	}
    
    
    
    
    
    // Accesseurs //
    
    public String getName(){
    	return this.nom;
    }
    public int getID() {
    	return id;
    }

    public ArrayList<Chirurgie> getChirurgies(){
    	return chirurgies;
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
	public void setChirurgies(ArrayList<Chirurgie> liste) {
		chirurgies = liste;
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
