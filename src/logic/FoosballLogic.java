package logic;

import dataBase.Adapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Didi on 04/13/2017.
 */
public class FoosballLogic
{
    private Adapter adapter = Adapter.getInstance();
    private static FoosballLogic ourInstance;
    private Tournament chosenTournamentToEdit;
    private Player chosenPlayerToEdit;
    private Team chosenTeamToEdit;
    private Match chosenMatchToEdit;
    private ArrayList<Team> teams = getTeams();
    private ArrayList<Player> players = getPlayers();
    private ArrayList<Match> matches = getMatches();
    private ArrayList<Tournament> tournaments = getTournaments();
    private Player playerLogged;
    private ArrayList<Team> playerTeams;
    private ArrayList<Match> playerMatches;
    private ArrayList<Tournament> playerTournaments = new ArrayList<>();
    private ArrayList<Player> availablePlayers = new ArrayList<>();
    private Player player;

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

    public void addNewMatch(LocalDate date, int tournamentID, int team1ID, int team2ID, String stage)
    {
        adapter.addNewMatch(date, tournamentID, team1ID, team2ID, stage);
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

    public void saveMatchChanges(LocalDate date, String tournament, String stage, String team1, String team2, int t1Scores, int t2Scores)
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

        adapter.saveMatchChanges(date, tournamentID, stage, team1ID, team2ID, t1Scores, t2Scores, chosenMatchToEdit.getId());
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
                adapter.savePlayerRankChanges(player1ID, player1Rank);
                player.setRank(player1Rank);
            }

            if(player2ID == player.getId())
            {
                player2Rank = player.getRank();
                player2Rank += scores;
                adapter.savePlayerRankChanges(player2ID, player2Rank);
                player.setRank(player2Rank);
            }
        }

    }

    public void setTeamsWonMatches(String team1Name, String team2Name, int t1scores, int t2scores)
    {
        String winner = "";
        String loser = "";
        int teamID = 0;

        if(t1scores > t2scores)
        {
            winner = team1Name;
            loser = team2Name;
        }
        else
        {
           winner = team2Name;
           loser = team1Name;
        }


        for (Team t: teams)
        {
            if (winner.equals(t.getName()))
            {
                t.setWonMatches(t.getWonMatches()+1);
                adapter.updateTeamWonMatches(t.getWonMatches(), t.getId());
            }

            if (loser.equals(t.getName()))
            {
                t.setLostMatches(t.getLostMatches()+1);
                adapter.updateTeamLostMatches(t.getLostMatches(), t.getId());
            }
        }
    }

    public void setNewValueTeamsWonMatches(String team1Name, String team2Name, int t1scores, int t2scores)
    {
        String winner = "";
        String loser = "";
        int teamID = 0;

        if(t1scores > t2scores)
        {
            winner = team1Name;
            loser = team2Name;
        }
        else
        {
            winner = team2Name;
            loser = team1Name;
        }

        for (Team t: teams)
        {
            if (winner.equals(t.getName()))
            {
                t.setWonMatches(t.getWonMatches()+1);
                t.setLostMatches(t.getLostMatches()-1);
                adapter.updateTeamLostMatches(t.getLostMatches(), t.getId());
                adapter.updateTeamWonMatches(t.getWonMatches(), t.getId());
            }

            if (loser.equals(t.getName()))
            {
                t.setLostMatches(t.getLostMatches()+1);
                t.setWonMatches(t.getWonMatches()-1);
                adapter.updateTeamWonMatches(t.getWonMatches(), t.getId());
                adapter.updateTeamLostMatches(t.getLostMatches(), t.getId());
            }
        }
    }

    public void checkPlayedMatches(int tournamentID)
    {
        List<Integer> winnersIDs = new ArrayList<>();
        LocalDate date = null;
        matches = getMatches();
        boolean firstRoundComplete = false;
        boolean semifinalComplete = false;
        boolean semifinalMatchesHaveBeenCreated = false;
        boolean finalMatchHaveBeenCreated = false;
        int tournamentWinnerID = 0;


        for (Match match : matches)
        {
            if(match.getTournamentID() == tournamentID)
            {
                if(match.getStage().equals("First Round"))
                {
                    if(match.getTeam1scores() == 0 && match.getTeam2scores() == 0)
                    {
                        firstRoundComplete = false;
                        System.out.println("Stage First round is not complete");
                        return;
                    }

                    firstRoundComplete = true;
                    System.out.println("Stage First round is complete");

                    if(firstRoundComplete)
                    {
                        winnersIDs = adapter.getTheWinnersIDs(match.getTournamentID(), match.getStage());
                        date = match.getDate().toLocalDate();
                    }
                }

                if(match.getStage().equals("Semifinal"))
                {
                    semifinalMatchesHaveBeenCreated = true;

                    if(match.getTeam1scores() == 0 && match.getTeam2scores() == 0)
                    {
                        semifinalComplete = false;
                        System.out.println("Semifinal is not complete");
                        return;
                    }

                    semifinalComplete = true;
                    System.out.println("Semifinal is complete");

                    if(semifinalComplete)
                    {
                        winnersIDs = adapter.getTheWinnersIDs(match.getTournamentID(), match.getStage());
                        date = match.getDate().toLocalDate();
                    }
                }
                if(match.getStage().equals("Final"))
                {
                    finalMatchHaveBeenCreated = true;
                    winnersIDs = adapter.getTheWinnersIDs(match.getTournamentID(), match.getStage());
                }
            }
        }

        if(firstRoundComplete && !semifinalMatchesHaveBeenCreated)
        {
           adapter.addNewMatch(date.plusDays(1), tournamentID, winnersIDs.get(0), winnersIDs.get(1), "Semifinal");
           adapter.addNewMatch(date.plusDays(1), tournamentID, winnersIDs.get(2), winnersIDs.get(3), "Semifinal");
        }

        if(semifinalComplete && !finalMatchHaveBeenCreated)
        {
            adapter.addNewMatch(date.plusDays(1), tournamentID, winnersIDs.get(0), winnersIDs.get(1), "Final");
        }

        if(finalMatchHaveBeenCreated)
        {
            tournamentWinnerID = winnersIDs.get(0);
            System.out.println(tournamentWinnerID);
        }
    }

    public void addToWinners(int tournamentID, String stage, int teamID, int scoresDiff)
    {
        adapter.addToWinners(tournamentID, stage, teamID, scoresDiff);
    }

    public void updateWinners(int tournamentID, String stage, int winnerID, int scoresDiff, int oldWinnerID, int oldScoreDiff)
    {
        adapter.updateWinners(tournamentID, stage, winnerID, scoresDiff, oldWinnerID, oldScoreDiff);
    }

    public void saveChangesForMatch(int team1OldScores, int team2OldScores, String team1Name, String team2Name, int t1scores, int t2scores)
    {
        if (team1OldScores == 0 && team2OldScores == 0)
        {
            setTeamsWonMatches(team1Name, team2Name, t1scores, t2scores);
            if(t1scores > t2scores)
            {
                int scoresDiff = t1scores - t2scores;
                addToWinners(chosenMatchToEdit.getTournamentID(), chosenMatchToEdit.getStage(), chosenMatchToEdit.getTeam1ID(), scoresDiff);
            }

            if(t1scores < t2scores)
            {
                int scoresDiff = t2scores - t1scores;
                addToWinners(chosenMatchToEdit.getTournamentID(), chosenMatchToEdit.getStage(), chosenMatchToEdit.getTeam2ID(), scoresDiff);
            }
        }



        if(team1OldScores > team2OldScores && t1scores < t2scores)
        {
            int oldScoreDiff = team1OldScores - team2OldScores;
            int newScoresDiff = oldScoreDiff - (t2scores - t1scores);
            if(newScoresDiff < 0)
            {
                newScoresDiff = newScoresDiff*(-1);
            }
            setNewValueTeamsWonMatches(team1Name, team2Name, 1, 2);
            updateWinners(chosenMatchToEdit.getTournamentID(), chosenMatchToEdit.getStage(), chosenMatchToEdit.getTeam2ID(), newScoresDiff,
                    chosenMatchToEdit.getTeam1ID(), oldScoreDiff);

            //foosballLogic.checkAreNextMatchesCreated(matchToEdit.getTournamentID(), matchToEdit.getStage())

        }

        if(team1OldScores < team2OldScores && t1scores > t2scores)
        {
            int oldScoreDiff = team2OldScores - team1OldScores;
            int newScoresDiff = (t1scores - t2scores) - oldScoreDiff;
            if(newScoresDiff < 0)
            {
                newScoresDiff = newScoresDiff*(-1);
            }
            setNewValueTeamsWonMatches(team1Name, team2Name, 2, 1);
            updateWinners(chosenMatchToEdit.getTournamentID(), chosenMatchToEdit.getStage(), chosenMatchToEdit.getTeam1ID(), newScoresDiff,
                    chosenMatchToEdit.getTeam2ID(), oldScoreDiff);
        }

        checkPlayedMatches(chosenMatchToEdit.getTournamentID());
    }

    public ArrayList<Schedule> getSchedule(String chosenTournament, String s)
    {
        int tourID = 0;
        for (Tournament t: tournaments)
        {
            if(chosenTournament.equals(t.getName()))
            {
                tourID = t.getId();
            }
        }
        return adapter.getSchedule(tourID, s);
    }

    public Player getPlayerLoggedIN(String userName, String pass)
    {
        for(Player p: adapter.getPlayers())
        {
            if(userName.equals(p.getMail()) && pass.equals(p.getPassword()))
            {
                playerLogged = p;
            }
        }
        return playerLogged;
    }

    public ArrayList<Team> getPlayerTeams()
    {
        ArrayList<Team> teams = new ArrayList<>();
        teams = adapter.getTeams();

        ArrayList<Team> playerTeams = new ArrayList<>();


        for (Team team: teams)
        {
            if(playerLogged.getId() == team.getPlayer1ID() || playerLogged.getId() == team.getPlayer2ID()){
                playerTeams.add(team);
            }
        }
        return playerTeams;
    }

    public ArrayList<Match> getPlayerMatches()
    {
        ArrayList<Match> playerMatches = new ArrayList<>();

        for(Team team: getPlayerTeams())
        {
            for(Match match: matches)
            {
                if(team.getId() == match.getTeam1ID() || team.getId() == match.getTeam2ID())
                {
                   playerMatches.add(match);
                }
            }
        }
        return playerMatches;
    }



    public Player getPlayerLoggedIN()
    {
        return this.playerLogged;
    }

    public ArrayList<Tournament> getPlayerTournaments()
    {
        ArrayList<Tournament> tournaments = new ArrayList<>();
        for(Integer i: adapter.getPlayerTournamentsIDs(playerLogged))
        {
            for(Tournament tournament: getTournaments())
            {
                if(i == tournament.getId())
                {
                    tournaments.add(tournament);
                }
            }
        }
        return tournaments;

    }

    public ArrayList<String> getPlayerTournamentsNames()
    {
        ArrayList<String> playerTournamentsNames = new ArrayList<>();

        for (Tournament tournament: getPlayerTournaments())
        {
            playerTournamentsNames.add(tournament.getName());
        }

        return playerTournamentsNames;
    }

    public ArrayList<Player> getAvailablePlayers()
    {
        for (Player pl: getPlayers())
        {
            if(pl.getStatus().equals("available"))
            {
                availablePlayers.add(pl);
            }
        }
        return availablePlayers;
    }

    public ArrayList<Player> searchAvailablePlayers(String searchText) {
        return adapter.searchAvailablePlayers(searchText);
    }

    public Player getPlayer()
    {
        return adapter.getPlayerByID(playerLogged.getId());
    }
}
