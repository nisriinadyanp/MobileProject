package com.example.projectmejatim;

public class Lukisan {
    private String id;
    private String judul;
    private String pelukis;
    private String url_gambar;
    private String deskripsi;

    public Lukisan(){
    }
    public Lukisan (String id, String judul, String pelukis, String deskripsi) {
        this.id = id;
        this.judul = judul;
        this.pelukis = pelukis;
        this.url_gambar = url_gambar;
        this.deskripsi = deskripsi;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPelukis(String pelukis) {
        this.pelukis = pelukis;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setUrl_gambar(String url_gambar) {
        this.url_gambar = url_gambar;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getPelukis() {
        return pelukis;
    }

    public String getUrl_gambar() {
        return url_gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
