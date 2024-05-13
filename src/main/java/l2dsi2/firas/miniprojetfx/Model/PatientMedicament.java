package l2dsi2.firas.miniprojetfx.Model;

public class PatientMedicament {
    private Integer id;
    private int id_patient;
    private int id_medicament;
    private int qte;


    public PatientMedicament() {
    }

    public PatientMedicament(Integer id, int id_patient, int id_medicament, int qte) {
        this.id = id;
        this.id_patient = id_patient;
        this.id_medicament = id_medicament;
        this.qte = qte;
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
