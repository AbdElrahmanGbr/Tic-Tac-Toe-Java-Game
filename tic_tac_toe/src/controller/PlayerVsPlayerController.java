package controller;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


public class PlayerVsPlayerController implements Initializable {
    
    @FXML
    private ImageView b0, b1, b2, b3, b4, b5, b6, b7, b8;
    
    @FXML
    private Button recordButton, screenshotBtn;
    
    @FXML
    private Label xPlayerName, oPlayerName;
    
    @FXML
    private VBox vboxMessages;
    
    @FXML
    private ScrollPane spMain;
    
    @FXML
    private ImageView sendArrow;
    
    
    @FXML
    private TextField textMessage;
    
    Dialog<ButtonType> dialog;
    
    List<ImageView> myGird;
    List<Image> XOImages;
    List<Integer> myCells,otherPlayerCells;
    private int choice = 0;
    private boolean myTurn=true;
    private int otherPlayerid;
    private boolean refused = false;
    private boolean startNewGame = false;

    public void setRefused(boolean refused) {
        this.refused = refused;
    }

    public void setOtherPlayerid(int otherPlayerid) {
        this.otherPlayerid = otherPlayerid;
    }
    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
    
    
    private void setImage(int cell,int XO){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                myGird.get(cell).setImage(XOImages.get(XO));
            }
        });
    }
    
    @FXML
    private void b0ButtonAction(MouseEvent event){
        addMove(0);
        
    }
    
    @FXML
    private void b1ButtonAction(MouseEvent event){
        addMove(1);
    }
    
    @FXML
    private void b2ButtonAction(MouseEvent event) {
        addMove(2);
    }
    
    @FXML
    private void b3ButtonAction(MouseEvent event){
        addMove(3);
    }
    
    @FXML
    private void b4ButtonAction(MouseEvent event){
        addMove(4);
    }
    
    @FXML
    private void b5ButtonAction(MouseEvent event){
        addMove(5);
    }
    
    @FXML
    private void b6ButtonAction(MouseEvent event) {
        addMove(6);
    }
    
    @FXML
    private void b7ButtonAction(MouseEvent event){
        addMove(7);
    }
    
    @FXML
    private void b8ButtonAction(MouseEvent event){
        addMove(8);
    }
    
    @FXML
    private void BackButtonAction(ActionEvent event)  {
        MainScreen.session.controlManager.setInvitationController(MainScreen.session.changeScene("/fxml/PlayerInvitationScreen.fxml"));
        MainScreen.session.viewOnlinePlayers = true;
        MainScreen.session.getOnlinePlayersRequest();
    }
    
    @FXML
    private void recordButtonAction(ActionEvent event)  {
        if(!myTurn)
            return;
        int p1 = MainScreen.session.player.getID(),p2 = otherPlayerid;
        String X=convertMoves(myCells),O=convertMoves(otherPlayerCells);
        if(choice==1){
            p2 = MainScreen.session.player.getID();
            p1 = otherPlayerid;
            O=convertMoves(myCells);
            X=convertMoves(otherPlayerCells);
        }
        MainScreen.session.recordGameRequest(p1, p2, X, O);
    }
    
    private String convertMoves(List<Integer> moves){
        
        if(moves.size()==0)
            return "9-9-9-9";
        
        String m ;
        m = moves.get(0).toString();
        
        for(int i=1;i<moves.size();i++)
            m = m+"-"+moves.get(i).toString();
        
        for(int i=0;i<4-moves.size();i++)
            m = m+"-9";
        
        return m;
    }
        
    private void sendMessage(){
        String messageToSend = textMessage.getText();
        if (!messageToSend.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            
            Text text = new Text(messageToSend);
            TextFlow textFlow = new TextFlow(text);
            
            textFlow.setStyle("-fx-color: rgb(239,242,255);" + 
                    "-fx-background-color: rgb(15,125,242);" +
                    "-fx-background-radius: 20px;");
            
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.945, 0.996));
            
            hBox.getChildren().add(textFlow);
            vboxMessages.getChildren().add(hBox);
            
            MainScreen.session.sendMessageRequest(textMessage.getText());
            textMessage.clear();
        }
    }
    
    @FXML
    private void sendMessageArrow(MouseEvent event){
        sendMessage();
    }
    
    @FXML
    public void sendMessageUponPressingEnter(KeyEvent event){
         if(event.getCode() == KeyCode.ENTER){
              sendMessage();
         }
    }
    
    public void recieveMessage(String msg){
        
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            Text text = new Text(msg);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-color: rgb(233,233,235);" + 
                    "-fx-background-color: rgb(208, 216, 207);"+ 
                    "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5, 10, 5, 10));

            hBox.getChildren().add(textFlow);
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    audio("msg3.mp3");
                    vboxMessages.getChildren().add(hBox);
            }
        });
    }
    
    private void addMove(int cell){
        if(!myTurn || myCells.contains(cell) || otherPlayerCells.contains(cell))
            return;
        myCells.add(cell);
        setImage(cell,choice);
        audio("btnClick.mp3");
        myTurn = false;
        MainScreen.session.addMoveRequest(cell, choice);
        if(checkWin(myCells)){
            MainScreen.session.insertDoneGameRequest(MainScreen.session.player.getID(), otherPlayerid, false);
            MainScreen.session.player.setScore(MainScreen.session.player.getScore()+5);
            endGameDialog("Winner", "/fxml/xWinner.fxml");
            MainScreen.session.player.setWins(MainScreen.session.player.getWins()+1);
            audio("Winner2.mp3");
        }
        else if(myCells.size()+otherPlayerCells.size()==9){
            MainScreen.session.insertDoneGameRequest(MainScreen.session.player.getID(), otherPlayerid, true);
            endGameDialog("Draw", "/fxml/draw.fxml");
            MainScreen.session.player.setTies(MainScreen.session.player.getTies()+1);
            audio("Draw2.mp3");
        }
    }
    
    private void resetBoard(){
        myCells.clear();
        otherPlayerCells.clear();
        for(ImageView I : myGird)
            I.setImage(null);
    }
    
    public void startNewGame(){
        if(choice==0){
            myTurn=true;
        }
        startNewGame = true;
    }
    
    public void replayDialog(){
        try {
            Dialog<ButtonType> dialog = new Dialog<>();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/InvitationDialog.fxml"));
            DialogPane winner = fxmlLoader.load();
            
            dialog.setDialogPane(winner);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            dialog.setTitle("Game Replay");
            Label content = new Label("Do you want to re-match");
            content.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5px");
            dialog.setGraphic(content);
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
            Optional<ButtonType> result = dialog.showAndWait();
            if(result.get()==ButtonType.YES){
                MainScreen.session.replayReplyRequest(true);
                resetBoard();
                if(!startNewGame)
                    myTurn = false;
            }
            else{
                MainScreen.session.replayReplyRequest(false);
                MainScreen.session.controlManager.setInvitationController(MainScreen.session.changeScene("/fxml/PlayerInvitationScreen.fxml"));
                MainScreen.session.viewOnlinePlayers = true;
                MainScreen.session.getOnlinePlayersRequest();
            }
        } catch (IOException ex) {
            Logger.getLogger(PlayerVsPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public  void setMove(int cell,int choice){
        otherPlayerCells.add(cell);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setImage(cell,choice);
                audio("btnClick.mp3");
            }
        });
        myTurn = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(checkWin(otherPlayerCells)){
                    endGameDialog("Game Over", "/fxml/oWinner.fxml");
                    MainScreen.session.player.setLosses(MainScreen.session.player.getLosses()+1);
                    audio("Loser2.mp3");
}
                else if(myCells.size()+otherPlayerCells.size()==9){
                    endGameDialog("Draw", "/fxml/draw.fxml");
                    MainScreen.session.player.setTies(MainScreen.session.player.getTies()+1);
                   audio("Draw2.mp3");
                }
            }
        });
    }
    
    @FXML
    private void ScreenShot(MouseEvent event) throws IOException, AWTException {
      
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
      //WritableImage snapshot = stage.getScene().snapshot(null);
     
   
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(null); 
        try {
          
            ImageIO.write((BufferedImage) image, "png", new File(file.getAbsolutePath()));
        } catch (IOException ex) {
            System.out.println("Failed to save image!");
        }
        {
            System.out.println("No file choosen!");
        }
    }
    
  
    
    
    private void endGameDialog(String title,String xml){
        try {
            dialog = new Dialog<>();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(xml));
            DialogPane winner = fxmlLoader.load();
            
            dialog.setDialogPane(winner);
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.setTitle(title);
            dialog.showAndWait();
            if(refused)
                return;
            replayDialog();
        } catch (IOException ex) {
            Logger.getLogger(PlayerVsPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkWin(List<Integer> cells){
        if(cells.contains(0)&&cells.contains(1)&&cells.contains(2)||
            cells.contains(3)&&cells.contains(4)&&cells.contains(5)||
            cells.contains(6)&&cells.contains(7)&&cells.contains(8)||
            cells.contains(0)&&cells.contains(3)&&cells.contains(6)||
            cells.contains(1)&&cells.contains(4)&&cells.contains(7)||
            cells.contains(2)&&cells.contains(5)&&cells.contains(8)||
            cells.contains(0)&&cells.contains(4)&&cells.contains(8)||
            cells.contains(2)&&cells.contains(4)&&cells.contains(6))
            return true;
        else
            return false;
    } 
    
    public void setPlayersNames(String X,String O){
        xPlayerName.setText(X);
        oPlayerName.setText(O);
    }
    
    //Animation and Sound Effects
    @FXML 
    private void recordOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    @FXML
    private void recordOnPress(MouseEvent event){
        recordButton.setPrefWidth(114);
        recordButton.setPrefHeight(20);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void recordOnRelease(MouseEvent event){
        recordButton.setPrefWidth(124);
        recordButton.setPrefHeight(30); 
    }
    
    @FXML 
    private void screenOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    @FXML
    private void screenOnPress(MouseEvent event){
        screenshotBtn.setPrefWidth(105);
        screenshotBtn.setPrefHeight(20);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void screenOnRelease(MouseEvent event){
        screenshotBtn.setPrefWidth(115);
        screenshotBtn.setPrefHeight(30); 
      }
    
    private void audio(String soundEffect){
        Media sound = new Media(getClass().getResource("/audio/"+soundEffect).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        // TODO
        myGird = new ArrayList<ImageView>();
        myGird.add(b0);
        myGird.add(b1);
        myGird.add(b2);
        myGird.add(b3);
        myGird.add(b4);
        myGird.add(b5);
        myGird.add(b6);
        myGird.add(b7);
        myGird.add(b8);
        
        XOImages = new ArrayList<Image>();
        XOImages.add(new Image(getClass().getResourceAsStream("/images/x.png")));
        XOImages.add(new Image(getClass().getResourceAsStream("/images/oO.png")));
        
        myCells = new ArrayList<Integer>();
        otherPlayerCells = new ArrayList<Integer>();
        
        // This part to make ScrollPane scroll down when send messages
        vboxMessages.heightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                spMain.setVvalue((Double) newValue);
            }
            
        });
    }    
    
    public void resume(RecordedGame game){
        if(game.getPlayer1ID()==MainScreen.session.player.getID()){
            setCells(myCells, game.getXMoves());
            setCells(otherPlayerCells, game.getOMoves());
            otherPlayerid = game.getPlayer2ID();
            xPlayerName.setText(game.getPlayer1Name());
            oPlayerName.setText(game.getPlayer2Name());
            choice = 0;
            for(int i : myCells)
                setImage(i, 0);
            for(int i : otherPlayerCells)
                setImage(i, 1);
        }else{
            setCells(otherPlayerCells, game.getXMoves());
            setCells(myCells, game.getOMoves());
            otherPlayerid = game.getPlayer1ID();
            xPlayerName.setText(game.getPlayer1Name());
            oPlayerName.setText(game.getPlayer2Name());
            choice = 1;
            for(int i : myCells)
                setImage(i, 1);
            for(int i : otherPlayerCells)
                setImage(i, 0);
        }
        
        if((myCells.size()+otherPlayerCells.size())%2==0 && choice==0 || (myCells.size()+otherPlayerCells.size())%2!=0 && choice==1)
            myTurn = true;
        else
            myTurn = false;
    }
    
    private void setCells(List<Integer>cells,String moves){
        String[] c = moves.split("-");
        for(String m : c){
            if(m.equals("9"))
                break;
            cells.add(Integer.parseInt(m));
        }
    }
    
}
