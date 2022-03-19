
package serverapplication;

import db.database;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


enum Status {
  ON,
  OFF
}

enum DahboardMsg {
  START,
  STOP,
  GETALLPLAYERS,
  CLOSE
}

enum ClientMsg {
  SIGNIN,
  SIGNUP,
  GET_LEADERBOARD,
  GET_ONLINE_PLAYERS,
  CLOSE_CONNECTION,
  INVETATION_SEND,
  INVETATION_REPLY,
  START_GAME,
  ADD_MOVE,
  SEND_MESSAGE,
  SAVE_DONE_GAME,
  REPLAY_ASK,
  REPLAY_REPLY,
  RECORD_GAME,
  GET_RECORDED_GAMES,
  GET_ALL_PLAYERS,
  RESUME_GAME_SEND,
  RESUME_GAME_REPLY,
  SERVER_STOPPED,
  PLAYER_DISCONECTED
}

public class ServerApplication {
    
    public static Status server_status;
    ClientServer clientServer;
    ServerSocket dashboardServerSocket;
    Socket dashboardSocket;
    static DataInputStream dis;
    static PrintStream ps;
    static database db;
    static boolean dashboardOpened = false;
    
    public ServerApplication() {
        db = new database();
        server_status = Status.OFF;
        clientServer = new ClientServer(db);
        try {
            dashboardServerSocket = new ServerSocket(5004);
        } catch (IOException ex) {
            Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            try {
                dashboardSocket = dashboardServerSocket.accept();
                connectDashboard();
                runDashboard();
            } catch (Exception ex) {
                continue;
            }
        }
    }
    
    public static void setAllplayers(){
        if(!dashboardOpened)
            return;
        JSONObject js = db.getAllPlayers();
        js.put("type", DahboardMsg.GETALLPLAYERS);
        String online = "";
        for(ClientHandler c : ClientHandler.clientsVector){
            online = online+","+c.player.getId();
        }
        js.put("online",online);
        ps.println(js);
    }
    
    public void runDashboard(){
        dashboardOpened = true;
        if(server_status == Status.ON){
            setAllplayers();
        }
        while(true){
            try {
                JSONObject request = new JSONObject(dis.readLine());
                DahboardMsg msg = request.getEnum(DahboardMsg.class,"type");
                
                switch (msg) {
                    case START:
                        if(server_status == Status.OFF)
                            startServer();
                        break;
                    case STOP:
                        if(server_status == Status.ON)
                            stopServer();
                        break;
                    case CLOSE:
                        closeDashboard();
                        return;
                }
            } catch (Exception ex) {
                closeDashboard();
                return;
            }
        }
    }
    
    void connectDashboard(){
        try {
            dis = new DataInputStream(dashboardSocket.getInputStream());
            ps = new PrintStream(dashboardSocket.getOutputStream());
        } catch (IOException ex) {
            closeDashboard();
        }
    }
    
    void closeDashboard (){
        try {
            dashboardOpened = false;
            dis.close();
            ps.close();
            dashboardSocket.close();
        } catch (IOException ex) {
        }
    }
    
    void startServer(){
        server_status = Status.ON;
        db.connect();
        setAllplayers();
    }
    
    void stopServer(){
        server_status = Status.OFF;
        ClientHandler.closeServer();
        db.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ServerApplication();
    }
    
    
    
    class ClientServer extends Thread{
        public ServerSocket clientsServerSocket;
        database db;
        
        public ClientServer(database _db) {
            db = _db;
            try {
                clientsServerSocket = new ServerSocket(5005);
            } catch (IOException ex) {
                Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            start();
        }

        @Override
        public void run() {
            while(true){
                try {
                    Socket s = clientsServerSocket.accept();
                    new ClientHandler(s,db);
                } catch (IOException ex) {
                    Logger.getLogger(ServerApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
