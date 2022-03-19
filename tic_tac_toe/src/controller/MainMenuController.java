package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ticTac.Connection.Session;

public class MainMenuController implements Initializable {
    
    @FXML
    private Button singleModeButton, MultiModeButton, hardModeButton, leaderButton, aboutButton;
    @FXML
    private ImageView singleIcon, multiIcon, leaderIcon;
    @FXML
    private ImageView profile_icon;
    
    @FXML
    private void singleModeButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SingleModeMenu.fxml"));
        Parent fxmlViewChild = loader.load();
        
        Scene fxmlViewScene = new Scene(fxmlViewChild);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fxmlViewScene);
        
        window.show();
    }
    
    @FXML
    private void MultiModeButtonAction(ActionEvent event) throws IOException{
        if(!MainScreen.session.Connected){
            MainScreen.session.loged = false;
            MainScreen.session.openConnection();
            return;
        }
        
        if (MainScreen.session.loged == false){
            MainScreen.session.controlManager.setLoginController(MainScreen.session.changeScene("/fxml/login.fxml"));
        }
        else{
            MainScreen.session.controlManager.setInvitationController(MainScreen.session.changeScene("/fxml/PlayerInvitationScreen.fxml"));
            MainScreen.session.viewOnlinePlayers = true;
            MainScreen.session.getOnlinePlayersRequest();
        }
    }
    
    @FXML
    private void LeaderBoardButtonAction(ActionEvent event) {
        if(!MainScreen.session.Connected){
            MainScreen.session.loged = false;
            MainScreen.session.openConnection();
            return;
        }
        
        if (MainScreen.session.loged == false){
                 try {
                    Dialog<ButtonType> dialog = new Dialog<>();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/InvitationDialog.fxml"));
                    DialogPane profile = fxmlLoader.load();
                    dialog.setDialogPane(profile);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                    dialog.setTitle("Profile");
                    Label content = new Label("To View The Leaderboard, Please Sign In!");
                    content.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px");
                    dialog.setGraphic(content);
                    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
                    Optional<ButtonType> result = dialog.showAndWait();

                    if(result.get()==ButtonType.OK){
                        MainScreen.session.changeScene("/fxml/login.fxml");
                    }
                    else{
                        MainScreen.session.changeScene("/fxml/mainMenu.fxml");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        else{
                       MainScreen.session.getLeaderboardRequest();
        }
        
        
    }
    
    @FXML
    private void aboutButtonAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AboutScreen.fxml"));
            Parent fxmlViewChild = loader.load();
            
            Scene fxmlViewScene = new Scene(fxmlViewChild);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(fxmlViewScene);
            
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void profileOnClick(MouseEvent event){
            if(!MainScreen.session.Connected){
                MainScreen.session.loged = false;
                MainScreen.session.openConnection();
                return;
            }   
            if (MainScreen.session.loged == false){
                 try {
                    Dialog<ButtonType> dialog = new Dialog<>();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/InvitationDialog.fxml"));
                    DialogPane profile = fxmlLoader.load();
                    dialog.setDialogPane(profile);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                    dialog.setTitle("Profile");
                    Label content = new Label("To View Your Profile, Please Sign In!");
                    content.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px");
                    dialog.setGraphic(content);
                    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
                    Optional<ButtonType> result = dialog.showAndWait();

                    if(result.get()==ButtonType.OK){
                        MainScreen.session.changeScene("/fxml/login.fxml");
                    }
                    else{
                        MainScreen.session.changeScene("/fxml/mainMenu.fxml");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        else{
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/ProfileScreen.fxml"));
                    Parent fxmlViewChild = loader.load();
                    
                    Scene fxmlViewScene = new Scene(fxmlViewChild);
                    
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(fxmlViewScene);
                    
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    
    
    //Animation and Sound Effects
    @FXML
    private void singleOnHover(MouseEvent event){
        //SingleModeButton
        singleModeButton.setPrefWidth(270);
        singleModeButton.setPrefHeight(45);
         audio("btnHover.mp3");
       
    }
    
    @FXML
    private void singleOnExit(MouseEvent event){
        //SingleModeButton
        singleModeButton.setPrefWidth(250);
        singleModeButton.setPrefHeight(35);
    }
      
    @FXML
    private void singleOnPress(MouseEvent event){
        //SingleModeButton
        singleModeButton.setPrefWidth(250);
        singleModeButton.setPrefHeight(35);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void singleOnRelease(MouseEvent event){
        //SingleModeButton
        singleModeButton.setPrefWidth(270);
        singleModeButton.setPrefHeight(45);
         audio("btnHover.mp3"); 
    }
    
    @FXML
    private void multiOnHover(MouseEvent event){
         //MultiModeButton
        MultiModeButton.setPrefWidth(270);
        MultiModeButton.setPrefHeight(45);
         audio("btnHover.mp3");
    }
    
    @FXML
    private void multiOnExit(MouseEvent event){
        //MultiModeButton
        MultiModeButton.setPrefWidth(250);
        MultiModeButton.setPrefHeight(35);

    }
    
    @FXML
    private void multiOnPress(MouseEvent event){
        //MultiModeButton
        MultiModeButton.setPrefWidth(250);
        MultiModeButton.setPrefHeight(35);
        audio("btnClick.mp3");
    }
    @FXML
    private void multiOnRelease(MouseEvent event){
         //MultiModeButton
        MultiModeButton.setPrefWidth(270);
        MultiModeButton.setPrefHeight(45);
         
    }
    
    @FXML
    private void leaderOnHover(MouseEvent event){
         //LeaderBoardButton
        leaderButton.setPrefWidth(270);
        leaderButton.setPrefHeight(45);
         audio("btnHover.mp3");
    }
    
    @FXML
    private void leaderOnExit(MouseEvent event){
        //LeaderBoardButton
        leaderButton.setPrefWidth(250);
        leaderButton.setPrefHeight(35);
    }
    
    @FXML
    private void leaderOnPress(MouseEvent event){
        //LeaderBoardButton
        leaderButton.setPrefWidth(250);
        leaderButton.setPrefHeight(35);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void leaderOnRelease(MouseEvent event){
         //LeaderBoardButton
        leaderButton.setPrefWidth(270);
        leaderButton.setPrefHeight(45);
    }
    
    
    @FXML
    private void aboutOnHover(MouseEvent event){
         //aboutButton
        aboutButton.setPrefWidth(270);
        aboutButton.setPrefHeight(45);
        audio("btnHover.mp3");
    }
    
    @FXML
    private void aboutOnExit(MouseEvent event){
        //aboutButton
        aboutButton.setPrefWidth(250);
        aboutButton.setPrefHeight(35);
    }
    
    @FXML
    private void aboutOnPress(MouseEvent event){
        //aboutButton
        aboutButton.setPrefWidth(250);
        aboutButton.setPrefHeight(35);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void aboutOnRelease(MouseEvent event){
         //aboutButton
        aboutButton.setPrefWidth(270);
        aboutButton.setPrefHeight(45);
    }
    @FXML
    private void profileOnPress(MouseEvent event){
        //Sound Effects and Animation
            profile_icon.setFitWidth(30);
            profile_icon.setFitHeight(30);
            audio("btnClick.mp3");
    }
    @FXML
    private void profileOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    
    @FXML
    private void profileOnRelease(MouseEvent event){
        profile_icon.setFitWidth(40);
        profile_icon.setFitHeight(40);
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
