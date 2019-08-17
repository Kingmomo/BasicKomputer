package id.ac.darmajaya.kokoro.Model;

public class BasicKomputer {

    private String gambar, notelp, title, alamat;


    public BasicKomputer() {
    }

    public BasicKomputer(String gambar, String notelp, String title, String alamat) {
        this.gambar = gambar;
        this.notelp = notelp;
        this.title = title;
        this.alamat = alamat;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
