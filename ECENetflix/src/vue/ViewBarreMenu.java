package vue;

import controleur.FenetreControleur;
import modele.BarreMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class ViewBarreMenu extends JMenuBar {
    //caracteristique de la barre
    FenetreControleur controleur;//controleur
    private BarreMenu menu = new BarreMenu();
    //Valeur dans notre barre de menu
    private JMenu acceuil = new JMenu("  Accueil  ");
    private JMenu films = new JMenu("  Films  ");
    private JMenu serie = new JMenu("  Séries  ");
    private JMenu maListe = new JMenu("  Ma Liste  ");
    private JMenu nouveautés = new JMenu("  Nouveautés  ");
    private JMenu icone = new JMenu("          ");
    private  JMenu graphique = new JMenu( " Graphique " );
    private JMenu clients =new JMenu( " Clients " );
    private JTextField recherche = new JTextField(" Titres, personnes, genres ",20);
    public ViewBarreMenu() { // constructeur barre de menu originale
        setBackground(Color.BLACK);//couleur de fond noir
        setBorderPainted(true);//colorie la bordure
        JMenu logo = new JMenu("");//logo au debut de la barre de menu
        logo.setIcon(new ImageIcon( "src/images/logo1.png"));
        logo.disable();//pour ne pas pouvoir selectionner
        JMenu vide = new JMenu("     ");
        vide.disable();//pour ne pas pouvoir selectionner
        add(vide);
        add(vide);
        add(logo);
        add(vide);
        add(vide);
    }
    public void setBarreVide(){
        setBackground(Color.BLACK);//couleur de fond noir
        setBorderPainted(true);//colorie la bordure
        icone.removeAll();
        recherche.removeAll();
        setBackground(Color.BLACK);//couleur de fond noir
        setBorderPainted(true);//colorie la bordure
        JMenu logo = new JMenu("");//logo au debut de la barre de menu
        logo.setIcon(new ImageIcon( "src/images/logo1.png"));
        logo.disable();//pour ne pas pouvoir selectionner
    }
    //Une fois que l'utilisateur est connécté on montre la barre de menu d'acceuil
    public void setBarreConnection(){
        removeAll();
        icone.removeAll();
        setBackground(Color.BLACK);//couleur de fond noir
        setBorderPainted(true);//colorie la bordure
        JMenu logo = new JMenu("");//logo au debut de la barre de menu
        logo.setIcon(new ImageIcon( "src/images/logo1.png"));
        logo.disable();//pour ne pas pouvoir selectionner
        JMenu vide = new JMenu("     ");
        vide.disable();//pour ne pas pouvoir selectionner
        add(vide);
        add(vide);
        add(logo);
        add(vide);
        JMenu vide2 = new JMenu("                       ");
        vide2.disable();
        vide.disable();//pour ne pas pouvoir selectionner
        JMenu LogoRecherche = new JMenu("");
        icone.setIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPhotoProfil());
        LogoRecherche.setIcon(new ImageIcon( "src/images/recherche.png"));
        recherche.setMaximumSize(recherche.getPreferredSize());
        recherche.setForeground(Color.white);
        recherche.setBackground(Color.black);
        recherche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //On efface le texte original
                recherche = ((JTextField)e.getSource());
                recherche.setText("");
                recherche.getFont().deriveFont(Font.PLAIN);
                recherche.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(recherche.getText().equals(""))
                    recherche.setText(" Titres, personnes, genres ");
            }
        });
        //on créé les boutons et des MouseListeners
        MouseListener ListenerMenu = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                //pour savoir sur quel action on est
                if(Objects.equals(acceuil,action)){
                    controleur.setAction("Netflix");
                }else if(Objects.equals(films,action)){
                    //Film
                    controleur.getDAO().setRecherche(controleur.getBDD().getFilms());
                    controleur.setAction("Recherche");
                }else if(Objects.equals(serie,action)){
                    //Serie
                    controleur.getDAO().setRecherche(controleur.getBDD().getSerie());
                    controleur.setAction("Recherche");
                }else if(Objects.equals(nouveautés,action)){
                    //nouveauté
                    controleur.getDAO().setRecherche(controleur.getBDD().getNouveaute());
                    controleur.setAction("Recherche");
                }else if(Objects.equals(maListe,action)){
                    //maliste
                    controleur.getDAO().setRecherche(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe());
                    controleur.setAction("Recherche");
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if(action==LogoRecherche){
                    //recherche sql des films
                    System.out.println("Film a recherche");
                    //On recherche parmi notre base
                    controleur.getDAO().setRecherche(controleur.getDAO().rechercheFilm(recherche.getText()));
                    controleur.setAction("Recherche");
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if(action==LogoRecherche) {
                    action.setIcon(new ImageIcon( "src/images/recherche.png"));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if(action!=LogoRecherche) {
                    action.setForeground(Color.lightGray);
                    action.setBackground(Color.BLACK);
                }else{
                    action.setIcon(new ImageIcon( "src/images/rechercheg.png"));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                if(action!=LogoRecherche) {
                    action.setForeground(Color.white);
                    action.setBackground(Color.BLACK);
                }else{
                    action.setIcon(new ImageIcon( "src/images/recherche.png"));
                }
            }
        };
        MouseListener ListenerMenuItem = new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                //action.setSelected(false);
                action.setEnabled(false);
                if(action.getText().equals("  Se déconnecter  ")){
                    remove(acceuil);
                    remove(maListe);
                    remove(films);
                    remove(serie);
                    remove(nouveautés);
                    remove(recherche);
                    remove(icone);
                    remove(LogoRecherche);
                    controleur.setAction("Acceuil");
                }else if(action.getText().equals("  Compte  ")){
                    remove(acceuil);
                    remove(maListe);
                    remove(films);
                    remove(serie);
                    remove(nouveautés);
                    remove(recherche);
                    remove(icone);
                    remove(LogoRecherche);
                    controleur.setAction("Mofidier Utilisateur");
                }else if(action.getText().equals("  Gérer les profils  ")){
                    remove(acceuil);
                    remove(maListe);
                    remove(films);
                    remove(serie);
                    remove(nouveautés);
                    remove(recherche);
                    remove(icone);
                    remove(LogoRecherche);
                    controleur.setAction("Gestion Profil");
                }else{
                    for(int i=0;i<controleur.getControleurCompte().getCompte().getUtilisateurs().size();i++){
                        if(action.getText()==controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo()){
                            controleur.getControleurCompte().getCompte().setUtilisateuractuel(i);
                            controleur.setAction("Netflix");
                        }
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(false);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(false);
                action.setForeground(Color.lightGray);
                action.setBackground(Color.BLACK);
                //action.setHorizontalAlignment(JMenuItem.LEFT);
                if((action.getText().equals("  Se déconnecter  ")) || (action.getText().equals("  Compte  ")) || (action.getText().equals("  Gérer les profils  "))){
                    action.setVerticalTextPosition(JMenuItem.CENTER);
                    action.setHorizontalTextPosition(JMenuItem.CENTER);
                    action.revalidate();
                    action.setSelected(false);
                    action.setEnabled(true);
                }else{
                    action.setHorizontalAlignment(JMenuItem.LEFT);
                }

            }
            @Override
            public void mouseExited(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(true);
                action.setForeground(Color.white);
                action.setBackground(Color.BLACK);
                //action.setHorizontalAlignment(JMenuItem.LEFT);
                if ((action.getText().equals("  Se déconnecter  ")) || (action.getText().equals("  Compte  ")) || (action.getText().equals("  Gérer les profils  "))) {
                    action.setVerticalTextPosition(JMenuItem.CENTER);
                    action.setHorizontalTextPosition(JMenuItem.CENTER);
                    action.setIcon(null);
                    action.setSelected(false);
                } else {
                    action.setHorizontalAlignment(JMenuItem.LEFT);
                }
            }
        };
        //on met les lettres en blanc
        acceuil.setForeground(Color.white);
        films.setForeground(Color.white);
        serie.setForeground(Color.white);
        nouveautés.setForeground(Color.white);
        maListe.setForeground(Color.white);
        acceuil.addMouseListener(ListenerMenu);
        films.addMouseListener(ListenerMenu);
        serie.addMouseListener(ListenerMenu);
        nouveautés.addMouseListener(ListenerMenu);
        maListe.addMouseListener(ListenerMenu);
        icone.addMouseListener(ListenerMenu);
        //logo recherche pour chercher un film ou serie etc...
        LogoRecherche.addMouseListener(ListenerMenu);
        for(int i=0;i<controleur.getControleurCompte().getCompte().getUtilisateurs().size();i++){
            if(i!=controleur.getControleurCompte().getCompte().getUtilisateuractuel()){
                JMenuItem util = new JMenuItem(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                util.setIcon(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
                util.setDisabledIcon(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
                util.setForeground(Color.white);
                util.setBackground(Color.black);
                util.setBorder(null);
                util.setPreferredSize(new Dimension(110, 50));
                util.setLayout(new FlowLayout());
                util.addMouseListener(ListenerMenuItem);
                util.setHorizontalAlignment(JMenuItem.LEFT);
                util.setFont(new Font("Arial",Font.PLAIN, 11));
                icone.add(util);
            }
        }
        JMenuItem param = new JMenuItem("  Gérer les profils  ");
        JMenuItem compte = new JMenuItem("  Compte  ");
        JMenuItem deconnect = new JMenuItem("  Se déconnecter  ");
        param.setMaximumSize(new Dimension(110, 50));
        compte.setMaximumSize(new Dimension(110, 50));
        deconnect.setMaximumSize(new Dimension(110, 50));
        icone.setForeground(Color.black);
        icone.setContentAreaFilled(true);
        //on colorie en noir et blanc
        param.setForeground(Color.white);
        compte.setForeground(Color.white);
        deconnect.setForeground(Color.white);
        param.setBackground(Color.black);
        compte.setBackground(Color.black);
        deconnect.setBackground(Color.black);
        //taille des sous menu et colori bordure
        param.setBorder(null);
        param.setPreferredSize(new Dimension(110, 50));
        param.setLayout(new FlowLayout());
        compte.setBorder(null);
        compte.setPreferredSize(new Dimension(110, 50));
        compte.setLayout(new FlowLayout());
        deconnect.setBorder(null);
        deconnect.setPreferredSize(new Dimension(110, 50));
        deconnect.setLayout(new FlowLayout());
        param.addMouseListener(ListenerMenuItem);
        compte.addMouseListener(ListenerMenuItem);
        deconnect.addMouseListener(ListenerMenuItem);
        param.setHorizontalAlignment(JMenuItem.LEFT);
        compte.setHorizontalAlignment(JMenuItem.LEFT);
        deconnect.setHorizontalAlignment(JMenuItem.LEFT);
        //on ajoute les sous menu a notre icone
        icone.add(param);
        icone.add(compte);
        icone.add(deconnect);
        //on ajoute les JMenu dans notre barre
        add(acceuil);
        add(films);
        add(serie);
        add(nouveautés);
        add(maListe);
        add(vide);
        add(LogoRecherche);
        add(recherche);
        //pour mettre l'icone tout au bout de la barre
        add(Box.createHorizontalGlue());
        add(icone);
    }
    public void setController(FenetreControleur fn){
        this.controleur=fn;
    }

    public void setBarreProprietaire(){
        removeAll();
        icone.removeAll();
        setBackground(Color.BLACK);//couleur de fond noir
        setBorderPainted(true);//colorie la bordure
        JMenu logo = new JMenu("");//logo au debut de la barre de menu
        logo.setIcon(new ImageIcon( "src/images/logo1.png"));
        logo.disable();//pour ne pas pouvoir selectionner
        JMenu vide = new JMenu("     ");
        vide.disable();//pour ne pas pouvoir selectionner
        add(vide);
        add(vide);
        add(logo);
        add(vide);
        JMenu vide2 = new JMenu("                       ");
        vide2.disable();
        vide.disable();//pour ne pas pouvoir selectionner
        JMenu LogoRecherche = new JMenu("");
        // icone.setIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPhotoProfil());
        LogoRecherche.setIcon(new ImageIcon( "src/images/recherche.png"));
        recherche.setMaximumSize(recherche.getPreferredSize());
        recherche.setForeground(Color.white);
        recherche.setBackground(Color.black);
        recherche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //On efface le texte original
                recherche = ((JTextField)e.getSource());
                recherche.setText("");
                recherche.getFont().deriveFont(Font.PLAIN);
                recherche.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(recherche.getText().equals(""))
                    recherche.setText(" Titres, personnes, genres ");
            }
        });
        //on créé les boutons et des MouseListeners
        MouseListener ListenerMenu = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                //pour savoir sur quel action on est
                if(Objects.equals(acceuil,action)){
                    controleur.setAction("Netflix Proprietaire");
                }else if(Objects.equals(films,action)){
                    //Film
                    controleur.getDAO().setRecherche(controleur.getBDD().getFilms());
                    controleur.setAction("Recherche");
                }else if(Objects.equals(serie,action)){
                    //Serie
                    controleur.getDAO().setRecherche(controleur.getBDD().getSerie());
                    controleur.setAction("Recherche");
                }else if(Objects.equals(nouveautés,action)){
                    //nouveauté
                    controleur.getDAO().setRecherche(controleur.getBDD().getNouveaute());
                    controleur.setAction("Recherche");
                }else if(Objects.equals(maListe,action)){
                    //maliste
                    controleur.getDAO().setRecherche(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe());
                    controleur.setAction("Recherche");
                }
                else if(Objects.equals(graphique ,action ))
                {
                    controleur.setAction( "Graphiques" );
                }
                else if(Objects.equals(clients ,action ))
                {
                    controleur.setAction( "Clients" );
                }

            }
            @Override
            public void mousePressed(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if(action==LogoRecherche){
                    //recherche sql des films
                    System.out.println("Film a recherche");
                    //On recherche parmi notre base
                    controleur.getDAO().setRecherche(controleur.getDAO().rechercheFilm(recherche.getText()));
                    controleur.setAction("Recherche");
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if(action==LogoRecherche) {
                    action.setIcon(new ImageIcon( "src/images/recherche.png"));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if(action!=LogoRecherche) {
                    action.setForeground(Color.lightGray);
                    action.setBackground(Color.BLACK);
                }else{
                    action.setIcon(new ImageIcon( "src/images/rechercheg.png"));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                if(action!=LogoRecherche) {
                    action.setForeground(Color.white);
                    action.setBackground(Color.BLACK);
                }else{
                    action.setIcon(new ImageIcon( "src/images/recherche.png"));
                }
            }
        };
        MouseListener ListenerMenuItem = new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(false);
                System.out.println(action.getText());
                if(action.getText().equals("  Se déconnecter  ")){
                    remove(acceuil);
                    remove(maListe);
                    remove(films);
                    remove(serie);
                    remove( graphique );
                    remove(clients);
                    remove(nouveautés);
                    remove(recherche);
                    remove(icone);
                    remove(LogoRecherche);
                    controleur.setAction("Acceuil");
                }else if(action.getText().equals("  Compte  ")){
                    remove(acceuil);
                    remove(maListe);
                    remove( graphique );
                    remove(clients);
                    remove(films);
                    remove(serie);
                    remove(nouveautés);
                    remove(recherche);
                    remove(icone);
                    remove(LogoRecherche);
                    controleur.setAction("Gestion Profil");
                }else if(action.getText().equals("  Paramètres  ")){
                    remove(acceuil);
                    remove(maListe);
                    remove(films);
                    remove(serie);
                    remove( graphique );
                    remove(clients);
                    remove(nouveautés);
                    remove(recherche);
                    remove(icone);
                    remove(LogoRecherche);
                    controleur.setAction("Gestion Profil");
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(false);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(false);
                action.setForeground(Color.lightGray);
                action.setBackground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                JMenuItem action = (JMenuItem) e.getSource();
                action.setEnabled(true);
                action.setForeground(Color.white);
                action.setBackground(Color.BLACK);
            }
        };
        //on met les lettres en blanc
        acceuil.setForeground(Color.white);
        films.setForeground(Color.white);
        serie.setForeground(Color.white);
        clients.setForeground( Color.white );
        graphique.setForeground( Color.white );
        nouveautés.setForeground(Color.white);
        maListe.setForeground(Color.white);
        acceuil.addMouseListener(ListenerMenu);
        films.addMouseListener(ListenerMenu);
        serie.addMouseListener(ListenerMenu);
        graphique.addMouseListener( ListenerMenu );
        clients.addMouseListener( ListenerMenu );
        nouveautés.addMouseListener(ListenerMenu);
        maListe.addMouseListener(ListenerMenu);
        icone.addMouseListener(ListenerMenu);
        //logo recherche pour chercher un film ou serie etc...
        LogoRecherche.addMouseListener(ListenerMenu);
        JMenuItem param = new JMenuItem("  Paramètres  ",null);
        JMenuItem compte = new JMenuItem("  Compte  ",null);
        JMenuItem aide = new JMenuItem("  Aide  ",null);
        JMenuItem deconnect = new JMenuItem("  Se déconnecter  ",null);
        icone.setForeground(Color.black);
        icone.setContentAreaFilled(true);
        //on colorie en noir et blanc
        param.setForeground(Color.white);
        compte.setForeground(Color.white);
        aide.setForeground(Color.white);
        deconnect.setForeground(Color.white);
        param.setBackground(Color.black);
        compte.setBackground(Color.black);
        aide.setBackground(Color.black);
        deconnect.setBackground(Color.black);
        //taille des sous menu et colori bordure
        param.setBorder(null);
        param.setPreferredSize(new Dimension(110, 50));
        param.setLayout(new FlowLayout());
        compte.setBorder(null);
        compte.setPreferredSize(new Dimension(110, 50));
        compte.setLayout(new FlowLayout());
        aide.setBorder(null);
        aide.setPreferredSize(new Dimension(110, 50));
        aide.setLayout(new FlowLayout());
        deconnect.setBorder(null);
        deconnect.setPreferredSize(new Dimension(110, 50));
        deconnect.setLayout(new FlowLayout());
        param.addMouseListener(ListenerMenuItem);
        compte.addMouseListener(ListenerMenuItem);
        aide.addMouseListener(ListenerMenuItem);
        deconnect.addMouseListener(ListenerMenuItem);
        param.setHorizontalAlignment(JMenuItem.CENTER);;//pour ne pas pouvoir selectionner
        compte.setHorizontalAlignment(JMenuItem.CENTER);;//pour ne pas pouvoir selectionner
        aide.setHorizontalAlignment(JMenuItem.CENTER);;//pour ne pas pouvoir selectionner
        deconnect.setHorizontalAlignment(JMenuItem.CENTER);;//pour ne pas pouvoir selectionner
        //on ajoute les sous menu a notre icone
        icone.add(param);
        icone.add(compte);
        icone.add(aide);
        icone.add(deconnect);
        //on ajoute les JMenu dans notre barre
        add(acceuil);
        add(films);
        add(serie);
        add(nouveautés);
        add(maListe);
        add(vide);
        add(LogoRecherche);
        add(recherche);
        add(vide);
        add(graphique);
        add(clients);
        //pour mettre l'icone tout au bout de la barre
        add(Box.createHorizontalGlue());
        add(icone);
    }
}
