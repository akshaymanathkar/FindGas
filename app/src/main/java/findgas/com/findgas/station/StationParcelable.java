package findgas.com.findgas.station;

import android.os.Parcelable;

import findgas.com.findgas.Parcel.AbstractGenericParcelable;
import findgas.com.findgas.Parcel.GenericParcelCreator;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class StationParcelable extends AbstractGenericParcelable<Station> implements Parcelable {

    public StationParcelable(Station object) {
        super(object);
    }

    public static Creator CREATOR = new GenericParcelCreator<>(Station.class,
            new GenericParcelCreator.InstanceGenerator<Station>() {
                @Override
                public AbstractGenericParcelable<Station> getInstance(Station object) {
                    return new StationParcelable(object);
                }
            });
}
