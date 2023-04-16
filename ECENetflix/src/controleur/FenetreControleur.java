package controleur;
import modele.Film;
import modele.Compte;
import modele.NetflixBDD;
import vue.ViewBarreMenu;
import vue.ViewContenu;
import vue.ViewFenetre;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//controlleur fenetre globale;
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
                case "Graphiques":
                    fenetre.Graphiques(bddFilm);
                    action="pause";
                    break;
                case "Netflix Proprietaire":
                    System.out.println();
                    fenetre.NetflixProprietaire();
                    action="pause";
                    break;
                case "FilmProprietaire":
                    fenetre.FilmProprietaire();
                    action="pause";
                    break;
                case "MAJ":
                    fenetre.MAJ();
                    action="pause";
                    break;
                case  "Clients":
                    fenetre.clients();
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
    public void remplirNote(ArrayList<String> note){
        ArrayList<Film> lst = new ArrayList<Film>();
        System.out.println("String size"+note);
        for(int i=0;i<note.size();i++){
            i+=2;
            for(Film flm : bddFilm.getTop()){
                if(note.get(i).equals(flm.getTitre()))
                    System.out.println("note"+Integer.parseInt(note.get(i+1)));
                    flm.setNote(Integer.parseInt(note.get(i+1)));
            }
            for(Film flm : bddFilm.getFilms()){
                if(note.get(i).equals(flm.getTitre()))
                    flm.setNote(Integer.parseInt(note.get(i+1)));
            }
            for(Film flm : bddFilm.getAnime()){
                if(note.get(i).equals(flm.getTitre()))
                    flm.setNote(Integer.parseInt(note.get(i+1)));
            }
            for(Film flm : bddFilm.getSerie()){
                if(note.get(i).equals(flm.getTitre()))
                    flm.setNote(Integer.parseInt(note.get(i+1)));
            }
            for(Film flm : bddFilm.getNouveaute()){
                if(note.get(i).equals(flm.getTitre()))
                    flm.setNote(Integer.parseInt(note.get(i+1)));
            }
            i+=1;
        }
    }

    //RECUP COMPTE
    public ArrayList<Compte> getAllComptes() {
        ArrayList<String> comptes = null;
        try {
            comptes = DAO.recupererComptes();
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }

        ArrayList<Compte> listeComptes = new ArrayList<>();
        final int EMAIL_INDEX = 0;
        final int MDP_INDEX = 1;
        final int UTILISATEURS_INDEX = 2;

        for (int i = 0; i < comptes.size(); i += 3) {
            String email = comptes.get(i + EMAIL_INDEX);
            String mdp = comptes.get(i + MDP_INDEX);
            String utilisateurs = comptes.get(i + UTILISATEURS_INDEX);

            Compte c = new Compte();
            c.setEmail(email);
            c.setMdp(mdp);
            c.setUsager(utilisateurs);
            listeComptes.add(c);
        }
        return listeComptes;
    }

    public String NombreUtilisateur(Compte compte) throws SQLException {
        return DAO.nombreUtilisateur(compte);
    }

}
