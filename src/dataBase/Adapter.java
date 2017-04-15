package dataBase;

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
                    "INSERT INTO `sql11168846`.`tournaments` (`id`, `name`, `start_date`, `end_date`)" +
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
}
