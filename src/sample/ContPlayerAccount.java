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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ContPlayerAccount implements Initializable
{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();
    Player currentPlayer;


    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField emailField, nameField, dateOfBirthField, passField;

    @FXML
    ComboBox statusComboBox;

    @FXML
    Label redLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));

        loadData();
    }

    private void loadData()
    {
        redLabel.setVisible(false);

        currentPlayer = foosballLogic.getPlayer();

        nameField.setText(currentPlayer.getName());
        dateOfBirthField.setText(currentPlayer.getDateOfBirth().toString());
        emailField.setText(currentPlayer.getMail());
        passField.setText(currentPlayer.getPassword());
        statusComboBox.setValue(currentPlayer.getStatus());

        ObservableList items = FXCollections.observableArrayList();
        items.addAll("available","not available");
        statusComboBox.setItems(items);

    }

    public void goBack(ActionEvent actionEvent) throws IOException
    {
        use.goBack(actionEvent, "screenPlayerChoice.fxml");
    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void saveChanges(ActionEvent actionEvent) throws IOException
    {
        redLabel.setVisible(false);

        //if there are no changes in the passwordField and the field for the status - is not doing anything
        if(statusComboBox.getSelectionModel().getSelectedItem().toString().equals(currentPlayer.getStatus()) &&
                passField.getText().equals(currentPlayer.getPassword()))
        {
            redLabel.setVisible(true);
            return;
        }

        //if only the field for the status is modified - save changes only for the status
        if(!statusComboBox.getSelectionModel().getSelectedItem().toString().equals(currentPlayer.getStatus()) &&
                passField.getText().equals(currentPlayer.getPassword()))
        {
            foosballLogic.savePlayerChanges
                    (
                    currentPlayer.getName(), currentPlayer.getDateOfBirth().toLocalDate(), currentPlayer.getMail(), currentPlayer.getPassword(),
                    statusComboBox.getSelectionModel().getSelectedItem().toString(), currentPlayer.getId()
                    );

            goBack(actionEvent);
            return;
        }

        //if only the field for the password is modified - save changes only for the password
        if(statusComboBox.getSelectionModel().getSelectedItem().toString().equals(currentPlayer.getStatus()) &&
                !passField.getText().equals(currentPlayer.getPassword()))
        {
            foosballLogic.savePlayerChanges
                    (
                            currentPlayer.getName(), currentPlayer.getDateOfBirth().toLocalDate(), currentPlayer.getMail(), passField.getText(),
                            currentPlayer.getStatus(), currentPlayer.getId()
                    );

            goBack(actionEvent);
            return;
        }

        //if both are modified - save changes for both
        if(!statusComboBox.getSelectionModel().getSelectedItem().toString().equals(currentPlayer.getStatus()) &&
                !passField.getText().equals(currentPlayer.getPassword()))
        {
            foosballLogic.savePlayerChanges
                    (
                            currentPlayer.getName(), currentPlayer.getDateOfBirth().toLocalDate(), currentPlayer.getMail(), passField.getText(),
                            statusComboBox.getSelectionModel().getSelectedItem().toString(), currentPlayer.getId()
                    );

            goBack(actionEvent);
            return;
        }
    }
}
