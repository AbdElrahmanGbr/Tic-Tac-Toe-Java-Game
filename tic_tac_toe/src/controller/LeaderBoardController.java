
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.json.JSONObject;

public class LeaderBoardController implements Initializable {
    
    @FXML
    private ImageView backButton;
    
    @FXML
    private Label username1, username2, username3, username4, username5, username6, username7, username8, username9, username10;
    
    @FXML
    private Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
    
    @FXML
    Vector<Label> usernames;
    
    @FXML
    Vector<Label> scores;

    /**
     * Initializes the controller class.
     */
    
    

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
    
    public void loadLeaderBoard(JSONObject leaderboard){        
        String[] name = leaderboard.get("names").toString().split(",");
        String[] score = leaderboard.get("scores").toString().split(",");
        for(int a=1;a<name.length;a++){
            usernames.get(a-1).setText(name[a]);
            scores.get(a-1).setText(score[a]);
        }
    }
    
   
    private void addArray(){
        //Vector 
        usernames = new Vector();
        scores = new Vector();
        usernames.add(username1);
        usernames.add(username2);
        usernames.add(username3);
        usernames.add(username4);
        usernames.add(username5);
        usernames.add(username6);
        usernames.add(username7);
        usernames.add(username8);
        usernames.add(username9);
        usernames.add(username10);
        
        scores.add(score1);
        scores.add(score2);
        scores.add(score3);
        scores.add(score4);
        scores.add(score5);
        scores.add(score6);
        scores.add(score7);
        scores.add(score8);
        scores.add(score9);
        scores.add(score10);

    }
    
    
    //Animation and Sound Effects
    @FXML
    private void backOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    @FXML
    private void backOnPress(MouseEvent event){
        backButton.setFitWidth(40);
        backButton.setFitHeight(51);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void backOnRelease(MouseEvent event){
        backButton.setFitWidth(50);
        backButton.setFitHeight(61);
    }

    private void audio(String soundEffect){
        Media sound = new Media(getClass().getResource("/audio/"+soundEffect).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addArray();
        audio("applause.mp3");
    }    
    
}
