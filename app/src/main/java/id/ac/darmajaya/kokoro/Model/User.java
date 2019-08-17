package id.ac.darmajaya.kokoro.Model;

public class User {
    public String email;
    public String password;
    public String nama;
    public String alamat;
    public String notelp;
    public String notoken;

    public User() {

    }

    public User(String email, String password, String nama, String alamat, String notelp, String notoken) {
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.alamat = alamat;
        this.notelp = notelp;
        this.notoken = notoken;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getNotoken() {
        return notoken;
    }

    public void setNotoken(String notoken) {
        this.notoken = notoken;
    }
}
