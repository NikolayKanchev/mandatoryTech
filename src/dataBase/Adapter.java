package dataBase;

import model.Match;
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

    public void addNewTeam(String name, int player1ID, int player2ID) {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `foosball_management`.`teams` (`id`, `name`, `player1_id`, `player2_id`, `won_matches`, `lost_matches`)" +
                            " VALUES (NULL, '"+ name +"', '"+ player1ID +"', '"+ player2ID +"', '0', '0');"
            );

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveTeamChanges(String name, int player1ID, int player2ID, Team teamToEdit)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE `foosball_management`.`teams` " +
                    "SET `name` = '"+ name +"', `player1_id` = '"+ player1ID +"', `player2_id` = '"+ player2ID+"' " +
                    "WHERE `teams`.`id` = " + teamToEdit.getId());

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Match> getMatches()
    {
        ArrayList<Match> matches = new ArrayList<>();

        conn = DBConn.getConn();

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM matches");

            while (rs.next())
            {
                Match newMatch = new Match
                        (
                        rs.getInt("id"), rs.getDate("date"),
                        rs.getInt("tournament_id"), rs.getString("stage"),
                        rs.getInt("team1_id"), rs.getInt("team2_id")
                        );
                newMatch.setTeam1scores(rs.getInt("team1_scores"));
                newMatch.setTeam2scores(rs.getInt("team2_scores"));
                matches.add(newMatch);
            }

            conn.close();
            return matches;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteMatch(int selectedMatch)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM `matches` WHERE id = " + selectedMatch);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewMatch(LocalDate date, int tournamentID, int team1ID, int team2ID, String stage)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(
                    "INSERT INTO `matches` (`id`, `date`, `tournament_id`, `stage`, `team1_id`, `team2_id`, `team1_scores`, `team2_scores`) " +
                            "VALUES (NULL, '"+ date +"', '"+ tournamentID +"', '"+ stage +"', '"+ team1ID +"', '"+ team2ID +"', '0', '0');"
            );

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveMatchChanges(LocalDate date, int tournament, String stage, int team1, int team2, int t1Scores, int t2scores, int id)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE `foosball_management`.`matches` " +
                    "SET `date` = '"+ date +"', `tournament_id` = '"+ tournament +"', `stage` = '"+ stage +"', " +
                    "`team1_id` = '"+ team1 +"', `team2_id` = '" + team2 + "', `team1_scores` = '"+ t1Scores +"', `team2_scores` = '"+ t2scores +"' " +
                    "WHERE `matches`.`id` = "+ id +";");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerRankChanges(int playerID, int playerRank)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(
                    "UPDATE  `foosball_management`.`players` " +
                        "SET  `rank` =  '"+ playerRank +"' " +
                        "WHERE  `players`.`id` = "+ playerID +";");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeamWonMatches(int wonMatches, int teamID)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE  `foosball_management`.`teams` SET  `won_matches` =  '"+ wonMatches +"' WHERE  `teams`.`id` = " + teamID);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeamLostMatches(int lostMatches, int teamID)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE  `foosball_management`.`teams` SET  `lost_matches` =  '"+ lostMatches +"' WHERE  `teams`.`id` = " + teamID);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getWinnersFirstRound(int tournamentID)
    {
        ArrayList<Integer> teams = new ArrayList<>();
        conn = DBConn.getConn();


        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("");

            while (rs.next()){

            }

            conn.close();

            return teams;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addToWinners(int tournamentID, String stage, int teamID, int scoresDiff)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO `foosball_management`.`winners` (`id`, `tour_id`, `stage`, `team_id`, `scores_difference`) " +
                    "VALUES (NULL, '"+ tournamentID +"', '"+ stage +"', '"+ teamID +"', '"+ scoresDiff +"');");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getTheWinnersIDs(int tourID, String stage)
    {
        ArrayList<Integer> theWinnersIDs = new ArrayList<>();
        conn = DBConn.getConn();

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT winners.team_id, SUM(scores_difference) AS sc_diff FROM winners " +
                    "WHERE winners.tour_id = "+ tourID +" AND winners.stage = '"+ stage +"' GROUP BY winners.team_id ORDER BY sc_diff DESC LIMIT 4");

            while (rs.next())
            {
                theWinnersIDs.add(rs.getInt("team_id"));
            }

            conn.close();

            return theWinnersIDs;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void updateWinners(int tournamentID, String stage, int winnerID, int scoresDiff, int oldWinnerID, int oldScoreDiff)
    {
        conn = DBConn.getConn();
        int oldWinner = 0;


        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT winners.id FROM winners WHERE winners.tour_id = "+ tournamentID+" " +
                    "AND winners.stage = '"+ stage +"' AND winners.team_id = "+ oldWinnerID +" AND winners.scores_difference = "+ oldScoreDiff +" LIMIT 1");
            if(rs.next())
            {
                oldWinner = rs.getInt("id");
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        insertNewValueForTheWinner(oldWinner, winnerID, scoresDiff, tournamentID, stage);

    }

    private void insertNewValueForTheWinner(int oldWinner, int newWinner, int scoresDiff, int tournamentID, String stage)
    {
        conn = DBConn.getConn();

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(
                    "UPDATE winners SET `tour_id` = "+ tournamentID +", `stage` = '"+ stage +"'" +
                            ", `team_id` = "+ newWinner +", `scores_difference` = "+ scoresDiff +" WHERE winners.id = "+ oldWinner +"");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
