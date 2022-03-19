package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class LoginController implements Initializable {
    
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Hyperlink signUpHyperlink;
    @FXML
    private Button loginButton;
    @FXML
    private Label passwdAndUser;
    @FXML
    private Label label;
    @FXML
    private Button backButton;
    
    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException{

         //Make sure the user doesn't leave the username or password empty
        if(username.getText().isEmpty() || password.getText().isEmpty()){    
            passwdAndUser.setVisible(true);
            //Once he/she starts writing, invalid label disappears
            username.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    passwdAndUser.setVisible(false);
                }
        });
            //Same with password
            password.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    passwdAndUser.setVisible(false);
                }
                
            });
        }else{
            MainScreen.session.signInRequest(username.getText(), password.getText());
        }
    }
    
    public void login_failre(){
        passwdAndUser.setVisible(true);
    }

    
    /* When this method is called, it will change the    @FXML
 scene
       to sign up screen */
    @FXML
    public void changeToSignUpScreen(ActionEvent event) throws IOException{
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/signUp.fxml"));
        Parent fxmlViewChild = loader.load();
        
        Scene fxmlViewScene = new Scene(fxmlViewChild);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fxmlViewScene);
        
        window.show();
        audio("btnHover.mp3");
    }
    
    
      @FXML
    private void BackButtonAction(ActionEvent event) throws IOException {
        MainScreen.session.changeScene("/fxml/mainMenu.fxml");
    }
    
    //Animation and Sound Effects
    @FXML
    private void loginOnHover(MouseEvent event){
        loginButton.setPrefWidth(163);
        loginButton.setPrefHeight(35);
           audio("btnHover.mp3");
    }
    
    @FXML
    private void loginOnExit(MouseEvent event){
        loginButton.setPrefWidth(153);
        loginButton.setPrefHeight(25); 
    }
    
    
       @FXML
    private void loginOnPress(MouseEvent event){
         loginButton.setPrefWidth(153);
        loginButton.setPrefHeight(25); 
           audio("btnClick.mp3");
    }
    
    @FXML
    private void loginOnRelease(MouseEvent event){
         loginButton.setPrefWidth(163);
        loginButton.setPrefHeight(35);
    }
    
    
    @FXML
    private void backOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    @FXML
    private void backOnPress(MouseEvent event){
        backButton.setPrefWidth(63);
        backButton.setPrefHeight(15);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void backOnRelease(MouseEvent event){
        backButton.setPrefWidth(83);
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
