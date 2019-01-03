package site.devsim.enjoyseoul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import site.devsim.enjoyseoul.Activity.DetailEventActivity;
import site.devsim.enjoyseoul.DB.POJO.EventItem;
import site.devsim.enjoyseoul.R;
import site.devsim.enjoyseoul.Util.ColorGradientUtil;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewHolder> {
    Context context;
    ArrayList<EventItem> items;

    public ListViewAdapter(Context context, ArrayList<EventItem> items){
        this.context = context;
        this.items = items;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        ListViewHolder vh = new ListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final EventItem item = items.get(position);
        Glide
                .with(context)
                .load(item.getMainImg())
                .into(holder.imgItem);
        holder.alertGenre.setText(item.getGenre());
        ColorGradientUtil.applyGenreColorGradient(context,item.getGenre(),holder.alertGenre);
        //fee
        String isFree = item.getIsFree();
        if(isFree.equals("0")) {//유료
            holder.alertFee.setText("유료");
        }else if(isFree.equals("1")){//무료
            holder.alertFee.setText("무료");
        }
        ColorGradientUtil.applyFeeColorGradient(context,item.getIsFree(),holder.alertFee);

        holder.txtTitle.setText(item.getTitle());
        holder.txtPeriod.setText(item.getStartDate() + "~" + item.getEndDate());
        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailEventActivity.class);
                i.putExtra("event", item);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
