package findgas.com.findgas.station;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import findgas.com.findgas.R;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class StationAdapter extends ArrayAdapter<Station> {
    private boolean isSortByDistance;

    public StationAdapter(Context context, List<Station> objects, boolean isSortByDistance) {
        super(context, 0, objects);
        this.isSortByDistance = isSortByDistance;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Station station = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.station_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.priceOrDistance = (TextView) convertView.findViewById(R.id.price_or_distance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(station.getAddress());
        String priceOrDist = String.valueOf(station.getRegPrice());
        if(isSortByDistance){
            priceOrDist = station.getDistance();
        }
        viewHolder.priceOrDistance.setText(priceOrDist);

        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView priceOrDistance;
    }

}
