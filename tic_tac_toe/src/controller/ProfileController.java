
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class ProfileController implements Initializable {
    @FXML
    Label username, score;
    @FXML 
    Button losses, wins, ties;
    @FXML
    ImageView backButton;
    
    
    
    @FXML
    private void backOnClick(MouseEvent event){
           try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/mainMenu.fxml"));
            Parent fxmlViewChild = loader.load();
            
            Scene fxmlViewScene = new Scene(fxmlViewChild);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(fxmlViewScene);
            
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LeaderBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void backOnPress(MouseEvent event){
        audio("btnClick.mp3");
    }
    
    @FXML
    private void backOnRelease(MouseEvent event){
        
    }
    
    private void audio(String soundEffect){
        Media sound = new Media(getClass().getResource("/audio/"+soundEffect).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(MainScreen.session.player.getUsername());
        String playerScore = Integer.toString(MainScreen.session.player.getScore());
        score.setText(playerScore);
        String playerWins = Integer.toString(MainScreen.session.player.getWins());
        wins.setText(playerWins);
        String playerLosses = Integer.toString(MainScreen.session.player.getLosses());
        losses.setText(playerLosses);
        String playerTies = Integer.toString(MainScreen.session.player.getTies());
        ties.setText(playerTies);
    }
    
    
    
    
}
