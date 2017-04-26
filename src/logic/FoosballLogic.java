package logic;

import dataBase.Adapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Match;
import model.Player;
import model.Team;
import model.Tournament;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Didi on 04/13/2017.
 */
public class FoosballLogic
{
    Adapter adapter = Adapter.getInstance();
    private static FoosballLogic ourInstance;
    private Tournament chosenTournamentToEdit;
    private Player chosenPlayerToEdit;
    private Team chosenTeamToEdit;
    private Match chosenMatchToEdit;
    private ArrayList<Team> teams = getTeams();
    private ArrayList<Player> players = getPlayers();
    private ArrayList<Match> matches = getMatches();

    public static synchronized FoosballLogic getInstance() {
        if (ourInstance == null){
            ourInstance = new FoosballLogic();
        }
        return ourInstance;
    }

    private FoosballLogic() {
    }

    public boolean checkUserAndPass(String name, String pass, String tableDB){
        if(!adapter.checkUserAndPassword(name, pass, tableDB)){
            return false;
        }
        return true;
    }

    public ArrayList<Tournament> getTournaments(){
        ArrayList<Tournament> tournaments = new ArrayList<>();
        tournaments.addAll(adapter.getTournaments());
        return tournaments;
    }

    public void deleteTournament(int tournamentId) {
        adapter.deleteTournament(tournamentId);
    }

    public void setChosenTournamentToEdit(Tournament chosenTournamentToEdit) {
        this.chosenTournamentToEdit = chosenTournamentToEdit;
    }

    public Tournament getChosenTournamentToEdit() {
        return chosenTournamentToEdit;
    }

    public void saveTournamentChanges(String name, LocalDate startDate, LocalDate endDate, Tournament tournamentToEdit) {
        adapter.saveTournamentsChanges(name, startDate, endDate, tournamentToEdit);
    }

    public ArrayList<Player> getPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(adapter.getPlayers());
        return players;
    }

    public void addNewPlayer(String name, LocalDate dateOfBirth, String eMail, String pass) {
        adapter.addNewPlayer(name, dateOfBirth, eMail, pass);
    }

    public void deletePlayer(int playerId) {
        adapter.deletePlayer(playerId);
    }

    public void setChosenPlayerToEdit(Player chosenPlayerToEdit) {
        this.chosenPlayerToEdit = chosenPlayerToEdit;
    }

    public Player getChosenPlayerToEdit() {
        return chosenPlayerToEdit;
    }

    public void savePlayerChanges(String name, LocalDate dateOfBirth, String eMail, String pass, String status, int playerId) {
        adapter.savePlayerChanges(name, dateOfBirth, eMail, pass, status, playerId);
    }

    public ArrayList<Player> searchPlayers(String searchText) {
        return adapter.searchPlayers(searchText);
    }

    public ArrayList<Team> getTeams() {
        return adapter.getTeams();
    }

    public void addNewTournament(String name, LocalDate startDate, LocalDate endDate)
    {
        adapter.addNewTournament(name, startDate, endDate);
    }

    public void deleteTeam(int id)
    {
        adapter.deleteTeam(id);
    }

    public void addNewTeam(String name, int player1ID, int player2ID) {
        adapter.addNewTeam(name, player1ID, player2ID);
    }

    public void setChosenTeamToEdit(Team chosenTeamToEdit) {
        this.chosenTeamToEdit = chosenTeamToEdit;
    }

    public Team getChosenTeamToEdit() {
        return chosenTeamToEdit;
    }

    public void saveTeamChanges(String name, String player1name, String player2name, Team teamToEdit)
    {
        int player1ID = 0;
        int player2ID = 0;

        for(Player player: getPlayers())
        {
            if (player.getName().equals(player1name))
            {
                player1ID = player.getId();
            }

            if (player.getName().equals(player2name))
            {
                player2ID = player.getId();
            }
        }

        adapter.saveTeamChanges(name, player1ID, player2ID, teamToEdit);

    }

    public ArrayList<Match> getMatches()
    {
        return adapter.getMatches();
    }

    public void setTeamNames(ArrayList<Match> matches, ArrayList<Team> teams)
    {
        for(Match match: matches)
        {
            int team1ID = match.getTeam1ID();
            int team2ID = match.getTeam2ID();

            for (Team team: teams)
            {
                if(team1ID == team.getId())
                {
                    match.setTeam1Name(team.getName());
                }

                if(team2ID == team.getId())
                {
                    match.setTeam2Name(team.getName());
                }
            }
        }
    }

    public void setTournamentsNames(ArrayList<Match> matches, ArrayList<Tournament> tournaments)
    {
        for(Match match: matches)
        {
            int tourID = match.getTournamentID();

            for (Tournament tournament: tournaments)
            {
                if(tourID == tournament.getId())
                {
                    match.setTournamentName(tournament.getName());
                }
            }
        }
    }

    public void deleteMatch(int selectedMatch)
    {
        adapter.deleteMatch(selectedMatch);
    }

    public void addNewMatch(LocalDate date, int tournamentID, int team1ID, int team2ID)
    {
        adapter.addNewTournament(date, tournamentID, team1ID, team2ID);
    }

    public Match getChosenMatch()
    {

        return chosenMatchToEdit;
    }

    public void setChosenMatch(Match chosenMatchToEdit)
    {
        this.chosenMatchToEdit = chosenMatchToEdit;
    }

    public String getTournamentName(int tournamentID)
    {
        String name = "";
        ArrayList<Tournament> tournaments = getTournaments();

        for(Tournament tour: tournaments)
        {
            if(tour.getId() == tournamentID)
            {
                name = tour.getName();
            }
        }
        return name;
    }


    public String getTeamName(int id)
    {
        String name = "";
        ArrayList<Team> teams = getTeams();

        for(Team team: teams)
        {
            if(team.getId() == id)
            {
                name = team.getName();
            }
        }
        return name;
    }

    public void saveMatchChanges(LocalDate date, String tournament, String stage, String team1, String team2, int t1Scores, int t2scores)
    {
        ArrayList<Team> teams = getTeams();
        ArrayList<Tournament> tournaments = getTournaments();
        int tournamentID = 0;
        int team1ID = 0;
        int team2ID = 0;

        for (Tournament tour: tournaments)
        {
            if(tour.getName().equals(tournament))
            {
                tournamentID = tour.getId();
            }
        }

        for (Team team: teams)
        {
            if(team.getName().equals(team1))
            {
                team1ID = team.getId();
            }

            if(team.getName().equals(team2))
            {
                team2ID = team.getId();
            }
        }

        adapter.saveMatchChanges(date, tournamentID, stage, team1ID, team2ID, t1Scores, t2scores, chosenMatchToEdit.getId());
    }

    public ObservableList<String> getTournamentsNames()
    {
        ObservableList<String> tournamentsNames = FXCollections.observableArrayList();
        ArrayList<Tournament> tournaments = getTournaments();

        for (Tournament t: tournaments)
        {
            tournamentsNames.add(t.getName());
        }
        return tournamentsNames;
    }

    public ObservableList<String> getTeamsNames()
    {
        ObservableList<String> teamsNames = FXCollections.observableArrayList();

        for (Team t: teams)
        {
            teamsNames.add(t.getName());
        }
        return teamsNames;
    }

    public ObservableList<String> getTeamsNames(String selectedTeam)
    {
        ObservableList<String> teamsNames = FXCollections.observableArrayList();

        for (Team t: teams)
        {
            if(!t.getName().equals(selectedTeam))
            {
                teamsNames.add(t.getName());
            }
        }
        return teamsNames;
    }

    public void setPlayerRank(String team1Name, String team2Name, int team1scores, int team2scores)
    {
        for (Match match: matches)
        {
            if(team1scores == match.getTeam1scores() && team2scores == match.getTeam2scores())
            {
                return;
            }

            if (team1scores != match.getTeam1scores() && team2scores == match.getTeam2scores())
            {

                savePlayerRankChanges(team1Name, team1scores);

                return;
            }

            if (team1scores == match.getTeam1scores() && team2scores != match.getTeam2scores())
            {

                savePlayerRankChanges(team2Name, team2scores);

                return;
            }
        }

        savePlayerRankChanges(team1Name, team1scores);
        savePlayerRankChanges(team2Name, team2scores);
    }

    public void savePlayerRankChanges(String team, int scores)
    {
        int player1ID = 0;
        int player2ID = 0;
        int player1Rank = 0;
        int player2Rank = 0;

        for (Team t: teams)
        {
            if (team.equals(t.getName()))
            {
                player1ID = t.getPlayer1ID();
                player2ID = t.getPlayer2ID();
            }
        }

        for (Player player: players)
        {
            if(player1ID == player.getId())
            {
                player1Rank = player.getRank();
                player1Rank += scores;
                player.setRank(player1Rank);
                adapter.savePlayerRankChanges(player1ID, player1Rank);
            }

            if(player2ID == player.getId())
            {
                player2Rank = player.getRank();
                player2Rank += scores;
                player.setRank(player2Rank);
                adapter.savePlayerRankChanges(player2ID, player2Rank);
            }
        }

    }
}
