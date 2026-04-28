package id.jason.rplproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import id.jason.rplproject.data.dao.AppDatabase;
import id.jason.rplproject.data.entity.StockEntity;
import id.jason.rplproject.data.entity.TransactionEntity;

public class InputFragment extends Fragment {

    private AutoCompleteTextView dropdownCommodity;
    private TextInputEditText etQuantity, etHarvestDate;
    private MaterialButton btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        dropdownCommodity = view.findViewById(R.id.dropdownCommodity);
        etQuantity = view.findViewById(R.id.etQuantity);
        etHarvestDate = view.findViewById(R.id.etHarvestDate);
        btnSave = view.findViewById(R.id.btnSave);

        String[] items = {"Tomat", "Cabai Merah", "Bawang Merah", "Kentang"};
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(requireContext(), R.layout.list_item_dropdown, items);
        dropdownCommodity.setAdapter(adapterItems);

        etHarvestDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveData());

        return view;
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    etHarvestDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveData() {
        String jumlahStr = etQuantity.getText().toString();
        String tanggalStr = etHarvestDate.getText().toString();
        String komoditas = dropdownCommodity.getText().toString();

        if (jumlahStr.isEmpty() || tanggalStr.isEmpty() || komoditas.isEmpty()) {
            Toast.makeText(requireContext(), "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double jumlah = Double.parseDouble(jumlahStr);
            AppDatabase db = AppDatabase.getInstance(requireContext());

            // 1. Simpan Log Transaksi
            TransactionEntity trx = new TransactionEntity();
            // Gunakan hashCode dari nama biar ID-nya beda-beda tiap barang
            trx.commodityId = komoditas.hashCode();
            trx.jenis = "MASUK";
            trx.jumlah = jumlah;
            trx.tanggal = System.currentTimeMillis();
            trx.keterangan = "Panen " + komoditas + " tgl: " + tanggalStr;
            db.inventoryDao().insertTransaction(trx);

            // 2. Update Saldo Stok (LOGIKA DINAMIS)
            // Kita cari stok berdasarkan NAMA yang dipilih di dropdown
            StockEntity saldo = db.inventoryDao().getStockByItemName(komoditas);

            if (saldo == null) {
                // Jika komoditas ini BELUM ADA di database, buat baris baru
                saldo = new StockEntity();
                saldo.commodityId = komoditas.hashCode();
                saldo.namaKomoditas = komoditas;
                saldo.jumlah = jumlah;
            } else {
                // Jika SUDAH ADA, tinggal tambahkan jumlahnya
                saldo.jumlah += jumlah;
            }

            saldo.lastUpdate = System.currentTimeMillis();
            db.inventoryDao().insertOrUpdateStock(saldo);

            Toast.makeText(requireContext(), "Berhasil simpan " + komoditas + " ke Gudang!", Toast.LENGTH_SHORT).show();

            // Clear input
            etQuantity.setText("");
            etHarvestDate.setText("");
            dropdownCommodity.setText("", false);

        } catch (Exception e) {
            Toast.makeText(requireContext(), "Gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}