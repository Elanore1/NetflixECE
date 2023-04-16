package modele;//modele qui est lié a la base de donnée

import java.util.ArrayList;
//Compte associé a une personne avec mail et mdp
public class Compte{
    private String email;
    private String mdp;
    //plus tard peut etre creation liste d'usager comme dans netflix avec pseudo différent et max 5
    private String usager;//soit Client soit Direction ECENetflix
    private ArrayList<Utilisateur> utilisateurs;//liste des utilisateurs possible
    private int utilisateuractuel;
    public Compte(){
        this.email="";
        this.mdp="";
        this.usager="";
        this.utilisateurs= new ArrayList<Utilisateur>();
    }
    public Compte(String _email, String _mdp,String _usager, ArrayList<Utilisateur> _utilisateurs){
        this.email=_email;
        this.mdp=_mdp;
        this.usager=_usager;
        this.utilisateurs= (ArrayList<Utilisateur>)_utilisateurs.clone();
    }
    //getter setter
    public String getEmail(){return email;}
    public String getMdp(){return mdp;}
    public String getUsager(){return usager;}
    public ArrayList<Utilisateur> getUtilisateurs(){return utilisateurs;}
    public void setEmail(String _email){
        this.email = _email;
    }
    public void setUsager(String _usager){
        this.usager = _usager;
    }
    public void setMdp(String _mdp){
        this.mdp=_mdp;
    }
    public void setUtilisateurs(ArrayList<Utilisateur> _utilisateurs){
        this.utilisateurs= (ArrayList<Utilisateur>)_utilisateurs.clone();
    }
    public int getUtilisateuractuel() {
        return utilisateuractuel;
    }
    public void setUtilisateuractuel(int utilisateuractuel) {
        this.utilisateuractuel = utilisateuractuel;
    }
    public void AjouterUtilisateur(Utilisateur a)
    {
        utilisateurs.add(a);
    }
}
