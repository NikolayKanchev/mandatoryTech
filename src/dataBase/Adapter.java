package dataBase;

import model.Player;
import model.Team;
import model.Tournament;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Didi on 04/13/2017.
 */
public class Adapter {

    private static Adapter ourInstance;
    private Connection conn;
    private ArrayList<Tournament> tournaments;
    private ArrayList<Player> players;
    private ArrayList<Team> teams;

    public  static synchronized Adapter getInstance() {
        if(ourInstance == null){
        ourInstance = new Adapter();
        }
        return ourInstance;
    }

    private Adapter() {
    }

    public boolean checkUserAndPassword(String eMail, String pass, String tableDB){
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM "+ tableDB +" WHERE `e-mail` = '"+ eMail +"' AND `password` = '"+ pass +"'");

            while (rs.next()){
                return true;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addNewTournament(String name, LocalDate start_date, LocalDate end_date) {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `tournaments` (`id`, `name`, `start_date`, `end_date`)" +
                    " VALUES (NULL, '"+ name +"', '"+ start_date +"', '"+ end_date +"');"
            );

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Tournament> getTournaments() {
        ArrayList<Tournament> tournaments = new ArrayList<>();
        conn = DBConn.getConn();


        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `tournaments`");

            while (rs.next()){
                Tournament newTournament = new Tournament(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4));
                tournaments.add(newTournament);
            }

            conn.close();
            return tournaments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournaments;
    }

    public void deleteTournament(int tournamentID) {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM `tournaments` WHERE id = " + tournamentID);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveTournamentsChanges(String name, LocalDate startDate, LocalDate endDate, Tournament tournamentToEdit) {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE `tournaments` SET `name`= '"+ name +"',`start_date`= '"+ startDate
                    +"',`end_date`= '"+ endDate +"' WHERE id = " + tournamentToEdit.getId());

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        conn = DBConn.getConn();


        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `players`");

            while (rs.next()){
                Player newPlayer = new Player(rs.getInt(1), rs.getString(2),  rs.getDate(3), rs.getString(4),  rs.getString(7));
                newPlayer.setStatus(rs.getString(6));
                newPlayer.setRank(rs.getInt(5));
                players.add(newPlayer);
            }

            conn.close();
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public void addNewPlayer(String name, LocalDate dateOfBirth, String eMail, String pass) {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `players` (`id`, `name`, `date_of_birth`, `e-mail`, `password`)" +
                            " VALUES (NULL, '"+ name +"', '"+ dateOfBirth +"', '"+ eMail +"' , '"+ pass +"');"
            );

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayer(int playerId) {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM `players` WHERE id = " + playerId);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerChanges(String name, LocalDate dateOfBirth, String eMail, String pass, String status, int playerId) {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE `players` SET `name`= '"+ name +"',`date_of_birth`= '"+ dateOfBirth
                    +"',`e-mail`= '"+ eMail +"', password = '"+ pass +"', status = '"+ status +"' WHERE id = " + playerId);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> searchPlayers(String searchText) {
        ArrayList<Player> players = new ArrayList<>();
        conn = DBConn.getConn();


        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM `players` WHERE name LIKE \"%"+ searchText +"%\" OR `e-mail` LIKE \"%"+ searchText +
                            "%\" OR password LIKE \"%"+ searchText +"%\" OR `date_of_birth` LIKE \"%"+ searchText +
                            "%\" OR `rank` LIKE \"%"+ searchText +"%\" OR status LIKE \"%"+ searchText +"%\""
            );

            while (rs.next()){
                Player newPlayer = new Player(rs.getInt(1), rs.getString(2),  rs.getDate(3), rs.getString(4),  rs.getString(7));
                newPlayer.setStatus(rs.getString(6));
                newPlayer.setRank(rs.getInt(5));
                players.add(newPlayer);
            }

            conn.close();
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        conn = DBConn.getConn();


        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `teams`");

            while (rs.next()){
                Team newTeam = new Team(rs.getInt(1), rs.getString(2), rs.getInt(3),  rs.getInt(4));
                newTeam.setWonMatches(rs.getInt(5));
                newTeam.setLostMatches(rs.getInt(6));
                teams.add(newTeam);
            }

            conn.close();

            return teams;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public void deleteTeam(int id)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM `teams` WHERE id = " + id);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
