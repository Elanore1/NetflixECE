package modele;

public class Fenetre {
    //attributs
    private int hauteur;
    private int largeur;
    private String titre;
    //getter des attributs
    public int getHauteur(){
        return hauteur;
    }
    public int getLargeur(){
        return largeur;
    }
    public String getTitre(){
        return titre;
    }
    //constructeur par défaut de la fenetre
    public Fenetre(){
        //paramètre par défaut
        this.hauteur = 800;
        this.largeur = 1200;
        this.titre = "ECENetflix";
    }
}
