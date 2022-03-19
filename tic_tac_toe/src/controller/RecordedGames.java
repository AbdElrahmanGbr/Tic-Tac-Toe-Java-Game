package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class RecordedGames implements Initializable {
    @FXML
    private Button BackButton, resumeButton;
    
    @FXML
    private TableView gamesTableView;
    
    private TableColumn<Player,Integer> Score;
    
    private TableColumn<Player,String> OnlinePlayers;
    
    private ObservableList<Player> PlayersList;
    
    @FXML
    private TableView playersTableView;
    
    private TableColumn<RecordedGame,String> Games;
    
    private ObservableList<Player> GamesList;
    
    public boolean wait;
    
    @FXML
    private void BackButtonAction(ActionEvent event) throws IOException{
        MainScreen.session.controlManager.setInvitationController(MainScreen.session.changeScene("/fxml/PlayerInvitationScreen.fxml"));
        MainScreen.session.viewOnlinePlayers = true;
        MainScreen.session.viewRecordedGames = false;
        MainScreen.session.getOnlinePlayersRequest();
    }    

    
    @FXML
    private void resumeGameButtonAction(ActionEvent event){
        if(wait)
            return;
        RecordedGame recordedGame = (RecordedGame)gamesTableView.getSelectionModel().getSelectedItem();
        if(recordedGame!=null){
            wait = true;
            MainScreen.session.resumeGameSendRequest(recordedGame);
            //disableInvitation();
        }
    }

    
    //Animation and Sound Effects
     @FXML
    private void backOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    @FXML
    private void backOnPress(MouseEvent event){
        BackButton.setPrefWidth(52);
        BackButton.setPrefHeight(15); 
        audio("btnClick.mp3");
    }
    
    @FXML
    private void backOnRelease(MouseEvent event){
        BackButton.setPrefWidth(72);
        BackButton.setPrefHeight(25); 
    }
    
    
    @FXML
    private void resumeOnHover(MouseEvent event){
        resumeButton.setPrefWidth(208);
        resumeButton.setPrefHeight(49);
        audio("btnHover.mp3");
    }
    
    @FXML
    private void resumeOnExit(MouseEvent event){
        resumeButton.setPrefWidth(188);
        resumeButton.setPrefHeight(39);
    }
    
    @FXML
    private void resumeOnPress(MouseEvent event){
        resumeButton.setPrefWidth(188);
        resumeButton.setPrefHeight(39);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void resumeOnRelease(MouseEvent event){
        resumeButton.setPrefWidth(188);
        resumeButton.setPrefHeight(39);
    }
    
    private void audio(String soundEffect){
        Media sound = new Media(getClass().getResource("/audio/"+soundEffect).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        }

    @FXML
    public void insertOnlinePlayers(ObservableList<Player> list){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playersTableView.setItems(list);
            }
        });
    }
    
    @FXML
    public void insertRecordedGames(ObservableList<RecordedGame> list){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gamesTableView.setItems(list);
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        OnlinePlayers = (TableColumn<Player,String>)playersTableView.getColumns().get(0);
        Score = (TableColumn<Player,Integer>)playersTableView.getColumns().get(1);
        OnlinePlayers.setCellValueFactory(new PropertyValueFactory<Player,String>("username"));
        Score.setCellValueFactory(new PropertyValueFactory<Player,Integer>("score"));
        PlayersList = FXCollections.observableArrayList();
        playersTableView.setItems(PlayersList);
        
        Games = (TableColumn<RecordedGame,String>)gamesTableView.getColumns().get(0);
        Games.setCellValueFactory(new PropertyValueFactory<RecordedGame,String>("OtherPlayerName"));
        GamesList = FXCollections.observableArrayList();
        gamesTableView.setItems(GamesList);

    }
    
    
}
