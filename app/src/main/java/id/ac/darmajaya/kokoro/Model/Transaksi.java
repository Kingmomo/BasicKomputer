package id.ac.darmajaya.kokoro.Model;

public class Transaksi {
    private String barang;
    private String detail;
    private String email;
    private String keterangan;
    private String nama;
    private String no_transaksi;
    private String setuju;
    private String telpon;
    private String token;
    private String biaya;


    public Transaksi() {
    }

    public Transaksi(String barang, String detail, String email, String keterangan, String nama, String no_transaksi, String setuju, String telpon, String token, String biaya) {
        this.barang = barang;
        this.detail = detail;
        this.email = email;
        this.keterangan = keterangan;
        this.nama = nama;
        this.no_transaksi = no_transaksi;
        this.setuju = setuju;
        this.telpon = telpon;
        this.token = token;
        this.biaya = biaya;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public void setNo_transaksi(String no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    public String getSetuju() {
        return setuju;
    }

    public void setSetuju(String setuju) {
        this.setuju = setuju;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }
}
