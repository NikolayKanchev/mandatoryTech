package logic;

import dataBase.Adapter;
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
    private Player players;
    private Player chosenPlayerToEdit;
    private Object teams;
    private ArrayList<Player> availablePlayers;
    private Team chosenTeamToEdit;

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
}
