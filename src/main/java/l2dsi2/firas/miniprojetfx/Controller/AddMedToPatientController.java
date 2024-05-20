package l2dsi2.firas.miniprojetfx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.fxml.FXML;
import l2dsi2.firas.miniprojetfx.DAO.MedicamentDAO;
import l2dsi2.firas.miniprojetfx.DAO.PatientDao;
import l2dsi2.firas.miniprojetfx.DAO.PatientMedicamentDAO;
import l2dsi2.firas.miniprojetfx.Model.PatientMedicament;
import l2dsi2.firas.miniprojetfx.Model.Medicament;
import l2dsi2.firas.miniprojetfx.Model.Patient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMedToPatientController implements Initializable {


    @FXML
    private ComboBox<Patient> patient;

    @FXML
    private ComboBox<Medicament> medicament;

    @FXML
    private TextField qte;

    @FXML
    private TextField prixTotal;

    @FXML
    private Button ajouter;

    @FXML
    private Button annuler;

    private PatientMedicamentsController patientMedicamentsController;

    public void setMedicamentsController(PatientMedicamentsController patientMedicamentsController) {
        this.patientMedicamentsController = patientMedicamentsController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
// Allow only integer values for qte
        TextFormatter<Integer> qteFormatter = new TextFormatter<>(new IntegerStringConverter());
        qte.setTextFormatter(qteFormatter);

        // Allow only float values for prixTotal
        TextFormatter<Double> prixTotalFormatter = new TextFormatter<>(new DoubleStringConverter());
        prixTotal.setTextFormatter(prixTotalFormatter);

        // Make prixTotal read-only
        prixTotal.setEditable(false);
        patient.setDisable(true);
        patient.setStyle("-fx-opacity: 1; -fx-text-inner-color: black;");



        // Set the value of prixTotal when qte changes
        qte.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                int qteValue = Integer.parseInt(newValue);
                double prixTotalValue = qteValue * medicament.getValue().getPrix();
                prixTotal.setText(String.valueOf(prixTotalValue));
            } else {
                prixTotal.setText("");
            }
        });

        // Initially disable the qte TextField
        qte.setDisable(true);

        // Enable the qte TextField when a Medicament is selected
        medicament.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                qte.setDisable(false);
            } else {
                qte.setDisable(true);
            }
        });

        // Set the value of prixTotal when medicament changes
        medicament.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!qte.getText().isEmpty()) {
                int qteValue = Integer.parseInt(qte.getText());
                double prixTotalValue = qteValue * newValue.getPrix();
                prixTotal.setText(String.valueOf(prixTotalValue));
            } else {
                prixTotal.setText("");
            }
        });
        medicament.setCellFactory(new Callback<ListView<Medicament>, ListCell<Medicament>>() {
            @Override
            public ListCell<Medicament> call(ListView<Medicament> param) {
                return new ListCell<Medicament>() {
                    @Override
                    protected void updateItem(Medicament item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNom());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        medicament.setConverter(new StringConverter<Medicament>() {
            @Override
            public String toString(Medicament medicament) {
                if (medicament == null) {
                    return null;
                } else {
                    return medicament.getNom();
                }
            }

            @Override
            public Medicament fromString(String string) {
                // This is not needed for a read-only ComboBox
                return null;
            }
        });

        // Set the cell factory and string converter for the patient ComboBox
        patient.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>() {
            @Override
            public ListCell<Patient> call(ListView<Patient> param) {
                return new ListCell<Patient>() {
                    @Override
                    protected void updateItem(Patient item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getNom() + " " + item.getPrenom());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        patient.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                if (patient == null) {
                    return null;
                } else {
                    return patient.getNom() + " " + patient.getPrenom();
                }
            }

            @Override
            public Patient fromString(String string) {
                // This is not needed for a read-only ComboBox
                return null;
            }
        });
        ArrayList<Patient> patients = PatientDao.getAllPatients();
        patient.getItems().addAll(patients);
        ArrayList<Medicament> medicaments = MedicamentDAO.getAllMedicaments();
        medicament.getItems().addAll(medicaments);

        ajouter.setOnAction(this::handleAjouterButtonAction);
        annuler.setOnAction(this::handleAnnulerButtonAction);

    }

    private void handleAjouterButtonAction(ActionEvent event) {
        // Get the selected Patient and Medicament and the entered quantity
        Patient selectedPatient = patient.getValue();
        Medicament selectedMedicament = medicament.getValue();
        int enteredQuantity;
        double enteredPrixTotal;

        if (selectedPatient == null || selectedMedicament == null || qte.getText().isEmpty() || prixTotal.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return;
        }
        try {
            enteredQuantity = Integer.parseInt(qte.getText());
            if (enteredQuantity < 0) {
                // Show an alert and return
                Alert alert = new Alert(Alert.AlertType.ERROR, "Quantité must be greater than or equal to 0");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Show an alert and return
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantité must be an integer");
            alert.showAndWait();
            return;
        }

        try {
            enteredPrixTotal = Double.parseDouble(prixTotal.getText());
            if (enteredPrixTotal <= 0) {
                // Show an alert and return
                Alert alert = new Alert(Alert.AlertType.ERROR, "Prix total must be greater than 0");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Show an alert and return
            Alert alert = new Alert(Alert.AlertType.ERROR, "Prix total must be a number");
            alert.showAndWait();
            return;
        }



        // Check if the selected quantity is greater than the available quantity
        if (enteredQuantity > selectedMedicament.getQteStock()) {
            // Show an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Quantite unsiffisante");
            alert.setHeaderText(null);
            alert.setContentText("La quantite de medicament disponible est insuffisante!\nVeuillez saisir une quantite inferieure ou egale a " + selectedMedicament.getQteStock() + " .");

            alert.showAndWait();
        } else {
            // Create a new PatientMedicament object
            PatientMedicament patientMedicament = new PatientMedicament(null,selectedPatient.getId(), selectedMedicament.getId(), enteredQuantity,null);
            PatientMedicamentDAO.addPatientMedicament(patientMedicament);
            if (patientMedicamentsController != null) {
                patientMedicamentsController.refreshTableMed();
            }
            // Close the stage
            Stage stage = (Stage) ajouter.getScene().getWindow();
            stage.close();
        }
    }

    private void handleAnnulerButtonAction(ActionEvent event) {
        // Clear the selections and inputs
        patient.setValue(null);
        medicament.setValue(null);
        qte.setText("");
        prixTotal.setText("");
        Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }

    public void setSelectedPatient(Patient selectedPatient) {
        patient.getSelectionModel().select(selectedPatient);
    }
}
