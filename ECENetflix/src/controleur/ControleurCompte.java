package controleur;

import modele.Compte;
import modele.Film;
import modele.Utilisateur;
import vue.ViewContenu;
import vue.ViewFenetre;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ControleurCompte {

    private ViewFenetre fenetre;//Vue
    private  ViewContenu panel;
    private  RechercheInfo DAO;//DAO
    private Compte compte;
    private Utilisateur utilisateur;
    public ControleurCompte () {
        this.DAO=new RechercheInfo();
        this.compte=new Compte();
        this.utilisateur=new Utilisateur();
    }
    public Compte getCompte() {
        return compte;
    }
    public void setCompte(String email, String mdp){
        compte.setMdp(mdp);
        compte.setEmail(email);
    }
    public void UtilisateurChoisi(Utilisateur a) {
        utilisateur=a;
    }
    public void NouveauCompte(String email,String mdp) throws SQLException {
        //On crée un nouvel utilisateur avec des données prédéfinies
        Utilisateur a = nouveauUtilisateur();
        // On ajoute au controlleur les attributs du compte qui vient d'être créer
        System.out.println("crea compte:"+email+mdp);
        compte.setEmail(email);
        compte.setMdp(mdp);
        //On ajoute le nouvel utilisateur créer et on positionne le nouvel utilisateur dessus
        compte.getUtilisateurs().add(a);
        compte.setUtilisateuractuel(0);
        //On ajoute a la base de données le nouveau compte
        DAO.NouveauCompte(email,mdp,"client");
        DAO.NouveauUtilisateur("Utilisateur1",email,"icone1");
    }
    public boolean NouveauMail(String mail){
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
    public String NouveauMDP(String _mdp){
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
    public void ConnexionCompte(String email,String mdp) {
        compte.setEmail( email );
        compte.setMdp(mdp);
    }

    public boolean validateMail(String mail) {
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
    public boolean validateMDP(String mail, String mdp){
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

    public void EnregistreCompte(String email,String mdp) {
        compte.setEmail( email );
        compte.setMdp( mdp );
    }

    public void setUtilisateur(String pseudo) {
        ArrayList<Utilisateur> utilisateurs= compte.getUtilisateurs();
        for(int i=0;i<utilisateurs.size();i++) {
            if(utilisateurs.get( i ).getPseudo()==pseudo) {
                compte.setUtilisateuractuel( i );
            }

        }
    }

    public void modificationUtilisateur(String pseudo) {


        ArrayList<Utilisateur>utilisateurs=compte.getUtilisateurs();
        boolean verif=true;


        for(int i =0; i<utilisateurs.size();i++) {
            if(utilisateurs.get( i ).getPseudo()==pseudo) {
                verif=false;
            }
        }

        if(verif) {

        }

    }
    public ArrayList<Utilisateur> RecuperUtilisateurs() throws SQLException {
        //System.out.println(compte.getEmail());
        ArrayList<String>liste =DAO.RecupererUtilisateurs( compte.getEmail( ) );
        ArrayList<Utilisateur>a = new ArrayList<>() ;
        String mail;
        //   System.out.println( liste.get(0) );
        String pe = null;
        String image;

        for(int i=0;i< liste.size();i++){
            // System.out.println(liste.get( i ) );
            if(i%3==0)
                mail=liste.get(i);
            else if(i%3==1)
                pe= liste.get(i);
            else if(i%3==2) {
                image=liste.get(i);
                Utilisateur e = new Utilisateur();
                e.setPseudo( pe );
                ImageIcon v = new ImageIcon( "/src/images/FondAcceuil.png" );
                e.setPhotoProfil(v);
                a.add( e );
            }
        }
        return a;
    }
    public Utilisateur nouveauUtilisateur() {
        String _pseudo="Utilisateur1";
        ImageIcon _photoProfil= new ImageIcon( "src/images/icone1.png" );
        ArrayList<Film> _maListe = new ArrayList<Film>();
        ArrayList<String> _parametre= new ArrayList<String>();

        Utilisateur a = new Utilisateur(_pseudo,_photoProfil,_maListe,_parametre);
        return a;
    }

}