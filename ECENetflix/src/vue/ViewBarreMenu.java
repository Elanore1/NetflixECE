package vue;

import controleur.FenetreControleur;
import modele.BarreMenu;
import javax.swing.*;
import java.awt.*;
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
    //Une fois que l'utilisateur est connécté on montre la barre de menu d'acceuil
    public void setBarreConnection(){
        JMenu vide = new JMenu("                    ");
        JMenu vide2 = new JMenu("                       ");
        vide2.disable();
        vide.disable();//pour ne pas pouvoir selectionner
        JMenu LogoRecherche = new JMenu("");
        icone.setIcon(new ImageIcon( "src/images/icone1.png"));
        LogoRecherche.setIcon(new ImageIcon( "src/images/recherche.png"));
        recherche.setMaximumSize(recherche.getPreferredSize());
        recherche.setForeground(Color.white);
        recherche.setBackground(Color.black);
        recherche.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //On efface le texte original
                recherche = ((JTextField)e.getSource());
                recherche.setText("");
                recherche.getFont().deriveFont(Font.PLAIN);
                recherche.setForeground(Color.white);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        //on créé les boutons et des MouseListeners
        MouseListener ListenerMenu = new MouseListener() {
            boolean clickR = false;
            public void changeEtat(){
                if(clickR==false){
                    //il ne faut pas afficher
                    recherche.setVisible(true);
                }else if(clickR==true) {
                    //il faut afficher
                    recherche.setVisible(false);
                    recherche.setText(" Titres, personnes, genres ");
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if((action != LogoRecherche) && (!Objects.equals(recherche, action))) {
                    recherche.setVisible(false);
                }
                //pour savoir sur quel action on est
                if(Objects.equals(acceuil,action)){
                    System.out.println("acceuil");
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                JMenu action = (JMenu) e.getSource();
                action.setSelected(false);
                if(action==LogoRecherche){
                    clickR=!clickR;
                    changeEtat();
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
                System.out.println("action : "+action.getText());
                if(action.getText().equals("  Se déconnecter  "))
                    controleur.setAction("Acceuil");
                action.setEnabled(false);
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
        JMenuItem param = new JMenuItem("  Paramètres  ");
        JMenuItem compte = new JMenuItem("  Compte  ");
        JMenuItem aide = new JMenuItem("  Aide  ");
        JMenuItem deconnect = new JMenuItem("  Se déconnecter  ");
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
        param.setPreferredSize(new Dimension(super.getWidth(), 50));
        compte.setBorder(null);
        compte.setPreferredSize(new Dimension(super.getWidth(), 50));
        aide.setBorder(null);
        aide.setPreferredSize(new Dimension(super.getWidth(), 50));
        deconnect.setBorder(null);
        deconnect.setPreferredSize(new Dimension(110, 50));
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
}
