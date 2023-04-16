package controleur;
import modele.Film;
import modele.Compte;
import modele.NetflixBDD;
import java.sql.SQLException;
import java.util.ArrayList;

//Recherche info dans bDD
public class RechercheInfo {
    private ConnexionDB maconnexion;
    private final java.awt.List listeDeTables;
    private final java.awt.List listeDeRequetes;
    ArrayList<Film> Recherche;

    public RechercheInfo(){
        super();
        Recherche = new ArrayList<Film>();
        try {
            try {
                // tentative de connexion si les 4 attributs sont remplis
                //maconnexion = new Connexion("jps", "root", "");
                maconnexion = new ConnexionDB("projetinfo", "root", "root");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Connexion echouee : probleme de classe");
                cnfe.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL");
            e.printStackTrace();
        }
        listeDeTables = new java.awt.List(100, false);
        listeDeRequetes = new java.awt.List(100, false);
        //on ajoute nos tables
        remplirTables();
        listeDeTables.select(0);
    }
    public void afficherTables() {
        for (String table : maconnexion.tables) {
            listeDeTables.add(table);
        }
    }
    public void afficherLignes(String nomTable) {
        try {
            ArrayList<String> liste;
            System.out.println("Afficher Lignes");
            // recupérér les résultats de la table selectionnee
            liste = maconnexion.remplirChampsTable(nomTable);

            // afficher les champs de la table selectionnee
            for (String liste1 : liste) {
                System.out.println(liste1);
            }
            // recuperer la liste de la table sélectionnée
            String requeteSelectionnee = "select * from " + nomTable + ";";
            liste = maconnexion.remplirChampsRequete(requeteSelectionnee);

            // afficher les lignes de la requete selectionnee a partir de la liste
            for (String liste1 : liste) {
                System.out.println(liste1);
            }

        } catch (SQLException e) {
            // afficher l'erreur dans les résultats
            System.out.println("Echec SQL");
            e.printStackTrace();
        }
    }
    public String verifMDP(String _email) throws SQLException {
        ArrayList<String> liste = null;
        String reqMDP ="SELECT mdp FROM compte WHERE email LIKE '"+_email+"';";
        maconnexion.ajouterRequete(reqMDP);
        liste = maconnexion.remplirChampsRequete(reqMDP);
        if(liste.size()!=0)
            return liste.get(0);
        else
            return null;
    }
    public String verifEmail(String _email) throws SQLException {
        ArrayList<String> liste = null;
        String reqMDP ="SELECT email FROM compte WHERE email LIKE '"+_email+"';";
        maconnexion.ajouterRequete(reqMDP);
        liste = maconnexion.remplirChampsRequete(reqMDP);
        if(liste.size()!=0)
            return liste.get(0);
        else
            return null;
    }
    public ArrayList<Film> rechercheFilm(String tape){
        boolean dejaLA=false;
        ArrayList<String> lst = new ArrayList<String>();
        ArrayList<Film> films = new ArrayList<Film>();
        ArrayList<Film> requete = new ArrayList<Film>();
        ArrayList<String> genre = new ArrayList<String>();
        ArrayList<String> acteurs = new ArrayList<String>();
        for(int ind=0;ind<25;ind++){
            String req = "SELECT * FROM film WHERE LOCATE('"+tape+"',titre,"+ind+") OR titre='"+tape+"' OR LOCATE('"+tape+"', genre,"+ind+") OR genre='"+tape+"' OR LOCATE('"+tape+"', liste_type,"+ind+") OR liste_type='"+tape+"' OR LOCATE('"+tape+"', acteurs,"+ind+") OR acteurs='"+tape+"' OR LOCATE('"+tape+"', realisateur,"+ind+") OR realisateur='"+tape+"';";
            System.out.println(req);
            String[] act,gen,rep;
            try {
                lst = maconnexion.remplirChampsRequete(req);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for(int a=0;a<lst.size();a++){
                rep = lst.get(a).split(",");
                for(int i=0;i<rep.length;i++){
                    Film nv =  new Film();
                    nv.setTitre(rep[i]);
                    nv.setRealisateur(rep[i+1]);
                    act = rep[i+2].split("\\|");
                    for(int j=0;j<act.length;j++){
                        acteurs.add(act[j]);
                    }
                    nv.setActeurs(acteurs);
                    acteurs.clear();
                    gen = rep[i+3].split("\\|");
                    for(int j=0;j<gen.length;j++){
                        genre.add(gen[j]);
                    }
                    nv.setGenre(genre);
                    genre.clear();
                    nv.setUrlAffiche(rep[i+4]);
                    nv.setUrlAfficheHor(rep[i+5]);
                    nv.setUrlfilm(rep[i+6]);
                    nv.setDescription(rep[i+7]);
                    nv.setDuree(rep[i+8]);
                    String temp = rep[i + 9].replaceAll("\\n+","");
                    for(Film elem : films){
                        if(nv.getTitre().equals(elem.getTitre())){
                            dejaLA=true;
                        }
                        System.out.println(dejaLA);
                    }
                    if(!dejaLA){
                        films.add(nv);
                    }
                    i+=10;
                    dejaLA=false;
                }
            }
        }
        return films;
    }
    public ArrayList<String> afficherRes(String requeteSelectionnee) throws SQLException {
        ArrayList<String> liste = null;
        try {
            System.out.println("Afficher res requetes");
            // recupérér les résultats de la requete selectionnee
            liste = maconnexion.remplirChampsRequete(requeteSelectionnee);

            for (String liste1 : liste) {
                System.out.println(liste1);
            }
        } catch (SQLException e) {
            // afficher l'erreur dans les résultats
            System.out.println("Echec requete SQL");
        }
        return liste;
    }
    private void remplirTables(){
        maconnexion.ajouterTable("compte");
        maconnexion.ajouterTable("film");
        maconnexion.ajouterTable("utilisateur");
        maconnexion.ajouterTable("maliste");
    }
    public NetflixBDD chargerFilm() throws SQLException {
        NetflixBDD BDDFilm = new NetflixBDD();
        ArrayList<Film> _top = new ArrayList<Film>();
        ArrayList<Film> _nouveaute = new ArrayList<Film>();
        ArrayList<Film> _serie = new ArrayList<Film>();
        ArrayList<Film> _film = new ArrayList<Film>();
        ArrayList<Film> _anime = new ArrayList<Film>();
        ArrayList<String> liste = new ArrayList<String>();
        ArrayList<String> acteurs = new ArrayList<String>();
        ArrayList<String> genre = new ArrayList<String>();
        String[] act,gen,rep;
        String reqMDP ="SELECT * FROM film;";
        liste = maconnexion.remplirChampsRequete(reqMDP);
        for(int a=0;a<liste.size();a++){
            rep = liste.get(a).split(",");
            for(int i=0;i<rep.length;i++){
                Film nv =  new Film();
                nv.setTitre(rep[i]);
                nv.setRealisateur(rep[i+1]);
                act = rep[i+2].split("\\|");
                for(int j=0;j<act.length;j++){
                    acteurs.add(act[j]);
                }
                nv.setActeurs(acteurs);
                acteurs.clear();
                gen = rep[i+3].split("\\|");
                for(int j=0;j<gen.length;j++){
                    genre.add(gen[j]);
                }
                nv.setGenre(genre);
                genre.clear();
                nv.setUrlAffiche(rep[i+4]);
                nv.setUrlAfficheHor(rep[i+5]);
                nv.setUrlfilm(rep[i+6]);
                nv.setDescription(rep[i+7]);
                nv.setDuree(rep[i+8]);
                String temp = rep[i + 9].replaceAll("\\n+","");
                if(temp.equals("top10")){
                    _top.add(nv);
                }else if(temp.equals("serie")){
                    _serie.add(nv);
                }else if(temp.equals("film")){
                    _film.add(nv);
                }else if(temp.equals("anime")){
                    _anime.add(nv);
                }else{
                    _nouveaute.add(nv);
                }
                i+=10;
            }
        }
        BDDFilm.setFilms(_film);
        BDDFilm.setNouveaute(_nouveaute);
        BDDFilm.setSerie(_serie);
        BDDFilm.setTop(_top);
        BDDFilm.setAnime(_anime);
        return BDDFilm;
    }
    public void NouveauCompte(String mail,String mdp,String utilisateur) throws SQLException {
        String requete = "INSERT INTO compte (email, mdp, usager) VALUES ('"+mail+"','"+mdp+"','"+utilisateur+"');";
        maconnexion.executeUpdate(requete);
    }
    public void changerNomUtilisateur(String mail, String Utilisateur) throws SQLException {
        maconnexion.executeUpdate("UPDATE compte SET utilisateur='"+Utilisateur+"' WHERE mail='"+mail+"';" );
    }
    public void NouveauUtilisateur(String pseudo,String mail,String image) throws SQLException {
        maconnexion.executeUpdate("INSERT INTO utilisateur(mail,pseudo,image)  VALUES ('"+mail+"','"+pseudo+"','"+image+"');" );
    }
    //Pour modifier l'utilisateur dans la BDD
    public void ModifUtilisateur(String mail,String pseudo,String image,String ancien_pseudo) throws SQLException {
        maconnexion.executeUpdate("UPDATE utilisateur SET pseudo='"+pseudo+"', image='"+image+"' WHERE mail ='"+mail+"'and pseudo='"+ancien_pseudo+"';");
    }
    public void supprimerUtilisateur(String mail,String pseudo)throws SQLException{
        maconnexion.executeUpdate("DELETE FROM utilisateur where pseudo='"+pseudo+"'and mail='"+mail+"';");
    }
    public ArrayList<String>RecupererUtilisateurs(String email) throws SQLException {
        String requete = "SELECT * FROM utilisateur WHERE mail='"+email+"';";
        ArrayList<String>liste=new ArrayList<>();
        liste=maconnexion.recupererDonnees(requete);
        return liste;
    }
    public ArrayList<Film> getRecherche() {
        return Recherche;
    }
    public void setRecherche(ArrayList<Film> recherche) {
        Recherche = recherche;
    }
    public ArrayList<String> RecupererListe(String email,String pseudo) throws SQLException {
        String requete = "SELECT * FROM maliste WHERE mail='"+email+"'AND pseudo='"+pseudo+"';";
        ArrayList<String>liste=new ArrayList<>();
        liste=maconnexion.recupererDonnees(requete);
        System.out.println(liste);
        return liste;
    }
    public void supprimerMaliste(String mail,String pseudo,String film)throws SQLException{
        maconnexion.executeUpdate("DELETE FROM maliste where pseudo='"+pseudo+"'and mail='"+mail+"'and film_titre='"+film+"';");
    }
    public void AjoutMaliste(String mail,String pseudo,String film) throws SQLException {
        maconnexion.executeUpdate("INSERT INTO maliste(mail,pseudo,film_titre) VALUES ('"+mail+"','"+pseudo+"','"+film+"');" );
    }
    public ArrayList<String> RecupererNote(String email,String pseudo) throws SQLException {
        String requete = "SELECT * FROM note WHERE mail='"+email+"'AND pseudo='"+pseudo+"';";
        ArrayList<String>liste=new ArrayList<>();
        liste=maconnexion.recupererDonnees(requete);
        System.out.println(liste);
        return liste;
    }
    //NOTE RECHER
    public void supprimerNote(String mail,String pseudo,String film)throws SQLException{
        maconnexion.executeUpdate("DELETE FROM note where pseudo='"+pseudo+"'and mail='"+mail+"'and film ='"+film+"';");
    }
    public void AjoutNote(String mail,String pseudo,String film,int note) throws SQLException {
        maconnexion.executeUpdate("INSERT INTO note(mail,pseudo,film,note) VALUES ('"+mail+"','"+pseudo+"','"+film+"','"+note+"');");
    }
    public void ModifNote(String mail,String pseudo,String film,int note) throws SQLException {
        maconnexion.executeUpdate("UPDATE note SET note='"+note+"' WHERE mail='"+mail+"'and pseudo ='"+pseudo+"'and film='"+film+"';");
    }
    //TOP 10
    public void mettreTop10(Film voir) throws SQLException {
        maconnexion.executeUpdate( "UPDATE film SET liste_type='top10' WHERE titre='"+voir.getTitre()+"';" );
    }
    public void enlevertop10(Film voir) throws SQLException {
        maconnexion.executeUpdate( "UPDATE film SET liste_type='' WHERE titre='"+voir.getTitre()+"';" );
    }
    public String nombreUtilisateur(Compte compte) throws SQLException {
        ArrayList<String> resultats = maconnexion.recupererDonnees("SELECT COUNT(*) AS nb_utilisateur FROM utilisateur WHERE mail = '"+compte.getEmail()+"';");
        return resultats.get(0);
    }
    public ArrayList<String> recupererComptes() throws SQLException {
        ArrayList<String>a =maconnexion.recupererDonnees( "SELECT * FROM compte");
        return a;
    }
    public void changerinfofilm(String realisateur,String Titre,String VraiTitre,String Url,String Genre) throws SQLException {
        maconnexion.executeUpdate( "UPDATE film set titre='"+Titre+"', realisateur='"+realisateur+"',urlfilm='"+Url+"',genre='"+Genre+"', Where titre ='"+VraiTitre+"';" );
    }
    public String recupCompte(String email,String mdp) throws SQLException {
        ArrayList<String> resultats = maconnexion.recupererDonnees( "SELECT usager FROM compte WHERE email='"+email+"'and mdp='"+mdp+"';");
        return resultats.get(0);
    }

}
