package l2dsi2.firas.miniprojetfx.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.MedicamentDAO;
import l2dsi2.firas.miniprojetfx.DAO.PatientMedicamentDAO;
import l2dsi2.firas.miniprojetfx.Model.Medicament;
import l2dsi2.firas.miniprojetfx.Model.Patient;
import l2dsi2.firas.miniprojetfx.Model.PatientMedicament;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MedicamentsController implements Initializable {
    private Patient patient;
    @FXML
    private Label patientLabel;

    @FXML
    private TableView<PatientMedicament> tableMed;

    @FXML
    private TableColumn<PatientMedicament, String> nomCol;
    @FXML
    private TableColumn<PatientMedicament, String> qteCol;
    @FXML
    private TableColumn<PatientMedicament, String> prixCol;
    @FXML
    private TableColumn<PatientMedicament, String> TypeCol;

    @FXML
    private Button back;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/Patients.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) back.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initialize(){
        ArrayList<PatientMedicament> medicaments = PatientMedicamentDAO.getPatientMedicamentByPatientId(patient.getId());
        nomCol.setCellValueFactory(cellData -> {
            Medicament medicament = MedicamentDAO.getMedicamentById(cellData.getValue().getId_medicament());
            return new SimpleStringProperty(medicament.getNom());
        });
        qteCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQte())));
        prixCol.setCellValueFactory(cellData -> {
            Medicament medicament = MedicamentDAO.getMedicamentById(cellData.getValue().getId_medicament());
            return new SimpleStringProperty(String.valueOf(medicament.getPrix()));
        });
        TypeCol.setCellValueFactory(cellData -> {
            Medicament medicament = MedicamentDAO.getMedicamentById(cellData.getValue().getId_medicament());
            return new SimpleStringProperty(medicament.getType().toString());
        });
        tableMed.getItems().setAll(medicaments);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        patientLabel.setText(patient.getNom() + " " + patient.getPrenom() + " : "+ patient.getTelephone());
        initialize();
    }
}
