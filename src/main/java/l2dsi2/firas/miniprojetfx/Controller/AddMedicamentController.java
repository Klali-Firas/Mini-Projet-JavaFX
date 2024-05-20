package l2dsi2.firas.miniprojetfx.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.MedicamentDAO;
import l2dsi2.firas.miniprojetfx.Model.Medicament;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMedicamentController implements Initializable {
    @FXML
    private TextField nomInput;
    @FXML
    private TextField qteInput;
    @FXML
    private TextField prixInput;
    @FXML
    private ComboBox<Medicament.TypeMedicament> typeInput;
    @FXML
    private Button add;
    @FXML
    private Button cancel;

    private Medicament medicament;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeInput.setItems(FXCollections.observableArrayList(Medicament.TypeMedicament.values()));
        add.setOnAction(event -> handleAdd());
        cancel.setOnAction(event -> handleCancel());
    }

    private void handleAdd() {
        String nom = nomInput.getText();
        String qteText = qteInput.getText();
        String prixText = prixInput.getText();
        Medicament.TypeMedicament type = typeInput.getValue();

        // Check if any field is empty
        if (nom.isEmpty() || qteText.isEmpty() || prixText.isEmpty() || type == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return;
        }


        // Validate qte
        int qte;
        try {
            qte = Integer.parseInt(qteText);
            if (qte < 0) {
                // Show an alert and return
                Alert alert = new Alert(AlertType.ERROR, "Quantité must be greater than or equal to 0");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Show an alert and return
            Alert alert = new Alert(AlertType.ERROR, "Quantité must be an integer");
            alert.showAndWait();
            return;
        }

        // Validate prix
        double prix;
        try {
            prix = Double.parseDouble(prixText);
            if (prix <= 0) {
                // Show an alert and return
                Alert alert = new Alert(AlertType.ERROR, "Prix must be greater than 0");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Show an alert and return
            Alert alert = new Alert(AlertType.ERROR, "Prix must be a number");
            alert.showAndWait();
            return;
        }

        medicament = new Medicament();
        medicament.setNom(nomInput.getText());
        medicament.setQteStock(qte);
        medicament.setPrix(prix);
        medicament.setType(typeInput.getValue());
        MedicamentDAO.addMedicament(medicament);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void handleCancel() {
        // Close the dialog or navigate back to the previous scene
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}