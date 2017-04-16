package model;

import dataBase.Adapter;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Didi on 04/13/2017.
 */
public class FoosballHouse {
    Adapter adapter = Adapter.getInstance();
    private static FoosballHouse ourInstance;
    private Tournament chosenTournamentToEdit;
    private Player players;
    private Player chosenPlayerToEdit;

    public static synchronized FoosballHouse getInstance() {
        if (ourInstance == null){
            ourInstance = new FoosballHouse();
        }
        return ourInstance;
    }

    private FoosballHouse() {
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

    public void savePlayerChanges(String name, LocalDate dateOfBirth, String eMail, String pass, String status) {
        adapter.savePlayerChanges(name, dateOfBirth, eMail, pass, status);
    }
}
