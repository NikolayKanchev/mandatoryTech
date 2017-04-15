package model;

import java.time.LocalDate;

/**
 * Created by nikol on 4/6/2017.
 */
public class Player {
    private String name;
    private LocalDate dateOfBirth;
    private String eMail;
    private int rank;

    public Player(String name, LocalDate dateOfBirth, String eMail){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
