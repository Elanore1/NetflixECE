package vue;
import controleur.FenetreControleur;
import modele.Fenetre;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

public class ViewFenetre extends JFrame{
    FenetreControleur controleur;
    //ajout d'une barre de menu
    ViewBarreMenu barmenu;
    JMenu menu = new JMenu();
    ViewContenu panel;
    Boolean connect = false;

    //constructeur par defaut
    public ViewFenetre(Fenetre fn) { // constructeur par surchargé
        barmenu = new ViewBarreMenu();
        setSize (fn.getLargeur(), fn.getHauteur()); // donne une taille en hauteur et largeur à la fenêtre
        setTitle (fn.getTitre()); // donne un titre à la fenêtre
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setLayout(new BorderLayout());
        setVisible(true);
        setJMenuBar(barmenu);
    }
    //Actualisation bar Menu
    public void BarreConnection(){
        ViewBarreMenu barconnect = new ViewBarreMenu();
        //affichage barre menu dans l'app
        barconnect.setBarreConnection();
        setJMenuBar(barconnect);
    }
    //Formulaire pour se connecter
    public void FormConnect(){
        //Affichage formulaire de connection
        panel.removeAll();
        panel.FormConnection();
        setPanel(panel);
        setVisible(true);
    }
    public void CreationCompte(){
        //Affichage formulaire de creation de compte
        panel.removeAll();
        panel.CreaCompte();
        panel.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void AppNetflix(){
        this.setLayout(null);
        BarreConnection();
        panel.removeAll();
        panel.Netflix();
        panel.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void Acceuil(){
        panel.removeAll();
        panel.Acceuil();
        panel.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    //Pour recevoir les informations du controlleur
    public void setController(FenetreControleur fn){
        this.controleur=fn;
    }
    //Getter Setter panel
    public void setPanel(ViewContenu pan){
        this.panel=pan;
        setContentPane(panel);
    }
    public ViewContenu getPanel(){
        return panel;
    }
}
