package controleur;

import modele.Fenetre;
import vue.ViewFenetre;

public class FenetreControleur {
    public static void main(String[]args){
        ViewFenetre fenetre = new ViewFenetre(new Fenetre());
        RechercheInfo nv = new RechercheInfo();
        nv.lectureDAO();
    }
}
