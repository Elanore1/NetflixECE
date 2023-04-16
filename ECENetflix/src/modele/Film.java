package modele;

import javax.swing.*;
import java.util.ArrayList;

public class Film {
    //attributs
    String Titre;
    String Realisateur;
    ArrayList<String> Acteurs;
    ArrayList<String> genre;
    String UrlAffiche;
    String UrlAfficheHor;
    String Urlfilm;
    String Description;
    JButton affiche;
    String duree;//format heure H min
    int note;
    int percent;
    int age;
    public Film(){
        this.Titre = "";
        this.Realisateur = "";
        this.Acteurs = new ArrayList<>();
        this.genre = new ArrayList<>();
        this.Description = "";
        this.Urlfilm = "";
        this.UrlAfficheHor = "";
        this.UrlAffiche = "";
        this.affiche = new JButton();
        this.duree = "0h00";
        this.percent = 50 + (int)(Math.random() * ((100 - 50) + 1));
        this.note = -1;
        int test = (int) (Math.random() * 3);
        if(test==0){
            age = 10;
        }else if(test==1){
            age = 13;
        }else if(test==2){
            age = 16;
        }else if(test==3){
            age = 18;
        }
    }
    public Film(String _duree,String _titre,String _realisateur,ArrayList<String> _acteurs,ArrayList<String> _genre,String _urlAffiche,String _urlfilm,String _description){
        this.Titre = _titre;
        this.Realisateur = _realisateur;
        this.Acteurs = (ArrayList<String>)_acteurs.clone();
        this.genre = (ArrayList<String>)_genre.clone();
        this.Description=_description;
        this.Urlfilm=_urlfilm;
        this.UrlAffiche=_urlAffiche;
        this.UrlAfficheHor="";
        this.affiche=new JButton();
        this.duree = _duree;
        this.percent = 50 + (int)(Math.random() * ((100 - 50) + 1));
    }
    //getters et setter
    public String getTitre(){return Titre;}
    public String getRealisateur(){return Realisateur;}
    public ArrayList<String> getActeurs(){return Acteurs;}
    public ArrayList<String> getGenre(){return genre;}
    public String getUrlAffiche(){return UrlAffiche;}
    public String getUrlfilm(){return Urlfilm;}
    public String getDescription(){return Description;}
    public String getUrlAfficheHor(){return UrlAfficheHor;}
    public JButton getAffiche(){ return affiche;}
    public void setTitre(String _titre){this.Titre = _titre;}
    public void setRealisateur(String _realisateur){this.Realisateur = _realisateur;}
    public void setActeurs(ArrayList<String> _acteurs){this.Acteurs = (ArrayList<String>)_acteurs.clone();}
    public void setGenre(ArrayList<String> _genre){this.genre = (ArrayList<String>)_genre.clone();}
    public void setUrlAffiche(String _urlAffiche){this.UrlAffiche = _urlAffiche;}
    public void setUrlAfficheHor(String _urlAffichehor){this.UrlAfficheHor = _urlAffichehor;}
    public void setUrlfilm(String _urlfilm){this.Urlfilm = _urlfilm;}
    public void setDescription(String _description){this.Description = _description;}
    public void setAffiche(JButton _aff){this.affiche = _aff;}
    public void setDuree(String _duree){this.duree = _duree;}
    public String getDuree(){return duree;}
    public int getPercent(){return percent;}
    public int getAge(){return age;}
    public int getNote(){return note;}
    public void setNote(int _note){this.note=_note;}
    public void afficherFilm(){
        System.out.println("Titre : "+Titre);
        System.out.println("Real : "+Realisateur);
        System.out.println("Acteurs : ");
        for(int i=0;i<Acteurs.size();i++){
            System.out.println(Acteurs.get(i));
        }
        System.out.println("Genre : ");
        for(int i=0;i<genre.size();i++){
            System.out.println(genre.get(i));
        }
        System.out.println("Description : "+Description);
        System.out.println("Urlfilm : "+ Urlfilm);
        System.out.println("UrlAffiche : "+UrlAffiche);
        System.out.println("UrlAfficheHor : "+ UrlAfficheHor);
        System.out.println("duree : "+ duree);
    }
}
