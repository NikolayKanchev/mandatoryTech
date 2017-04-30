package model;

import java.sql.Date;

/**
 * Created by nikol on 4/6/2017.
 */
public class Player
{
    private int id;
    private String name;
    private Date dateOfBirth;
    private String mail;
    private int rank;
    private String status;
    private String password;

    public Player(int id, String name, Date dateOfBirth, String mail, String password)
    {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.mail = mail;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
