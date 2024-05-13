package l2dsi2.firas.miniprojetfx.DAO;

import l2dsi2.firas.miniprojetfx.Model.Medicament;

import java.sql.Connection;
import java.util.ArrayList;

public class MedicamentDAO {
    static Connection con = Connexion.connect();

    public static void addMedicament(Medicament medicament) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("insert into medicament (nom, prix, qteStock, description, type) values (?, ?, ?, ?, ?)");
            ps.setString(1, medicament.getNom());
            ps.setDouble(2, medicament.getPrix());
            ps.setInt(3, medicament.getQteStock());
            ps.setString(4, medicament.getDescription());
            ps.setString(5, medicament.getType().toString());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMedicament(Medicament medicament) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("update medicament set nom = ?, prix = ?, qteStock = ?, description = ?, type = ? where id = ?");
            ps.setString(1, medicament.getNom());
            ps.setDouble(2, medicament.getPrix());
            ps.setInt(3, medicament.getQteStock());
            ps.setString(4, medicament.getDescription());
            ps.setString(5, medicament.getType().toString());
            ps.setInt(6, medicament.getId());
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMedicament(Integer id) {
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("delete from medicament where id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Medicament> getAllMedicaments() {
        ArrayList<Medicament> medicaments = new ArrayList<>();
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from medicament");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                medicaments.add(new Medicament(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("qteStock"), rs.getString("description"), Medicament.TypeMedicament.valueOf(rs.getString("type"))));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }

    public static Medicament getMedicamentById(Integer id) {
        Medicament medicament = null;
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from medicament where id = ?");
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                medicament = new Medicament(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("qteStock"), rs.getString("description"), Medicament.TypeMedicament.valueOf(rs.getString("type")));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return medicament;
    }

    public static ArrayList<Medicament> getMedicamentsByPatientId(Integer id) {
        ArrayList<Medicament> medicaments = new ArrayList<>();
        try {
            java.sql.PreparedStatement ps = con.prepareStatement("select * from medicament where id in (select id_medicament from patient_medicament where id_patient = ?)");
            ps.setInt(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                medicaments.add(new Medicament(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"), rs.getInt("qteStock"), rs.getString("description"), Medicament.TypeMedicament.valueOf(rs.getString("type"))));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }


    public static void main(String[] args) {
//        Medicament medicament = new Medicament(2, "Doliprane", 2.5, 250, "Paracetamol", Medicament.TypeMedicament.NORMAL);
//        MedicamentDAO.addMedicament(medicament);
//        MedicamentDAO.updateMedicament(medicament);
//        MedicamentDAO.deleteMedicament(1);
        System.out.println(MedicamentDAO.getAllMedicaments());
//        System.out.println(MedicamentDAO.getMedicamentById(2));
    }
}
