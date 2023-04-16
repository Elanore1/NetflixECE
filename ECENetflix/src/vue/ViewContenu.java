package vue;

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

public class ViewContenu extends JPanel{
    FenetreControleur controleur;
    Image img=null;
    //pour acceder au mot de passe et email de la Base
    RechercheInfo DAO = new RechercheInfo();
    public boolean var = false;
    JPanel desc  = new JPanel(null);
    JPanel container = new JPanel(null);

    int indImg;

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
    //redimensioner taille de l'image
    public ImageIcon resizeImage(BufferedImage buffimage,int resizeWidth,int resizeHeight) throws IOException {
        Image image = buffimage.getScaledInstance(resizeWidth,resizeHeight, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(image);

    }
    //charger une image via un URL
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
    public JPanel description(Film film,int i){
        JPanel panel = new JPanel(null);
        //on appelle sspgrm pour charger une image depuis URL
        JLabel _titre = new JLabel(film.getTitre());
        JLabel duree = new JLabel(film.getAge()+"+  "+film.getDuree()+ "  HD ");
        JLabel genres = new JLabel(" ");
        JLabel reco = new JLabel("Recommandé à "+ film.getPercent()+"%");
        String temp = genres.getText();
        for(int j=0;j<film.getGenre().size();j++){
            temp += film.getGenre().get(j);
            if(j!=film.getGenre().size()-1) temp += " \u25cf ";
        }
        genres.setText(temp);
        _titre.setFont(new Font("Arial",Font.BOLD, 18));
        _titre.setForeground(Color.white);
        duree.setFont(new Font("Arial",Font.BOLD, 15));
        duree.setForeground(Color.white);
        genres.setFont(new Font("Arial",Font.BOLD, 15));
        genres.setForeground(Color.white);
        reco.setFont(new Font("Arial",Font.BOLD, 15));
        reco.setForeground(	new Color(0,153,0));
        panel.setBackground(new Color(20,20,20));
        panel.setForeground(Color.white);

        panel.setBorder(BorderFactory.createLineBorder(Color.black,2));
        _titre.setBounds(10,-80, 250, 210);
        reco.setBounds(10,-50, 250, 210);
        duree.setBounds(10,-25, 250, 210);
        genres.setBounds(10,0, 250, 210);
        if(i<2){
            panel.setBounds(90 + i*195,300, 250, 130);
        }else if(i<5){
            panel.setBounds(140 + i*195,300, 250, 130);
        }else{
            panel.setBounds(130 + i*215-1135,300, 250, 130);
        }
        panel.add(_titre);
        panel.add(reco);
        panel.add(duree);
        panel.add(genres);
        panel.revalidate();
        panel.repaint();
        panel.updateUI();
        return panel;
    }
    public JPanel descriptionBande(Film film,int i){
        JPanel panel = new JPanel(null);
        int percent = 50 + (int)(Math.random() * ((100 - 50) + 1));
        //on appelle sspgrm pour charger une image depuis URL
        JLabel _titre = new JLabel(film.getTitre());
        JLabel duree = new JLabel(film.getAge()+" + "+film.getDuree()+ "  HD ");
        JLabel genres = new JLabel(" ");
        JLabel reco = new JLabel("Recommandé à "+ film.getPercent()+"%");
        String temp = genres.getText();
        for(int j=0;j<film.getGenre().size();j++){
            temp += film.getGenre().get(j);
            if(j!=film.getGenre().size()-1) temp += " \u25cf ";
        }
        genres.setText(temp);
        _titre.setFont(new Font("Arial",Font.BOLD, 18));
        _titre.setForeground(Color.white);
        duree.setFont(new Font("Arial",Font.BOLD, 15));
        duree.setForeground(Color.white);
        genres.setFont(new Font("Arial",Font.BOLD, 15));
        genres.setForeground(Color.white);
        reco.setFont(new Font("Arial",Font.BOLD, 15));
        reco.setForeground(	new Color(0,153,0));
        panel.setBackground(new Color(20,20,20));
        panel.setForeground(Color.white);

        panel.setBorder(BorderFactory.createLineBorder(Color.black,2));
        _titre.setBounds(10,-80, 250, 210);
        reco.setBounds(10,-50, 250, 210);
        duree.setBounds(10,-25, 250, 210);
        genres.setBounds(10,0, 250, 210);
        if(i==0)
            panel.setBounds(0 + i*220,215, 240, 120);
        else if(i<2)
            panel.setBounds(0 + i*220,215, 250, 120);
        else if(i==2)
            panel.setBounds(10 + i*220,215, 250, 120);
        else if(i==3)
            panel.setBounds(20 + i*220,215, 250, 120);
        else
            panel.setBounds(35 + i*220,215, 250, 120);

        panel.add(_titre);
        panel.add(reco);
        panel.add(duree);
        panel.add(genres);
        panel.revalidate();
        panel.repaint();
        panel.updateUI();
        return panel;
    }
    public JPanel bande(ArrayList<Film> films,String titre,int k){
        int bouton;//nombre de boutons max 5
        ArrayList<JButton> Maliste = new ArrayList<JButton>();
        JPanel bande = new JPanel(null);
        JLabel Titre = new JLabel(titre);
        Titre.setFont(new Font("Arial",Font.BOLD, 25));
        Titre.setForeground(Color.white);
        Titre.setBounds(60,0,400,60);
        bande.setBackground(new Color(20,20,20));
        JButton droite = new JButton(" > ");
        JButton gauche = new JButton(" < ");
        droite.setBackground(new Color(0,0,0,150));
        droite.setForeground(Color.white);
        droite.setBounds(1121,55,50,200);
        droite.setEnabled(false);
        gauche.setBackground(new Color(0,0,0,150));
        gauche.setForeground(Color.white);
        gauche.setBounds(0,55,50,200);
        gauche.setEnabled(false);
        droite.setVisible(true);
        gauche.setVisible(true);
        MouseListener d = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){
                //on affiche tous les 5 films
                int temp=0;
                for(int i=controleur.getBDD().getMalisteInt().get(k);i<controleur.getBDD().getMalisteInt().get(k)+5;i++){
                    System.out.println("i :"+i);
                    try {
                        if(i >= films.size()){
                            BufferedImage image = null;
                            image = chargerIm(films.get(i%films.size()).getUrlAfficheHor());
                            Maliste.get(temp).setIcon(resizeImage(image,200,160));
                        }else{
                            BufferedImage image = null;
                            image = chargerIm(films.get(i).getUrlAfficheHor());
                            Maliste.get(temp).setIcon(resizeImage(image,200,160));
                        }
                        temp+=1;
                        if(i == controleur.getBDD().getMalisteInt().get(k)+4){
                            controleur.getBDD().getMalisteInt().set(k,controleur.getBDD().getMalisteInt().get(k)+5);
                            controleur.getBDD().getMalisteInt().set(k,controleur.getBDD().getMalisteInt().get(k)% films.size());
                            break;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
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
        };
        droite.addMouseListener(d);
        MouseListener g =new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){
                //on affiche tous les 5 films*
                int temp=0;
                for(int i=controleur.getBDD().getMalisteInt().get(k)-10;i<controleur.getBDD().getMalisteInt().get(k)-5;i++){
                    try {
                        if(i < 0){
                            System.out.println("i<0"+i);
                            BufferedImage image = null;
                            image = chargerIm(films.get((films.size()+i)).getUrlAfficheHor());
                            Maliste.get(temp).setIcon(resizeImage(image,200,160));
                        }else{
                            System.out.println("else"+i);
                            BufferedImage image = null;
                            image = chargerIm(films.get(i).getUrlAfficheHor());
                            Maliste.get(temp).setIcon(resizeImage(image,200,160));
                        }
                        temp+=1;
                        if(i == (controleur.getBDD().getMalisteInt().get(k)-6)){
                            controleur.getBDD().getMalisteInt().set(k,controleur.getBDD().getMalisteInt().get(k)-5);
                            if(controleur.getBDD().getMalisteInt().get(k) < 0){
                                controleur.getBDD().getMalisteInt().set(k,controleur.getBDD().getMalisteInt().get(k)+films.size());
                            }else{
                                controleur.getBDD().getMalisteInt().set(k,controleur.getBDD().getMalisteInt().get(k)%films.size());
                            }
                            System.out.println("maliste"+controleur.getBDD().getMalisteInt().get(k));
                            break;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
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
        };
        gauche.addMouseListener(g);
        bande.add(droite);
        bande.add(gauche);
        try{
            if(films.size()>5)
                bouton = 5;
            else
                bouton = films.size();
            if(bouton!=5){
                droite.removeMouseListener(d);
                gauche.removeMouseListener(g);
            }
            for(int i=0;i<bouton;i++) {
                Maliste.add(maListe(films.get(i), i));
                Maliste.get(i).setSelected(false);
                Maliste.get(i).addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //voir film
                        for (int i = 0; i < bouton; i++) {
                            if (e.getSource() == Maliste.get(i)){
                                Maliste.get(i).setSelected(false);
                                if(controleur.getBDD().getMalisteInt().get(k) == 0){
                                    controleur.getBDD().getMalisteInt().set(k,films.size());
                                }
                                if(controleur.getBDD().getMalisteInt().get(k)<5 && (films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i<films.size())){
                                    System.out.println("film n°"+(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i));
                                    //Cas Maliste
                                    if(k==0){
                                        //avec controleur compte
                                        controleur.setFilm(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().get(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i));
                                        controleur.setAction("Film");
                                    }else if(k==1){//cas Film
                                        controleur.setFilm(controleur.getBDD().getFilms().get(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i));
                                        controleur.setAction("Film");
                                    }else if(k==2){//cas serie
                                        controleur.setFilm(controleur.getBDD().getSerie().get(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i));
                                        controleur.setAction("Film");
                                    }else if(k==3){//cas nouveauté
                                        controleur.setFilm(controleur.getBDD().getNouveaute().get(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i));
                                        controleur.setAction("Film");
                                    }else if(k==4){//cas anime
                                        controleur.setFilm(controleur.getBDD().getAnime().get(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i));
                                        controleur.setAction("Film");
                                    }
                                }else if(controleur.getBDD().getMalisteInt().get(k)-5+i>=0){
                                    System.out.println("film n°"+(controleur.getBDD().getMalisteInt().get(k)-5+i));
                                    if(k==0){
                                        //avec controleur compte
                                        controleur.setFilm(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().get(controleur.getBDD().getMalisteInt().get(k)-5+i));
                                        controleur.setAction("Film");
                                    }else if(k==1){//cas Film
                                        controleur.setFilm(controleur.getBDD().getFilms().get(controleur.getBDD().getMalisteInt().get(k)-5+i));
                                        controleur.setAction("Film");
                                    }else if(k==2){//cas serie
                                        controleur.setFilm(controleur.getBDD().getSerie().get(controleur.getBDD().getMalisteInt().get(k)-5+i));
                                        controleur.setAction("Film");
                                    }else if(k==3){//cas nouveauté
                                        controleur.setFilm(controleur.getBDD().getNouveaute().get(controleur.getBDD().getMalisteInt().get(k)-5+i));
                                        controleur.setAction("Film");
                                    }else if(k==4){//cas anime
                                        controleur.setFilm(controleur.getBDD().getAnime().get(controleur.getBDD().getMalisteInt().get(k)-5+i));
                                        controleur.setAction("Film");
                                    }
                                }else{
                                    System.out.println("film n°"+(controleur.getBDD().getMalisteInt().get(k)+5+i));
                                    if(k==0){
                                        //avec controleur compte
                                        controleur.setFilm(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().get(controleur.getBDD().getMalisteInt().get(k)+5+i));
                                        controleur.setAction("Film");
                                    }else if(k==1){//cas Film
                                        controleur.setFilm(controleur.getBDD().getFilms().get(controleur.getBDD().getMalisteInt().get(k)+5+i));
                                        controleur.setAction("Film");
                                    }else if(k==2){//cas serie
                                        controleur.setFilm(controleur.getBDD().getSerie().get(controleur.getBDD().getMalisteInt().get(k)+5+i));
                                        controleur.setAction("Film");
                                    }else if(k==3){//cas nouveauté
                                        controleur.setFilm(controleur.getBDD().getNouveaute().get(controleur.getBDD().getMalisteInt().get(k)+5+i));
                                        controleur.setAction("Film");
                                    }else if(k==4){//cas anime
                                        controleur.setFilm(controleur.getBDD().getAnime().get(controleur.getBDD().getMalisteInt().get(k)+5+i));
                                        controleur.setAction("Film");
                                    }
                                }
                                bande.updateUI();
                            }
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        for (int i = 0; i < bouton; i++) {
                            if (e.getSource() == Maliste.get(i)){
                                Maliste.get(i).setSelected(false);
                                bande.updateUI();
                            }
                        }
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        for (int i = 0; i < bouton; i++) {
                            if (e.getSource() == Maliste.get(i)){
                                Maliste.get(i).setSelected(false);
                                bande.updateUI();
                            }
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //lorsqu'on entre sur l'image
                        for (int i = 0; i < bouton; i++) {
                            if (e.getSource() == Maliste.get(i)){
                                Maliste.get(i).setSelected(false);
                                if(controleur.getBDD().getMalisteInt().get(k)==0)
                                    controleur.getBDD().getMalisteInt().set(k,films.size());
                                if(controleur.getBDD().getMalisteInt().get(k)<5 && (films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i<films.size())){
                                    desc = descriptionBande(films.get((films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i)),i);
                                    try {
                                        BufferedImage image = chargerIm(films.get(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i).getUrlAfficheHor());
                                        Maliste.get(i).setIcon(resizeImage(image,250,200));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }else if(controleur.getBDD().getMalisteInt().get(k)-5+i>=0){
                                    desc = descriptionBande(films.get((controleur.getBDD().getMalisteInt().get(k)-5+i)),i);
                                    try {
                                        BufferedImage image = chargerIm(films.get(controleur.getBDD().getMalisteInt().get(k)-5+i).getUrlAfficheHor());
                                        Maliste.get(i).setIcon(resizeImage(image,250,200));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }else{
                                    desc = descriptionBande(films.get((controleur.getBDD().getMalisteInt().get(k)+5+i)),i);
                                    try {
                                        BufferedImage image = chargerIm(films.get(controleur.getBDD().getMalisteInt().get(k)+5+i).getUrlAfficheHor());
                                        Maliste.get(i).setIcon(resizeImage(image,250,200));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                                if(i==0)
                                    Maliste.get(i).setBounds(0 + i*220,15, 240, 200);
                                else if(i<2)
                                    Maliste.get(i).setBounds(0 + i*220,15, 250, 200);
                                else if(i==2)
                                    Maliste.get(i).setBounds(10 + i*220,15, 250, 200);
                                else if(i==3)
                                    Maliste.get(i).setBounds(20 + i*220,15, 250, 200);
                                else
                                    Maliste.get(i).setBounds(35 + i*220,15, 250, 200);

                                bande.add(desc);
                                bande.updateUI();
                                updateUI();
                            }
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        for (int i = 0; i < bouton; i++) {
                            if (e.getSource() == Maliste.get(i)){
                                Maliste.get(i).setSelected(false);
                                if(controleur.getBDD().getMalisteInt().get(k)==0)
                                    controleur.getBDD().getMalisteInt().set(k,films.size());
                                if(controleur.getBDD().getMalisteInt().get(k)<5 && (films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i<films.size())){
                                    try {
                                        BufferedImage image = chargerIm(films.get(films.size()-5+controleur.getBDD().getMalisteInt().get(k)+i).getUrlAfficheHor());
                                        Maliste.get(i).setIcon(resizeImage(image,200,160));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }else if(controleur.getBDD().getMalisteInt().get(k)-5+i>=0){
                                    try {
                                        BufferedImage image = chargerIm(films.get(controleur.getBDD().getMalisteInt().get(k)-5+i).getUrlAfficheHor());
                                        Maliste.get(i).setIcon(resizeImage(image,200,160));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }else{
                                    try {
                                        BufferedImage image = chargerIm(films.get(controleur.getBDD().getMalisteInt().get(k)+5+i).getUrlAfficheHor());
                                        Maliste.get(i).setIcon(resizeImage(image,200,160));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                                Maliste.get(i).setBounds(15 + i*230,75, 200, 160);
                                bande.remove(desc);
                                bande.updateUI();
                                updateUI();
                            }
                        }
                    }
                });
                bande.add(Maliste.get(i));
            }
            bande.add(Titre);
        }catch(IOException e){
            e.printStackTrace();
        }
        bande.updateUI();
        return bande;
    }
    public JButton maListe(Film film,int i) throws IOException {
        BufferedImage image = chargerIm(film.getUrlAfficheHor());
        JButton affiche = new JButton(resizeImage(image,200,160));
        affiche.setBounds(15 + i*230,75, 200, 160);
        return affiche;
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

            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = bconfirm.getModel();
                //on appuie sur le bouton
                if (model.isArmed()) {
                    boolean mdpSaisie=false;
                    //Validation de l'email
                    if(controleur.getControleurCompte().validateMail(TextEmail.getText())){
                        //le mail est valide
                        BadEmail.setForeground(Color.green);
                        BadEmail.setText("Le compte associé a été trouvé");
                        TextEmail.setEditable(false);
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
                        if(controleur.getControleurCompte().validateMDP(TextEmail.getText(),TextMDP.getText())){
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
                        controleur.getControleurCompte().setCompte(TextEmail.getText(),TextMDP.getText());
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
    }
    public void Netflix(){
        container.removeAll();
        container.setLayout(null);
        desc.removeAll();
        //Appli Netflix
        //On set les k a 5  au debut
        for(int i=0;i<5;i++){
            controleur.getBDD().setMalisteInt(i,5);
        }
        container.setBackground(new Color(20,20,20));
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
        JPanel b1 = bande(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe(),"Ma Liste",0);
        JPanel b2 = bande(controleur.getBDD().getFilms(),"Films",1);
        JPanel b3 = bande(controleur.getBDD().getSerie(),"Séries",2);
        JPanel b4 = bande(controleur.getBDD().getNouveaute(),"Nouveautés",3);
        JPanel b5 = bande(controleur.getBDD().getAnime(),"Animés",4);
        //Si Maliste de l'utilisateur est vide
        if(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().size()==0){
            container.setSize(2050,1750);
            container.setPreferredSize(container.getSize());
            b2.setBounds(0,330,1200,340);
            b3.setBounds(0,665,1200,340);
            b4.setBounds(0,1000,1200,340);
            b5.setBounds(0,1335,1200,340);
        }else{
            container.setSize(2050,2050);
            container.setPreferredSize(container.getSize());
            b1.setBounds(0,330,1200,340);
            b2.setBounds(0,665,1200,340);
            b3.setBounds(0,1000,1200,340);
            b4.setBounds(0,1335,1200,340);
            b5.setBounds(0,1670,1200,340);
        }
        droite.addMouseListener(new MouseAdapter() {
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
        });
        gauche.addMouseListener(new MouseAdapter() {
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
        });
        top10.add(droite);
        top10.add(gauche);
        //on affiche les films top 10
        for(int i=0;i<controleur.getBDD().getTop().size();i++){
            try{
                BufferedImage image = chargerIm(controleur.getBDD().getTop().get(i).getUrlAffiche());
                controleur.getBDD().getTop().get(i).setAffiche(new JButton(resizeImage(image,120,170)));
                if(i<5){
                    controleur.getBDD().getTop().get(i).getAffiche().setBounds(135 + i*220,10, 120, 170);
                }else{
                    controleur.getBDD().getTop().get(i).getAffiche().setBounds(135 + i*225,10, 120, 170);
                }
                controleur.getBDD().getTop().get(i).getAffiche().addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //aficher le film
                        for(int i=0;i<controleur.getBDD().getTop().size();i++){
                            if(e.getSource() == controleur.getBDD().getTop().get(i).getAffiche()) {
                                System.out.println("film"+controleur.getBDD().getTop().get(i).getTitre());
                                controleur.getBDD().getTop().get(i).getAffiche().setSelected(false);
                                try {
                                    container.remove(desc);
                                    container.repaint();
                                    container.revalidate();
                                    container.updateUI();
                                    BufferedImage image = chargerIm(controleur.getBDD().getTop().get(i).getUrlAffiche());
                                    controleur.getBDD().getTop().get(i).getAffiche().setIcon(resizeImage(image,120,170));
                                    if(i<5)
                                        controleur.getBDD().getTop().get(i).getAffiche().setBounds(135 + i*220,10, 120, 170);
                                    else
                                        controleur.getBDD().getTop().get(i).getAffiche().setBounds(135 + i*225,10, 120, 170);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                //On transmet au controlleur le film et l'action
                                removeAll();
                                controleur.setFilm(controleur.getBDD().getTop().get(i));//on met le film
                                controleur.setAction("Film");//transmission au controlleur
                            }
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        for(int i=0;i<controleur.getBDD().getTop().size();i++){
                            if(e.getSource() == controleur.getBDD().getTop().get(i).getAffiche()) {
                                controleur.getBDD().getTop().get(i).getAffiche().setSelected(false);
                            }
                        }
                    }
                    @Override
                    public void mouseReleased(MouseEvent e){
                        for(int i=0;i<controleur.getBDD().getTop().size();i++){
                            if(e.getSource() == controleur.getBDD().getTop().get(i).getAffiche()) {
                                controleur.getBDD().getTop().get(i).getAffiche().setSelected(false);
                            }
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //lorsqu'on entre sur l'image
                        for(int i=0;i<controleur.getBDD().getTop().size();i++){
                            if(e.getSource() == controleur.getBDD().getTop().get(i).getAffiche()) {
                                controleur.getBDD().getTop().get(i).getAffiche().setSelected(false);
                                try {
                                    BufferedImage image = chargerIm(controleur.getBDD().getTop().get(i).getUrlAfficheHor());
                                    controleur.getBDD().getTop().get(i).getAffiche().setIcon(resizeImage(image,250,200));
                                    if(i<2){
                                        controleur.getBDD().getTop().get(i).getAffiche().setBounds(90 + i*195,0, 250, 200);

                                    }else if(i<5){
                                        controleur.getBDD().getTop().get(i).getAffiche().setBounds(140 + i*195,0, 250, 200);

                                    }else{
                                        controleur.getBDD().getTop().get(i).getAffiche().setBounds(130 + i*215,0, 250, 200);
                                    }
                                } catch (IOException ex){
                                    throw new RuntimeException(ex);
                                }
                                desc = description(controleur.getBDD().getTop().get(i),i);

                                if(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().size()==0)
                                    container.remove(b2);
                                else
                                    container.remove(b1);
                                container.add(desc);
                                if(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().size()==0)
                                    container.add(b2);
                                else
                                    container.add(b1);
                                container.repaint();
                                container.revalidate();
                                container.updateUI();
                                top10.add(controleur.getBDD().getTop().get(i).getAffiche());
                            }

                        }

                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        for(int i=0;i<controleur.getBDD().getTop().size();i++){
                            if(e.getSource() == controleur.getBDD().getTop().get(i).getAffiche()) {
                                controleur.getBDD().getTop().get(i).getAffiche().setSelected(false);
                                try {
                                    container.remove(desc);
                                    container.repaint();
                                    container.revalidate();
                                    container.updateUI();
                                    BufferedImage image = chargerIm(controleur.getBDD().getTop().get(i).getUrlAffiche());
                                    controleur.getBDD().getTop().get(i).getAffiche().setIcon(resizeImage(image,120,170));
                                    if(i<5)
                                        controleur.getBDD().getTop().get(i).getAffiche().setBounds(135 + i*220,10, 120, 170);
                                    else
                                        controleur.getBDD().getTop().get(i).getAffiche().setBounds(135 + i*225,10, 120, 170);
                                    top10.updateUI();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                });
                top10.add(controleur.getBDD().getTop().get(i).getAffiche());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        container.add(separateur,BorderLayout.CENTER);
        container.add(top10,BorderLayout.CENTER);
        container.add(b5);
        container.add(b4);
        container.add(b3);
        container.add(b2);
        if(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().size()>0)
            container.add(b1);
        //on définie les barres en noir
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
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
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBar(null);
        add(scrollPane, BorderLayout.CENTER);
        updateUI();
        setVisible(true);
    }
    public void ChoixUtilisateurs() throws SQLException {
        container.removeAll();
        //on apelle le ss programme qui recup utilisateur dans BDD
        controleur.getControleurCompte().RecuperUtilisateurs();
        MouseAdapter test;
        test = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controleur.setAction("Ajouter Utilisateur");
            }
        };
        container.setBackground(new Color(20,20,20));
        JLabel titre = new JLabel("Qui est-ce ?",JLabel.CENTER);
        titre.setFont(new Font("Arial",Font.BOLD, 30));
        titre.setForeground(Color.white);
        titre.setBounds(490,170,200,40);
        for(int i=0;i<controleur.getControleurCompte().getCompte().getUtilisateurs().size();i++){
            if(controleur.getControleurCompte().getCompte().getUtilisateurs().size()==3){
                JButton icone = new JButton(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                icone.setBounds(150+250*i,250,150,150);
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                pseudo.setBounds(160+250*i,360,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        ArrayList<String> lst;
                        try {
                            lst = controleur.getDAO().RecupererListe(controleur.getControleurCompte().getCompte().getEmail(),controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).setMaListe(controleur.remplirFilm(lst));
                        controleur.setAction("Netflix");
                        System.out.println("Utilisateur"+finalI);
                    }
                });
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size() - 1){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.setBounds(150+250*(i+1),250,150,150);
                    add.addMouseListener(test);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(150+250*(i+1),360,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }else if((controleur.getControleurCompte().getCompte().getUtilisateurs().size() == 4) || (controleur.getControleurCompte().getCompte().getUtilisateurs().size() == 5)){
                JButton icone = new JButton(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)));
                icone.setBounds(90+210*i,250,120,120);
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                pseudo.setBounds(92+210*i,340,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        ArrayList<String> lst;
                        try {
                            lst = controleur.getDAO().RecupererListe(controleur.getControleurCompte().getCompte().getEmail(),controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).setMaListe(controleur.remplirFilm(lst));
                        controleur.setAction("Netflix");
                        System.out.println("Utilisateur"+finalI);
                    }
                });
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size()-1 && controleur.getControleurCompte().getCompte().getUtilisateurs().size() == 4){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.setBounds(90+210*(i+1),245,150,150);
                    add.addMouseListener(test);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(90+210*(i+1),340,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }else if(controleur.getControleurCompte().getCompte().getUtilisateurs().size()==2){
                JButton icone = new JButton(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                icone.setBounds(260+250*i,250,150,150);
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        ArrayList<String> lst;
                        try {
                            lst = controleur.getDAO().RecupererListe(controleur.getControleurCompte().getCompte().getEmail(),controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).setMaListe(controleur.remplirFilm(lst));
                        controleur.setAction("Netflix");
                        System.out.println("Utilisateur"+finalI);
                    }
                });
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                pseudo.setBounds(270+250*i,360,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size() - 1){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.addMouseListener(test);
                    add.setBounds(260+250*(i+1),250,150,150);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(260+250*(i+1),360,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }else{
                JButton icone = new JButton(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                icone.setBounds(360+250*i,250,150,150);
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        ArrayList<String> lst;
                        try {
                            lst = controleur.getDAO().RecupererListe(controleur.getControleurCompte().getCompte().getEmail(),controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).setMaListe(controleur.remplirFilm(lst));
                        controleur.setAction("Netflix");
                        System.out.println("Utilisateur"+ finalI);
                    }
                });
                pseudo.setBounds(370+250*i,360,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size() - 1){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.addMouseListener(test);
                    add.setBounds(360+250*(i+1),250,150,150);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(360+250*(i+1),360,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }
        }
        JButton manageCompte = new JButton("Gérer les profils");
        manageCompte.setBounds(490,560,200,70);
        manageCompte.setBackground(new Color(1,113,121));
        manageCompte.setForeground(Color.white);
        manageCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.setAction("Gestion Profil");
            }
        });
        container.add(manageCompte);
        container.add(titre);
        container.setSize(1200,800);
        container.setPreferredSize(container.getPreferredSize());
        add(container,BorderLayout.CENTER);
        updateUI();
        setVisible(true);
    }
    public void CreaCompte() {
        ///creation d'un compte
        //on met image en fond
        try {
            setImage("src/images/FondAcceuil.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }//container qui a une nouvelle couleur
        JPanel contener = new JPanel();
        contener.setPreferredSize(new Dimension(450,500));
        contener.setBackground(new Color(0,0,0,150));
        contener.setLayout(new GridBagLayout());
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
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = bconfirm.getModel();
                //on appuie sur le bouton
                if (model.isArmed()) {
                    boolean mdpSaisie=false;
                    //Validation de l'email
                    if(controleur.getControleurCompte().NouveauMail(TextEmail.getText())){
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
                        String verif = controleur.getControleurCompte().NouveauMDP(TextMDP.getText());
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
                }else{//relachement
                    if(var==true){
                        try {
                            controleur.getControleurCompte().NouveauCompte(TextEmail.getText(),TextMDP.getText());
                        }catch (SQLException ex) {
                            throw new RuntimeException( ex );
                        }
                        controleur.setAction("Choix Utilisateurs");
                        var=false;
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
        contener.add(ident,input);
        //positionnement Saisir email
        input.anchor = GridBagConstraints.WEST;
        input.insets = textInsets;
        input.gridy = 1;
        contener.add(mail,input);
        //positionnement textEmail
        input.anchor = GridBagConstraints.CENTER;
        input.insets = textInsets;
        input.gridy = 2;
        contener.add(TextEmail,input);
        //positionnement text mauvais email
        input.gridy = 3;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        contener.add(BadEmail,input);
        //positionnement Saisir email
        input.anchor = GridBagConstraints.WEST;
        input.insets = textInsets;
        input.gridy = 4;
        contener.add(mdp,input);
        //positionnement textMDP
        input.gridy = 5;
        input.insets = textInsets;
        input.anchor = GridBagConstraints.CENTER;
        contener.add(TextMDP,input);
        //positionnement text mauvais mdp
        input.gridy = 6;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        contener.add(BadMDP,input);
        //placement bouton
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.WEST;
        input.gridx = 0;
        input.gridy = 7;
        contener.add(bconfirm,input);
        //bouton retour
        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 0;
        input.gridy = 8;
        contener.add(bretour,input);
        //pour placer le panel transparent (container) sur notre panel avec image en fond
        input.anchor = GridBagConstraints.CENTER;
        input.gridx = 2;
        input.gridy = 5;
        add(contener,input);
        updateUI();
    }
    public void filmView(Film voir){
        boolean InListe=false;
        JPanel film = new JPanel(null);
        JLabel note  = new JLabel("Votre Avis :");
        JButton liste = new JButton("Ajouter a ma Liste");
        for(int i=0;i<controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().size();i++){
            if(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().get(i).getTitre().equals(voir.getTitre())){
                InListe = true;
            }
        }
        if(InListe==true){
            liste.setText("Retirer de ma Liste");
        }
        JLabel desc = new JLabel("Description :");
        JLabel act_ = new JLabel("Acteurs :");
        JLabel real_ = new JLabel("Realisateur :");
        JLabel real = new JLabel(voir.getRealisateur());
        JLabel reco = new JLabel("Recommandé à "+voir.getPercent()+"%");
        reco.setFont(new Font("Arial",Font.BOLD, 15));
        reco.setForeground(	new Color(0,153,0));
        JTextArea desc_ = new JTextArea(voir.getDescription().replaceAll(";",","));
        JTextArea title = new JTextArea(voir.getTitre());
        String temp="-";//recevoir la liste d'acteurs
        String genre ="";
        for(int i=0;i<voir.getActeurs().size();i++){
            temp+=voir.getActeurs().get(i);
            if(i != voir.getActeurs().size()-1)
                temp+="\n-";
        }
        for(int i=0;i<voir.getGenre().size();i++){
            genre+=voir.getGenre().get(i);
            genre+=" ";
        }
        JLabel info = new JLabel("Genre : "+genre +" durée : "+ voir.getDuree()+"  "+voir.getAge()+"+ ");
        JTextArea act = new JTextArea(temp);
        desc_.setForeground(Color.white);
        act.setForeground(Color.white);
        note.setForeground(Color.white);
        note.setFont(new Font("Arial",Font.BOLD, 18));
        act_.setFont(new Font("Arial",Font.BOLD, 25));
        real_.setFont(new Font("Arial",Font.BOLD, 25));
        desc_.setFont(new Font("Arial",Font.PLAIN, 15));
        real.setFont(new Font("Arial",Font.PLAIN, 15));
        act.setFont(new Font("Arial",Font.PLAIN, 15));
        title.setFont(new Font("Arial",Font.BOLD, 25));
        info.setFont(new Font("Arial",Font.PLAIN, 15));
        title.setForeground(Color.white);
        act_.setForeground(Color.white);
        real_.setForeground(Color.white);
        real.setForeground(Color.white);
        info.setForeground(Color.white);
        title.setEditable(false);
        title.setCursor(null);
        title.setOpaque(false);
        title.setFocusable(false);
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        title.setForeground(Color.white);
        desc_.setEditable(false);
        desc_.setCursor(null);
        desc_.setOpaque(false);
        desc_.setFocusable(false);
        desc_.setLineWrap(true);
        desc_.setWrapStyleWord(true);
        act.setEditable(false);
        act.setCursor(null);
        act.setOpaque(false);
        act.setFocusable(false);
        act.setLineWrap(true);
        act.setWrapStyleWord(true);
        desc_.setBounds(800,165,370,500);
        if(voir.getDescription().length()<300){
            act.setBounds(800,325,370,500);
            act_.setBounds(800,275,370,50);
            real_.setBounds(800,325+voir.getActeurs().size()*17,370,50);
            real.setBounds(950,325+voir.getActeurs().size()*17,370,50);
            info.setBounds(800,375 +voir.getActeurs().size()*17,370,25);
        }else if(voir.getDescription().length()<450){
            act.setBounds(800,370,370,500);
            act_.setBounds(800,325,370,50);
            real_.setBounds(800,370+voir.getActeurs().size()*17,370,50);
            real.setBounds(950,370+voir.getActeurs().size()*17,370,50);
            info.setBounds(800,415 +voir.getActeurs().size()*17,370,25);
        }else{
            act.setBounds(800,390,370,500);
            act_.setBounds(800,345,370,50);
            real_.setBounds(800,390+voir.getActeurs().size()*17,370,50);
            real.setBounds(950,390+voir.getActeurs().size()*17,370,50);
            info.setBounds(800,435 +voir.getActeurs().size()*17,370,25);
        }
        desc.setFont(new Font("Arial",Font.BOLD, 25));
        desc.setForeground(Color.white);
        desc.setBounds(800,115,400,60);
        liste.setBounds(80,580,160,20);
        note.setBounds(330,570,160,40);
        //Pour donner une note au film
        JCheckBox c1 = new JCheckBox("", new ImageIcon("src/images/etoileVide.png"), false);
        JCheckBox c2 = new JCheckBox("", new ImageIcon("src/images/etoileVide.png"), false);
        JCheckBox c3 = new JCheckBox("", new ImageIcon("src/images/etoileVide.png"), false);
        JCheckBox c4 = new JCheckBox("", new ImageIcon("src/images/etoileVide.png"), false);
        JCheckBox c5 = new JCheckBox("", new ImageIcon("src/images/etoileVide.png"), false);
        c1.setSelectedIcon(new ImageIcon("src/images/etoilePleine.png"));
        c2.setSelectedIcon(new ImageIcon("src/images/etoilePleine.png"));
        c3.setSelectedIcon(new ImageIcon("src/images/etoilePleine.png"));
        c4.setSelectedIcon(new ImageIcon("src/images/etoilePleine.png"));
        c5.setSelectedIcon(new ImageIcon("src/images/etoilePleine.png"));
        c5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c5.isSelected()){
                    c4.setSelected(true);
                    c3.setSelected(true);
                    c2.setSelected(true);
                    c1.setSelected(true);
                }else{
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(false);
                    c2.setSelected(false);
                    c1.setSelected(false);
                }
            }
        });
        c4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c4.isSelected() && !c5.isSelected()){
                    c3.setSelected(true);
                    c2.setSelected(true);
                    c1.setSelected(true);
                }else if(c5.isSelected()){
                    c5.setSelected(false);
                    c4.setSelected(true);
                    c3.setSelected(true);
                    c2.setSelected(true);
                    c1.setSelected(true);
                }else{
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(false);
                    c2.setSelected(false);
                    c1.setSelected(false);
                }
            }
        });
        c3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c3.isSelected() && !c5.isSelected() && !c4.isSelected()){
                    c2.setSelected(true);
                    c1.setSelected(true);
                }else if(c4.isSelected() || c5.isSelected()){
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(true);
                    c2.setSelected(true);
                    c1.setSelected(true);
                }else{
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(false);
                    c2.setSelected(false);
                    c1.setSelected(false);
                }
            }
        });
        c2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c2.isSelected() && !c5.isSelected() && !c4.isSelected() && !c3.isSelected()){
                    c1.setSelected(true);
                }else if(c4.isSelected() || c5.isSelected() || c3.isSelected()) {
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(false);
                    c2.setSelected(true);
                    c1.setSelected(true);
                }else{
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(false);
                    c2.setSelected(false);
                    c1.setSelected(false);
                }
            }
        });
        c1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c1.isSelected() && !c5.isSelected() && !c4.isSelected() && !c3.isSelected()&& !c2.isSelected()){
                    c1.setSelected(true);
                }else if(c4.isSelected() || c5.isSelected() || c3.isSelected()|| c2.isSelected()) {
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(false);
                    c2.setSelected(false);
                    c1.setSelected(true);
                }else{
                    c5.setSelected(false);
                    c4.setSelected(false);
                    c3.setSelected(false);
                    c2.setSelected(false);
                    c1.setSelected(false);
                }
            }
        });
        c1.setBounds(435,560,60,60);
        c2.setBounds(495,560,60,60);
        c3.setBounds(555,560,60,60);
        c4.setBounds(615,560,60,60);
        c5.setBounds(675,560,60,60);
        c1.setBackground(new Color(20,20,20));
        c2.setBackground(new Color(20,20,20));
        c3.setBackground(new Color(20,20,20));
        c4.setBackground(new Color(20,20,20));
        c5.setBackground(new Color(20,20,20));
        liste.setBackground(new Color(1,113,121));
        liste.setForeground(Color.white);
        liste.setPreferredSize(new Dimension(250,35));
        liste.setFocusPainted(false);
        liste.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                boolean Dansliste=false;
                int placement = -1;
                //On ajoute le film dans la liste si il n'est pas déja dedans
                for(int i=0;i<controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().size();i++){
                    if(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().get(i).getTitre().equals(voir.getTitre())){
                        Dansliste = true;
                        placement = i;
                    }
                }
                if(Dansliste==true){
                    controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getMaListe().remove(placement);
                    try {
                        controleur.getDAO().supprimerMaliste(controleur.getControleurCompte().getCompte().getEmail(),controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo(),voir.getTitre());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    liste.setText("Ajouter a ma Liste");
                }else{
                    controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).ajouterMaliste(voir);
                    try {
                        controleur.getDAO().AjoutMaliste(controleur.getControleurCompte().getCompte().getEmail(),controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo(),voir.getTitre());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    liste.setText("Retirer de ma Liste");
                }
                updateUI();
            }
        });
        title.setBounds(800,55,400,500);
        reco.setBounds(800,87,370,30);
        film.setBackground(new Color(20,20,20));
        film.setSize(2050,1750);
        film.setPreferredSize(film.getPreferredSize());
        //on ouvre une nouvelle video
        Video test = new Video(voir.getUrlfilm());
        BufferedImage bufimg;
        JLabel image;
        try{
            bufimg = chargerIm(voir.getUrlAfficheHor());
            ImageIcon img = resizeImage(bufimg,730,490);
            image = new JLabel(img);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        image.setBounds(30,50,730,490);
        film.add(image);
        film.add(title);
        film.add(liste);
        film.add(desc);
        film.add(desc_);
        film.add(reco);
        film.add(act_);
        film.add(act);
        film.add(real_);
        film.add(real);
        film.add(info);
        film.add(note);
        film.add(c5);
        film.add(c4);
        film.add(c3);
        film.add(c2);
        film.add(c1);
        add(film);
        updateUI();
    }
    public void recherche(){
        //liste film a afficher
        container.removeAll();
        if(controleur.getDAO().getRecherche().size()==0){
            JLabel text = new JLabel("Pas de Film",JLabel.CENTER);
            text.setFont(new Font("Arial",Font.BOLD, 35));
            text.setForeground(Color.white);
            container.add(text);
        }
        int nbRow = controleur.getDAO().getRecherche().size()/5;
        if(controleur.getDAO().getRecherche().size()%4!=0)
            nbRow+=1;
        int nbCol = 4;
        GridBagConstraints c = new GridBagConstraints();
        container.setLayout(new GridBagLayout());
        c.insets = new Insets(10, 5, 10, 5);
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        int x = 0;
        int y = 0;
        for(int i=0;i<controleur.getDAO().getRecherche().size();i++){
            BufferedImage buff = null;
            try {
                if(x==3)
                    y+=1;
                x=i%nbCol;
                buff = chargerIm(controleur.getDAO().getRecherche().get(i).getUrlAfficheHor());
                JButton but = new JButton();
                but.setSize(260,210);
                but.setPreferredSize(but.getSize());
                but.setIcon(resizeImage(buff, but.getPreferredSize().width, but.getPreferredSize().height));
                c.gridx = x;
                c.gridy = y;
                int finalI = i;
                but.addMouseListener(new MouseAdapter() {
                     @Override
                     public void mouseClicked(MouseEvent e) {
                         super.mouseClicked(e);
                         controleur.setFilm(controleur.getDAO().getRecherche().get(finalI));
                         controleur.setAction("Film");
                     }
                 });
                        container.add(but, c);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        container.setBackground(new Color(20,20,20));
        if(nbRow>2)
            container.setSize(1190,1500);
        else
            container.setSize(1190,700);
        container.setPreferredSize(container.getSize());
        //container.setBounds(0,0,1190,1500);
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
        scrollPane.setBounds(0,0,1190,700);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbHighlightColor = new Color(0x8d8d8d);
                thumbLightShadowColor = new Color(0x8d8d8d);
                thumbDarkShadowColor = new Color(0x8d8d8d);
                thumbColor = new Color(20,20,20);
            }
        });
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBar(null);
        add(scrollPane, BorderLayout.CENTER);
        updateUI();
    }
    public void ModifUtilisateur(){
        try {
            controleur.getControleurCompte().RecuperUtilisateurs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        container.removeAll();
        container.setBackground(new Color(20,20,20));
        JLabel titre = new JLabel("Modifier le profil",JLabel.CENTER);
        titre.setFont(new Font("Arial",Font.BOLD, 40));
        titre.setForeground(Color.white);
        titre.setBounds(370,55,400,50);
        JLabel modif = new JLabel("Choisir une photo de profil",JLabel.CENTER);
        modif.setFont(new Font("Arial",Font.BOLD, 35));
        modif.setForeground(Color.white);
        modif.setBounds(310,285,600,50);
        JButton img = new JButton(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPhotoProfil().getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        img.setBounds(390,130,100,100);
        img.setSelected(false);
        img.setBorderPainted(false);
        JTextField pseudo = new JTextField();
        pseudo.setFont(new Font("Arial",Font.PLAIN, 18));
        pseudo.setBounds(530,155,260,40);
        pseudo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(pseudo.isEditable()){
                    JTextField TextMDP =(JTextField) e.getSource();
                    TextMDP.setText("");
                    TextMDP.setForeground(Color.white);
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                if(pseudo.getText().equals("")){
                    pseudo.setText(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
                }
            }
        });
        pseudo.setText(controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
        pseudo.setForeground(Color.white);
        pseudo.setBackground(Color.gray);
        for(int i=0;i<6;i++){
            ImageIcon test = new ImageIcon("src/images/icone"+(i+1)+".png");
            JButton icone = new JButton(new ImageIcon(test.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            icone.setBounds(50+i*190,370,100,100);
            int finalI = i;
            icone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    indImg= finalI;
                    //on change la photo de profil
                    ImageIcon temp = new ImageIcon("src/images/icone"+(indImg+1)+".png");
                    img.setIcon(new ImageIcon(temp.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                    updateUI();
                }
            });
            container.add(icone);
        }
        JButton enreg = new JButton("Enregistrer");
        JButton annul = new JButton("Annuler");
        JButton suppr = new JButton("Supprimer Profil");
        suppr.setBounds(660,560,200,50);
        suppr.setBackground(new Color(1,113,121));
        suppr.setForeground(Color.white);
        annul.setBounds(500,560,150,50);
        annul.setBackground(new Color(1,113,121));
        annul.setForeground(Color.white);

        enreg.setBounds(340,560,150,50);
        enreg.setBackground(new Color(1,113,121));
        enreg.setForeground(Color.white);
        enreg.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String image="";
                //on enregistre
                if(indImg==0)
                    image="icone1";
                else if(indImg==1)
                    image="icone2";
                else if(indImg==2)
                    image="icone3";
                else if(indImg==3)
                    image="icone4";
                else if(indImg==4)
                    image="icone5";
                else if(indImg==5)
                    image="icone5";
                //Dans la BDD
                try {
                    controleur.getDAO().ModifUtilisateur(controleur.getControleurCompte().getCompte().getEmail(),pseudo.getText(),image,controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //Dans notre controleur
                controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).setPseudo(pseudo.getText());
                controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).setPhotoProfil(new ImageIcon("src/images/icone"+(indImg+1)+".png"));
                controleur.setAction("Gestion Profil");
            }
        });
        annul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //verif on peut suppr et on suppr
                controleur.setAction("Gestion Profil");
            }
        });
        suppr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //verif on peut suppr et on suppr
                if(controleur.getControleurCompte().getCompte().getUtilisateurs().size()==1){
                    //On ne peut pas suprr
                    suppr.setBackground(Color.red);
                    suppr.setSelected(false);
                }else{
                    //Dans BDD
                    try {
                        controleur.getDAO().supprimerUtilisateur(controleur.getControleurCompte().getCompte().getEmail(),controleur.getControleurCompte().getCompte().getUtilisateurs().get(controleur.getControleurCompte().getCompte().getUtilisateuractuel()).getPseudo());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    //Dans Controleur
                    controleur.getControleurCompte().getCompte().getUtilisateurs().remove(controleur.getControleurCompte().getCompte().getUtilisateuractuel());
                    controleur.setAction("Choix Utilisateurs");
                }
            }
        });
        container.add(suppr);
        container.add(enreg);
        container.add(annul);
        container.add(modif);
        container.add(titre);
        container.add(pseudo);
        container.add(img);
        add(container);
        updateUI();
    }
    public void AjouterUtilisateur(){
        container.removeAll();
        container.setBackground(new Color(20,20,20));
        JLabel titre = new JLabel("Nouvel Utilisateur",JLabel.CENTER);
        titre.setFont(new Font("Arial",Font.BOLD, 40));
        titre.setForeground(Color.white);
        titre.setBounds(377,55,400,50);
        JLabel modif = new JLabel("Choisir une photo de profil",JLabel.CENTER);
        modif.setFont(new Font("Arial",Font.BOLD, 35));
        modif.setForeground(Color.white);
        modif.setBounds(310,285,600,50);
        ImageIcon tet = new ImageIcon("src/images/icone1.png");
        JButton img = new JButton(new ImageIcon(tet.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        img.setBounds(390,130,100,100);
        img.setSelected(false);
        img.setBorderPainted(false);
        JTextField pseudo = new JTextField();
        pseudo.setFont(new Font("Arial",Font.PLAIN, 18));
        pseudo.setBounds(530,155,260,40);
        pseudo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(pseudo.isEditable()){
                    JTextField TextMDP =(JTextField) e.getSource();
                    TextMDP.setText("");
                    TextMDP.setForeground(Color.white);
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                if(pseudo.getText().equals("")){
                    pseudo.setText("Utilisateur"+(controleur.getControleurCompte().getCompte().getUtilisateurs().size()+1));
                }
            }
        });
        pseudo.setText("Utilisateur"+(controleur.getControleurCompte().getCompte().getUtilisateurs().size()+1));
        pseudo.setForeground(Color.white);
        pseudo.setBackground(Color.gray);
        for(int i=0;i<6;i++){
            ImageIcon test = new ImageIcon("src/images/icone"+(i+1)+".png");
            JButton icone = new JButton(new ImageIcon(test.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            icone.setBounds(50+i*190,370,100,100);
            int finalI = i;
            icone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    indImg= finalI;
                    //on change la photo de profil
                    ImageIcon temp = new ImageIcon("src/images/icone"+(indImg+1)+".png");
                    img.setIcon(new ImageIcon(temp.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                    updateUI();
                }
            });
            container.add(icone);
        }
        JButton enreg = new JButton("Enregistrer");
        JButton annul = new JButton("Annuler");
        annul.setBounds(610,560,150,50);
        annul.setBackground(new Color(1,113,121));
        annul.setForeground(Color.white);

        enreg.setBounds(440,560,150,50);
        enreg.setBackground(new Color(1,113,121));
        enreg.setForeground(Color.white);
        enreg.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Utilisateur nv = new Utilisateur();
                nv.setPseudo(pseudo.getText());
                String image="";
                //on enregistre
                if(indImg==0){
                    image="icone1";
                    nv.setPhotoProfil(new ImageIcon("src/images/icone1.png"));
                }else if(indImg==1){
                    image="icone2";
                    nv.setPhotoProfil(new ImageIcon("src/images/icone2.png"));
                }else if(indImg==2){
                    image="icone3";
                    nv.setPhotoProfil(new ImageIcon("src/images/icone3.png"));
                }else if(indImg==3){
                    image="icone4";
                    nv.setPhotoProfil(new ImageIcon("src/images/icone4.png"));
                }else if(indImg==4){
                    image="icone5";
                    nv.setPhotoProfil(new ImageIcon("src/images/icone5.png"));
                }else if(indImg==5){
                    image="icone6";
                    nv.setPhotoProfil(new ImageIcon("src/images/icone6.png"));
                }
                //Dans la BDD
                try {
                    controleur.getDAO().NouveauUtilisateur(pseudo.getText(),controleur.getControleurCompte().getCompte().getEmail(),image);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //Dans notre controleur
                controleur.getControleurCompte().getCompte().getUtilisateurs().add(nv);
                controleur.getControleurCompte().getCompte().setUtilisateuractuel(controleur.getControleurCompte().getCompte().getUtilisateurs().size()-1);
                controleur.setAction("Choix Utilisateurs");
            }
        });
        annul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //verif on peut suppr et on suppr
                controleur.setAction("Gestion Profil");
            }
        });
        container.add(enreg);
        container.add(annul);
        container.add(modif);
        container.add(titre);
        container.add(pseudo);
        container.add(img);
        add(container);
        updateUI();
    }
    public void gestionProfil(){
        container.removeAll();
        MouseAdapter test;
        test = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //Choix du profil
                controleur.setAction("Ajouter Utilisateur");
            }
        };
        container.setBackground(new Color(20,20,20));
        JLabel titre = new JLabel("Gestion des profils :",JLabel.CENTER);
        titre.setFont(new Font("Arial",Font.BOLD, 30));
        titre.setForeground(Color.white);
        titre.setBounds(400,170,400,40);
        for(int i=0;i<controleur.getControleurCompte().getCompte().getUtilisateurs().size();i++){
            if(controleur.getControleurCompte().getCompte().getUtilisateurs().size()==3){
                JButton icone = new JButton();
                icone.setIcon(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                icone.setText("Modifier");
                icone.setVerticalTextPosition(SwingConstants.CENTER);
                icone.setHorizontalTextPosition(SwingConstants.CENTER);
                icone.setForeground(Color.black);
                icone.setFont(new Font("Arial",Font.BOLD, 18));

                icone.setBounds(150+250*i,250,150,150);
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                pseudo.setBounds(160+250*i,360,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        controleur.setAction("Mofidier Utilisateur");
                        System.out.println("Utilisateur"+finalI);
                    }
                });
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size() - 1){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.setBounds(150+250*(i+1),250,150,150);
                    add.addMouseListener(test);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(150+250*(i+1),360,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }else if((controleur.getControleurCompte().getCompte().getUtilisateurs().size() == 4) || (controleur.getControleurCompte().getCompte().getUtilisateurs().size() == 5)){
                JButton icone = new JButton();
                icone.setIcon(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)));
                icone.setText("Modifier");
                icone.setVerticalTextPosition(SwingConstants.CENTER);
                icone.setHorizontalTextPosition(SwingConstants.CENTER);
                icone.setForeground(Color.black);
                icone.setFont(new Font("Arial",Font.BOLD, 18));
                icone.setBounds(90+210*i,250,120,120);
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                pseudo.setBounds(92+210*i,340,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        controleur.setAction("Mofidier Utilisateur");
                        System.out.println("Utilisateur"+finalI);
                    }
                });
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size()-1 && controleur.getControleurCompte().getCompte().getUtilisateurs().size() == 4){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.setBounds(90+210*(i+1),245,150,150);
                    add.addMouseListener(test);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(90+210*(i+1),340,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }else if(controleur.getControleurCompte().getCompte().getUtilisateurs().size()==2){
                JButton icone = new JButton();
                icone.setIcon(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                icone.setText("Modifier");
                icone.setVerticalTextPosition(SwingConstants.CENTER);
                icone.setHorizontalTextPosition(SwingConstants.CENTER);
                icone.setForeground(Color.black);
                icone.setFont(new Font("Arial",Font.BOLD, 18));
                icone.setBounds(260+250*i,250,150,150);
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        controleur.setAction("Mofidier Utilisateur");
                        System.out.println("Utilisateur"+finalI);
                    }
                });
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                pseudo.setBounds(270+250*i,360,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size() - 1){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.addMouseListener(test);
                    add.setBounds(260+250*(i+1),250,150,150);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(260+250*(i+1),360,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }else{
                JButton icone = new JButton();
                icone.setIcon(new ImageIcon(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPhotoProfil().getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                icone.setText("Modifier");
                icone.setVerticalTextPosition(SwingConstants.CENTER);
                icone.setHorizontalTextPosition(SwingConstants.CENTER);
                icone.setForeground(Color.black);
                icone.setFont(new Font("Arial",Font.BOLD, 18));
                icone.setBounds(360+250*i,250,150,150);
                JLabel pseudo = new JLabel(controleur.getControleurCompte().getCompte().getUtilisateurs().get(i).getPseudo());
                int finalI = i;
                icone.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        controleur.getControleurCompte().getCompte().setUtilisateuractuel(finalI);
                        controleur.setAction("Mofidier Utilisateur");
                        System.out.println("Utilisateur"+ finalI);
                    }
                });
                pseudo.setBounds(370+250*i,360,150,150);
                pseudo.setForeground(Color.white);
                pseudo.setFont(new Font("Arial",Font.PLAIN, 25));
                container.add(icone);
                container.add(pseudo);
                if(i == controleur.getControleurCompte().getCompte().getUtilisateurs().size() - 1){
                    JButton add = new JButton(new ImageIcon("src/images/Ajout.png"));
                    add.setBackground(new Color(20,20,20));
                    add.setBorderPainted(false);
                    add.setSelected(false);
                    add.addMouseListener(test);
                    add.setBounds(360+250*(i+1),250,150,150);
                    JLabel ajout = new JLabel("Nouvel Utilisateur");
                    ajout.setBounds(360+250*(i+1),360,250,150);
                    ajout.setForeground(Color.white);
                    ajout.setFont(new Font("Arial",Font.PLAIN, 25));
                    container.add(add);
                    container.add(ajout);
                }
            }
        }
        JButton manageCompte = new JButton("Terminé");
        manageCompte.setBounds(490,560,200,70);
        manageCompte.setBackground(new Color(1,113,121));
        manageCompte.setForeground(Color.white);
        manageCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                controleur.setAction("Choix Utilisateurs");
            }
        });
        container.add(manageCompte);
        container.add(titre);
        container.setSize(1200,800);
        container.setPreferredSize(container.getPreferredSize());
        add(container,BorderLayout.CENTER);
        updateUI();
        setVisible(true);
    }
    public void setController(FenetreControleur fn){
        this.controleur=fn;
    }

}
