package controleur;

import java.sql.SQLException;
import java.util.ArrayList;

public class RechercheInfo {

    private ConnexionDB maconnexion;
    private final java.awt.List listeDeTables;
    private final java.awt.List listeDeRequetes;

    public RechercheInfo(){
        super();
        listeDeTables = new java.awt.List(100, false);
        listeDeRequetes = new java.awt.List(100, false);
    }

    public void afficherTables() {
        for (String table : maconnexion.tables) {
            listeDeTables.add(table);
        }
    }

    public void afficherLignes(String nomTable) {
        try {
            ArrayList<String> liste;
            System.out.println("Afficher LIgnes");
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

                // afficher les résultats de la requete selectionnee
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
