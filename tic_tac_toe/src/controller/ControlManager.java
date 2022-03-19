
package controller;

import javafx.fxml.FXMLLoader;


public class ControlManager {
    private LeaderBoardController leaderBoardController;
    private LoginController loginController ;
    private PlayerVsPlayerController playerVsPlayerController;
    private PlayerInvitationController invitationController;
    private RecordedGames recordedGames;
    private SignUpController signUpController;

    public LeaderBoardController getLeaderBoardController() {
        return leaderBoardController;
    }

    public void setLeaderBoardController(FXMLLoader loader) {
        this.leaderBoardController = (LeaderBoardController)loader.getController();
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(FXMLLoader loader) {
        this.loginController = (LoginController)loader.getController();
    }

    public PlayerVsPlayerController getPlayerVsPlayerController() {
        return playerVsPlayerController;
    }

    public void setPlayerVsPlayerController(FXMLLoader loader) {
        this.playerVsPlayerController = (PlayerVsPlayerController)loader.getController();
    }

    public PlayerInvitationController getInvitationController() {
        return invitationController;
    }

    public void setInvitationController(FXMLLoader loader) {
        this.invitationController = (PlayerInvitationController)loader.getController();
    }

    public RecordedGames getRecordedGames() {
        return recordedGames;
    }

    public void setRecordedGames(FXMLLoader loader) {
        this.recordedGames = (RecordedGames)loader.getController();
    }

    public SignUpController getSignUpController() {
        return signUpController;
    }

    public void setSignUpController(FXMLLoader loader) {
        this.signUpController = (SignUpController)loader.getController();
    }
}
