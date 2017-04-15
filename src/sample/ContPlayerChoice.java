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

public class ContPlayerChoice implements Initializable{

    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;


    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }

    public void seeTournaments(ActionEvent actionEvent) throws IOException {
       changeScreen(actionEvent, "screenPlayerMatches.fxml");
    }

    public void seeAllTournamentsAndMatches(ActionEvent actionEvent) throws IOException {
        changeScreen(actionEvent, "screenPlayerTournaments.fxml");
    }

    public void seeTeams(ActionEvent actionEvent) throws IOException {
        changeScreen(actionEvent, "screenPlayerTeams.fxml");
    }

    public void seeAvailablePlayers(ActionEvent actionEvent) throws IOException {
        changeScreen(actionEvent, "screenPlayerPlayers.fxml");
    }

    public void seeAccountInfo(ActionEvent actionEvent) throws IOException {
        changeScreen(actionEvent, "screenPlayerAccount.fxml");
    }

    public void changeScreen(ActionEvent e, String s) throws IOException {
        Stage stage = (Stage)(((Node) e.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(s)), 800, 600));
    }
}
