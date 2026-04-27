package id.jason.rplproject.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "suppliers")
public class SupplierEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String namaPetani;
    public String alamat;
    public String kontak;
}