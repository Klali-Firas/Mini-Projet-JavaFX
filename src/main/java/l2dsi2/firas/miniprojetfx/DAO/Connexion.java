package l2dsi2.firas.miniprojetfx.DAO;

import java.sql.Connection;
import java.sql.SQLException;

public class Connexion {
    private static Connection con;
    private static String user = "postgres";
    private static String password = "aze.1234";

    public static Connection getCon() {
        return con;
    }

    public static Connection connect() {
        if (con == null) {
            try {

                Class.forName("org.postgresql.Driver");
                con = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/Pharmacie", user, password);
            } catch (ClassNotFoundException e) {
                System.out.println("Erreur de chargement du driver");
            } catch (SQLException e) {
                System.out.println("Erreur de connexion à la base de données");
            }
        }
        return con;
    }


    public static void main(String[] args) {
        Connection con = Connexion.connect();
        if (con != null) {
            System.out.println("Connexion établie");
        } else {
            System.out.println("Erreur");
        }
    }
}