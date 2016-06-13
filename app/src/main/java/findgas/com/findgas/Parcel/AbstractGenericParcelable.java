package findgas.com.findgas.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public abstract class AbstractGenericParcelable<T> implements Parcelable {
    private T object;

    public AbstractGenericParcelable(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Gson gson = new GsonBuilder()
                .create();
        String jsonString = gson.toJson(object);
        parcel.writeString(jsonString);
    }
}
