package l2dsi2.firas.miniprojetfx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.UserDAO;
import l2dsi2.firas.miniprojetfx.Model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    User user;
    @FXML
    private Button loginButton;
    @FXML
    private Label label;
    @FXML
    private TextField loginInput;
    @FXML
    private PasswordField passInput;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginInput.setText("firas@gmail.com");
        passInput.setText("aze.1234");
    }

    @FXML

    protected void login(ActionEvent event) {
        String login = loginInput.getText();
        String password = passInput.getText();

        // Check if any field is empty
        if (login.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return;
        }

        user = UserDAO.login(login, password);
        if (user != null) {
            Alert dia = new Alert(Alert.AlertType.INFORMATION);
            dia.setTitle("Authentification");
            dia.setHeaderText("Message");
            dia.setContentText("Bienvenue " + user.getUsername());
            dia.show();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/Patients.fxml"));
            try {
                // Set the new scene
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setTitle("Phamacie App");
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert dia = new Alert(Alert.AlertType.ERROR);
            dia.setTitle("Authentification");
            dia.setHeaderText("Message");
            dia.setContentText("Login ou mot de passe incorrect");
            dia.show();
        }
    }


}
