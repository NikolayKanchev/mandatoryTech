package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.FoosballHouse;
import model.Player;
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminPlayerEdit implements Initializable{
    private FoosballHouse foosballHouse = FoosballHouse.getInstance();
    private Player playerToEdit;
    private UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField nameTF, eMailTF, passTF, statusTF;

    @FXML
    DatePicker dateOfBirth;

    @FXML
    Button saveButton;

    @FXML
    Label redLabel;


    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenAdminPlayers.fxml");
    }

//    public void saveTournamentChanges(ActionEvent actionEvent) throws IOException {
//        foosballHouse.saveTournamentChanges(nameTF.getText(), startDate.getValue(), endDate.getValue(), tournamentToEdit);
//        goBack(actionEvent);
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerToEdit = foosballHouse.getChosenPlayerToEdit();
        loadData();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

//        startDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
//            @Override
//            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
//                try {
//                    if (startDate.isManaged()) {
//                        redLabel.setVisible(false);
//                        endDate.setValue(startDate.getValue().plusDays(3));
//                    }
//                }catch (Exception e){
//                    redLabel.setText("You have to fill out all the fields");
//                    redLabel.setVisible(true);
//                }
//            }
//        });
//
//        endDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
//            @Override
//            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
//                if(endDate.isManaged()){
//                    if(endDate.getValue().isBefore(startDate.getValue().plusDays(3))){
//                        redLabel.setText("The end date should be minimum 3 days after the start date");
//                        redLabel.setVisible(true);
//                        endDate.setValue(startDate.getValue().plusDays(3));
//                        return;
//                    }
//
//                    if(endDate.getValue().isAfter(startDate.getValue().plusDays(3))){
//                        redLabel.setVisible(false);
//                        return;
//                    }
//                }
//            }
//        });
    }

    public void loadData(){
        nameTF.setText(playerToEdit.getName());
        dateOfBirth.setValue(playerToEdit.getDateOfBirth().toLocalDate());
        eMailTF.setText(playerToEdit.getMail());
        passTF.setText(playerToEdit.getPassword());
        statusTF.setText(playerToEdit.getStatus());
    }

    public void savePlayerChanges(ActionEvent actionEvent) {
        foosballHouse.savePlayerChanges(nameTF.getText(), dateOfBirth.getValue(), eMailTF.getText(), passTF.getText(), statusTF.getText());
    }
}
