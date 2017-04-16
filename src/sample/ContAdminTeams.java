package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.FoosballHouse;
import model.Team;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContAdminTeams implements Initializable{
    private FoosballHouse foosballHouse = FoosballHouse.getInstance();
    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    Label redLabelTop;

    @FXML
    TableView tableView;

    @FXML
    TableColumn<Team, Integer> idColumn, wonMatchesColumn, lostMatchesColumn;

    @FXML
    TableColumn<Team, String> nameColumn, player1Column, player2Column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTeams(foosballHouse.getTeams());
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }


    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminChoice.fxml");
    }

    public void addNewTeam(ActionEvent actionEvent) {

    }

    public void deleteTeam(ActionEvent actionEvent) {
        

    }

    public void editTeam(ActionEvent actionEvent) {

    }

    public void searchTeams(ActionEvent actionEvent) {

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
}
