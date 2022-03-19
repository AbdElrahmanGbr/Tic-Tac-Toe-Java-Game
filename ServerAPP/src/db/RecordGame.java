
package db;


public class RecordGame extends Game{
    int gameID;
    String[] xPositions;
    String[] oPositions;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String[] getxPositions() {
        return xPositions;
    }

    public void setxPositions(String[] xPositions) {
        this.xPositions = xPositions;
    }

    public String[] getoPositions() {
        return oPositions;
    }

    public void setoPositions(String[] oPositions) {
        this.oPositions = oPositions;
    }

    public int getPlayer1() {
        return player1;
    }

    public void setPlayer1(int player1) {
        this.player1 = player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
    
    
}
