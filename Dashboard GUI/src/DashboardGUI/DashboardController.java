
package DashboardGUI;


import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;




public class DashboardController implements Initializable {
    
    @FXML
    private ImageView StartBtn;
    @FXML
    private ImageView StopBtn;
    
    
    
    @FXML
    TableView tableView;
    
    @FXML
    TableColumn players, status;
    @FXML
    TableColumn score; 
    
    private ObservableList<player> PlayersList;
    
    @FXML
    public void disableStartButton(){
        StartBtn.setDisable(true);
    }
    
     @FXML
    public void disableStopButton(){
        StopBtn.setDisable(true);
    }
    
    
    @FXML
    public void insertOnlinePlayers(ObservableList<player> list){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tableView.setItems(list);
            }
        });
    }
    
    
    //Animation and Sound Effects
    @FXML
    public void onStartHover(MouseEvent event){
        StartBtn.setFitWidth(143);
        StartBtn.setFitHeight(115);
        audio("btnHover.mp3");
        
    }
    
    @FXML
    public void onStartExit(MouseEvent event){
        StartBtn.setFitWidth(123);
        StartBtn.setFitHeight(125);
    }
    
    @FXML
    public void startOnPress(MouseEvent event){
        DashboardApplication.session.startServer();
        StartBtn.setFitWidth(113);
        StartBtn.setFitHeight(115);
        audio("btnClick.mp3");
    }
    
    @FXML
    public void startOnRelease(MouseEvent event){
        StartBtn.setFitWidth(143);
        StartBtn.setFitHeight(115);
    }
    
    @FXML
    public void onStopHover(MouseEvent event){
        StopBtn.setFitWidth(143);
        StopBtn.setFitHeight(115);
        audio("btnHover.mp3");
    }
    
    @FXML
    public void onStopExit(MouseEvent event){
        StopBtn.setFitWidth(123);
        StopBtn.setFitHeight(125);
    }
    
    @FXML
    public void stopOnPress(MouseEvent event){
        DashboardApplication.session.stopServer();
        StopBtn.setFitWidth(113);
        StopBtn.setFitHeight(130);
        audio("btnClick.mp3");
    }
    
    @FXML
    public void stopOnRelease(MouseEvent event){
        StopBtn.setFitWidth(143);
        StopBtn.setFitHeight(115);
    }
    

    private void audio(String soundEffect){
        Media sound = new Media(getClass().getResource("/audio/"+soundEffect).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        players = (TableColumn<player,String>)tableView.getColumns().get(0);
        score = (TableColumn<player,Integer>)tableView.getColumns().get(1);
        status = (TableColumn<player,Integer>)tableView.getColumns().get(2);
        players.setCellValueFactory(new PropertyValueFactory<player,String>("name"));
        score.setCellValueFactory(new PropertyValueFactory<player,Integer>("score"));
        status.setCellValueFactory(new PropertyValueFactory<player,Integer>("status"));
        PlayersList = FXCollections.observableArrayList();
        tableView.setItems(PlayersList);
    }    
    
}
