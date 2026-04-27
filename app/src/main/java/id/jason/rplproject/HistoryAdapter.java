package id.jason.rplproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryModel> itemList;

    public HistoryAdapter(List<HistoryModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // PERHATIKAN: Ganti R.layout.item_history sesuai nama XML kamu
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel item = itemList.get(position);
        holder.tvJudul.setText(item.getJudul());
        holder.tvKeterangan.setText(item.getKeterangan());
        holder.tvDetail.setText(item.getDetail());
    }

    @Override
    public int getItemCount() { return itemList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvKeterangan, tvDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Sesuaikan ID dengan yang ada di item_history.xml kamu
            tvJudul = itemView.findViewById(R.id.tvHistoryTitle);
            tvKeterangan = itemView.findViewById(R.id.tvHistoryDesc);
            tvDetail = itemView.findViewById(R.id.tvHistoryDetail);
        }
    }
}