package vue;
import modele.Fenetre;

import javax.swing.*;
import java.awt.*;

public class ViewFenetre extends JFrame {
    //ajout d'une barre de menu
    ViewBarreMenu barmenu = new ViewBarreMenu();
    JMenu menu = new JMenu();
    ViewContenu panel = new ViewContenu();
    public Boolean connect = false;

    //constructeur par defaut
    public ViewFenetre(Fenetre fn) { // constructeur par surchargé
        boolean connect = false;
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
        panel.FormConnection();
        setContentPane(panel);
        setVisible(true);
        BarreConnection();
    }
}
