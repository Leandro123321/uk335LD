package ch.ny.ui.searchactivity.listviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.ny.ui.homeactivity.R;

public class ListViewAdapter extends ArrayAdapter<ListViewObject> {

    private final LayoutInflater mLayoutInflater;

    public ListViewAdapter(Context context, List<ListViewObject> items) {
        super(context, R.layout.activity_search_listview_layout);
        addAll(items);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_search_listview_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.itemNameTextView = convertView.findViewById(R.id.textview_itemname_listview);
            viewHolder.itemNumberTextView = convertView.findViewById(R.id.textview_itemnumber_listview);
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

    static class ViewHolder {
        TextView itemNameTextView;
        TextView itemNumberTextView;
    }
}

