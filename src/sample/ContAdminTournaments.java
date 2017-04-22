package sample;

import dataBase.Adapter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import logic.FoosballLogic;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/09/2017.
 */
public class ContAdminTournaments implements Initializable{


    private UseAgain use = UseAgain.getInstance();
    private Adapter adapter = Adapter.getInstance();
    private FoosballLogic foosballLogic = FoosballLogic.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    DatePicker startDateField, endDateField;

    @FXML
    TextField nameField;

    @FXML
    Label redLabel, redLabelTop;

    @FXML
    Button addButton;

    @FXML
    TableView<Tournament> table;

    @FXML
    TableColumn<Tournament, Integer> idColumn;

    @FXML
    TableColumn<Tournament, String> nameColumn;

    @FXML
    TableColumn<Tournament, Date> startDateColumn, endDateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTournaments();

        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        startDateField.setValue(LocalDate.now());
        endDateField.setValue(startDateField.getValue().plusDays(3));

        startDateField.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (startDateField.getValue().isBefore(LocalDate.now())) {
                    startDateField.setValue(LocalDate.now());
                    redLabel.setText("The tournament can not start from previous date");
                    redLabel.setVisible(true);
                }

                if (startDateField.getValue().isAfter(LocalDate.now())) {
                    redLabel.setVisible(false);
                }
                endDateField.setValue(startDateField.getValue().plusDays(3));


            }
        });

    }

    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void addNewTournament(ActionEvent actionEvent) {
        if(nameField.getText().isEmpty()){
            redLabel.setText("You have fill out the field 'Name'");
            redLabel.setVisible(true);
            return;
        }else {
            redLabel.setVisible(false);
            foosballLogic.addNewTournament(nameField.getText(), startDateField.getValue(), endDateField.getValue());
            loadTournaments();
        }
    }

    public void deleteTournament(ActionEvent actionEvent) {
        Tournament tournament = table.getSelectionModel().getSelectedItem();
        if(tournament == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);
        foosballLogic.deleteTournament(tournament.getId());
        loadTournaments();
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminChoice.fxml");

    }

    public void editTournament(ActionEvent actionEvent) throws IOException {
        Tournament tournament = table.getSelectionModel().getSelectedItem();
        if(tournament == null){
            redLabelTop.setVisible(true);
            return;
        }

        redLabelTop.setVisible(false);

        foosballLogic.setChosenTournamentToEdit(tournament);
        Stage stage = (Stage)(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("screenAdminTournamentEdit.fxml")), 800, 600));
    }

    public void loadTournaments() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        ObservableList<Tournament> tournaments = FXCollections.observableArrayList();
        tournaments.addAll(foosballLogic.getTournaments());
        table.setItems(tournaments);
    }
}
