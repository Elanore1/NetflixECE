package controleur;

import modele.Film;
import modele.NetflixBDD;
import vue.ViewBarreMenu;
import vue.ViewContenu;
import vue.ViewFenetre;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FenetreControleur{
    boolean end;//bool pour quitter la fenetre
    ViewFenetre fenetre;//Vue
    ViewContenu panel;
    ViewBarreMenu barre;
    RechercheInfo DAO;//DAO
    ControleurCompte compte;
    NetflixBDD bddFilm;
    String action;
    Film film;

    public FenetreControleur(ViewFenetre fn, ViewContenu vc, ViewBarreMenu bc){
        super();
        this.end=false;
        this.fenetre = fn;
        this.fenetre.setPanel(vc);
        this.fenetre.setBarre(bc);
        this.panel = fenetre.getPanel();
        this.barre = fenetre.getBarre();
        this.compte = new ControleurCompte();
        this.DAO=new RechercheInfo();
        this.film = new Film();
        this.action="Acceuil";
        fenetre.setController(this);
        panel.setController(this);
        barre.setController(this);
        Film film = new Film();
    }
    //deroulement
    public void run() throws IOException, SQLException {
        try {
            //On charge tous les films de la base de données
            bddFilm = DAO.chargerFilm();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                    fenetre.ChoixUtilisateur();
                    action="pause";
                    break;
                case "Film":
                    fenetre.LancementFilm();
                    action="pause";
                    break;
                case "Ajouter Utilisateur":
                    fenetre.NouvelUtilisateur();
                    action="pause";
                    break;
                case "Gestion Profil":
                    fenetre.GestionProfil();
                    action="pause";
                    break;
                case "Mofidier Utilisateur":
                    fenetre.ModifUtilisateur();
                    action="pause";
                    break;
                case "Recherche":
                    fenetre.Recherche();
                    action="pause";
                    break;
            }
        }while(end!=true);
    }
    public void setAction(String act){
        action=act;
    }
    public NetflixBDD getBDD(){
        return bddFilm;
    }
    public RechercheInfo getDAO(){return DAO;}
    public Film getFilm(){return film;}
    public void setFilm(Film _flm){this.film=_flm;}
    public ControleurCompte getControleurCompte(){
        return compte;
    }
    public ArrayList<Film> remplirFilm(ArrayList<String> maliste){
        ArrayList<Film> lst = new ArrayList<Film>();
        System.out.println("String size"+maliste);
        for(int i=0;i<maliste.size();i++){
            i+=2;
            for(Film flm : bddFilm.getTop()){
                if(maliste.get(i).equals(flm.getTitre()))
                    lst.add(flm);
            }
            for(Film flm : bddFilm.getFilms()){
                if(maliste.get(i).equals(flm.getTitre()))
                    lst.add(flm);
            }
            for(Film flm : bddFilm.getAnime()){
                if(maliste.get(i).equals(flm.getTitre()))
                    lst.add(flm);
            }
            for(Film flm : bddFilm.getSerie()){
                if(maliste.get(i).equals(flm.getTitre()))
                    lst.add(flm);
            }
            for(Film flm : bddFilm.getNouveaute()){
                if(maliste.get(i).equals(flm.getTitre()))
                    lst.add(flm);
            }
        }
        System.out.println("film size"+lst.size());
        return lst;
    }

}
