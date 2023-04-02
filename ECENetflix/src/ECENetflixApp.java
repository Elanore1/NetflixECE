import controleur.FenetreControleur;
import controleur.RechercheInfo;
import modele.Fenetre;
import vue.ViewContenu;
import vue.ViewFenetre;

public class ECENetflixApp {
    public static void main(String[]args){
        FenetreControleur fc = new FenetreControleur(new ViewFenetre(new Fenetre()),new ViewContenu());
        fc.run();
    }
}
