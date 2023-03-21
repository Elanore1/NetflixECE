package modele;

public class BarreMenu{
    //nombre de champs a ajoute dans le menu horizontale
    private int menuInfo;
    //nombre de champs a ajoute dans le sous menu
    private int compteSubMenu;
    public int getMenuInfo(){
        return menuInfo;
    }
    public int getCompteSubMenu(){
        return compteSubMenu;
    }
    public void setMenuInfo(int _menuInfo){this.menuInfo=menuInfo;}
    public void setCompteSubMenu(int _comptesubmenu){this.compteSubMenu=_comptesubmenu;}
}
