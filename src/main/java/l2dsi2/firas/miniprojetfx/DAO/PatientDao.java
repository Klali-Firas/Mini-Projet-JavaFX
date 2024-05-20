package l2dsi2.firas.miniprojetfx.DAO;

import l2dsi2.firas.miniprojetfx.Model.Patient;

import java.sql.Connection;
import java.util.ArrayList;

public class PatientDao {

    static Connection con = Connexion.connect();

    public static void addPatient(Patient patient) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("insert into patient (nom, prenom, telephone, birthday) values (?, ?, ?, ?)");
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setInt(3, patient.getTelephone());
            ps.setDate(4, new java.sql.Date(patient.getBirthday().getTime()));
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePatient(Patient patient) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("update patient set nom = ?, prenom = ?, telephone = ?, birthday = ? where id = ?");
            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setInt(3, patient.getTelephone());
            ps.setDate(4, new java.sql.Date(patient.getBirthday().getTime()));
            ps.setInt(5, patient.getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePatient(Integer id) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("delete from patient where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from patient");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                patients.add(new Patient(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("telephone"), rs.getDate("birthday")));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public static Patient getPatientById(Integer id) {
        Patient patient = null;
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from patient where id = ?");
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                patient = new Patient(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("telephone"), rs.getDate("birthday"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

}
