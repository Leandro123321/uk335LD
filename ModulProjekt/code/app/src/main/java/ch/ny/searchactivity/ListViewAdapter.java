package ch.ny.searchactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.ny.homeactivity.R;

public class ListViewAdapter extends ArrayAdapter<ListViewObject> {

    private LayoutInflater mLayoutInflater;

    /**
     * Konstruktor. Initialisiert gewisse Member Variablen.
     *
     * @param context Context der Applikation.
     * @param items Item in der Liste
     * @since 1.0
     */
    public ListViewAdapter(Context context, List<ListViewObject> items) {
        super(context, R.layout.activity_search_listview_layout);
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_search_listview_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.itemNameTextView = convertView.findViewById(R.id.textview_itemname_listview);
            viewHolder.itemNumberTextView = convertView.findViewById(R.id.textview_itemnumber_listview);
            viewHolder.itemButtonTextView = convertView.findViewById(R.id.showdetailsbutton);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name = getItem(position).getCityName();
        viewHolder.itemNameTextView.setText(name);
        String number = String.valueOf(getItem(position).getCountry());
        viewHolder.itemNumberTextView.setText(number);

        return convertView;
    }

    public static class ViewHolder {
        TextView itemNameTextView;
        TextView itemNumberTextView;
        TextView itemButtonTextView;
    }
}

