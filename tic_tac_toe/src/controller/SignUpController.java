package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import validator.Validator;


public class SignUpController implements Initializable {
    @FXML
    private TextField username,password;
 
    @FXML
    private Button signUp;
    
    @FXML
    private Label passwdAndUser;
    @FXML
    private PasswordField confirmpassword;
    @FXML
    private Label passnotmatch;
    @FXML
    private Label invailduser;
    @FXML
    private Label invaildpass;
    @FXML
    private Hyperlink loginHyperlink;

    
    @FXML
    private void changeToLoginScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/login.fxml"));
        Parent fxmlViewChild = loader.load();
        
        Scene fxmlViewScene = new Scene(fxmlViewChild);
        
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(fxmlViewScene);
        
        window.show();
        audio("btnHover.mp3");
 
    }
    
    /* When this method is called, it will change the scene
       to Login screen */
    @FXML
    private void SignUpButtonAction(ActionEvent event) throws IOException {

          Validator valid = new Validator();
        if (!valid.validateUserName(username.getText())) {
            invailduser.setVisible(true);
            //Once he/she starts writing, invalid label disappears
            username.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    invailduser.setVisible(false);
                }
            });

         
        }
        else if (!valid.validatePassword(password.getText(), confirmpassword.getText())) {
            invaildpass.setVisible(true);
            //Same with password
            password.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    invaildpass.setVisible(false);
                }

            });
          
        }

        else if (!valid.matchpass(password.getText(), confirmpassword.getText())) {
            passnotmatch.setVisible(true);

            //Same with password
            password.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    passnotmatch.setVisible(false);
                }

            });
          
        }else{
            MainScreen.session.signUpRequest(username.getText(), password.getText());
            //changeToLoginScreen(event);
        }
          
      
    }
    
    public void signUpFailure(){
        passnotmatch.setVisible(true);
    }
    
       @FXML
    private void signUpOnHover(MouseEvent event){
        signUp.setPrefWidth(163);
        signUp.setPrefHeight(35);
           audio("btnHover.mp3");
    }
    
    @FXML
    private void signUpOnExit(MouseEvent event){
        signUp.setPrefWidth(153);
        signUp.setPrefHeight(25); 
    }
    
    
    @FXML
    private void signUpOnPress(MouseEvent event){
         signUp.setPrefWidth(153);
        signUp.setPrefHeight(25); 
           audio("btnClick.mp3");
    }
    
    private void signUpOnRelease(MouseEvent event){
         signUp.setPrefWidth(163);
        signUp.setPrefHeight(35);
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
