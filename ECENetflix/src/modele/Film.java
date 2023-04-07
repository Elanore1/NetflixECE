package modele;

import javax.swing.*;
import java.util.ArrayList;

public class Film {
    //attributs
    String Titre;
    String Realisateur;
    ArrayList<String> Acteurs;
    String UrlAffiche;
    String UrlAfficheHor;
    String Urlfilm;
    String Description;
    JButton affiche;
    public Film(){
        this.Titre = "";
        this.Realisateur = "";
        this.Acteurs = new ArrayList<>();
        this.Description = "";
        this.Urlfilm = "";
        this.UrlAfficheHor = "";
        this.UrlAffiche = "";
        this.affiche=new JButton();
    }
    public Film(String _titre,String _realisateur,ArrayList<String> _acteurs,String _urlAffiche,String _urlfilm,String _description){
        this.Titre = _titre;
        this.Realisateur = _realisateur;
        this.Acteurs = (ArrayList<String>)_acteurs.clone();
        this.Description=_description;
        this.Urlfilm=_urlfilm;
        this.UrlAffiche=_urlAffiche;
        this.UrlAfficheHor="";
        this.affiche=new JButton();
    }
    //getters et setter
    public String getTitre(){return Titre;}
    public String getRealisateur(){return Realisateur;}
    public ArrayList<String> getActeurs(){return Acteurs;}
    public String getUrlAffiche(){return UrlAffiche;}
    public String getUrlfilm(){return Urlfilm;}
    public String getDescription(){return Description;}
    public String getUrlAfficheHor(){return UrlAfficheHor;}
    public JButton getAffiche(){ return affiche;}
    public void setTitre(String _titre){this.Titre = _titre;}
    public void setRealisateur(String _realisateur){this.Realisateur = _realisateur;}
    public void setActeurs(ArrayList<String> _acteurs){this.Acteurs = (ArrayList<String>)_acteurs.clone();}
    public void setUrlAffiche(String _urlAffiche){this.UrlAffiche = _urlAffiche;}
    public void setUrlAfficheHor(String _urlAffichehor){this.UrlAfficheHor = _urlAffichehor;}
    public void setUrlfilm(String _urlfilm){this.Urlfilm = _urlfilm;}
    public void setDescription(String _description){this.Description = _description;}
    public void setAffiche(JButton _aff){this.affiche = _aff;}
}
