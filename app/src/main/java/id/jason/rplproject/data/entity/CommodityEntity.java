package id.jason.rplproject.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "commodities")
public class CommodityEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String namaBarang;
    public String satuan; // Kg, Ton, dll
    public double stokMinimum;
}