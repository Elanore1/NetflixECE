package controleur;
import modele.Film;
import modele.NetflixBDD;
import vue.ViewBarreMenu;
import vue.ViewContenu;
import vue.ViewFenetre;
import java.io.IOException;
import java.sql.SQLException;

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
        for(int i = 0;i<bddFilm.getTop().size();i++){
            System.out.println(bddFilm.getTop().get(i).getTitre());//on affiche chaque film
        }
        action="Netflix";
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
                    System.out.println("Film");
                    fenetre.LancementFilm();
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
    public Film getFilm(){return film;}
    public void setFilm(Film _flm){this.film=_flm;}
    public ControleurCompte getControleurCompte(){
        return compte;
    }

}
