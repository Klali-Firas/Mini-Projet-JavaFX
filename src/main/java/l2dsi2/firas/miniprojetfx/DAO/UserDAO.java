package l2dsi2.firas.miniprojetfx.DAO;

import l2dsi2.firas.miniprojetfx.Model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;

public class UserDAO {
    static Connection con = Connexion.connect();

    public static User login(String login, String password) {
        String query = "SELECT * FROM users WHERE login = ?";
        User user;
        try {
            java.sql.PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, login);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("login"), rs.getInt("telephone"));
                if (BCrypt.checkpw(password, user.getPassword())) {
                    return user;
                }
            }
            return null;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean register(String username, String password, String login, Integer telephone) {
        String query = "INSERT INTO users (username, password, login, telephone) VALUES (?, ?, ?, ?)";
        try {
            java.sql.PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, login);
            ps.setInt(4, telephone);
            ps.executeUpdate();
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(User user) {
        String query = "UPDATE users SET username = ?, password = ?, login = ?, telephone = ? WHERE id = ?";
        try {
            java.sql.PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            ps.setString(3, user.getLogin());
            ps.setInt(4, user.getTelephone());
            ps.setInt(5, user.getId());
            ps.executeUpdate();
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(Integer id) {
        String query = "DELETE FROM users WHERE id = ?";
        try {
            java.sql.PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
