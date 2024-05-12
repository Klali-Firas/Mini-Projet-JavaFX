package l2dsi2.firas.miniprojetfx.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.UserDAO;
import l2dsi2.firas.miniprojetfx.Model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
    private TextField passInput;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void login(ActionEvent event) {
        user = UserDAO.login(loginInput.getText(), passInput.getText());
//        System.out.println(BCrypt.hashpw("aze.1234", BCrypt.gensalt()));
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
