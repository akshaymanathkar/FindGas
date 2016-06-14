package findgas.com.findgas.station;

import android.os.Parcelable;

import findgas.com.findgas.Parcel.GenericParcelable;
import findgas.com.findgas.Parcel.GenericParcelCreator;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class StationParcelable extends GenericParcelable<Station> implements Parcelable {

    public StationParcelable(Station object) {
        super(object);
    }

    public static Creator CREATOR = new GenericParcelCreator<>(Station.class,
            new GenericParcelCreator.ObjectInstanceGenerator<Station>() {
                @Override
                public GenericParcelable<Station> getInstance(Station object) {
                    return new StationParcelable(object);
                }
            });
}
