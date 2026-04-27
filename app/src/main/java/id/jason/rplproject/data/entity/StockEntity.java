package id.jason.rplproject.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stocks")
public class StockEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int commodityId;
    public String namaKomoditas;
    public double jumlah;
    public long lastUpdate;
}