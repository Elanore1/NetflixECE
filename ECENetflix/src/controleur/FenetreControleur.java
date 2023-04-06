package controleur;

import modele.Fenetre;
import vue.ViewContenu;
import vue.ViewFenetre;

import java.util.Observable;
import java.util.Observer;

public class FenetreControleur{
    boolean end;//bool pour quitter la fenetre
    ViewFenetre fenetre;//Vue
    ViewContenu panel;
    RechercheInfo DAO;//DAO
    String action;

    public FenetreControleur(ViewFenetre fn, ViewContenu vc){
        super();
        this.end=false;
        this.fenetre = fn;
        this.fenetre.setPanel(vc);
        this.panel = fenetre.getPanel();
        this.DAO=new RechercheInfo();
        this.action="Acceuil";
        fenetre.setController(this);
        panel.setController(this);
    }
    //deroulement
    public void run(){
        action = "Netflix";
        do{
            switch(action){
                case "Acceuil":
                    fenetre.Acceuil();
                    action="pause";
                    break;
                case "Créer un compte":
                    fenetre.CreationCompte();
                    action="pause";
                    break;
                case "S'identifier":
                    fenetre.FormConnect();
                    action="pause";
                    break;
                case "Netflix":
                    fenetre.AppNetflix();
                    action="pause";
                    break;
                case "Choix Utilisateurs":
                    System.out.println("choix des utilisateurs");
                    action="pause";
                break;
            }
        }while(end!=true);

    }

    public void setAction(String act){
        action=act;
    }
}
