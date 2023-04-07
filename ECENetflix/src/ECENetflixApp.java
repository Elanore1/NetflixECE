import controleur.FenetreControleur;
import modele.Fenetre;
import vue.ViewBarreMenu;
import vue.ViewContenu;
import vue.ViewFenetre;

public class ECENetflixApp {
    public static void main(String[]args){
        FenetreControleur fc = new FenetreControleur(new ViewFenetre(new Fenetre()),new ViewContenu(),new ViewBarreMenu());
        fc.run();
    }
}
