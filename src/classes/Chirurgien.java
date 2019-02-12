package classes;

@SuppressWarnings("rawtypes")
public class Chirurgien implements Comparable{
    private String nom;
    
    
  
    public Chirurgien(String nom) {
		this.nom = nom;
		
	}
    
    @Override
    public boolean equals(Object o) {
    	if (o==null) {
    		return false;
    	}
    	if (this==o) {
    		return true;
    	}
    	if (this.getClass()!=o.getClass()) {
    		return false;
    	}
    	Chirurgien other = (Chirurgien) o;
    	
    	if (!other.getName().equals(this.nom)) {
    		return false;
    	}
    	return true;
    }
    


    @Override
    public String toString() {
    	return nom;
    }

	//ACCESSSEURS
    public String getName() {
    	return nom;
    }


	@Override
	public int compareTo(Object o) {
		
		return this.nom.compareTo(((Chirurgien)o).getName());
	}
}