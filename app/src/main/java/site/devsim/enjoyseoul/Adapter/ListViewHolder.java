package site.devsim.enjoyseoul.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import site.devsim.enjoyseoul.R;

public class ListViewHolder extends RecyclerView.ViewHolder {
    View box;
    ImageView imgItem;
    TextView alertGenre;
    TextView alertFee;
    TextView txtTitle;
    TextView txtPeriod;

    public ListViewHolder(View itemView){
        super(itemView);
        box = itemView;
        imgItem = itemView.findViewById(R.id.img_item);
        alertGenre = itemView.findViewById(R.id.alert_genre);
        alertFee= itemView.findViewById(R.id.alert_fee);
        txtTitle = itemView.findViewById(R.id.txt_title);
        txtPeriod = itemView.findViewById(R.id.txt_period);
    }




}
