package l2dsi2.firas.miniprojetfx.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import l2dsi2.firas.miniprojetfx.DAO.MedicamentDAO;
import l2dsi2.firas.miniprojetfx.Model.Medicament;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MedicamentsContoller implements Initializable {
    ArrayList<Medicament> medicaments;

    @FXML
    private Button ajouterMedicament;

    @FXML
    private Button retour;

    @FXML
    private TableView<Medicament> tabMeds;

    @FXML
    private TableColumn<Medicament, String> nomCol;
    @FXML
    private TableColumn<Medicament, String> qteCol;
    @FXML
    private TableColumn<Medicament, String> prixCol;
    @FXML
    private TableColumn<Medicament, String> typeCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        retour.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/Patients.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage) retour.getScene().getWindow();
                stage.setTitle("Phamacie App");
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        medicaments = MedicamentDAO.getAllMedicaments();
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        qteCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf( cellData.getValue().getQteStock())));
        prixCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf( cellData.getValue().getPrix())));
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType().toString()));
        tabMeds.getItems().setAll(medicaments);

        tabMeds.setRowFactory(tv -> {

            TableRow<Medicament> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem updateItem = new MenuItem("Update");
            updateItem.setOnAction(event -> {
                openUpdateDialog(row.getItem());
                tabMeds.refresh();
            });
            contextMenu.getItems().add(updateItem);

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this medicament?");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    deleteMedicament(row.getItem());
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
        tabMeds.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tabMeds.getSelectionModel().getSelectedItem() != null) { // Checking double click
                openUpdateDialog(tabMeds.getSelectionModel().getSelectedItem());
                tabMeds.refresh();
            }

        });
        ajouterMedicament.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/AddMedicament.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Add Medicament");
                stage.setScene(scene);
                stage.showAndWait();
                medicaments = MedicamentDAO.getAllMedicaments(); // Fetch the updated list of medicaments
                tabMeds.getItems().setAll(medicaments); // Update the table items
                tabMeds.refresh(); // Refresh the table
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void openUpdateDialog(Medicament medicament) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/l2dsi2/firas/miniprojetfx/UpdateMedicament.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Medicament");
            stage.setScene(scene);
            UpdateMedicamentController controller = fxmlLoader.getController();
            controller.setMedicament(medicament);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteMedicament(Medicament medicament) {
        MedicamentDAO.deleteMedicament(medicament.getId());
        tabMeds.getItems().remove(medicament);
    }
}