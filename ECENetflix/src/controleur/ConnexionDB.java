package controleur;
import java.sql.*;
import java.util.ArrayList;

/**
 * Connexion a votre BDD locale ou à distance sur le serveur de l'ECE via le tunnel SSH
 * @author segado
 */
public class ConnexionDB {

    /**
     * Attributs prives : connexion JDBC, statement, ordre requete et resultat
     * requete
     */
    private final Connection conn;
    private final Statement stmt;
    private ResultSet rset;
    private ResultSetMetaData rsetMeta;
    /**
     * ArrayList public pour les tables
     */
    public ArrayList<String> tables = new ArrayList<>();
    /**
     * ArrayList public pour les requêtes de sélection
     */
    public ArrayList<String> requetes = new ArrayList<>();
    /**
     * ArrayList public pour les requêtes de MAJ
     */
    public ArrayList<String> requetesMaj = new ArrayList<>();

    /**
     * Constructeur avec 3 paramètres : nom, login et password de la BDD locale
     *
     * @param nameDatabase
     * @param loginDatabase
     * @param passwordDatabase
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    //connexion a la base de données
    public ConnexionDB(String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException{
        // chargement driver "com.mysql.jdbc.Driver"
        Class.forName("com.mysql.cj.jdbc.Driver");
        String urlDatabase = "jdbc:mysql://localhost:3306/"+ nameDatabase;
        //création d'une connexion JDBC à la base
        conn = DriverManager.getConnection(urlDatabase, loginDatabase, passwordDatabase);
        // création d'un ordre SQL (statement)
        stmt = conn.createStatement();
    }
    /**
     * Méthode qui ajoute la table en parametre dans son ArrayList
     * @param table
     */
    public void ajouterTable(String table) {
        tables.add(table);
    }

    /**
     * Méthode qui ajoute la requete de selection en parametre dans son
     * ArrayList
     * @param requete
     */
    public void ajouterRequete(String requete) {
        requetes.add(requete);
    }

    /**
     * Méthode qui ajoute la requete de MAJ en parametre dans son
     * ArrayList
     * @param requete
     */
    public void ajouterRequeteMaj(String requete) {
        requetesMaj.add(requete);
    }

    /**
     * Méthode qui retourne l'ArrayList des champs de la table en parametre
     * @param table
     * @return
     * @throws SQLException
     */
    public ArrayList remplirChampsTable(String table) throws SQLException {
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery("select * from " + table);

        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();

        // calcul du nombre de colonnes du resultat
        int nbColonne = rsetMeta.getColumnCount();

        // creation d'une ArrayList de String
        ArrayList<String> liste;
        liste = new ArrayList<>();
        String champs = "";
        // Ajouter tous les champs du resultat dans l'ArrayList
        for (int i = 0; i < nbColonne; i++) {
            champs = champs + " " + rsetMeta.getColumnLabel(i + 1);
        }

        // ajouter un "\n" à la ligne des champs
        champs = champs + "\n";

        // ajouter les champs de la ligne dans l'ArrayList
        liste.add(champs);

        // Retourner l'ArrayList
        return liste;
    }

    /**
     * Methode qui retourne l'ArrayList des champs de la requete en parametre
     * @param requete
     * @return
     * @throws SQLException
     */
    public ArrayList<String> recupererDonnees(String requete) throws SQLException {
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery(requete);
        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();
        // calcul du nombre de colonnes du resultat
        int nbColonne = rsetMeta.getColumnCount();
        // création d'une ArrayList de String
        ArrayList<String> liste = new ArrayList<>();
        // pour chaque ligne du résultat
        while (rset.next()) {
            // ajouter chaque champ de la ligne dans l'ArrayList
            for (int i = 1; i <= nbColonne; i++) {
                liste.add(rset.getString(i));
            }
        }
        // Retourner l'ArrayList
        return liste;
    }
    public ArrayList remplirChampsRequete(String requete) throws SQLException {
        System.out.println("debut requette");
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery(requete);
        System.out.println("apres requete");
        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();

        // calcul du nombre de colonnes du resultat
        int nbColonne = rsetMeta.getColumnCount();

        // creation d'une ArrayList de String
        ArrayList<String> liste;
        liste = new ArrayList<>();

        // tant qu'il reste une ligne
        while (rset.next()) {
            String champs;
            champs = rset.getString(1); // ajouter premier champ

            // Concatener les champs de la ligne separes par ,
            for (int i = 1; i < nbColonne; i++) {
                champs = champs + "," + rset.getString(i + 1);
            }

            // ajouter un "\n" à la ligne des champs
            champs = champs + "\n";

            // ajouter les champs de la ligne dans l'ArrayList
            liste.add(champs);
        }

        // Retourner l'ArrayList
        return liste;
    }
    /**
     * Méthode qui execute une requete de MAJ en parametre
     * @param requeteMaj
     * @throws SQLException
     */
    public void executeUpdate(String requeteMaj) throws SQLException {
        stmt.executeUpdate(requeteMaj);
    }
}
