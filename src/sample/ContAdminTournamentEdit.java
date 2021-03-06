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
import model.Tournament;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Didi on 04/14/2017.
 */
public class ContAdminTournamentEdit implements Initializable{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();
    Tournament tournamentToEdit;
    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @FXML
    TextField nameTF;

    @FXML
    DatePicker startDate, endDate;

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
        use.goBack(actionEvent, "screenAdminTournaments.fxml");
    }

    public void saveTournamentChanges(ActionEvent actionEvent) throws IOException
    {
        foosballLogic.saveTournamentChanges(nameTF.getText(), startDate.getValue(), endDate.getValue(), tournamentToEdit);
        goBack(actionEvent);
    }


    /*Adding 2 listeners. The one for the start date is changing the end date to be 3 days after the start date.
     * The one for the end date is checking if the chosen ednDate value is min 3 days after the start date
      * if it is not - it sets it to 3 days after*/
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        tournamentToEdit = foosballLogic.getChosenTournamentToEdit();
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
        loadData();

        startDate.valueProperty().addListener(new ChangeListener<LocalDate>()
        {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
            {
                try
                {
                    if (startDate.isManaged())
                    {
                        redLabel.setVisible(false);
                        endDate.setValue(startDate.getValue().plusDays(3));
                    }

                }catch (Exception e)

                {
                    redLabel.setText("You have to fill out all the fields");
                    redLabel.setVisible(true);
                }
            }
        });

        endDate.valueProperty().addListener(new ChangeListener<LocalDate>()
        {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
            {
                if(endDate.isManaged())
                {
                    if(endDate.getValue().isBefore(startDate.getValue().plusDays(3)))
                    {
                        redLabel.setText("The end date should be minimum 3 days after the start date");
                        redLabel.setVisible(true);
                        endDate.setValue(startDate.getValue().plusDays(3));
                        return;
                    }

                    if(endDate.getValue().isAfter(startDate.getValue().plusDays(3)))
                    {
                        redLabel.setVisible(false);
                        return;
                    }
                }
            }
        });
    }

    public void loadData()
    {
        nameTF.setText(tournamentToEdit.getName());
        startDate.setValue(tournamentToEdit.getStartDate().toLocalDate());
        endDate.setValue(tournamentToEdit.getEndDate().toLocalDate());
    }
}
