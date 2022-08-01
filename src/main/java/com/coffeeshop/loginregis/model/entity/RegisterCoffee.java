package com.coffeeshop.loginregis.model.entity;



import javax.persistence.*;

@Entity
@Table(name = "tb_user")
public class RegisterCoffee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idnyaini")
    @Column(name = "id_user")
    private Integer idUser;
    private String pass;
    @Column(name = "no_telp")
    private String noTelp;
    private String alamat;
    private String nama;

    private Integer idRole;

    private String namaFile;
    private String type;

    @Lob
    private byte[] data;


    @OneToOne
    @JoinColumn(name = "idRole", insertable = false, updatable = false)
    private Roles roles;

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public RegisterCoffee() {
    }

    public RegisterCoffee(String fileName, String contentType, byte[] bytes, RegisterCoffee registerCoffee) {
    }

    public RegisterCoffee(String namaFile, String type, byte[] data) {
        this.namaFile = namaFile;
        this.type = type;
        this.data = data;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
