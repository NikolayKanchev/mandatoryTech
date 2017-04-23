package model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by nikol on 4/6/2017.
 */
public class Match {
    private int id;
    private Date date;
    private int tournamentID;
    private String tournamentName;
    private String stage;
    private int team1ID;
    private int team2ID;
    private String team1Name;
    private String team2Name;
    private int team1scores;
    private int team2scores;

    public Match(int id, Date date, int tournamentID, String stage, int team1ID, int team2ID)
    {
        this.id = id;
        this.date = date;
        this.tournamentID = tournamentID;
        this.stage = stage;
        this.team1ID = team1ID;
        this.team2ID = team2ID;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getTournamentID()
    {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID)
    {
        this.tournamentID = tournamentID;
    }

    public String getTournamentName()
    {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName)
    {
        this.tournamentName = tournamentName;
    }

    public String getStage()
    {
        return stage;
    }

    public void setStage(String stage)
    {
        this.stage = stage;
    }

    public int getTeam1ID()
    {
        return team1ID;
    }

    public void setTeam1ID(int team1ID)
    {
        this.team1ID = team1ID;
    }

    public int getTeam2ID()
    {
        return team2ID;
    }

    public void setTeam2ID(int team2ID)
    {
        this.team2ID = team2ID;
    }

    public String getTeam1Name()
    {
        return team1Name;
    }

    public void setTeam1Name(String team1Name)
    {
        this.team1Name = team1Name;
    }

    public String getTeam2Name()
    {
        return team2Name;
    }

    public void setTeam2Name(String team2Name)
    {
        this.team2Name = team2Name;
    }

    public int getTeam1scores()
    {
        return team1scores;
    }

    public void setTeam1scores(int team1scores)
    {
        this.team1scores = team1scores;
    }

    public int getTeam2scores()
    {
        return team2scores;
    }

    public void setTeam2scores(int team2scores)
    {
        this.team2scores = team2scores;
    }
}
