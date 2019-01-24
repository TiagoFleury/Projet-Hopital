import java.util.ArrayList;

public class Bloc {
    private String nom;
    private int id;
    private ArrayList<Chirurgie> chirurgiesDuJour;

    //Ajout d'un commentaire
    //Ajout dqsdmfh
    
    // balbalfbeaifh
    
    
    
    
    
    
    
    
    public Bloc(String nom, int identifiant) {
    	this.nom = nom;
    	this.id = identifiant;
    	chirurgiesDuJour = new ArrayList<Chirurgie>();
    }
    
    //Accesseur
    
    public String getName(){
    	return nom;
    }
}
