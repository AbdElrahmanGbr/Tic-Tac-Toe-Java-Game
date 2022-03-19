
package serverapplication;

import db.Player;
import db.database;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class ClientHandler extends Thread{
    
    DataInputStream dis;
    PrintStream ps;
    database db;
    JSONObject request,response;
    Player player;
    static Vector<ClientHandler> clientsVector = new Vector<ClientHandler>();
    public static Vector<Player>allPlayers ;
    
    public ClientHandler(Socket sc,database _db){
        db = _db;
        if(ServerApplication.server_status==Status.ON)
            db.getAllPlayers();
        response = new JSONObject();
        try {
            ps = new PrintStream(sc.getOutputStream());
            dis = new DataInputStream(sc.getInputStream());
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void closeConnection(){
        try {
            dis.close();
            ps.close();
            this.stop();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeServer(){
        for(ClientHandler c : clientsVector){
            JSONObject js = new JSONObject();
            js.put("type", ClientMsg.SERVER_STOPPED);
            c.ps.println(js);
            c.closeConnection();
        }
        clientsVector.clear();
        ServerApplication.setAllplayers();
    }
    
    
    @Override
    public void run(){
        if(ServerApplication.server_status == Status.ON)
        {
            if(log()){
                clientsVector.add(this);
                changeOnlineStatus();
            }else{
                return;
            }
        }
        else{
            JSONObject js = new JSONObject();
            js.put("type", ClientMsg.SERVER_STOPPED);
            ps.println(js);
            closeConnection();
            return;
        }
        
        while(true){
            try {
                String re = dis.readLine();
                if(re == null)
                {
                    clientsVector.remove(this);
                    for(ClientHandler c : clientsVector){
                        c.get_online_players();
                    }
                    ServerApplication.setAllplayers();
                    closeConnection();
                    return;
                }
                JSONObject request = new JSONObject(re);
                ClientMsg msg = request.getEnum(ClientMsg.class,"type");
                switch (msg) {
                    case GET_LEADERBOARD:
                        get_leaderboard();
                        break;
                    case GET_ONLINE_PLAYERS:
                        get_online_players();
                        break;
                    case INVETATION_SEND:
                        sendInvitation(request);
                        break;
                    case INVETATION_REPLY:
                        replyInvitation(request);
                        break;
                    case RESUME_GAME_SEND:
                        sendResumeGame(request);
                        break;
                    case RESUME_GAME_REPLY:
                        replyResumeGame(request);
                        break;
                    case START_GAME:
                        startGame(request);
                        break;
                    case SAVE_DONE_GAME:
                        inserDoneGame(request);
                        break ;
                    case GET_RECORDED_GAMES:
                        getRecordedGames();
                        break ;
                    case GET_ALL_PLAYERS:
                        ps.println(db.getAllPlayers());
                        break ;
                    case CLOSE_CONNECTION:
                        clientsVector.remove(this);
                        for(ClientHandler c : clientsVector){
                            c.get_online_players();
                        }
                        ServerApplication.setAllplayers();
                        closeConnection();
                        return;
                }
               
           } catch (IOException ex) {
               clientsVector.remove(this);
               ServerApplication.setAllplayers();
               closeConnection();
               return;
           }
        }
    }
    
    private void getRecordedGames(){
        response = db.getRecordedGames(player.getId());
        response.put("type", ClientMsg.GET_RECORDED_GAMES);
        ps.println(response);
    }
    
    private void changeOnlineStatus(){
        ServerApplication.setAllplayers();
        for(ClientHandler c : clientsVector){
            c.get_online_players();
        }
    } 
    
    boolean log(){
        while(true){
        try {
            String re = dis.readLine();
            request = new JSONObject(re);
            ClientMsg msg = request.getEnum(ClientMsg.class,"type");
            switch (msg) {
                case SIGNIN:
                    if(signIn(request)){
                        return true;
                    }
                    break;
                case SIGNUP:
                    signUp(request);
                    break;
                case CLOSE_CONNECTION:
                    closeConnection();
                    return false;
            }
        } catch (IOException ex) {
            closeConnection();
        }
        }
    }
    
    boolean signIn(JSONObject request){
        response.clear();
        response.put("type", ClientMsg.SIGNIN);
        
        if(isPlayerOnline(request.getString("username"))){
            response.put("id", -1);
            ps.println(response);
            return false;
        }
        
        int r = db.signIn(request.getString("username"), request.getString("passwd"));
        if(r==0){
            response.put("id", 0);
            ps.println(response);
            return false;
        }
        else{
            player = db.getPlayerProfile(r);
            response.put("id", player.getId());
            response.put("score", player.getScore());
            response.put("losses", player.getLosses());
            response.put("wins", player.getWins());
            response.put("username", player.getUsername());
            response.put("ties", player.getTies());
            ps.println(response);
            return true;
        }
    }

    boolean signUp(JSONObject request){
        response.clear();
        response.put("type", ClientMsg.SIGNUP);
        int r = db.signUp(request.getString("username"), request.getString("passwd"));
        
        if(r==0){
            response.put("id",-1);
            ps.println(response);
            return false;
        }
        else{
            player = db.getPlayerProfile(r);
            allPlayers.add(player);
            response.put("id", player.getId());
            ps.println(response);
            return true;
        }
    }

    
    void get_leaderboard(){
        response = db.getLeaderBoard();
        response.put("type", ClientMsg.GET_LEADERBOARD);
        ps.println(response);
    }
    
    void get_online_players(){
        String names="",scores="",id="";
        for(ClientHandler c : clientsVector){
            if(c!=this){
                names = names + ","+c.player.getUsername();
                scores = scores+","+c.player.getScore();
                id = id+","+c.player.getId();
            }
        }
        response.clear();
        response.put("name", names);
        response.put("score", scores);
        response.put("id", id);
        response.put("type", ClientMsg.GET_ONLINE_PLAYERS);
        ps.println(response);
    }
    
    void sendInvitation(JSONObject request){
        ClientHandler player_info = GetPlayerByID(request.getInt("reciever id"));
        response.clear();
        response.put("type", ClientMsg.INVETATION_SEND);
        response.put("sender id", player.getId());
        response.put("sender name", player.getUsername());
        response.put("reciever id", player_info.player.getId());
        player_info.ps.println(response);
    }
    
    void replyInvitation(JSONObject request){
        ClientHandler player_info = GetPlayerByID(request.getInt("reciever id"));
        response.clear();
        response.put("type", ClientMsg.INVETATION_REPLY);
        response.put("sender id", player.getId());
        response.put("sender name", player.getUsername());
        response.put("reply", request.getString("reply"));
        response.put("reciever id", player_info.player.getId());
        player_info.ps.println(response);
    }
    
    void sendResumeGame(JSONObject request){
        ClientHandler player_info = GetPlayerByID(request.getInt("reciever id"));
        if(player_info==null){
            request.clear();
            request.put("type", ClientMsg.RESUME_GAME_REPLY);
            request.put("reply", "no");
            ps.println(request);
            return;
        }
        request.put("type", ClientMsg.RESUME_GAME_SEND);
        request.put("sender id", player.getId());
        request.put("sender name", player.getUsername());
        request.put("reciever id", player_info.player.getId());
        player_info.ps.println(request);
    }
    
    void replyResumeGame(JSONObject request){
        ClientHandler player_info = GetPlayerByID(request.getInt("reciever id"));
        if(player_info==null){
            request.put("reply", "no");
        }else
            request.put("reply", request.getString("reply"));
        if(request.getString("reply").equals("yes")){
            db.removeRecordedGame(request.getInt("game id"));
        }
        request.put("type", ClientMsg.RESUME_GAME_REPLY);
        request.put("sender id", player.getId());
        request.put("sender name", player.getUsername());
        request.put("reciever id", player_info.player.getId());
        player_info.ps.println(request);
    }
    
    void inserDoneGame(JSONObject request){
        if(db.insertDoneGame(request.getInt("winner"), request.getInt("loser"), request.getBoolean("draw"))!=0)
        db.increaseScore(request.getInt("winner"));
        player = db.getPlayerProfile(player.getId());
        if(GetPlayerByID(request.getInt("loser"))!=null)
            GetPlayerByID(request.getInt("loser")).player = db.getPlayerProfile(request.getInt("loser"));
    }
    
    boolean replyForReplay(JSONObject request,ClientHandler p2){
        p2.ps.println(request);
        return request.getBoolean("reply");
    }
    
    void record_Game(JSONObject request){
        db.insertRecordedGame(request.getInt("player1"),request.getInt("player2"), request.getString("X"), request.getString("O"));
    }
    
    private void startGame(JSONObject request){
        int p2ID = request.getInt("player2");
        ClientHandler player2 = GetPlayerByID(p2ID);
        while(true){
            if(player2==null){
                request.clear();
                request.put("type", ClientMsg.PLAYER_DISCONECTED);
                ps.println(request);
                return;
            }
            try {
            String re = dis.readLine();
            if(re == null)
            {
                request.clear();
                request.put("type", ClientMsg.PLAYER_DISCONECTED);
                player2.ps.println(request);
                clientsVector.remove(this);
                for(ClientHandler c : clientsVector){
                    c.get_online_players();
                }
                ServerApplication.setAllplayers();
                closeConnection();
                return;
            }
            request = new JSONObject(re);
            ClientMsg msg = request.getEnum(ClientMsg.class,"type");
            switch (msg) {
                case ADD_MOVE:
                    player2.ps.println(request);
                    break;
                case SEND_MESSAGE:
                    player2.ps.println(request);
                    break;
                case SAVE_DONE_GAME:
                    inserDoneGame(request);
                    break ;
                case REPLAY_REPLY:
                    if(!replyForReplay(request,player2))
                        return;
                    break ;
                case GET_ONLINE_PLAYERS:
                    get_online_players();
                    return;
                case RECORD_GAME:
                    record_Game(request);
                    player2.ps.println(request);
                    return;
                case CLOSE_CONNECTION:
                    request.clear();
                    request.put("type", ClientMsg.PLAYER_DISCONECTED);
                    player2.ps.println(request);
                    clientsVector.remove(this);
                    for(ClientHandler c : clientsVector){
                        c.get_online_players();
                    }
                    ServerApplication.setAllplayers();
                    closeConnection();
                    return;
            }
            } catch (IOException ex) {
                request.clear();
                request.put("type", ClientMsg.PLAYER_DISCONECTED);
                player2.ps.println(request);
                clientsVector.remove(this);
                closeConnection();
                return;
            }
        }
    }
    
    public static String GetPlayerNameByID(int id){
        for(Player p : allPlayers){
            if(p.getId() == id){
                return p.getUsername();
            }
        }
        return null;
    }
    
    ClientHandler GetPlayerByID(int id){
        for(ClientHandler c : clientsVector){
            if(c.player.getId() == id){
                return c;
            }
        }
        return null;
    }

    boolean isPlayerOnline(String username){
        for(ClientHandler c : clientsVector){
            if(c.player.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    
}
