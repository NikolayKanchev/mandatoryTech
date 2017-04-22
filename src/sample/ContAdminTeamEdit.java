package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import logic.FoosballLogic;
import model.Player;
import model.Team;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminTeamEdit implements Initializable{
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


    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminTournaments.fxml");
    }

    public void saveTeamChanges(ActionEvent actionEvent) throws IOException {
        //foosballLogic.saveTournamentChanges(nameTF.getText(), startDate.getValue(), endDate.getValue(), tournamentToEdit);
        goBack(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamToEdit = foosballLogic.getChosenTeamToEdit();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        loadData();

    }

    public void loadData(){
        String player1Name = "";
        String player2Name = "";

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

    public void choosePlayer1(ActionEvent event) {

    }

    public void choosePlayer2(ActionEvent event) {
        
    }
}
