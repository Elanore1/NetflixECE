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
        do{
            switch(action){
                case "Acceuil":
                    fenetre.Acceuil();
                    action="pause";
                    break;
                case "Créer un compte":
                    action="pause";
                    break;
                case "S'identifier":
                    fenetre.FormConnect();
                    action="pause";
                    break;
                case "Netflix":
                    System.out.println("netflix");
                    fenetre.AppNetflix();
                    action="pause";
                    break;
            }
        }while(end!=true);

    }

    public void setAction(String act){
        action=act;
    }
    /*
    //Déroulement
    //fenetre.FormConnect();
    do{
        fenetre.Acceuil();
        switch(){
            case "S'identifier":
                System.out.println("cas s'identifier");
                fenetre.FormConnect();
                break;
            case "Nouveau compte":
                System.out.println("cas s'nouveau compte");
                System.out.println("creation nouveau compte");
                break;
        }

    }while(end==true);
    //nv.lectureDAO(); */
}
