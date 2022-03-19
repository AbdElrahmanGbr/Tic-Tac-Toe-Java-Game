
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import ticTac.Connection.Session;


public class StartScreenController implements Initializable {
    private ImageView myImage;
    @FXML
    private BorderPane rootPane;
    
    Session session;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new SplashScreen().start();
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(myImage);
        rotate.setDuration(Duration.millis(3500));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setByAngle(360);
        rotate.play();
    }    
    //The beginning screen to enter the game
    class SplashScreen extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(3500);
                
                Platform.runLater(() -> {
                    MainScreen.session.changeScene("/fxml/mainMenu.fxml");
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(StartScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
