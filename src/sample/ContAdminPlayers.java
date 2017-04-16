package sample;

import javafx.collections.FXCollections;
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
import model.FoosballHouse;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContAdminPlayers implements Initializable{

    private FoosballHouse foosballHouse = FoosballHouse.getInstance();
    private UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TableView<Player> tableView;

    @FXML
    TableColumn<Player, Integer> idColumn;

    @FXML
    TableColumn<Player, String> nameColumn, eMailColumn, passColumn, statusColumn;

    @FXML
    TableColumn<Player, Date> dateOfBirthColumn;

    @FXML
    TableColumn<Player, Integer> rankColumn;

    @FXML
    TextField nameField, eMailField, passField;

    @FXML
    DatePicker dateOfBirthField;

    @FXML
    Label redLabel, redLabelTop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPlayers();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }

    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminChoice.fxml");
    }

    public void addNewPlayer(ActionEvent actionEvent) {
        if (nameField.getText().isEmpty() || eMailField.getText().isEmpty() || passField.getText().isEmpty()){
            redLabel.setVisible(true);
            return;
        }else{
            redLabel.setVisible(false);
        }

        if (dateOfBirthField.getValue() == null){
            redLabel.setText("You have to choose 'Date of birth'");
            redLabel.setVisible(true);
        }else{
            redLabel.setVisible(false);
        }

        foosballHouse.addNewPlayer(
                nameField.getText(),
                dateOfBirthField.getValue(),
                eMailField.getText(),
                passField.getText()
        );

        loadPlayers();
    }

    public void deletePlayer(ActionEvent actionEvent) {
        Player player = tableView.getSelectionModel().getSelectedItem();
        if(player == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);

        foosballHouse.deletePlayer(player.getId());
        loadPlayers();
    }

    public void editPlayer(ActionEvent actionEvent) throws IOException {
        Player player = tableView.getSelectionModel().getSelectedItem();
        if(player == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);


        foosballHouse.setChosenPlayerToEdit(player);
        Stage stage = (Stage)(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("screenAdminPlayerEdit.fxml")), 800, 600));

    }

    public void searchPlayers(ActionEvent actionEvent) {

    }

    public void loadPlayers() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        eMailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        ObservableList<Player> players = FXCollections.observableArrayList();
        players.addAll(foosballHouse.getPlayers());
        tableView.setItems(players);
    }
}
