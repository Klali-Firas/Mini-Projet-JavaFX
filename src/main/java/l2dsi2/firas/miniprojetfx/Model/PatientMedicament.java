package l2dsi2.firas.miniprojetfx.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PatientMedicament {
    private Integer id;
    private int id_patient;
    private int id_medicament;
    private int qte;
    private Timestamp date_achat;


    public Timestamp getDate_achat() {
        return date_achat;
    }

    public void setDate_achat(Timestamp date_achat) {
        this.date_achat = date_achat;
    }

    public PatientMedicament() {
    }

    public PatientMedicament(Integer id, int id_patient, int id_medicament, int qte, Timestamp date_achat) {
        this.id = id;
        this.id_patient = id_patient;
        this.id_medicament = id_medicament;
        this.qte = qte;
        this.date_achat = date_achat;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public int getId_medicament() {
        return id_medicament;
    }

    public void setId_medicament(int id_medicament) {
        this.id_medicament = id_medicament;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
