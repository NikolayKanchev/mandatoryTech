package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContAdminChoice implements Initializable{

    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;



    public void manageTournaments(ActionEvent actionEvent) throws IOException
    {
        changeScreen(actionEvent, "screenAdminTournaments.fxml");
    }

    public void manageMatches(ActionEvent actionEvent) throws IOException
    {
        changeScreen(actionEvent, "screenAdminMatches.fxml");
    }

    public void manageTeams(ActionEvent actionEvent) throws IOException
    {
        changeScreen(actionEvent, "screenAdminTeams.fxml");
    }

    public void managePlayers(ActionEvent actionEvent) throws IOException
    {
        changeScreen(actionEvent, "screenAdminPlayers.fxml");
    }

    public void manageSchedule(ActionEvent actionEvent) throws IOException
    {
        changeScreen(actionEvent, "screenAdminSchedule.fxml");
    }

    public void exitOrLogOut(MouseEvent mouseEvent)
    {
       use.exitOrLogOut(mouseEvent, exitOptions);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }

    public void changeScreen(ActionEvent e, String s) throws IOException
    {
        Stage stage = (Stage)(((Node) e.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(s)), 800, 600));
    }
}
