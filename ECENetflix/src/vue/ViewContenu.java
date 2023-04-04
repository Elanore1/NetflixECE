package vue;

import controleur.FenetreControleur;
import controleur.RechercheInfo;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ViewContenu extends JPanel{
    FenetreControleur controleur;
    Image img=null;
    //pour acceder au mot de passe et email de la Base
    RechercheInfo DAO = new RechercheInfo();
    public boolean var = false;

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
    //affichage d'un formulaire de connection
    public void FormConnection(){
        //on met image en fond
        try {
            setImage("src/images/FondAcceuil.png");
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
                System.out.println(text);
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
                //on appuie sur le bouton
                if (model.isArmed()) {
                    boolean mdpSaisie=false;
                    //Validation de l'email
                    if(validateMail(TextEmail.getText())){
                        //le mail est valide
                        BadEmail.setForeground(Color.green);
                        BadEmail.setText("Le compte associé a été trouvé");
                        TextEmail.setEditable(false);
                        System.out.println(TextMDP.getText());
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
                    if(var==true){
                        //pour laisser un temps avant de s connecter
                        try {
                            //on attend et on quitte
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
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
            setImage("src/images/FondAcceuil.png");
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
        revalidate();
    }

    public void Netflix(){
        //lorsqu'on a choisit l'utilisateur
    }
    public void ChoixUtilisateurs(){

    }

    public void CreaCompte(){
        //on met image en fond
        try {
            setImage("src/images/FondAcceuil.png");
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
            private boolean validateMail(String mail){
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
            }
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = bconfirm.getModel();
                //on appuie sur le bouton
                if (model.isArmed()) {
                    boolean mdpSaisie=false;
                    //Validation de l'email
                    if(validateMail(TextEmail.getText())){
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
                        String verif = validateMDP(TextMDP.getText());
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
                    if(var==true){
                        //pour laisser un temps avant de s connecter
                        try {
                            //on attend et on quitte
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
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
