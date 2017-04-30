package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.FoosballLogic;
import model.Player;
import model.Team;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminTeamEdit implements Initializable
{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();

    Team teamToEdit;

    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField nameTF;

    @FXML
    ComboBox player1ComboBox, player2ComboBox;

    @FXML
    Button saveButton;

    @FXML
    Label redLabel;


    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException
    {
        use.goBack(actionEvent, "screenAdminTeams.fxml");
    }

    public void saveTeamChanges(ActionEvent actionEvent) throws IOException
    {
        redLabel.setVisible(false);
        String player1Name = "";
        String player2Name = "";

        if(player1ComboBox.getSelectionModel().isEmpty())
        {
            player1Name = player1ComboBox.getPromptText();
        }else
        {
            player1Name = player1ComboBox.getSelectionModel().getSelectedItem().toString();
        }

        if(player2ComboBox.getSelectionModel().isEmpty())
        {
            player2Name = player2ComboBox.getPromptText();
        }else
        {
            player2Name = player2ComboBox.getSelectionModel().getSelectedItem().toString();
        }


        if(player1Name.equals(player2Name))
            {
                redLabel.setText("Player1 and Player2 can't be the same person");
                redLabel.setVisible(true);
                return;
            }

        foosballLogic.saveTeamChanges(nameTF.getText(), player1Name, player2Name, teamToEdit);
        goBack(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        teamToEdit = foosballLogic.getChosenTeamToEdit();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        loadData();
    }

    public void loadData(){
        String player1Name = "";
        String player2Name = "";

        //looping tru tru the players to find player1 name and player2 name
        for (Player player: foosballLogic.getPlayers()){
            if(player.getId() == teamToEdit.getPlayer1ID()){
                player1Name = player.getName();
            }

            if(player.getId() == teamToEdit.getPlayer2ID()){
                player2Name = player.getName();
            }

        }

        nameTF.setText(teamToEdit.getName());
        player1ComboBox.setPromptText(player1Name);
        player2ComboBox.setPromptText(player2Name);
    }

    //loading player1-names in the comboBox
    public void choosePlayer1(MouseEvent mouseEvent)
    {
        ArrayList<Player> availablePlayers = foosballLogic.getPlayers();

        ObservableList players = FXCollections.observableArrayList();

        for (Player player: availablePlayers)
        {
            players.add(player.getName());
        }

        player1ComboBox.setItems(players);
    }

    //loading player-names in the comboBox but excluding the chosen player1
    public void choosePlayer2(MouseEvent mouseEvent)
    {
        redLabel.setVisible(false);
        String player1Name = "";

        if (player1ComboBox.getSelectionModel().isEmpty())
        {
            player1Name = player1ComboBox.getPromptText();
        }else
            {
                player1Name = player1ComboBox.getSelectionModel().getSelectedItem().toString();
            }


        ArrayList<Player> availablePlayers = foosballLogic.getPlayers();

        ObservableList players = FXCollections.observableArrayList();

        for (Player player: availablePlayers)
        {
            if (!player.getName().equals(player1Name))
            {
                players.add(player.getName());
            }
        }

        player2ComboBox.setItems(players);

    }
}
