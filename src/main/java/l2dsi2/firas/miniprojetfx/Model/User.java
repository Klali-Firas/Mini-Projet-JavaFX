package l2dsi2.firas.miniprojetfx.Model;

public class User {
    private String username;
    private String password;
    private String login;
    private Integer telephone;
    private Integer id;

    public User(Integer id,String username, String password, String login, Integer telephone) {
        this.username = username;
        this.password = password;
        this.login = login;
        this.telephone = telephone;
        this.id = id;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }
}
