package l2dsi2.firas.miniprojetfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.PatientDao;
import l2dsi2.firas.miniprojetfx.Model.Patient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPatientController implements Initializable {
    @FXML
    private TextField nomInput;
    @FXML
    private TextField prenomInput;
    @FXML
    private TextField telInput;
    @FXML
    private DatePicker birthdayInput;
    @FXML
    private Button add;
    @FXML
    private Button cancel;

    private Patient patient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add.setOnAction(event -> handleAdd());
        cancel.setOnAction(event -> handleCancel());
    }

    private void handleAdd() {
        patient = new Patient();
        patient.setNom(nomInput.getText());
        patient.setPrenom(prenomInput.getText());
        patient.setTelephone(Integer.parseInt(telInput.getText()));
        patient.setBirthday(java.sql.Date.valueOf(birthdayInput.getValue()));
        PatientDao.addPatient(patient);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void handleCancel() {
        // Close the dialog or navigate back to the previous scene
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}