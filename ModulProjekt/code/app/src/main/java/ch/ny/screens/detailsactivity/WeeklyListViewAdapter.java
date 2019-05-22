package ch.ny.screens.detailsactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.ny.screens.homeactivity.R;

class WeeklyListViewAdapter extends ArrayAdapter<WeeklyListViewObject> {

    private final LayoutInflater mLayoutInflater;

    /**
     * Konstruktor. Initialisiert gewisse Member Variablen.
     *
     * @param context Context der Applikation.
     * @param items Item in der Liste
     * @since 1.0
     */
    public WeeklyListViewAdapter(Context context, List<WeeklyListViewObject> items) {
        super(context, R.layout.activity_details_weekly_listview_layout);
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
        WeeklyListViewAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_details_weekly_listview_layout, null);

            viewHolder = new WeeklyListViewAdapter.ViewHolder();
            viewHolder.weekDayTextView = convertView.findViewById(R.id.lblWeekDay);
            viewHolder.minTempTextView = convertView.findViewById(R.id.lblMinTemp);
            viewHolder.maxTempTextView = convertView.findViewById(R.id.lblMaxTemp);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (WeeklyListViewAdapter.ViewHolder) convertView.getTag();
        }

        String weekDay = getItem(position).getWeekday();
        viewHolder.weekDayTextView.setText(weekDay);
        String minTemp = getItem(position).getMinTemp();
        viewHolder.minTempTextView.setText(minTemp + "°C");
        String maxTemp = getItem(position).getMaxTemp();
        viewHolder.maxTempTextView.setText(maxTemp + "°C");

        return convertView;
    }

    static class ViewHolder {
        TextView weekDayTextView;
        TextView minTempTextView;
        TextView maxTempTextView;
    }
}
