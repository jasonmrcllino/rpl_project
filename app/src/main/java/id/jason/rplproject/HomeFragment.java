package id.jason.rplproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import id.jason.rplproject.data.dao.AppDatabase;
import id.jason.rplproject.data.entity.StockEntity;

public class HomeFragment extends Fragment {

    private RecyclerView rvInventory;
    private InventoryAdapter adapter;
    private TextView tvTotalStok, tvTotalOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvInventory = view.findViewById(R.id.rvInventory);
        rvInventory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvInventory.setNestedScrollingEnabled(false); // Biar scroll halus
        if (rvInventory != null) {
            rvInventory.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return view;
    }

    // Method ini akan terpanggil SETIAP KALI user masuk ke tab Home
    @Override
    public void onResume() {
        super.onResume();
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {

        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<StockEntity> stockList = db.inventoryDao().getAllStocks();

        List<InventoryModel> itemList = new ArrayList<>();
        double total = db.inventoryDao().getTotalStok();
        if (stockList.isEmpty()) {
            itemList.add(new InventoryModel("Gudang Kosong", "0 Kg", "Silahkan isi stok di menu Input"));
        } else {
            for (StockEntity s : stockList) {
                // Sederhananya, 1=Tomat, 2=Cabai, dst (nanti kita buat dinamis)
                String nama = String.valueOf(s.namaKomoditas);
                if (nama==null||nama.isEmpty()){
                    nama="Produk : "+ s.namaKomoditas;
                }

                String status = (s.jumlah < 10) ? "Stok Menipis" : "Stok Aman";
                itemList.add(new InventoryModel(nama, s.jumlah + " Kg", status));
                tvTotalStok.setText(String.format("%.1f Kg", total));
            }
        }

        adapter = new InventoryAdapter(itemList);
        rvInventory.setAdapter(adapter);
    }
}