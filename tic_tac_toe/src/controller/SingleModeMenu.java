package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class SingleModeMenu implements Initializable {
    
    @FXML
    private Button simpleModeButton, mediumModeButton, hardModeButton, backButton;
    

    
    @FXML
    private void simpleModeButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SingleGameEasyScreen.fxml"));
        Parent fxmlViewChild = loader.load();
        
        
        Scene fxmlViewScene = new Scene(fxmlViewChild);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fxmlViewScene);
        
        window.show();
       
    }
    
    @FXML
    private void mediumModeButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SingleGameMediumScreen.fxml"));
        Parent fxmlViewChild = loader.load();
        
        Scene fxmlViewScene = new Scene(fxmlViewChild);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fxmlViewScene);
        
        window.show();
       
        
    }
    
    @FXML
    private void hardModeButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SingleGameHardScreen.fxml"));
        Parent fxmlViewChild = loader.load();
        
        Scene fxmlViewScene = new Scene(fxmlViewChild);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fxmlViewScene);
        
        window.show();
       
        
    }
    
     @FXML
    private void BackButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/mainMenu.fxml"));
        Parent fxmlViewChild = loader.load();
        
        Scene fxmlViewScene = new Scene(fxmlViewChild);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fxmlViewScene);
        
        window.show();
        
    }
    
    
    //Animation and Sound Effects
    @FXML
    private void simpleOnHover(MouseEvent event){
        //hardModeButton
        simpleModeButton.setPrefWidth(270);
        simpleModeButton.setPrefHeight(45);
        audio("btnHover.mp3");
       
    }
    
    @FXML
    private void simpleOnExit(MouseEvent event){
        //hardModeButton
        simpleModeButton.setPrefWidth(250);
        simpleModeButton.setPrefHeight(35);
    }
    
    @FXML
    private void simpleOnPress(MouseEvent event){
        simpleModeButton.setPrefWidth(250);
        simpleModeButton.setPrefHeight(35);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void simpleOnRelease(MouseEvent event){
        simpleModeButton.setPrefWidth(270);
        simpleModeButton.setPrefHeight(45);
    }
    
    @FXML
    private void medOnHover(MouseEvent event){
         //mediumModeButton
        mediumModeButton.setPrefWidth(270);
        mediumModeButton.setPrefHeight(45);
        audio("btnHover.mp3");
    }
    
    @FXML
    private void medOnExit(MouseEvent event){
        //mediumModeButton
        mediumModeButton.setPrefWidth(250);
        mediumModeButton.setPrefHeight(35);
    }
    
    @FXML
    private void medOnPress(MouseEvent event){
        mediumModeButton.setPrefWidth(250);
        mediumModeButton.setPrefHeight(35);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void medOnRelease(MouseEvent event){
        mediumModeButton.setPrefWidth(270);
        mediumModeButton.setPrefHeight(45);
    }
    
      @FXML
    private void hardOnHover(MouseEvent event){
        hardModeButton.setPrefWidth(270);
        hardModeButton.setPrefHeight(45);
          audio("btnHover.mp3");
    }
    
    @FXML
    private void hardOnExit(MouseEvent event){
        hardModeButton.setPrefWidth(250);
        hardModeButton.setPrefHeight(35);
    }
    
    
    
    @FXML
    private void hardOnPress(MouseEvent event){
        hardModeButton.setPrefWidth(250);
        hardModeButton.setPrefHeight(35);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void hardOnRelease(MouseEvent event){
        hardModeButton.setPrefWidth(270);
        hardModeButton.setPrefHeight(45);
    }
    
    
    @FXML
    private void backOnPress(MouseEvent event){
        backButton.setPrefWidth(85);
        backButton.setPrefHeight(15);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void backOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    @FXML
    private void backOnRelease(MouseEvent event){
        backButton.setPrefWidth(105);
        backButton.setPrefHeight(25);
    }
    
    private void audio(String soundEffect){
        Media sound = new Media(getClass().getResource("/audio/"+soundEffect).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
