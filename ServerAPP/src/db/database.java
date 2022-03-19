
package db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import serverapplication.ClientHandler;


public class database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tictactoe";
    private static final String USER = "root";
    private static final String PASS = "aG4102015iT";
    
    Connection connection;
    Statement statement;
    PreparedStatement preparedSt;
    ResultSet resultSet;
    
    
    public void connect(){
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
           // System.out.println("Connected Successfully");
        } catch (SQLException ex) {
          //  System.out.println("Connection Failed!");
        }
    }
    
    public void close(){
        try {
            connection.close();
           // System.out.println("Connection Closed Successfully!");
        } catch (SQLException ex) {
          //  System.out.println("Connection Failed to close!");
        }
    }
    
    public int removeRecordedGame(int gameID) {
        int status = 1;
        try {
            String insertNewPlayer = new String("delete from recorded_games where id = "+gameID+";");
            preparedSt = connection.prepareStatement(insertNewPlayer);
            preparedSt.executeUpdate();
            preparedSt.close();
        } catch (SQLException ex) {
            status = 0;
        }
        return status;
    }
    
    public int signUp(String username, String password) {
        int status = 0;
        ResultSet rs = null;
        //Encrypt the password before sending it to the database
        String encryptedPassword = passwordEncryption(password);
        try {
             String checkExist = new String("SELECT username FROM player where username=?;");
             preparedSt = connection.prepareStatement(checkExist);
            preparedSt.setString(1, username);
            rs = preparedSt.executeQuery();
           
            if(!rs.next()) {
            String insertNewPlayer = new String("insert into player(username, password, score) values(?, ?, ?);");
            preparedSt = connection.prepareStatement(insertNewPlayer);
            preparedSt.setString(1, username);
            preparedSt.setString(2, encryptedPassword);
            preparedSt.setInt(3, 0);
            status = preparedSt.executeUpdate();
            preparedSt.close();
               status =1;
            }else{
                status=0;
            }
        } catch (SQLException ex) {
            status = 0;
        }
        return status;
    }
    
    
    public int signIn(String username, String password) {
        int result = 0;
        //Encrypt the password before comparing it to the passwords in the database
        String encryptedPassword = passwordEncryption(password);
        try {
            String siginQuery = "SELECT ID FROM player where username = ? and password = ?";
            preparedSt = connection.prepareStatement(siginQuery);
            preparedSt.setString(1, username);
            preparedSt.setString(2, encryptedPassword);
            resultSet = preparedSt.executeQuery();
            if(resultSet.next())
                result = resultSet.getInt("ID");
            preparedSt.close();
        } catch (SQLException ex) {
           // System.out.println("Sign-in Authentication Failed!");
        }
        return result;
    }
    
    public Player getPlayerProfile(int player_id){
        Player playerProfile = new Player();
        try {
            statement = connection.createStatement();
            String siginQuery = new String("select * from player where id = "+player_id);
            resultSet = statement.executeQuery(siginQuery);
            resultSet.next();
            playerProfile.setId(resultSet.getInt(1));
            playerProfile.setUsername(resultSet.getString(2));
            playerProfile.setPassword(resultSet.getString(3));
            playerProfile.setScore(resultSet.getInt(4));
            
            //wins, losses and ties
            //wins
            String winQuery = new String("select count(gid) as wins from done_games as g where " + player_id +" = player_1 and gdraw = false;");
            resultSet = statement.executeQuery(winQuery);
            resultSet.next();
            playerProfile.setWins(resultSet.getInt(1));
            
            //losses
            String loseQuery = new String("select count(gid) as losses from done_games as g where " + player_id +" = player_2 and gdraw = false;");
            resultSet = statement.executeQuery(loseQuery);
            resultSet.next();
            playerProfile.setLosses(resultSet.getInt(1));
            
            //ties
            String tieQuery = new String("select count(gdraw) as ties from done_games as g where "+ player_id + " in(player_1, player_2) and gdraw = true;");
            resultSet = statement.executeQuery(tieQuery);
            resultSet.next();
            playerProfile.setTies(resultSet.getInt(1));
        } catch (SQLException ex) {
          //  System.out.println("There is no player with such an id!");
        }
        return playerProfile;
    }
    
    
    public int increaseScore(int player_id){
        int status;
        try {
            String increaseScore = new String("update player set score = score + 5 where id = ?");
            preparedSt = connection.prepareStatement(increaseScore);
            preparedSt.setInt(1, player_id);
            status = preparedSt.executeUpdate();    
        } catch (SQLException ex) {
            ex.printStackTrace();
            status = 0;
        }
        return status;   
    }
    
    
    public int insertDoneGame(int winner, int loser, boolean draw){
        int status = 0;
        try {
            String insertNewGame = new String("insert into done_games(player_1, player_2, draw) values(?, ?, ?);");
            preparedSt = connection.prepareStatement(insertNewGame);
            preparedSt.setInt(1, winner);
            preparedSt.setInt(2, loser);
            preparedSt.setBoolean(3, draw);
            status = preparedSt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public int insertRecordedGame(int player1_id, int player2_id, String X,String O){
       int status = 0;
       //Save the X moves in a vector and the same for the O moves
       String[]XMoves = X.split("-");
       String[]OMoves = O.split("-");
       //Now we insert the recorded game
        try {
            String insertRecordedGame = new String("insert into recorded_games(player_1, player_2, x1, x2, x3, x4, o1, o2, o3, o4, lastmove) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            preparedSt = connection.prepareStatement(insertRecordedGame);
            preparedSt.setInt(1, player1_id);
            preparedSt.setInt(2, player2_id);
            preparedSt.setString(3, XMoves[0]);
            preparedSt.setString(4, XMoves[1]);
            preparedSt.setString(5, XMoves[2]);
            preparedSt.setString(6, XMoves[3]);
            preparedSt.setString(7, OMoves[0]);
            preparedSt.setString(8, OMoves[1]);
            preparedSt.setString(9, OMoves[2]);
            preparedSt.setString(10, OMoves[3]);
            preparedSt.setString(11, OMoves[3]);
            status = preparedSt.executeUpdate();
        } catch (SQLException ex) {
            status = 0;
        }
        return status;
    }
    
    public JSONObject getRecordedGames(int Playerid){
        JSONObject RecordedGames = new JSONObject();
        try {
            statement = connection.createStatement();
            String leaderBoardQuery = new String("select * from recorded_games where player_1 = "+Playerid+" or player_2 = "+Playerid+";");
            resultSet = statement.executeQuery(leaderBoardQuery);
            String id="",player1ID="",player2ID="",player1Name="",player2Name="", X="",O="";
            while(resultSet.next()){
                id = id + ","+resultSet.getInt("id");
                player1ID = player1ID + ","+resultSet.getInt("player_1");
                player1Name = player1Name+","+ClientHandler.GetPlayerNameByID(resultSet.getInt("player_1"));
                player2ID = player2ID + ","+resultSet.getInt("player_2");
                player2Name = player2Name+","+ClientHandler.GetPlayerNameByID(resultSet.getInt("player_2"));
                X = X + ","+resultSet.getString("x1")+"-"+resultSet.getString("x2")+"-"+resultSet.getString("x3")+"-"+resultSet.getString("x4");
                O = O + ","+resultSet.getString("o1")+"-"+resultSet.getString("o2")+"-"+resultSet.getString("o3")+"-"+resultSet.getString("o4");
            }
            RecordedGames.put("gameID", id);
            RecordedGames.put("player1ID", player1ID);
            RecordedGames.put("player2ID", player2ID);
            RecordedGames.put("player1Name", player1Name);
            RecordedGames.put("player2Name", player2Name);
            RecordedGames.put("X", X);
            RecordedGames.put("O", O);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return RecordedGames;           
    }
    
    public JSONObject getLeaderBoard (){
        JSONObject leaderBoard = new JSONObject(); 
        try {
            statement = connection.createStatement();
            String leaderBoardQuery = new String("select username, score from player where username not in('computer') order by score desc limit 10;");
            resultSet = statement.executeQuery(leaderBoardQuery);
            String names="",scores="";
            while(resultSet.next()){
                names = names + ","+resultSet.getString(1);
                scores = scores+","+resultSet.getInt(2);
            }
            leaderBoard.put("names", names);
            leaderBoard.put("scores", scores);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return leaderBoard; 
    }
    
    
    public JSONObject getAllPlayers (){
        JSONObject allPlayers = new JSONObject(); 
        try {
            statement = connection.createStatement();
            String allPlayersQuery = new String("select id,username, score from player;");
            resultSet = statement.executeQuery(allPlayersQuery);
            String id="",names="",scores="";
            ClientHandler.allPlayers = new Vector<Player>();
            Player p;
            while(resultSet.next()){
                p = new Player();
                p.setId(resultSet.getInt("id"));
                p.setUsername(resultSet.getString("username"));
                p.setScore(resultSet.getInt("score"));
                ClientHandler.allPlayers.add(p);
                names = names + ","+p.getUsername();
                scores = scores+","+p.getScore();
                id = id+","+p.getId();
            }
            allPlayers.put("id", id);
            allPlayers.put("names", names);
            allPlayers.put("scores", scores);
            statement.close();
        } catch (Exception ex) {
            connect();
        }
        return allPlayers; 
    }
    
    //Password Encryption in database
    private String passwordEncryption(String password) {

        String encryptedpassword = null;
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");
            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());
            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();
            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedpassword;
    }

}
