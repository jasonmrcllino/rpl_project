package id.jason.rplproject;

public class HistoryModel {
    private String judul, keterangan, detail;

    public HistoryModel(String judul, String keterangan, String detail) {
        this.judul = judul;
        this.keterangan = keterangan;
        this.detail = detail;
    }

    public String getJudul() { return judul; }
    public String getKeterangan() { return keterangan; }
    public String getDetail() { return detail; }
}