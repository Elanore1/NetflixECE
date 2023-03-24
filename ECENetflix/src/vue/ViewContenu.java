package vue;

import controleur.RechercheInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ViewContenu extends JPanel {
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
    //on
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
            public void mouseExited(MouseEvent e) {}
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
            public void mouseExited(MouseEvent e) {}
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
                } else {//rlachement
                    if(var==true){
                        //pour laisser un temps avant de s connecter
                        try {
                            //on attend et on quitte
                            Thread.sleep(4000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
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
        //pour placer le panel transparent (container) sur notre panel avec image en fond
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 2;
        input.gridy = 5;
        add(container,input);
    }


    public void Acceuil(){

    }
}
