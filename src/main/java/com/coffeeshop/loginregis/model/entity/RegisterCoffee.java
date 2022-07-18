package com.coffeeshop.loginregis.model.entity;


import javax.persistence.*;

@Entity
@Table(name = "tb_user")
public class RegisterCoffee {
    @Id
    @Column(name = "id_user")
    private Integer idUser;
    @Column(nullable = false)
    private String pass;
    @Column(name = "no_telp", length = 13, nullable = false)
    private String noTelp;
    private String alamat;
    private String nama;

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
}
