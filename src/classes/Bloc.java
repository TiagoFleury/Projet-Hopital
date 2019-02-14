package classes;

@SuppressWarnings("rawtypes")
public class Bloc implements Comparable{
    private String nom;
    private int id;
    

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
}
