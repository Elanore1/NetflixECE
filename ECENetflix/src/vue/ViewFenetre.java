package vue;

import modele.Fenetre;

import javax.swing.*;

public class ViewFenetre extends JFrame {
    //ajout d'une barre de menu
    ViewBarreMenu barmenu = new ViewBarreMenu();
    JMenu menu = new JMenu();

    //constructeur par defaut
    public ViewFenetre(Fenetre fn) { // constructeur par surchargé
        setSize (fn.getLargeur(), fn.getHauteur()); // donne une taille en hauteur et largeur à la fenêtre
        setTitle (fn.getTitre()); // donne un titre à la fenêtre
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        barmenu.setBarreConnection();
        setJMenuBar(barmenu);
        setVisible(true);

    }
}
