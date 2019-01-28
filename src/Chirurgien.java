import java.util.ArrayList;

public class Chirurgien {
    private String nom;
    
    
  
    public Chirurgien(String nom) {
		this.nom = nom;
		
	}


    @Override
    public String toString() {
    	return nom;
    }

	//ACCESSSEURS
    public String getName() {
    	return nom;
    }
}
