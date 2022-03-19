
package DashboardGUI;


public class player {
    private String name,status, score;

    public player() {
    }

    public player(String name, String status, String score) {
        this.name = name;
        this.status = status;
        this.score = score;
    }

    
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
