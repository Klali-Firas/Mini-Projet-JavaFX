package l2dsi2.firas.miniprojetfx.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.PatientDao;
import l2dsi2.firas.miniprojetfx.Model.Patient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
    ArrayList<Patient> patients;
    @FXML
    private TableView<Patient> patientsTable;
    @FXML
    private TableColumn<Patient, String> nomCol;
    @FXML
    private TableColumn<Patient, String> prenomCol;
    @FXML
    private TableColumn<Patient, String> telCol;
    @FXML
    private TableColumn<Patient, String> birthdayCol;

    @FXML
    private Button addPatient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        patients = PatientDao.getAllPatients();
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        telCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone().toString()));
        birthdayCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthday().toString()));
        patientsTable.getItems().setAll(patients);

        patientsTable.setRowFactory(tv -> {
            TableRow<Patient> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            final MenuItem medicamentsItem = getMedicamentsItem(row);
            contextMenu.getItems().add(medicamentsItem);

            MenuItem updateItem = new MenuItem("Update");
            updateItem.setOnAction(event -> {
                openUpdateDialog(row.getItem());
                patientsTable.refresh();
            });
            contextMenu.getItems().add(updateItem);

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this patient?");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    deletePatient(row.getItem());
                }
            });
            contextMenu.getItems().add(deleteItem);

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });
        patientsTable.setOnMouseClicked(event -> {
    if (event.getClickCount() == 2 && patientsTable.getSelectionModel().getSelectedItem() != null) { // Checking double click
        openUpdateDialog(patientsTable.getSelectionModel().getSelectedItem());
        patientsTable.refresh();
    }

});
        addPatient.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/AddPatient.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                patients = PatientDao.getAllPatients(); // Fetch the updated list of patients
                patientsTable.getItems().setAll(patients); // Update the table items
                patientsTable.refresh(); // Refresh the table
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private MenuItem getMedicamentsItem(TableRow<Patient> row) {
        MenuItem medicamentsItem = new MenuItem("Medicaments");
        medicamentsItem.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/Medicaments.fxml"));
                Scene scene = new Scene(fxmlLoader.load()); // Load the scene first
                MedicamentsController medicamentsController = fxmlLoader.getController(); // Then get the controller
                medicamentsController.setPatient(row.getItem()); // set the patient
                Stage stage = (Stage) patientsTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return medicamentsItem;
    }

    private void openUpdateDialog(Patient patient) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/UpdatePatient.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            UpdatePatientController controller = fxmlLoader.getController();
            controller.setPatient(patient);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletePatient(Patient patient) {
        PatientDao.deletePatient(patient.getId());
        patientsTable.getItems().remove(patient);
    }
}