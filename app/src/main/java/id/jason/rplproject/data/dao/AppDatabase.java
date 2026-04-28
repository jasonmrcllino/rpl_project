package id.jason.rplproject.data.dao;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import id.jason.rplproject.data.entity.CommodityEntity;
import id.jason.rplproject.data.entity.StockEntity;
import id.jason.rplproject.data.entity.SupplierEntity;
import id.jason.rplproject.data.entity.TransactionEntity;

@Database(entities = {CommodityEntity.class, SupplierEntity.class, TransactionEntity.class, StockEntity.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract InventoryDao inventoryDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "scm_tani_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}