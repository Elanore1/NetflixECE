package modele;//modele qui est lié a la base de donnée

import java.util.regex.Pattern;
//Compte associé a une personne avec mail et mdp
public class Compte{
    private String email;
    private String mdp;
    //plus tard peut etre creation liste d'usager comme dans netflix avec pseudo différent et max 5
    private String usager;//soit Client soit Direction ECENetflix
    public Compte(){
        this.email="";
        this.mdp="";
        this.usager="";
    }
    public Compte(String _email, String _mdp,String _usager){
        this.email=_email;
        this.mdp=_mdp;
        this.usager=_usager;
    }

    //getter setter
    public String getEmail(){return email;}
    public String getMdp(){return mdp;}
    public void setEmail(String _email){
        //Regular Expression pour un mail
        //on verif que le mail est valide
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regx);
        if (pattern.matcher(_email).matches()) {
            this.email = _email;
        }
    }
    public void setUsager(String _usager){
        this.usager = _usager;
    }
    public void setMdp(String _mdp){
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
        if(special>=1&&loCount>=1&&upCount>=1&&digit>=1){
            this.mdp=_mdp;
        }
    }

}
