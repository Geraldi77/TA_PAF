
package model;

public class Kamar {
    private int id;
    private int idTipeKamar;
    private String nomorKamar;
    private String status;
    private TipeKamar tipeKamar; 

    public Kamar() {
    }

    public Kamar(int id, int idTipeKamar, String nomorKamar, String status, TipeKamar tipeKamar) {
        this.id = id;
        this.idTipeKamar = idTipeKamar;
        this.nomorKamar = nomorKamar;
        this.status = status;
        this.tipeKamar = tipeKamar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTipeKamar() {
        return idTipeKamar;
    }

    public void setIdTipeKamar(int idTipeKamar) {
        this.idTipeKamar = idTipeKamar;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TipeKamar getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(TipeKamar tipeKamar) {
        this.tipeKamar = tipeKamar;
    }
    
}
