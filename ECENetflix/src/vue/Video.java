package vue;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Video {
    private File vclInstallPath = new File("C:/Program Files/VideoLAN/VLC");
    private MediaPlayerFactory mediaPlayerFactory;
    private EmbeddedMediaPlayer player;

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