package controleur;

import modele.Film;
import modele.NetflixBDD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class RechercheInfo {
    private ConnexionDB maconnexion;
    private final java.awt.List listeDeTables;
    private final java.awt.List listeDeRequetes;

    public RechercheInfo(){
        super();
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
    public ArrayList<String>RecupererUtilisateurs(String email) throws SQLException {
        String requete = "SELECT * FROM utilisateur WHERE email='"+email+"';";

        ArrayList<String>liste=new ArrayList<>();
        liste=maconnexion.recupererDonnees( requete );
        //  System.out.println( liste.get(0) );
        return liste;
    }

    void lectureDAO(){
        try {
            try {
                // tentative de connexion si les 4 attributs sont remplis
                //maconnexion = new Connexion("jps", "root", "");
                maconnexion = new ConnexionDB("projetinfo", "root", "root");

                // effacer les listes de tables et de requêtes
                listeDeTables.removeAll();
                listeDeRequetes.removeAll();

                remplirTables();

                // afficher la liste de tables et des requetes
                afficherTables();

                // se positionner sur la première table et requête de selection
                listeDeTables.select(0);

                // afficher les champs de la table sélectionnée
                String nomTable = listeDeTables.getSelectedItem();

                // recuperer les lignes de la table selectionnee
                afficherLignes(nomTable);

                Scanner sc =new Scanner(System.in);
                System.out.println("Saisir email");
                String email = sc.nextLine();
                String mdpVerif =verifMDP(email);
                System.out.println("bon mdp :"+mdpVerif);
                System.out.println("Saisir mdp");
                String mdp = sc.nextLine();
                if(mdp==mdpVerif)
                    System.out.println("Mot de passe est bon");

            } catch (ClassNotFoundException cnfe) {
                System.out.println("Connexion echouee : probleme de classe");
                cnfe.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL");
            e.printStackTrace();
        }
    }

}
