
package controller;


public class RecordedGame {
    
    private String XMoves,OMoves,Player1Name,Player2Name,OtherPlayerName;
    private int GameID,player1ID,player2ID,OtherPlayerID;

    public RecordedGame(String XMoves, String OMoves, String Player1Name, String Player2Name, String OtherPlayerName, int GameID, int player1ID, int player2ID, int OtherPlayerID) {
        this.XMoves = XMoves;
        this.OMoves = OMoves;
        this.Player1Name = Player1Name;
        this.Player2Name = Player2Name;
        this.OtherPlayerName = OtherPlayerName;
        this.GameID = GameID;
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        this.OtherPlayerID = OtherPlayerID;
    }
    
    public RecordedGame(String XMoves, String OMoves, String Player1Name, String Player2Name, int GameID, int player1ID, int player2ID) {
        this.XMoves = XMoves;
        this.OMoves = OMoves;
        this.Player1Name = Player1Name;
        this.Player2Name = Player2Name;
        this.GameID = GameID;
        this.player1ID = player1ID;
        this.player2ID = player2ID;
    }

    
    
    public String getXMoves() {
        return XMoves;
    }

    public void setXMoves(String XMoves) {
        this.XMoves = XMoves;
    }

    public String getOMoves() {
        return OMoves;
    }

    public void setOMoves(String OMoves) {
        this.OMoves = OMoves;
    }

    

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int GameID) {
        this.GameID = GameID;
    }
    

    public String getOtherPlayerName() {
        return OtherPlayerName;
    }

    public void setOtherPlayerName(String OtherPlayerName) {
        this.OtherPlayerName = OtherPlayerName;
    }

    public int getOtherPlayerID() {
        return OtherPlayerID;
    }

    public void setOtherPlayerID(int OtherPlayerID) {
        this.OtherPlayerID = OtherPlayerID;
    }

    
    public RecordedGame() {
    }


    public String getPlayer1Name() {
        return Player1Name;
    }

    public void setPlayer1Name(String Player1Name) {
        this.Player1Name = Player1Name;
    }

    public String getPlayer2Name() {
        return Player2Name;
    }

    public void setPlayer2Name(String Player2Name) {
        this.Player2Name = Player2Name;
    }

    public int getPlayer1ID() {
        return player1ID;
    }

    public void setPlayer1ID(int player1ID) {
        this.player1ID = player1ID;
    }

    public int getPlayer2ID() {
        return player2ID;
    }

    public void setPlayer2ID(int player2ID) {
        this.player2ID = player2ID;
    }

}
