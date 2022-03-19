
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
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


public class SingleGameScreenHardController implements Initializable {
    @FXML
    private Button backButton, resetButton, screenshotBtn;
    
    Dialog<ButtonType> dialog = new Dialog<>(); 
            
    @FXML
    private ImageView b0, b1, b2, b3, b4, b5, b6, b7, b8;
    
    @FXML
    private Label ur_score, cump_score;
    
    private int lockButtons = 0;
    
    Image X = new Image(getClass().getResourceAsStream("/images/x.png"));
    Image O = new Image(getClass().getResourceAsStream("/images/oO.png"));
    
    
    private int your_score=0;
    private int computer_score=0;
    private int flage=1;
    
    private int buttonPosition[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    
    private int oPlayerWon[] = {0, 0, 0, 0, 0, 0, 0, 0, 0}; //player
    private int xPlayerWon[] = {0, 0, 0, 0, 0, 0, 0, 0, 0}; //computer
    
    
    
    private void updateComputerScore(){
        computer_score++;
        String cScore = ""+computer_score;
        cump_score.setText(cScore);
    }
    
    private void updatePlayerScore (){
        your_score++;
        String score = ""+your_score;
        ur_score.setText(score);
    }
    
    private int checkFillPositions(){
        int counter = 0;
        for (int i=0; i<buttonPosition.length; i++){
            if (buttonPosition[i]==1){
                counter +=1;
            }
        }
        return counter;
    }
    
    private void fillPositions(){
        for (int i=0; i<buttonPosition.length; i++){
            buttonPosition[i]=1;
        }
    }
    
    private void drawAction() throws IOException{
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/draw.fxml"));
        DialogPane winner = fxmlLoader.load();
        
        dialog.setDialogPane(winner);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE); 
        dialog.setTitle("Draw");
        dialog.show();
        audio("Draw2.mp3");
    }
    
    private void oWinnerAction() throws IOException{
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/xWinner.fxml"));
        DialogPane winner = fxmlLoader.load();
        
        dialog.setDialogPane(winner);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE); 
        dialog.setTitle("Winner");
        dialog.show();
        audio("Winner2.mp3");
    }
    
    
    private void xWinnerAction() throws IOException{
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/oWinner.fxml"));
        DialogPane winner = fxmlLoader.load();
        
        dialog.setDialogPane(winner);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/images/icon.png").toString()));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE); 
        dialog.setTitle("Game Over");
        dialog.show();
        audio("Loser.mp3");
    }
     
    private void checkPlayer(){
        int oResult= oPlayerWonGame();
        if (oResult == 1){
            try {
                flage=0;
                updatePlayerScore();

                fillPositions();
                oWinnerAction();
                
            } catch (IOException ex) {
                Logger.getLogger(SingleGameScreenHardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
           
    private void computer (){
        if(checkFillPositions()!=9){
            computerPlayer();
        }
        
        int xResult= computerWonGame();
        if (xResult == 1){
            try {
                flage=0;
                updateComputerScore();

                fillPositions();
                xWinnerAction();
                
            } catch (IOException ex) {
                Logger.getLogger(SingleGameScreenHardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    private void checkTie (){
        if (flage==1 && checkFillPositions()==9){
            try {
                fillPositions();
                drawAction();
            } catch (IOException ex) {
                Logger.getLogger(SingleGameScreenHardController.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
    }
    
    @FXML
    private void b0ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[0]==0){ 
                b0.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[0]=1;
                oPlayerWon[0]=1;

                checkPlayer();

                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b1ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[1]==0){ 
                b1.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[1]=1;
                oPlayerWon[1]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b2ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[2]==0){
                b2.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[2]=1;
                oPlayerWon[2]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b3ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[3]==0){ 
                b3.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[3]=1;
                oPlayerWon[3]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b4ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[4]==0){
                b4.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[4]=1;
                oPlayerWon[4]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b5ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[5]==0){  
                b5.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[5]=1;
                oPlayerWon[5]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b6ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[6]==0){
                b6.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[6]=1;
                oPlayerWon[6]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b7ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[7]==0){    
                b7.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[7]=1;
                oPlayerWon[7]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
    @FXML
    private void b8ButtonAction(MouseEvent event) {
        if (lockButtons==0){
            if (buttonPosition[8]==0){ 
                b8.setImage(O);
                audio("btnClick.mp3");
                buttonPosition[8]=1;
                oPlayerWon[8]=1;

                checkPlayer();
                lockButtons = 1;
                new Thread(() -> {
                    try {
                        Thread.sleep(600);
                        Platform.runLater(() -> {
                            lockButtons=0;
                            computer();
                            checkTie();
                        });

                    } catch (InterruptedException ex) {
                        Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            else{
                System.out.println("this button is already used!!!!!");
            }
        }
    }
    
   
    
    private void computerPlayer(){
        Vector<Integer> emptyPositions = new Vector<>(); //empty postions
        
        for (int index=0; index < buttonPosition.length; index++){
            if (buttonPosition[index] == 0){
                emptyPositions.add(index);
            }
        }
        
        int computerPosition = (int) (Math.random()*emptyPositions.size()); //To get Random position
        
        if (((xPlayerWon[0] == 1 && xPlayerWon[1] == 1)|| 
             (xPlayerWon[4] == 1 && xPlayerWon[6] == 1)||
             (xPlayerWon[5] == 1 && xPlayerWon[8] == 1)) 
             && oPlayerWon[2]== 0 && xPlayerWon[2]== 0){
            b2.setImage(X);
            xPlayerWon[2]=1;
            buttonPosition[2]=1;
        }
        else if (((xPlayerWon[1] == 1 && xPlayerWon[2] == 1)||
                  (xPlayerWon[4] == 1 && xPlayerWon[8] == 1)||
                  (xPlayerWon[3] == 1 && xPlayerWon[6] == 1)) 
                  && oPlayerWon[0]== 0 && xPlayerWon[0]== 0){
            b0.setImage(X);
            xPlayerWon[0]=1;
            buttonPosition[0]=1;
        }
        else if (((xPlayerWon[0] == 1 && xPlayerWon[2] == 1)||
                  (xPlayerWon[4] == 1 && xPlayerWon[7] == 1)) 
                  && oPlayerWon[1]== 0 && xPlayerWon[1]== 0){
            b1.setImage(X);
            xPlayerWon[1]=1;
            buttonPosition[1]=1;
        }
        else if (((xPlayerWon[3] == 1 && xPlayerWon[4] == 1)||
                  (xPlayerWon[2] == 1 && xPlayerWon[8] == 1))
                  && oPlayerWon[5]== 0 && xPlayerWon[5]== 0){
            b5.setImage(X);
            xPlayerWon[5]=1;
            buttonPosition[5]=1;
        }
        else if (((xPlayerWon[3] == 1 && xPlayerWon[5] == 1)||
                  (xPlayerWon[2] == 1 && xPlayerWon[6] == 1)||
                  (xPlayerWon[0] == 1 && xPlayerWon[8] == 1)||
                  (xPlayerWon[1] == 1 && xPlayerWon[7] == 1))
                  && oPlayerWon[4]== 0 && xPlayerWon[4]== 0){
            b4.setImage(X);
            xPlayerWon[4]=1;
            buttonPosition[4]=1;
        }
        else if (((xPlayerWon[4] == 1 && xPlayerWon[5] == 1)||
                  (xPlayerWon[0] == 1 && xPlayerWon[6] == 1))  
                  && oPlayerWon[3]== 0 && xPlayerWon[3]== 0){
            b3.setImage(X);
            xPlayerWon[3]=1;
            buttonPosition[3]=1;
        }
        else if (((xPlayerWon[6] == 1 && xPlayerWon[7] == 1)||
                  (xPlayerWon[0] == 1 && xPlayerWon[4] == 1)||
                  (xPlayerWon[2] == 1 && xPlayerWon[5] == 1))
                  && oPlayerWon[8]== 0 && xPlayerWon[8]== 0){
            b8.setImage(X);
            xPlayerWon[8]=1;
            buttonPosition[8]=1;
        }
        else if (((xPlayerWon[7] == 1 && xPlayerWon[8] == 1)||
                  (xPlayerWon[2] == 1 && xPlayerWon[4] == 1)||
                  (xPlayerWon[0] == 1 && xPlayerWon[3] == 1))
                  && oPlayerWon[6]== 0 && xPlayerWon[6]== 0){
            b6.setImage(X);
            xPlayerWon[6]=1;
            buttonPosition[6]=1;
        }
        else if (((xPlayerWon[6] == 1 && xPlayerWon[8] == 1)||
                  (xPlayerWon[1] == 1 && xPlayerWon[4] == 1)) 
                  && oPlayerWon[7]== 0 && xPlayerWon[7]== 0){
            b7.setImage(X);
            xPlayerWon[7]=1;
            buttonPosition[7]=1;
        }
        else if (((oPlayerWon[1] == 1 && oPlayerWon[2] == 1)||
                  (oPlayerWon[4] == 1 && oPlayerWon[8] == 1)||
                  (oPlayerWon[3] == 1 && oPlayerWon[6] == 1))
                  && xPlayerWon[0]== 0 && oPlayerWon[0]== 0){
            b0.setImage(X);
            xPlayerWon[0]=1;
            buttonPosition[0]=1;
        }
        else if (((oPlayerWon[0] == 1 && oPlayerWon[1] == 1)||
                  (oPlayerWon[4] == 1 && oPlayerWon[6] == 1)||
                  (oPlayerWon[5] == 1 && oPlayerWon[8] == 1))
                  && xPlayerWon[2]== 0 && oPlayerWon[2]== 0){
            b2.setImage(X);
            xPlayerWon[2]=1;
            buttonPosition[2]=1;
        }
        else if (((oPlayerWon[0] == 1 && oPlayerWon[2] == 1)||
                  (oPlayerWon[4] == 1 && oPlayerWon[7] == 1))
                  && xPlayerWon[1]== 0 && oPlayerWon[1]== 0){
            b1.setImage(X);
            xPlayerWon[1]=1;
            buttonPosition[1]=1;
        }
        else if (((oPlayerWon[3] == 1 && oPlayerWon[4] == 1)||
                  (oPlayerWon[2] == 1 && oPlayerWon[8] == 1))
                  && xPlayerWon[5]== 0 && oPlayerWon[5]== 0){
            b5.setImage(X);
            xPlayerWon[5]=1;
            buttonPosition[5]=1;
        }
        else if (((oPlayerWon[3] == 1 && oPlayerWon[5] == 1)||
                  (oPlayerWon[0] == 1 && oPlayerWon[8] == 1)||
                  (oPlayerWon[2] == 1 && oPlayerWon[6] == 1)||
                  (oPlayerWon[1] == 1 && oPlayerWon[7] == 1))
                  && xPlayerWon[4]== 0 && oPlayerWon[4]== 0){
            b4.setImage(X);
            xPlayerWon[4]=1;
            buttonPosition[4]=1;
        }
        else if (((oPlayerWon[4] == 1 && oPlayerWon[5] == 1)||
                  (oPlayerWon[0] == 1 && oPlayerWon[6] == 1))
                   && xPlayerWon[3]== 0 && oPlayerWon[3]== 0){
            b3.setImage(X);
            xPlayerWon[3]=1;
            buttonPosition[3]=1;
        }
        else if (((oPlayerWon[6] == 1 && oPlayerWon[7] == 1)||
                  (oPlayerWon[0] == 1 && oPlayerWon[4] == 1)||
                  (oPlayerWon[2] == 1 && oPlayerWon[5] == 1))
                  && xPlayerWon[8]== 0 && oPlayerWon[8]== 0){
            b8.setImage(X);
            xPlayerWon[8]=1;
            buttonPosition[8]=1;
        }
        else if (((oPlayerWon[7] == 1 && oPlayerWon[8] == 1)||
                  (oPlayerWon[2] == 1 && oPlayerWon[4] == 1)||
                  (oPlayerWon[0] == 1 && oPlayerWon[3] == 1))
                  && xPlayerWon[6]== 0 && oPlayerWon[6]== 0){
            b6.setImage(X);
            xPlayerWon[6]=1;
            buttonPosition[6]=1;
        }
        else if (((oPlayerWon[6] == 1 && oPlayerWon[8] == 1)||
                  (oPlayerWon[1] == 1 && oPlayerWon[4] == 1))
                  && xPlayerWon[7]== 0 && oPlayerWon[7]== 0){
            b7.setImage(X);
            xPlayerWon[7]=1;
            buttonPosition[7]=1;
        }
        else{ 
            if (emptyPositions.get(computerPosition) == 0){
                b0.setImage(X);
            }           
            else if (emptyPositions.get(computerPosition) == 1){
                b1.setImage(X);
            }
            else if (emptyPositions.get(computerPosition) == 2){
                b2.setImage(X);
            }
            else if (emptyPositions.get(computerPosition) == 3){
                b3.setImage(X);
            }
            else if (emptyPositions.get(computerPosition) == 4){
                b4.setImage(X);
            }
            else if (emptyPositions.get(computerPosition) == 5){
                b5.setImage(X);
            }
            else if (emptyPositions.get(computerPosition) == 6){
                b6.setImage(X);
            }
            else if (emptyPositions.get(computerPosition) == 7){
                b7.setImage(X);
            }
            else if (emptyPositions.get(computerPosition) == 8){
                b8.setImage(X);
            }
            xPlayerWon[emptyPositions.get(computerPosition)]=1;
            buttonPosition[emptyPositions.get(computerPosition)]=1;
        }
        audio("btnClick.mp3");
    }
    
    private int oPlayerWonGame(){
        if ((oPlayerWon[0]==1 && oPlayerWon[1]==1 && oPlayerWon[2]==1)||
                (oPlayerWon[3]==1 && oPlayerWon[4]==1 && oPlayerWon[5]==1)||
                (oPlayerWon[6]==1 && oPlayerWon[7]==1 && oPlayerWon[8]==1)||
                (oPlayerWon[0]==1 && oPlayerWon[3]==1 && oPlayerWon[6]==1)||
                (oPlayerWon[1]==1 && oPlayerWon[4]==1 && oPlayerWon[7]==1)||
                (oPlayerWon[2]==1 && oPlayerWon[5]==1 && oPlayerWon[8]==1)||
                (oPlayerWon[0]==1 && oPlayerWon[4]==1 && oPlayerWon[8]==1)||
                (oPlayerWon[2]==1 && oPlayerWon[4]==1 && oPlayerWon[6]==1)){
            return 1;
        }
        return 0;
    }
    
    int computerWonGame(){
        if ((xPlayerWon[0]==1 && xPlayerWon[1]==1 && xPlayerWon[2]==1)||
                (xPlayerWon[3]==1 && xPlayerWon[4]==1 && xPlayerWon[5]==1)||
                (xPlayerWon[6]==1 && xPlayerWon[7]==1 && xPlayerWon[8]==1)||
                (xPlayerWon[0]==1 && xPlayerWon[3]==1 && xPlayerWon[6]==1)||
                (xPlayerWon[1]==1 && xPlayerWon[4]==1 && xPlayerWon[7]==1)||
                (xPlayerWon[2]==1 && xPlayerWon[5]==1 && xPlayerWon[8]==1)||
                (xPlayerWon[0]==1 && xPlayerWon[4]==1 && xPlayerWon[8]==1)||
                (xPlayerWon[2]==1 && xPlayerWon[4]==1 && xPlayerWon[6]==1)){
            return 1;
        }
        return 0;
    }
    
    @FXML
    private void playAgainButtonAction(ActionEvent event) {
        b0.setImage(null);
        b1.setImage(null);        
        b2.setImage(null);        
        b3.setImage(null);        
        b4.setImage(null);        
        b5.setImage(null);        
        b6.setImage(null);        
        b7.setImage(null);        
        b8.setImage(null);        
        flage=1;
        for (int i=0 ; i<9 ; i++){
            buttonPosition[i] = 0;
            xPlayerWon[i] = 0;
            oPlayerWon[i] = 0;
        }
        lockButtons = 1;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    lockButtons=0;
                    computer();
                });

            } catch (InterruptedException ex) {
                Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    
    @FXML
    private void BackButtonAction(ActionEvent event) throws IOException {
        MainScreen.session.changeScene("/fxml/SingleModeMenu.fxml");
    }
    
    @FXML
    private void ScreenshotButtonAction(ActionEvent event) throws AWTException, IOException {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
 
     
   
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
    
    
    
    //Sound Effects and Animation
     @FXML 
    private void backOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    @FXML
    private void backOnPress(MouseEvent event){
        backButton.setPrefWidth(72);
        backButton.setPrefHeight(15);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void backOnRelease(MouseEvent event){
        backButton.setPrefWidth(92);
        backButton.setPrefHeight(25); 
    }
    
    @FXML 
    private void resetOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    @FXML
    private void resetOnPress(MouseEvent event){
        resetButton.setPrefWidth(72);
        resetButton.setPrefHeight(15);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void resetOnRelease(MouseEvent event){
        resetButton.setPrefWidth(92);
        resetButton.setPrefHeight(25); 
    }
    
    @FXML 
    private void screenOnHover(MouseEvent event){
        audio("btnHover.mp3");
    }
    
    @FXML
    private void screenOnPress(MouseEvent event){
        screenshotBtn.setPrefWidth(138);
        screenshotBtn.setPrefHeight(21);
        audio("btnClick.mp3");
    }
    
    @FXML
    private void screenOnRelease(MouseEvent event){
        screenshotBtn.setPrefWidth(158);
        screenshotBtn.setPrefHeight(31); 
      }
    
    private void audio(String soundEffect){
        Media sound = new Media(getClass().getResource("/audio/"+soundEffect).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lockButtons = 1;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    lockButtons = 0;
                    computer();
                });

            } catch (InterruptedException ex) {
                Logger.getLogger(SingleGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }    
    
}
