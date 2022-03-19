
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


public class VideoWindowController implements Initializable {

    @FXML MediaView mv;
    Stage thisStage;
    
    private String typePlayer ;
    public static MediaPlayer mp;
    
  
    
    
    public void  setType(String  stringTypePlayer){ 
         typePlayer = stringTypePlayer;
         System.out.println(typePlayer);
         displayVideo(); 
         //flag = true ;
               
    }
    
    private void displayVideo(){
        
        if(typePlayer.equals("winner")){
               //get video file and set it to media
            setMedia("/resources/video.mp4");
        }else{
           setMedia("/resources/loser.mp4");
            
        }
       
        
    }
    
    private void setMedia(String videoPath){
        Media media = new Media(getClass().getResource(videoPath).toExternalForm());

             mp = new MediaPlayer(media);
             mv.setMediaPlayer(mp);
             mp.play();
        
    }
    
    
       @Override
    public void initialize(URL url, ResourceBundle rb) {
//        thisStage = (Stage) mv.getScene().getWindow();
        
    } 
}
