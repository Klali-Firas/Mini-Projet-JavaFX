package l2dsi2.firas.miniprojetfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.PatientDao;
import l2dsi2.firas.miniprojetfx.Model.Patient;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdatePatientController implements Initializable {
    @FXML
    private TextField nomInput;
    @FXML
    private TextField prenomInput;
    @FXML
    private TextField telInput;
    @FXML
    private DatePicker birthdayInput;
    @FXML
    private Button update;
    @FXML
    private Button cancel;

    private Patient patient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update.setOnAction(event -> handleUpdate());
        cancel.setOnAction(event -> handleCancel());
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        nomInput.setText(patient.getNom());
        prenomInput.setText(patient.getPrenom());
        telInput.setText(patient.getTelephone().toString());
        birthdayInput.setValue(LocalDate.parse(patient.getBirthday().toString()));
    }

    private void handleUpdate() {
        String nom = nomInput.getText();
        String prenom = prenomInput.getText();
        String tel = telInput.getText();
        LocalDate birthday = birthdayInput.getValue();

        // Check if any field is empty
        if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty() || birthday == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return;
        }

        patient.setNom(nom);
        patient.setPrenom(prenom);
        patient.setTelephone(Integer.parseInt(tel));
        patient.setBirthday(java.sql.Date.valueOf(birthday));
        PatientDao.updatePatient(patient);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void handleCancel() {
        // Close the dialog or navigate back to the previous scene
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}