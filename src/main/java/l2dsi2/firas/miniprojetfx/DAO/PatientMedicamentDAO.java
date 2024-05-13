package l2dsi2.firas.miniprojetfx.DAO;

import l2dsi2.firas.miniprojetfx.Model.PatientMedicament;

import java.sql.Connection;
import java.util.ArrayList;

public class PatientMedicamentDAO {
    static Connection con = Connexion.connect();

    
    public static void addPatientMedicament(PatientMedicament patientMedicament) {
        try {

            //check if Medicament exist and qte is available
            java.sql.PreparedStatement ps = con.prepareStatement("select * from medicament where id = ?");
            ps.setInt(1, patientMedicament.getId_medicament());
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int qteStock = rs.getInt("qteStock");
                if (qteStock < patientMedicament.getQte()) {
                    throw new Exception("Qte not available");
                }
            } else {
                throw new Exception("Medicament not found");
            }
            //decrease qteStock
            ps = con.prepareStatement("update medicament set qteStock = qteStock - ? where id = ?");
            ps.setInt(1, patientMedicament.getQte());
            ps.setInt(2, patientMedicament.getId_medicament());
            ps.executeUpdate();

            //add patient medicament
             ps = con.prepareStatement("insert into patient_medicament (id_patient, id_medicament, qte) values (?, ?, ?)");
            ps.setInt(1, patientMedicament.getId_patient());
            ps.setInt(2, patientMedicament.getId_medicament());
            ps.setInt(3, patientMedicament.getQte());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void updatePatientMedicament(PatientMedicament patientMedicament) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("update patient_medicament set id_patient = ?, id_medicament = ?, qte = ? where id = ?");
            ps.setInt(1, patientMedicament.getId_patient());
            ps.setInt(2, patientMedicament.getId_medicament());
            ps.setInt(3, patientMedicament.getQte());
            ps.setInt(4, patientMedicament.getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deletePatientMedicament(Integer id) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("delete from patient_medicament where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    public static ArrayList<PatientMedicament> getAllPatientMedicaments() {
        ArrayList<PatientMedicament> patientMedicaments = new ArrayList<>();
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from patient_medicament");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                patientMedicaments.add(new PatientMedicament(rs.getInt("id"), rs.getInt("id_patient"), rs.getInt("id_medicament"), rs.getInt("qte")));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return patientMedicaments;
    }

    public static PatientMedicament getPatientMedicamentById(Integer id) {
        PatientMedicament patientMedicament = null;
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from patient_medicament where id = ?");
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                patientMedicament = new PatientMedicament(rs.getInt("id"), rs.getInt("id_patient"), rs.getInt("id_medicament"), rs.getInt("qte"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return patientMedicament;
    }


    public static ArrayList<PatientMedicament> getPatientMedicamentByPatientId(Integer id) {
        ArrayList<PatientMedicament> patientMedicaments = new ArrayList<>();
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from patient_medicament where id_patient = ?");
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                patientMedicaments.add(new PatientMedicament(rs.getInt("id"), rs.getInt("id_patient"), rs.getInt("id_medicament"), rs.getInt("qte")));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return patientMedicaments;
    }

    public static void main(String[] args) {
        PatientMedicament patientMedicament = new PatientMedicament(1, 1, 2, 3);
        PatientMedicamentDAO.addPatientMedicament(patientMedicament);
        System.out.println(PatientMedicamentDAO.getAllPatientMedicaments());
    }
}
