package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.FoosballLogic;
import model.Player;
import model.Team;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContAdminTeams implements Initializable{
    private FoosballLogic foosballLogic = FoosballLogic.getInstance();
    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField nameField;

    @FXML
    Label redLabelTop, redLabel;

    @FXML
    TableView tableView;

    @FXML
    TableColumn<Team, Integer> idColumn, wonMatchesColumn, lostMatchesColumn;

    @FXML
    TableColumn<Team, String> nameColumn, player1Column, player2Column;

    @FXML
    ComboBox player1ComboBox, player2ComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTeams(foosballLogic.getTeams());
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        
    }


    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminChoice.fxml");
    }

    public void addNewTeam(ActionEvent actionEvent) {
        redLabel.setVisible(false);

        if (nameField.getText().isEmpty() || player1ComboBox.getSelectionModel().isEmpty() || player2ComboBox.getSelectionModel().isEmpty())
        {
            redLabel.setText("You have to fill out all the fields");
            redLabel.setVisible(true);
        }

        int player1ID = 0;
        int player2ID = 0;

        for (Player player: foosballLogic.getPlayers())
        {
            if(player.getName().equals(player1ComboBox.getSelectionModel().getSelectedItem().toString()))
            {
                player1ID = player.getId();
            }

            if(player.getName().equals(player2ComboBox.getSelectionModel().getSelectedItem().toString()))
            {
                player2ID = player.getId();
            }

        }

        foosballLogic.addNewTeam(nameField.getText(),player1ID, player2ID);
        loadTeams(foosballLogic.getTeams());
    }

    public void deleteTeam(ActionEvent actionEvent) {
        Team team = (Team) tableView.getSelectionModel().getSelectedItem();
        if(team == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);
        foosballLogic.deleteTeam(team.getId());
        loadTeams(foosballLogic.getTeams());

    }

    public void editTeam(ActionEvent actionEvent) throws IOException {
        Team team = (Team) tableView.getSelectionModel().getSelectedItem();
        if(team == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);

        foosballLogic.setChosenTeamToEdit(team);
        Stage stage = (Stage)(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("screenAdminTeamEdit.fxml")), 800, 600));
    }

    private void loadTeams(ArrayList<Team> t) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1Name"));
        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2Name"));
        wonMatchesColumn.setCellValueFactory(new PropertyValueFactory<>("wonMatches"));
        lostMatchesColumn.setCellValueFactory(new PropertyValueFactory<>("lostMatches"));

        ObservableList<Team> teams = FXCollections.observableArrayList();
        teams.addAll(t);
        tableView.setItems(teams);
    }

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

    public void choosePlayer2(MouseEvent mouseEvent)
    {
        redLabel.setVisible(false);

        if (player1ComboBox.getSelectionModel().isEmpty())
        {
            redLabel.setText("Choose first 'player1'");
            redLabel.setVisible(true);
            return;
        }

        String player1Name = player1ComboBox.getSelectionModel().getSelectedItem().toString();

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
