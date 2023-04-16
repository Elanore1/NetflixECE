package modele;

import javax.swing.*;
import java.util.ArrayList;

public class Utilisateur {//un compte netflix repertorie plusieurs utilisateurs
    private String pseudo;//pseudo de l'utilisateur
    private ImageIcon photoProfil;
    private ArrayList<Film> maListe;
    private ArrayList<String> parametre;

    public Utilisateur(){
        this.pseudo = "";
        this.photoProfil = null;
        this.maListe = new ArrayList<Film>();
        this.parametre = new ArrayList<String>();
    }
    public Utilisateur(String _pseudo,ImageIcon _photoProfil,ArrayList<Film> _maListe,ArrayList<String> _parametre){
        this.pseudo = _pseudo;
        this.photoProfil = _photoProfil;
        this.maListe = (ArrayList<Film>)_maListe.clone();
        this.parametre = (ArrayList<String>)_parametre.clone();
    }
    public String getPseudo(){return pseudo;}
    public ImageIcon getPhotoProfil(){return photoProfil;}
    public ArrayList<Film> getMaListe(){return maListe;}
    public ArrayList<String> getParametre(){return parametre;}
    public void setPseudo(String _pseudo){this.pseudo = _pseudo;}
    public  void setPhotoProfil(ImageIcon _photoProfil){this.photoProfil = _photoProfil;}
    public void setMaListe(ArrayList<Film> _maListe){ this.maListe = (ArrayList<Film>)_maListe.clone();}
    public void setParametre(ArrayList<String> _parametre){this.parametre = (ArrayList<String>)_parametre.clone();}
    public void ajouterMaliste(Film film){
        maListe.add(film);
    }
}

