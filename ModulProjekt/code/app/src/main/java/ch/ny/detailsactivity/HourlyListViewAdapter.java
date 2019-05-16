package ch.ny.detailsactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.ny.homeactivity.R;

public class HourlyListViewAdapter extends ArrayAdapter<HourlyListViewObject> {

    private LayoutInflater mLayoutInflater;

    /**
     * Konstruktor. Initialisiert gewisse Member Variablen.
     *
     * @param context Context der Applikation.
     * @param items Item in der Liste
     * @since 1.0
     */
    public HourlyListViewAdapter(Context context, List<HourlyListViewObject> items) {
        super(context, R.layout.activity_details_hourly_listview_layout);
        addAll(items);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gibt die View eines einzelnen Eintrags in der Liste zurück.
     *
     * @param position Position des anzuzeigenden Eintrags.
     * @param convertView Das alte View, welches wiederbenutzt werden soll. Null wenn kein altes
     * View vorhanden ist.
     * @param parent Das Eltern-View.
     * @return View eines spezifischen ListView-Eintrags
     * @since 1.0
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        HourlyListViewAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_details_hourly_listview_layout, null);

            viewHolder = new HourlyListViewAdapter.ViewHolder();
            viewHolder.statusImageView = convertView.findViewById(R.id.iconStatus);
            viewHolder.temperatureTextView = convertView.findViewById(R.id.lblTemp);
            viewHolder.timeTextView = convertView.findViewById(R.id.lblTime);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (HourlyListViewAdapter.ViewHolder) convertView.getTag();
        }

        String time = getItem(position).getTime();
        viewHolder.timeTextView.setText(time);
        String temperature = getItem(position).getTemp();
        viewHolder.temperatureTextView.setText(temperature);

        return convertView;
    }

    public static class ViewHolder {
        TextView timeTextView;
        TextView temperatureTextView;
        ImageView statusImageView;
    }
}
