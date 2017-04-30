package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.FoosballLogic;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.YELLOW;


public class ContLogin implements Initializable
{
    FoosballLogic foosballLogic = FoosballLogic.getInstance();
    Player currentPlayer;

    @FXML
    Label redLabel, blueLabel;

    @FXML
    ChoiceBox choice;

    @FXML
    TextField userNameField;

    @FXML
    PasswordField passField;

    public void signIn(ActionEvent actionEvent) throws IOException
    {
        String tableDB = "";

        if (choice.getSelectionModel().isEmpty()){
            redLabel.setText("You have to choose 'Status'");
            redLabel.setVisible(true);
            return;
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Admin")){
            tableDB = "admins";
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Player")){
            tableDB = "players";
        }

        if(userNameField.getText().isEmpty() || passField.getText().isEmpty()){
            redLabel.setText("You have to fill out all the fields");
            redLabel.setVisible(true);
            return;
        }

        if(!foosballLogic.checkUserAndPass(userNameField.getText(), passField.getText(), tableDB)){
            redLabel.setText("Wrong username or password");
            redLabel.setVisible(true);
            return;
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Admin")){
            changeScreen(actionEvent, "screenAdminChoice.fxml");
        }

        if(choice.getSelectionModel().getSelectedItem().equals("Player")){
            currentPlayer = foosballLogic.getPlayerLoggedIN(userNameField.getText(), passField.getText());
            changeScreen(actionEvent, "screenPlayerChoice.fxml");
        }
    }


    /*Adds a listener witch sets the prompt text for the field user name*/
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        choice.setItems(FXCollections.observableArrayList("Admin", "Player"));
        choice.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(choice.getSelectionModel().getSelectedItem().equals("Admin")){
                    userNameField.setPromptText(null);
                    redLabel.setVisible(false);
                }else{
                    userNameField.setPromptText("e-mail");
                    redLabel.setVisible(false);
                }
            }
        });

        Tooltip tooltip = new Tooltip();

        tooltip.setText(
                "To login as an 'Admin' use:" +
                        "\n user name : admin" +
                        "\n password  : 1234\n" +
                        "\n To login as a 'Player' use:" +
                        "\n user name : dim@yahoo.com" +
                        "\n password  : dimpass");

        blueLabel.setTooltip(tooltip);

    }

    public void changeScreen(ActionEvent e, String s) throws IOException
    {
        Stage stage = (Stage)(((Node) e.getSource()).getScene().getWindow());
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(s)), 800, 600));
    }

    public void showTip(MouseEvent mouseEvent)
    {

    }
}
