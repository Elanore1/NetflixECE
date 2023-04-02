package modele;

import javax.swing.*;
import java.util.ArrayList;

public class Utilisateurs {//un compte netflix repertorie plusieurs utilisateurs
    private String pseudo;//pseudo de l'utilisateur
    private ImageIcon photoProfil;
    private ArrayList<String> maListe;
    private ArrayList<String> parametre;

    public Utilisateurs(){
        this.pseudo = "";
        this.photoProfil = null;
        this.maListe = new ArrayList<String>();
        this.parametre = new ArrayList<String>();
    }
    public Utilisateurs(String _pseudo,ImageIcon _photoProfil,ArrayList<String> _maListe,ArrayList<String> _parametre){
        this.pseudo = _pseudo;
        this.photoProfil = _photoProfil;
        this.maListe = (ArrayList<String>)_maListe.clone();
        this.parametre = (ArrayList<String>)_parametre.clone();
    }
    public String getPseudo(){return pseudo;}
    public ImageIcon getPhotoProfil(){return photoProfil;}
    public ArrayList<String> getMaListe(){return maListe;}
    public ArrayList<String> getParametre(){return parametre;}
    public void setPseudo(String _pseudo){this.pseudo = _pseudo;}
    public  void setPhotoProfil(ImageIcon _photoProfil){this.photoProfil = _photoProfil;}
    public void setMaListe(ArrayList<String> _maListe){ this.maListe = (ArrayList<String>)_maListe.clone();}
    public void setParametre(ArrayList<String> _parametre){this.parametre = (ArrayList<String>)_parametre.clone();}
}
