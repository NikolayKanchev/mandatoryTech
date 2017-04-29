package model;

import java.sql.Date;

/**
 * Created by Nikolaj on 29-04-2017.
 */
public class Schedule
{
    private Date date;
    private int matchID;
    private String team1ID;
    private String team2ID;
    private String result;
    private int tourID;
    private String stage;

    public Schedule(Date date, int matchID, String team1ID, String team2ID, String result, int tourID, String stage)
    {
        this.date = date;
        this.matchID = matchID;
        this.team1ID = team1ID;
        this.team2ID = team2ID;
        this.result = result;
        this.tourID = tourID;
        this.stage = stage;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getMatchID()
    {
        return matchID;
    }

    public void setMatchID(int matchID)
    {
        this.matchID = matchID;
    }

    public String getTeam1ID()
    {
        return team1ID;
    }

    public void setTeam1ID(String team1ID)
    {
        this.team1ID = team1ID;
    }

    public String getTeam2ID()
    {
        return team2ID;
    }

    public void setTeam2ID(String team2ID)
    {
        this.team2ID = team2ID;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public int getTourID()
    {
        return tourID;
    }

    public void setTourID(int tourID)
    {
        this.tourID = tourID;
    }

    public String getStage()
    {
        return stage;
    }

    public void setStage(String stage)
    {
        this.stage = stage;
    }
}
