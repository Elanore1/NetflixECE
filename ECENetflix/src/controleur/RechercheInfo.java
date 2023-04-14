package controleur;

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
                maconnexion = new ConnexionDB("projetinfo", "root", "");
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

    public ArrayList<String>RecupererUtilisateurs(String email) throws SQLException {
        String requete = "SELECT * FROM utilisateur WHERE email='"+email+"';";
        ArrayList<String>liste;
        liste=maconnexion.remplirChampsRequete( requete );
        return liste;
    }




    public void NouveauCompte(String mail,String mdp,String utilisateur) throws SQLException {


        String requete = "INSERT INTO compte (email, mdp, usager) VALUES ('"+mail+"','"+mdp+"','"+utilisateur+"');";
      //  System.out.println( requete );

        maconnexion.executeUpdate(requete);
        //afficherRes(requete);


    }

    public void NouveauUtilisateur(String pseudo,String mail,String image) throws SQLException {
        maconnexion.executeUpdate("INSERT INTO utilisateur (email,pseudo,photo)  VALUES ('"+mail+"','"+pseudo+"','"+image+"');" );
       // maconnexion.executeUpdate(requete);
    }

    public void changerNomUtilisateur(String mail, String Utilisateur) throws SQLException {
        maconnexion.executeUpdate("UPDATE compte SET utilisateur='"+Utilisateur+"' WHERE mail='"+mail+"';" );

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
            System.out.println("Afficher les requetes");
            // recupérér les résultats de la requete selectionnee
            liste = maconnexion.remplirChampsRequete(requeteSelectionnee);

            for(int i=0;i<liste.size();i++)
            System.out.println( liste.get( i ) );

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
    }

    void lectureDAO(){
        try {
            try {
                // tentative de connexion si les 4 attributs sont remplis
                //maconnexion = new Connexion("jps", "root", "");
                maconnexion = new ConnexionDB("projetinfo", "root", "");

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
