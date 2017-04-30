package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import logic.FoosballLogic;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContPlayerPlayers implements Initializable
{
    private FoosballLogic foosballLogic = FoosballLogic.getInstance();

    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TableView<Player> tableView;

    @FXML
    TableColumn<Player, Integer> idColumn;

    @FXML
    TableColumn<Player, String> nameColumn, eMailColumn;

    @FXML
    TableColumn<Player, Date> dateOfBirthColumn;

    @FXML
    TableColumn<Player, Integer> rankColumn;

    @FXML
    TextField searchField;

    @FXML
    Label redLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadPlayers(foosballLogic.getAvailablePlayers());

        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException
    {
        use.goBack(actionEvent, "screenPlayerChoice.fxml");
    }

    public void loadPlayers(ArrayList<Player> pl)
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        eMailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        ObservableList<Player> players = FXCollections.observableArrayList();
        players.addAll(pl);
        tableView.setItems(players);
    }

    public void searchPlayers(KeyEvent keyEvent)
    {
        loadPlayers(foosballLogic.searchAvailablePlayers(searchField.getText()));
    }

}
