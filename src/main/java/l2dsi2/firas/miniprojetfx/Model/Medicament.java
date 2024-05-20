package l2dsi2.firas.miniprojetfx.Model;

public class Medicament {
    private Integer id;
    private String nom;
    private double prix;
    private int qteStock;
    private String description;
    private TypeMedicament type;

    public Medicament() {
    }

    public Medicament(Integer id, String nom, double prix, int qteStock, String description, TypeMedicament type) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.qteStock = qteStock;
        this.description = description;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    // getters and setters

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQteStock() {
        return qteStock;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeMedicament getType() {
        return type;
    }

    public void setType(TypeMedicament type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Id : " + id + " Nom : " + nom + " Prix : " + prix + " QteStock : " + qteStock + " Description : " + description + " Type : " + type;

    }

    public enum TypeMedicament {
        SPECIAL,
        NORMAL
    }
}