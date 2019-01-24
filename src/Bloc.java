import java.util.ArrayList;

public class Bloc {
    private String nom;
    private int id;
    private ArrayList<Chirurgie> chirurgiesDuJour;
    
    public Bloc(String nom, int identifiant) {
    	this.nom = nom;
    	this.id = identifiant;
    	chirurgiesDuJour = new ArrayList<Chirurgie>();
    }
    
}
