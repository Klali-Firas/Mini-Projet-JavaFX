package l2dsi2.firas.miniprojetfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.UserDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAdminController  implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField telephoneField;

    @FXML
    private Button Annuler;


    @FXML
    private void handleSubmitButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String login = loginField.getText();
        String telephone = telephoneField.getText();

        // Check if any field is empty
        if (username.isEmpty() || password.isEmpty() || login.isEmpty() || telephone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return;
        }

        // Check if telephone is an integer of length 8
        if (!telephone.matches("\\d{8}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Telephone number must be an integer of length 8");
            alert.showAndWait();
            return;
        }

        if(UserDAO.register(username, password, login, Integer.parseInt(telephone)))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Admin added successfully");
            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/Patients.fxml"));
            Stage stage = (Stage) Annuler.getScene().getWindow();
            stage.setTitle("Patients");
            try {
                stage.setScene(new javafx.scene.Scene(fxmlLoader.load()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error adding admin");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Annuler.setOnAction(event -> {
            usernameField.setText("");
            passwordField.setText("");
            loginField.setText("");
            telephoneField.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/Patients.fxml"));
            Stage stage = (Stage) Annuler.getScene().getWindow();
            stage.setTitle("Patients");
            try {
                stage.setScene(new javafx.scene.Scene(fxmlLoader.load()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



    }
}