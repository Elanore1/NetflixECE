package vue;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Video {
    public Video(String url){
        Desktop desktop = Desktop.getDesktop();
        URI uri = null ;
        try {
            uri = new URI(url);
            desktop.browse(uri);//on ouvre la video sur une nouvelle page
        } catch(IOException ioe) {
            System.out.println("Le système ne trouve pas le " + uri +" fichier spécifié");
            //ioe.printStackTrace();
        } catch(URISyntaxException use) {
            System.out.println("Caractère illégal dans le chemin");
            //use.printStackTrace();
            throw new RuntimeException(use);
        }
    }
}