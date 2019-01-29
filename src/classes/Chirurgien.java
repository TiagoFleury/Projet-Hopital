package classes;

import java.util.ArrayList;

public class Chirurgien implements Comparable{
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


	@Override
	public int compareTo(Object o) {
		
		return this.nom.compareTo(((Chirurgien)o).getName());
	}
}
