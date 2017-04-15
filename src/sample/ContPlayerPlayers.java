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

/**
 * Created by Didi on 04/09/2017.
 */
public class ContPlayerPlayers implements Initializable{

    UseAgain use = UseAgain.getInstance();

    @FXML
    ChoiceBox exitOptions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitOptions.setItems(FXCollections.observableArrayList("Log out", "Exit"));
    }

    public void exitOrLogOut(MouseEvent mouseEvent) {
        use.exitOrLogOut(mouseEvent, exitOptions);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        use.goBack(actionEvent, "screenPlayerChoice.fxml");
    }

    public void searchAvailablePlayers(ActionEvent actionEvent) {

    }
}
