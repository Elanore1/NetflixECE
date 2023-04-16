package modele;

import java.util.ArrayList;

public class NetflixBDD { //stockage des films dans la base de données
    ArrayList<Film> Top;
    ArrayList<Film> Series;
    ArrayList<Film> Films;
    ArrayList<Film> Nouveaute;
    ArrayList<Film> Anime;
    ArrayList<Integer> malisteInt;
    public NetflixBDD(){
        Top=new ArrayList<Film>();
        Series=new ArrayList<Film>();
        Films=new ArrayList<Film>();
        Nouveaute=new ArrayList<Film>();
        Anime=new ArrayList<Film>();
        malisteInt=new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            malisteInt.add(5);//on met 5 pour toutes les listes de base
        }
    }
    public NetflixBDD(ArrayList<Film> _top,ArrayList<Film> _serie,ArrayList<Film> _film,ArrayList<Film> _nv,ArrayList<Film> _anim){
        Top=(ArrayList<Film>) _top.clone();
        Series=(ArrayList<Film>) _serie.clone();
        Films=(ArrayList<Film>) _film.clone();
        Nouveaute=(ArrayList<Film>) _nv.clone();
        Anime=(ArrayList<Film>) _anim.clone();
        malisteInt=new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            malisteInt.add(5);//on met 5 pour toutes les listes de base
        }
    }
    public void setTop(ArrayList<Film> _top){this.Top = (ArrayList<Film>)_top.clone();}
    public ArrayList<Film> getTop(){ return Top;}
    public void setSerie(ArrayList<Film> _serie){this.Series = (ArrayList<Film>)_serie.clone();}
    public ArrayList<Film> getSerie(){ return Series;}
    public void setFilms(ArrayList<Film> _liste){this.Films = (ArrayList<Film>)_liste.clone();}
    public ArrayList<Film> getFilms(){ return Films;}
    public void setNouveaute(ArrayList<Film> _liste){this.Nouveaute = (ArrayList<Film>)_liste.clone();}
    public ArrayList<Film> getNouveaute(){ return Nouveaute;}
    public void setAnime(ArrayList<Film> _liste){this.Anime = (ArrayList<Film>)_liste.clone();}
    public ArrayList<Film> getAnime(){ return Anime;}

    public ArrayList<Integer> getMalisteInt(){
        return malisteInt;
    }
    //position pour film serie etc.. et val pour la valeur
    public void setMalisteInt(int pos, int val){
        malisteInt.set(pos,val);
    }
}
