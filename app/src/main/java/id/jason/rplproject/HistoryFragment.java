package id.jason.rplproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import id.jason.rplproject.data.dao.AppDatabase;
import id.jason.rplproject.data.entity.TransactionEntity;

public class HistoryFragment extends Fragment {

    private RecyclerView rvHistory;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rvHistory = view.findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    // PENTING: Gunakan onResume agar data refresh otomatis saat pindah tab
    @Override
    public void onResume() {
        super.onResume();
        loadHistoryData();
    }

    private void loadHistoryData() {
        // 1. Panggil Database
        AppDatabase db = AppDatabase.getInstance(requireContext());
        List<TransactionEntity> trxList = db.inventoryDao().getAllTransactions();

        List<HistoryModel> historyList = new ArrayList<>();

        // Formatter tanggal agar enak dibaca manusia
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        if (trxList != null && !trxList.isEmpty()) {
            for (TransactionEntity t : trxList) {
                String tglFormatted = sdf.format(new Date(t.tanggal));
                String qtyText = "+" + t.jumlah + " Kg";

                // Masukkan ke HistoryModel(Title, Desc, Detail)
                // Mengikuti ID XML baru: tvHistoryTitle (Keterangan), tvHistoryDesc (Qty), tvHistoryDetail (Tanggal)
                historyList.add(new HistoryModel(t.keterangan, qtyText, tglFormatted));
            }
        } else {
            // Jika kosong tampilkan satu item info
            historyList.add(new HistoryModel("Belum ada transaksi", "0 Kg", "-"));
        }

        // 2. Pasang ke Adapter
        adapter = new HistoryAdapter(historyList);
        rvHistory.setAdapter(adapter);
    }
}