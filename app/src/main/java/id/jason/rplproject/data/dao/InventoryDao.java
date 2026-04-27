package id.jason.rplproject.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy; // Tambahkan ini
import androidx.room.Query;
import id.jason.rplproject.data.entity.CommodityEntity;
import id.jason.rplproject.data.entity.SupplierEntity;
import id.jason.rplproject.data.entity.TransactionEntity;
import id.jason.rplproject.data.entity.StockEntity; // Import StockEntity
import java.util.List;

@Dao
public interface InventoryDao {

    @Insert
    void insertCommodity(CommodityEntity commodity);

    @Query("SELECT * FROM commodities")
    List<CommodityEntity> getAllCommodities();

    @Insert
    void insertSupplier(SupplierEntity supplier);

    @Query("SELECT * FROM suppliers")
    List<SupplierEntity> getAllSuppliers();

    @Insert
    void insertTransaction(TransactionEntity transaction);

    @Query("SELECT * FROM transactions ORDER BY tanggal DESC")
    List<TransactionEntity> getAllTransactions();

    // --- TAMBAHAN UNTUK STOK ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateStock(StockEntity stock);

    @Query("SELECT * FROM stocks WHERE commodityId = :commId LIMIT 1")
    StockEntity getStockByCommodity(int commId);

    @Query("SELECT * FROM stocks")
    List<StockEntity> getAllStocks();
}