package vue;

import controleur.FenetreControleur;
import modele.Fenetre;
import modele.NetflixBDD;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ViewFenetre extends JFrame{
    FenetreControleur controleur;
    //ajout d'une barre de menu
    ViewBarreMenu barmenu;
    JMenu menu = new JMenu();
    ViewContenu panel;
    Boolean connect = false;

    //constructeur par defaut
    public ViewFenetre(Fenetre fn) { // constructeur par surchargé
        setSize (fn.getLargeur(), fn.getHauteur()); // donne une taille en hauteur et largeur à la fenêtre
        setTitle (fn.getTitre()); // donne un titre à la fenêtre
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setLayout(new BorderLayout());
        setVisible(true);
        barmenu=new ViewBarreMenu();
        barmenu.removeAll();
        barmenu.setBarreVide();
        setJMenuBar(barmenu);
    }
    //Actualisation bar Menu
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
        barmenu.setBarreConnection();
        panel.removeAll();
        this.setLayout(null);
        panel.setLayout(null);
        panel.removeAll();
        panel.Netflix();
        panel.updateUI();
        barmenu.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void LancementFilm(){
        panel.removeAll();
        this.setLayout(null);
        panel.removeAll();
        panel.filmView(controleur.getFilm());
        setPanel(panel);
        setVisible(true);

    }
    public void ChoixUtilisateur() throws SQLException{
        this.setLayout(null);
        panel.removeAll();
        panel.ChoixUtilisateurs();
        panel.updateUI();
        panel.setVisible(true);
        setPanel(panel);
        setVisible(true);
    }
    public void Acceuil(){
        this.setLayout(new GridBagLayout());
        panel.removeAll();
        panel.Acceuil();
        panel.updateUI();
        panel.setVisible(true);
        setPanel(panel);
        setVisible(true);
    }
    public void Recherche(){
        this.setLayout(null);
        panel.setLayout(null);
        panel.removeAll();
        panel.recherche();
        panel.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void GestionProfil(){
        this.setLayout(null);
        panel.setLayout(null);
        panel.removeAll();
        panel.gestionProfil();
        panel.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void ModifUtilisateur(){
        this.setLayout(null);
        panel.setLayout(null);
        panel.removeAll();
        panel.ModifUtilisateur();
        panel.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void NouvelUtilisateur(){
        this.setLayout(null);
        panel.setLayout(null);
        panel.removeAll();
        panel. AjouterUtilisateur();
        panel.updateUI();
        setPanel(panel);
        setVisible(true);
    }

    public void Graphiques(NetflixBDD bddFilm) throws SQLException {
        panel.removeAll();
        this.setLayout(null);
        panel.setLayout(null);
        panel.test(bddFilm);
        panel.updateUI();
        panel.setVisible( true );
        setPanel(panel);
        setVisible(true);
    }
    public void NetflixProprietaire() {
        barmenu.setBarreProprietaire();
        panel.setLayout( null );
        panel.removeAll();
        this.setLayout(null);
        panel.setLayout(null);
        panel.removeAll();
        panel.NetflixProprietaire();
        panel.updateUI();
        barmenu.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void FilmProprietaire(){
        boolean top;
        panel.removeAll();
        panel.FormConnection();
        setPanel(panel);
        setVisible(true);
        if(controleur.getBDD().getTop().contains( controleur.getFilm() ))
            top=true;
        else
            top=false;
        panel.FilmProprietaire(controleur.getFilm(),top);
        setPanel(panel);
        setVisible(true);
    }

    public void clients(){
        this.setLayout(null);
        barmenu.setBarreProprietaire();
        panel.setLayout(null);
        panel.removeAll();
        panel.clients();
        panel.updateUI();
        barmenu.updateUI();
        setPanel(panel);
        setVisible(true);
    }
    public void MAJ() {
        this.setLayout(null);
        barmenu.setBarreProprietaire();
        panel.removeAll();
        panel.MAJFILM(controleur.getFilm());
        panel.updateUI();
        panel.setVisible( true );
        barmenu.setBarreProprietaire();
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
    public void setBarre(ViewBarreMenu bar){
        this.barmenu=bar;
        setJMenuBar(barmenu);
    }
    public ViewBarreMenu getBarre(){
        return barmenu;
    }
}
