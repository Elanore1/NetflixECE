import controleur.FenetreControleur;
import modele.Fenetre;
import vue.ViewBarreMenu;
import vue.ViewContenu;
import vue.ViewFenetre;

import java.sql.SQLException;

public class ECENetflixApp {
    public static void main(String[]args) throws SQLException {
        FenetreControleur fc = new FenetreControleur(new ViewFenetre(new Fenetre()),new ViewContenu(),new ViewBarreMenu());
        try {
            fc.run();
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }
}
