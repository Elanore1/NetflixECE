package vue;

import controleur.ControleurCompte;
import controleur.FenetreControleur;
import controleur.RechercheInfo;
import modele.Film;
import modele.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ViewContenu extends JPanel{
    FenetreControleur controleur;
    Image img=null;
    //pour acceder au mot de passe et email de la Base
    RechercheInfo DAO = new RechercheInfo();
    public boolean var = false;

    ControleurCompte compte =new ControleurCompte();


    ArrayList<Film> listFilm;

    public ViewContenu() { // constructeur par surchargé
        setVisible(true);
        setBackground(new Color(255,255,255));
        setLayout(new GridBagLayout());
    }
    //pour mettre une image en fond
    public void setImage(String s)throws IOException{
        try {
            this.img = ImageIO.read(new File(s));
            repaint();
        }
        catch (IOException e) {
            throw new IOException(s+" introuvable", e);
        }
    }
    @Override
    public void paintComponent(Graphics g){
        if(img!=null){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
    }
    public ImageIcon resizeImage(BufferedImage buffimage,int resizeWidth,int resizeHeight) throws IOException {
        Image image = buffimage.getScaledInstance(resizeWidth,resizeHeight, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(image);

    }

    public BufferedImage chargerIm(String urlA) throws IOException {
        BufferedImage image = null;
        URL url = null;
        try {
            url = new URL(urlA);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.connect();
            InputStream urlStream = conn.getInputStream();
            image = ImageIO.read(urlStream);
            return image;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


    }
    //affichage d'un formulaire de connection
    public void FormConnection(){
        //on met image en fond
        try {
            setImage("D:\\EXO JAVA\\NetflixECE-master\\NetflixECE-master\\ECENetflix\\src\\images/FondAcceuil.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }//container qui a une nouvelle couleur
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(450,500));
        container.setBackground(new Color(0,0,0,150));
        container.setLayout(new GridBagLayout());

        JLabel ident = new JLabel("S'identifier");
        ident.setFont(new Font("Arial", Font.BOLD,20));
        JLabel BadEmail = new JLabel(" ");
        JLabel BadMDP = new JLabel(" ");
        JButton bconfirm = new JButton("Valider");
        JButton bretour = new JButton("Retour");
        final JTextField TextEmail = new JTextField("E-mail");
        JPasswordField TextMDP = new JPasswordField("Mot de Passe");
        TextMDP.setEchoChar((char)0);
        TextEmail.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //On efface le texte original
                if(TextEmail.isEditable()){
                    JTextField TextEmail =(JTextField) e.getSource();
                    TextEmail.setText("");
                    TextEmail.setForeground(Color.white);
                }
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e) {
                if(TextEmail.getText().length()==0){
                    TextEmail.setText("E-mail");
                }
            }
        });
        TextMDP.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //On efface le texte original
                if(TextMDP.isEditable()){
                    JTextField TextMDP =(JTextField) e.getSource();
                    TextMDP.setText("");
                    TextMDP.setForeground(Color.white);
                }
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e) {
                if(TextMDP.getText().length()==0){
                    TextMDP.setText("Mot de Passe");
                }
            }
        });
        bconfirm.getModel().addChangeListener(new ChangeListener() {
            //verif que le mail existe dans la base de données
            private boolean validateMail(String mail) {
                String text;
                try {
                    text = DAO.verifEmail(mail);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
               // System.out.println(text);
                if(text!=null) {
                    return true;
                }else
                    return false;
            }
            private boolean validateMDP(String mail,String mdp){
                String text;
                try {
                    text = DAO.verifMDP(mail);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                //on suppr les espaces de fin
                text = text.replaceAll("\\s", "");
                if(text.compareTo(mdp)==0) {
                    System.out.println("bon mdp");
                    return true;
                }else
                    return false;
            }
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = bconfirm.getModel();
                boolean it = true;
                //on appuie sur le bouton
                if (model.isArmed()) {
                    boolean mdpSaisie=false;
                    //Validation de l'email
                    if(validateMail(TextEmail.getText())){
                        //le mail est valide
                        BadEmail.setForeground(Color.green);
                        BadEmail.setText("Le compte associé a été trouvé");
                        TextEmail.setEditable(false);
                       // System.out.println(TextMDP.getText());
                        mdpSaisie=true;
                    }else if(!(TextEmail.getText().length() >0)){//si il n'y a rien dedans
                        BadEmail.setText("Saisir un e-mail");//on affiche rien
                        BadMDP.setText(" ");
                    }else{
                        BadEmail.setForeground(Color.red);
                        BadEmail.setText("Aucun compte associé");
                        BadMDP.setText(" ");
                    }
                    //si un bon email a été entré
                    if(mdpSaisie==true){
                        //Validation mdp
                        if(validateMDP(TextEmail.getText(),TextMDP.getText())){
                            //le mail est valide
                            BadMDP.setForeground(Color.green);
                            BadMDP.setText("Le mot de passe est bon");
                            TextMDP.setEditable(false);
                            bconfirm.getModel().setPressed(false);
                            var=true;
                        }else if(!(TextMDP.getText().length()>0)){//si il n'y a rien dedans
                            BadMDP.setForeground(Color.red);
                            BadMDP.setText("Veuillez saisir un mot de passe");//on affiche rien

                        }else{
                            BadMDP.setForeground(Color.red);
                            BadMDP.setText("Le mot de passe n'est pas valide");
                        }
                    }
                    //pour actualiser notre panel
                    updateUI();
                } else {//relachement
                    if(var==true && it ==true){
                        //pour laisser un temps avant de s connecter
                        it=false;
                        try {

                            //on attend et on quitte
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        compte.EnregistreCompte( TextEmail.getText(),TextMDP.getText() );
                        controleur.setAction("Choix Utilisateurs");
                    }
                }
            }
        });
        bretour.getModel().addChangeListener(new ChangeListener() {
            //bouton retour
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = bretour.getModel();
                //on appuie sur le bouton
                if (model.isArmed()) {
                    controleur.setAction("Acceuil");
                }
            }
        });
        //Couleur des textes
        ident.setForeground(Color.white);
        BadEmail.setForeground(Color.red);
        BadEmail.setFont(new Font("SansSerif", Font.BOLD, 11));
        BadMDP.setForeground(Color.red);
        BadMDP.setFont(new Font("SansSerif", Font.BOLD, 11));
        //Bouton
        bconfirm.setBackground(new Color(1,113,121));
        bconfirm.setForeground(Color.white);
        bconfirm.setPreferredSize(new Dimension(250,35));
        bconfirm.setFocusPainted(false);
        bretour.setBackground(new Color(1,113,121));
        bretour.setForeground(Color.white);
        bretour.setPreferredSize(new Dimension(150,35));
        bretour.setFocusPainted(false);
        //taille textfield
        TextEmail.setPreferredSize(new Dimension(250,35));
        TextMDP.setPreferredSize(new Dimension(250,35));
        //couleur textField
        TextEmail.setForeground(Color.white);
        TextMDP.setForeground(Color.white);
        TextEmail.setBackground(Color.DARK_GRAY);
        TextMDP.setBackground(Color.DARK_GRAY);
        //Inset
        Insets textInsets = new Insets(10, 10, 5, 10);
        Insets buttonInsets = new Insets(20, 10, 10, 10);
        Insets errorInsets = new Insets(0,20,0,0);
        //positionnement S'identifier
        GridBagConstraints input = new GridBagConstraints();
        input.anchor = GridBagConstraints.CENTER;
        input.insets = textInsets;
        input.gridy = 0;
        container.add(ident,input);
        //positionnement textEmail
        input.anchor = GridBagConstraints.CENTER;
        input.insets = textInsets;
        input.gridy = 1;
        container.add(TextEmail,input);
        //positionnement text mauvais email
        input.gridy = 2;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        container.add(BadEmail,input);
        //positionnement textMDP
        input.gridy = 3;
        input.insets = textInsets;
        input.anchor = GridBagConstraints.CENTER;
        container.add(TextMDP,input);
        //positionnement text mauvais mdp
        input.gridy = 4;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        container.add(BadMDP,input);
        //placement bouton
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.WEST;
        input.gridx = 0;
        input.gridy = 5;
        container.add(bconfirm,input);
        //bouton retour
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 0;
        input.gridy = 8;
        container.add(bretour,input);
        //pour placer le panel transparent (container) sur notre panel avec image en fond
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 2;
        input.gridy = 5;
        add(container,input);
        updateUI();
    }

    public void Acceuil(){
        //on met image en fond
        try {
            setImage("D:\\EXO JAVA\\NetflixECE-master\\NetflixECE-master\\ECENetflix\\src\\images/FondAcceuil.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }//container qui a image en fond
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(450,500));
        container.setBackground(new Color(0,0,0,150));
        container.setLayout(new GridBagLayout());
        JButton bIdent = new JButton("S'identifier");
        JButton bNvCompte = new JButton("Créer un Compte");
        //Bouton Identification
        bIdent.setBackground(new Color(1,113,121));
        bIdent.setForeground(Color.white);
        bIdent.setPreferredSize(new Dimension(250,35));
        bIdent.setFocusPainted(false);
        bIdent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //On choisit de s'identifier
                controleur.setAction("S'identifier");
                System.out.println("S'identifier");
            }
        } );
        //Bouton Nouveau compte
        bNvCompte.setBackground(new Color(1,113,121));
        bNvCompte.setForeground(Color.white);
        bNvCompte.setPreferredSize(new Dimension(250,35));
        bNvCompte.setFocusPainted(false);
        bNvCompte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controleur.setAction("Créer un compte");
                System.out.println("Créer un compte");
            }
        } );
        //Inset
        Insets buttonInsets = new Insets(20, 10, 10, 10);
        GridBagConstraints input = new GridBagConstraints();
        //placement bouton ident
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.WEST;
        input.gridx = 0;
        input.gridy = 2;
        container.add(bIdent,input);
        //placement bouton nv compte
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.WEST;
        input.gridx = 0;
        input.gridy = 4;
        container.add(bNvCompte,input);
        //pour placer le panel transparent (container) sur notre panel avec image en fond
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 2;
        input.gridy = 5;
        add(container,input);
        updateUI();
    }

    public void Netflix(){
        //On met ca juste pour les test d'affichage
        listFilm=new ArrayList<Film>();
        //test affichage
        for(int i=0;i<10;i++){
            Film nv = new Film();
            nv.setUrlAffiche("https://i.pinimg.com/564x/8b/2f/a6/8b2fa6fb94810cd0d335b479896f7fc8.jpg");
            nv.setUrlAfficheHor("https://boomstickcomics.com/wp-content/uploads/2014/06/another-avatar-1024x768.jpg");
            listFilm.add(nv);
        }
        //Appli Netflix
        JPanel container = new JPanel(null);
        container.setSize(2050,2500);
        container.setPreferredSize(container.getPreferredSize());
        container.setBackground(Color.red);
        //Top 10
        JLabel top10 = new JLabel(new ImageIcon("src/images/top10.png"),JLabel.CENTER);
        JPanel separateur =new JPanel(null);
        JLabel titre = new JLabel("TOP 10 en France aujourd'hui");
        titre.setFont(new Font("Arial",Font.BOLD, 25));
        titre.setForeground(Color.white);
        titre.setBounds(10,15,400,60);
        separateur.setBackground(new Color(20,20,20));
        separateur.setBounds(0,0,1200,100);
        separateur.add(titre);
        top10.setBounds(0,100,2800,200);
        JButton droite = new JButton(" > ");
        JButton gauche = new JButton(" < ");
        droite.setBackground(new Color(0,0,0,150));
        droite.setForeground(Color.white);
        droite.setBounds(1121,0,50,200);
        droite.setEnabled(false);
        gauche.setBackground(new Color(0,0,0,150));
        gauche.setForeground(Color.white);
        gauche.setBounds(0,0,50,200);
        gauche.setEnabled(false);
        droite.setVisible(true);
        gauche.setVisible(true);
        droite.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){
                droite.setBackground(new Color(0,0,0,150));
                System.out.println(top10.getLocation());
                //si on est de 1 à 7
                if(top10.getLocation().equals(new Point(0,100))){
                    top10.setLocation(-1135,100);
                    droite.setBounds(2256,0,50,200);
                    gauche.setBounds(1133,0,50,200);
                }else{
                    top10.setLocation(0,100);
                    droite.setBounds(1121,0,50,200);
                    gauche.setBounds(0,0,50,200);
                }
                updateUI();
            }
            @Override
            public void mousePressed(MouseEvent e){
                droite.setBackground(new Color(0,0,0,150));
                updateUI();
            }
            @Override
            public void mouseReleased(MouseEvent e){
                droite.setBackground(new Color(0,0,0,150));
                updateUI();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                droite.setBackground(new Color(0,0,0,150));
                updateUI();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                droite.setBackground(new Color(0,0,0,150));
                updateUI();
            }
        });
        gauche.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){
                gauche.setBackground(new Color(0,0,0,150));
                System.out.println(top10.getLocation());
                //si on est de 1 à 7
                if(top10.getLocation().equals(new Point(0,100))){
                    top10.setLocation(-1135,100);
                    droite.setBounds(2256,0,50,200);
                    gauche.setBounds(1133,0,50,200);
                }else{
                    top10.setLocation(0,100);
                    droite.setBounds(1121,0,50,200);
                    gauche.setBounds(0,0,50,200);
                }
                updateUI();
            }
            @Override
            public void mousePressed(MouseEvent e){
                gauche.setBackground(new Color(0,0,0,150));
                updateUI();
            }
            @Override
            public void mouseReleased(MouseEvent e){
                gauche.setBackground(new Color(0,0,0,150));
                updateUI();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                gauche.setBackground(new Color(0,0,0,150));
                updateUI();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                gauche.setBackground(new Color(0,0,0,150));
                updateUI();
            }
        });
        top10.add(droite);
        top10.add(gauche);
        //on affiche les films top 10
        for(int i=0;i<10;i++){
            try{
                //on appelle sspgrm pour charger une image depuis URL
                BufferedImage image = chargerIm(listFilm.get(i).getUrlAffiche());
                listFilm.get(i).setAffiche(new JButton(resizeImage(image,120,170)));
                //JLabel affiche = new JLabel(resizeImage(image,120,170));
                if(i<5)
                    listFilm.get(i).getAffiche().setBounds(135 + i*220,10, 120, 170);
                else
                    listFilm.get(i).getAffiche().setBounds(135 + i*225,10, 120, 170);
                listFilm.get(i).getAffiche().addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //aficher le film
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e){
                        for(int i=0;i<listFilm.size();i++){
                            if(e.getSource() == listFilm.get(i).getAffiche()) {
                                listFilm.get(i).getAffiche().setSelected(false);
                            }
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //lorsqu'on entre sur l'image
                        for(int i=0;i<listFilm.size();i++){
                            if(e.getSource() == listFilm.get(i).getAffiche()) {
                                listFilm.get(i).getAffiche().setSelected(false);
                                try {
                                    System.out.println("debut try");
                                    BufferedImage image = chargerIm(listFilm.get(i).getUrlAfficheHor());
                                    System.out.println("bouton créer");
                                    listFilm.get(i).getAffiche().setIcon(resizeImage(image,250,200));
                                    if(i<2)
                                        listFilm.get(i).getAffiche().setBounds(90 + i*195,0, 250, 200);
                                    else if(i<5)
                                        listFilm.get(i).getAffiche().setBounds(140 + i*195,0, 250, 200);
                                    else
                                        listFilm.get(i).getAffiche().setBounds(130 + i*215,0, 250, 200);
                                    System.out.println("changement boutton");
                                    top10.updateUI();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        for(int i=0;i<listFilm.size();i++){
                            if(e.getSource() == listFilm.get(i).getAffiche()) {
                                listFilm.get(i).getAffiche().setSelected(false);
                                try {
                                    System.out.println("debut try");
                                    BufferedImage image = chargerIm(listFilm.get(i).getUrlAffiche());
                                    System.out.println("bouton créer");
                                    listFilm.get(i).getAffiche().setIcon(resizeImage(image,120,170));
                                    if(i<5)
                                        listFilm.get(i).getAffiche().setBounds(135 + i*220,10, 120, 170);
                                    else
                                        listFilm.get(i).getAffiche().setBounds(135 + i*225,10, 120, 170);
                                    System.out.println("changement boutton");
                                    top10.updateUI();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                });
                top10.add(listFilm.get(i).getAffiche());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        container.add(separateur,BorderLayout.CENTER);
        container.add(top10,BorderLayout.CENTER);
        //on définie les barres en noir
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        scrollPane.getHorizontalScrollBar();
        scrollPane.setBounds(0,0,1190,713);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbHighlightColor = new Color(0x8d8d8d);
                thumbLightShadowColor = new Color(0x8d8d8d);
                thumbDarkShadowColor = new Color(0x8d8d8d);
                thumbColor = new Color(20,20,20);
            }
        });
        scrollPane.setHorizontalScrollBar(null);
        add(scrollPane, BorderLayout.CENTER);
        updateUI();
    }


    public void Proprietaire()
    {

    }
    public void ChoixUtilisateurs() throws SQLException {
     /*   try {
            setImage("D:\\EXO JAVA\\NetflixECE-master\\NetflixECE-master\\ECENetflix\\src\\images/Screenshot_1.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        ArrayList<Utilisateur> utilisateur;
        utilisateur = compte.RecuperUtilisateurs();

        for(int i =0;i<utilisateur.size();i++)
        {
           System.out.println(   utilisateur.get( i ).getPseudo());
        }

        JPanel container = new JPanel(new GridBagLayout());
        container.setPreferredSize(new Dimension(600, 500));
        container.setBackground(new Color(0, 0, 0, 150));

        JLabel texte = new JLabel("Choisir un utilisateur");
        texte.setPreferredSize(new Dimension(400, 60));
        texte.setFont(new Font("Arial", Font.BOLD, 20));
        texte.setForeground(Color.WHITE);
        texte.setHorizontalAlignment(JLabel.CENTER);
        texte.setBounds(10,15,400,60);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        for (int i = 0; i < utilisateur.size(); i++) {
            Utilisateur u = utilisateur.get(i);
            JLabel pseudo = new JLabel(u.getPseudo(), u.getPhotoProfil(), JLabel.CENTER);
            pseudo.setVerticalTextPosition(JLabel.BOTTOM);
            pseudo.setHorizontalTextPosition(JLabel.CENTER);
            pseudo.setForeground(Color.WHITE);
            pseudo.setFont(new Font("Arial", Font.BOLD, 14));
            pseudo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    compte.UtilisateurChoisi(u);
                }
            });
            if (i % 3 == 0) {
                gbc.gridx = 0;
                gbc.gridy = i / 3;
            } else {
                gbc.gridx++;
            }
            container.add(pseudo, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        container.add(texte, gbc);
        updateUI();

    }


    public void CreaCompte(){
        //on met image en fond
        try {
            setImage("D:\\EXO JAVA\\NetflixECE-master\\NetflixECE-master\\ECENetflix\\src\\images/FondAcceuil.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }//container qui a une nouvelle couleur
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(450,500));
        container.setBackground(new Color(0,0,0,150));
        container.setLayout(new GridBagLayout());

        JLabel ident = new JLabel("Créer un Nouveau Compte");
        JLabel mail = new JLabel("Saisir une adresse mail");
        JLabel mdp = new JLabel("Saisir un mot de passe");
        ident.setFont(new Font("Arial", Font.BOLD,20));
        JLabel BadEmail = new JLabel(" ");
        JLabel BadMDP = new JLabel(" ");
        JButton bconfirm = new JButton("Valider");
        JButton bretour = new JButton("Retour");
        JTextField TextEmail = new JTextField("XXXXX@mail.com");
        JPasswordField TextMDP = new JPasswordField("X3skdj3qsZ@6NS&def1");
        TextMDP.setEchoChar((char)0);
        TextEmail.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //On efface le texte original
                if(TextEmail.isEditable()){
                    JTextField TextEmail =(JTextField) e.getSource();
                    TextEmail.setText("");
                    TextEmail.setForeground(Color.white);
                }
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e) {
                if(TextEmail.getText().length()==0){
                    TextEmail.setText("XXXXX@mail.com");
                }
            }
        });
        TextMDP.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //On efface le texte original
                if(TextMDP.isEditable()){
                    JTextField TextMDP =(JTextField) e.getSource();
                    TextMDP.setText("");
                    TextMDP.setForeground(Color.white);
                }
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e) {
                if(TextMDP.getText().length()==0){
                    TextMDP.setText("X3skdj3qsZ@6NS&def1");
                }
            }
        });
        bconfirm.getModel().addChangeListener(new ChangeListener() {
            //verif que le mail est valable
         /*   private boolean validateMail(String mail){
                //Regular Expression pour un mail
                //on verif que le mail est valide
                String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
                Pattern pattern = Pattern.compile(regx);
                System.out.println(mail);
                if(mail.equals("XXXXX@mail.com")){
                    return false;
                }else if(pattern.matcher(mail).matches()){
                    return true;//c'est bon
                }else{
                    return false;//pas bon
                }
            }
            private String validateMDP(String _mdp){
                //Verif mdp contient une minuscule, maj, cara special et chiffre
                int digit=0;
                int special=0;
                int upCount=0;
                int loCount=0;
                for(int i =0;i<_mdp.length();i++){
                    char c = _mdp.charAt(i);
                    if(Character.isUpperCase(c)){
                        upCount++;
                    }
                    if(Character.isLowerCase(c)){
                        loCount++;
                    }
                    if(Character.isDigit(c)){
                        digit++;
                    }
                    if(c>=33&&c<=46||c==64){
                        special++;
                    }
                }
                //il faut un caractere minuscule, un max, un special et un chiffre pour validation
                if(_mdp.equals("X3skdj3qsZ@6NS&def1")){
                    return "false";
                }else if(special>=1&&loCount>=1&&upCount>=1&&digit>=1){
                    return "true";
                }else{
                    if(loCount<1)
                        return "minuscule";
                    else if(upCount<1)
                        return "majuscule";
                    else if(digit<1)
                        return "numérique";
                    else
                        return "special";
                }
            }*/
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = bconfirm.getModel();
                //on appuie sur le bouton
                boolean compteCree = false;
                if (model.isArmed()) {
                    boolean mdpSaisie=false;
                    //Validation de l'email

                    System.out.println( TextEmail.getText() );
                    if(compte.NouveauMail(TextEmail.getText())){
                        //le mail est valide
                        BadEmail.setForeground(Color.green);
                        BadEmail.setText("L'email saisi est valide");
                        TextEmail.setEditable(false);
                        System.out.println(TextMDP.getText());
                        mdpSaisie=true;
                    }else if(!(TextEmail.getText().length()>0)){//si il n'y a rien dedans
                        TextEmail.setText("E-mail");
                        BadEmail.setText("Saisir un e-mail");//on affiche rien
                        BadMDP.setText(" ");
                    }else{
                        BadEmail.setForeground(Color.red);
                        BadEmail.setText("Saisir un email valide");
                        BadMDP.setText(" ");
                    }
                    //si un bon email a été entré
                    if(mdpSaisie==true){
                        String verif = compte.NouveauMDP(TextMDP.getText());
                        //Validation mdp
                        if(verif=="true"){
                            //le mail est valide
                            BadMDP.setForeground(Color.green);
                            BadMDP.setText("Le mot de passe est valide");
                            TextMDP.setEditable(false);
                            bconfirm.getModel().setPressed(false);
                            var=true;
                        }else if(verif.equals("false")){//si il n'y a rien dedans
                            TextMDP.setText("X3skdj3qsZ@6NS&def1");
                            BadMDP.setForeground(Color.red);
                            BadMDP.setText("Veuillez saisir un mot de passe");//on affiche rien

                        }else{
                            BadMDP.setForeground(Color.red);
                            BadMDP.setText("Saississez au moins un caractere "+ verif);
                        }
                    }
                    //pour actualiser notre panel
                    updateUI();
                } else {//relachement
                    if(var==true && !compteCree){
                        compteCree = true; // le compte a été créé
                        try {
                            compte.NouveauCompte( TextEmail.getText(),TextMDP.getText() );
                        } catch (SQLException ex) {
                            throw new RuntimeException( ex );
                        }
                        //pour laisser un temps avant de s connecter
                        try {
                            //on attend et on quitte
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        //TextEmail.getText()="";
                       // TextMDP.getText()="";
                        controleur.setAction("Choix Utilisateurs");
                    }
                }
            }
        });
        bretour.getModel().addChangeListener(new ChangeListener() {
            //bouton retour
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = bretour.getModel();
                //on appuie sur le bouton
                if (model.isArmed()) {
                    controleur.setAction("Acceuil");
                }
            }
        });
        //Couleur des textes
        ident.setForeground(Color.white);
        mail.setForeground(new Color(1,113,121));
        mdp.setForeground(new Color(1,113,121));
        BadEmail.setForeground(Color.red);
        BadEmail.setFont(new Font("SansSerif", Font.BOLD, 11));
        BadMDP.setForeground(Color.red);
        BadMDP.setFont(new Font("SansSerif", Font.BOLD, 11));
        //Bouton
        bconfirm.setBackground(new Color(1,113,121));
        bconfirm.setForeground(Color.white);
        bconfirm.setPreferredSize(new Dimension(250,35));
        bconfirm.setFocusPainted(false);
        bretour.setBackground(new Color(1,113,121));
        bretour.setForeground(Color.white);
        bretour.setPreferredSize(new Dimension(150,35));
        bretour.setFocusPainted(false);
        //taille textfield
        TextEmail.setPreferredSize(new Dimension(250,35));
        TextMDP.setPreferredSize(new Dimension(250,35));
        //couleur textField
        TextEmail.setForeground(Color.white);
        TextMDP.setForeground(Color.white);
        TextEmail.setBackground(Color.DARK_GRAY);
        TextMDP.setBackground(Color.DARK_GRAY);
        //Inset
        Insets textInsets = new Insets(10, 10, 5, 10);
        Insets buttonInsets = new Insets(20, 10, 10, 10);
        Insets errorInsets = new Insets(0,20,0,0);
        //positionnement S'identifier
        GridBagConstraints input = new GridBagConstraints();
        input.anchor = GridBagConstraints.CENTER;
        input.insets = textInsets;
        input.gridy = 0;
        container.add(ident,input);
        //positionnement Saisir email
        input.anchor = GridBagConstraints.WEST;
        input.insets = textInsets;
        input.gridy = 1;
        container.add(mail,input);
        //positionnement textEmail
        input.anchor = GridBagConstraints.CENTER;
        input.insets = textInsets;
        input.gridy = 2;
        container.add(TextEmail,input);
        //positionnement text mauvais email
        input.gridy = 3;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        container.add(BadEmail,input);
        //positionnement Saisir email
        input.anchor = GridBagConstraints.WEST;
        input.insets = textInsets;
        input.gridy = 4;
        container.add(mdp,input);
        //positionnement textMDP
        input.gridy = 5;
        input.insets = textInsets;
        input.anchor = GridBagConstraints.CENTER;
        container.add(TextMDP,input);
        //positionnement text mauvais mdp
        input.gridy = 6;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        container.add(BadMDP,input);
        //placement bouton
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.WEST;
        input.gridx = 0;
        input.gridy = 7;
        container.add(bconfirm,input);
        //bouton retour
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 0;
        input.gridy = 8;
        container.add(bretour,input);
        //pour placer le panel transparent (container) sur notre panel avec image en fond
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 2;
        input.gridy = 5;
        add(container,input);
        updateUI();
    }
    public void setController(FenetreControleur fn){
        this.controleur=fn;
    }

}
