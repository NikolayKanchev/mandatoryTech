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
}
