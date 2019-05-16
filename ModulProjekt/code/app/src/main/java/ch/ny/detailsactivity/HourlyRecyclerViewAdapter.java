package ch.ny.detailsactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.ny.homeactivity.R;

public class HourlyRecyclerViewAdapter extends RecyclerView.Adapter<HourlyRecyclerViewAdapter.HourlyViewHolder> {

    private List<HourlyListViewObject> itemList;
    private Context context;

    public HourlyRecyclerViewAdapter(List<HourlyListViewObject> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the layout file
        View hourlyObjectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_details_hourly_recyclerview_layout, parent, false);
        HourlyViewHolder hvh = new HourlyViewHolder(hourlyObjectView);
        return hvh;
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
        TextView timeTextView;
        TextView temperatureTextView;
        ImageView statusImageView;

        public HourlyViewHolder(View view) {
            super(view);
            timeTextView = view.findViewById(R.id.lblTime);
            temperatureTextView = view.findViewById(R.id.lblTemp);
            statusImageView = view.findViewById(R.id.iconStatus);
        }
    }
}
