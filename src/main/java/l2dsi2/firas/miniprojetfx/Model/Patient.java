package l2dsi2.firas.miniprojetfx.Model;


import java.util.Date;

public class Patient {
    private String nom;
    private String prenom;
    private Integer telephone;
    private Date birthday;
    private Integer id;

    public Patient() {
    }

    public Patient(Integer id, String nom, String prenom, Integer telephone, Date birthday) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.birthday = birthday;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
