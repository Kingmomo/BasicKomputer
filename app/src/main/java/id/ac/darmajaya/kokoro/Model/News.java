package id.ac.darmajaya.kokoro.Model;

import android.content.Intent;

public class News {
    public Long iddata;
    public String foto;
    public String judul;
    public String deskripsi;

    public News() {

    }

    public News(Long iddata, String foto, String judul, String deskripsi) {
        this.iddata = iddata;
        this.foto = foto;
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    public Long getiddata() {
        return iddata;
    }

    public void setiddata(Long iddata) {
        this.iddata = iddata;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


}
