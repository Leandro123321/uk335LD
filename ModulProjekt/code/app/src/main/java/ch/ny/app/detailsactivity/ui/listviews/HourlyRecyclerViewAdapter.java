package ch.ny.ui.detailsactivity.listviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import ch.ny.ui.detailsactivity.DetailsActivity;
import ch.ny.ui.homeactivity.R;

public class HourlyRecyclerViewAdapter extends RecyclerView.Adapter<HourlyRecyclerViewAdapter.HourlyViewHolder> {

    private final List<HourlyListViewObject> itemList;

    public HourlyRecyclerViewAdapter(List<HourlyListViewObject> itemList, Context context) {
        this.itemList = itemList;
        Context context1 = context;
    }

    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the layout file
        View hourlyObjectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_details_hourly_recyclerview_layout, parent, false);
        return new HourlyViewHolder(hourlyObjectView);
    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, final int position) {
        // Set properties for holder
        holder.timeTextView.setText(itemList.get(position).getTime() + "h");
        holder.temperatureTextView.setText(itemList.get(position).getTemp() + "°C");
        holder.statusImageView.setImageResource(DetailsActivity.getIcon(itemList.get(position).getStatus()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class HourlyViewHolder extends RecyclerView.ViewHolder{
        final TextView timeTextView;
        final TextView temperatureTextView;
        final ImageView statusImageView;

        HourlyViewHolder(View view) {
            super(view);
            timeTextView = view.findViewById(R.id.lblTime);
            temperatureTextView = view.findViewById(R.id.lblTemp);
            statusImageView = view.findViewById(R.id.iconStatus);
        }
    }
}
