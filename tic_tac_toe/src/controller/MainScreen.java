package controller;

import ticTac.Connection.Session;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainScreen extends Application {
    
    private Parent root;
    private Scene scene;
    public static Session session;
    public Dialog<String> dialog = new Dialog<String>(); 
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setResizable(false);
        stage.setTitle("Tic Tac Toe");
        session = new Session(stage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/startScreen.fxml"));
        Parent fxmlViewChild = loader.load();
        
        scene = new Scene(fxmlViewChild);
        stage.setScene(scene);
        
        Image icon = new Image("/images/icon.png");
	    stage.getIcons().add(icon);
        
        
        stage.show();
    }
    
  

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    void changeScene(String mainMenufxml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void stop(){
        try {
            super.stop();
            if(session.Connected)
                session.CloseConnection();
            session.stop();
        } catch (Exception ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void inviteDialog(){
        
    }
    
}
