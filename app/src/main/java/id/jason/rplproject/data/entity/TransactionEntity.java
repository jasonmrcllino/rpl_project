package id.jason.rplproject.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class TransactionEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int commodityId; // Relasi ke ID Barang
    public Integer supplierId; // Relasi ke ID Petani (bisa null kalau barang keluar)
    public String jenis; // "MASUK" atau "KELUAR"
    public double jumlah;
    public long tanggal; // Simpan waktu dalam miliseconds
    public String keterangan; // Opsi 1: "Kirim ke Pasar", "Panen Lahan A", dll
}