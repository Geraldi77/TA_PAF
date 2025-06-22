
package model;


public class TipeKamar {
    private int id;
    private String namaTipe;
    private String fasilitas;
    private int harga;
    private String gambar;
    
    public TipeKamar() {
        
    }

    public TipeKamar(int id, String namaTipe, String fasilitas, int harga, String gambar) {
        this.id = id;
        this.namaTipe = namaTipe;
        this.fasilitas = fasilitas;
        this.harga = harga;
        this.gambar = gambar;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaTipe() {
        return namaTipe;
    }

    public void setNamaTipe(String namaTipe) {
        this.namaTipe = namaTipe;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
