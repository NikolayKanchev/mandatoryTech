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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.FoosballLogic;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContAdminPlayers implements Initializable{

    private FoosballLogic foosballLogic = FoosballLogic.getInstance();
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
    TextField nameField, eMailField, passField, searchField;

    @FXML
    DatePicker dateOfBirthField;

    @FXML
    Label redLabel, redLabelTop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPlayers(foosballLogic.getPlayers());
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

        try{
            if (dateOfBirthField.getValue().isAfter(LocalDate.now().minusYears(15))){
                redLabel.setText("The player should be at least 15 years old");
                redLabel.setVisible(true);
                dateOfBirthField.setValue(LocalDate.now().minusYears(15));
                return;
            }else{
                redLabel.setVisible(false);
            }
        }catch (Exception e){
            redLabel.setText("You have to choose 'Date of birth'");
            redLabel.setVisible(true);
            return;
        }

        if(foosballLogic.checkUserAndPass(eMailField.getText(), passField.getText(), "players")){
            redLabel.setText("This player exist in the system");
            redLabel.setVisible(true);
            return;
        }

        foosballLogic.addNewPlayer(
                nameField.getText(),
                dateOfBirthField.getValue(),
                eMailField.getText(),
                passField.getText()
        );

        loadPlayers(foosballLogic.getPlayers());
    }

    public void deletePlayer(ActionEvent actionEvent) {
        Player player = tableView.getSelectionModel().getSelectedItem();
        if(player == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);

        foosballLogic.deletePlayer(player.getId());
        loadPlayers(foosballLogic.getPlayers());
    }

    public void editPlayer(ActionEvent actionEvent) throws IOException {
        Player player = tableView.getSelectionModel().getSelectedItem();
        if(player == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);


        foosballLogic.setChosenPlayerToEdit(player);
        Stage stage = (Stage)(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("screenAdminPlayerEdit.fxml")), 800, 600));

    }

    public void loadPlayers(ArrayList<Player> pl) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        eMailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        ObservableList<Player> players = FXCollections.observableArrayList();
        players.addAll(pl);
        tableView.setItems(players);
    }


    public void searchPlayers(KeyEvent keyEvent) {
        loadPlayers(foosballLogic.searchPlayers(searchField.getText()));
    }
}
