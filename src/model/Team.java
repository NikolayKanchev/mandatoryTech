package model;

/**
 * Created by nikol on 4/6/2017.
 */
public class Team {
    private String name;
    private Player player1;
    private Player player2;

    public Team(String name, Player player1, Player player2) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
