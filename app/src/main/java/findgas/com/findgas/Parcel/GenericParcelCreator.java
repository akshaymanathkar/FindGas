package findgas.com.findgas.Parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class GenericParcelCreator<T> implements Parcelable.Creator {
    private Class<T> responseClass;
    private ObjectInstanceGenerator<T> objectInstanceGenerator;


    public GenericParcelCreator(Class<T> responseClass, ObjectInstanceGenerator<T> objectInstanceGenerator) {
        this.responseClass = responseClass;
        this.objectInstanceGenerator = objectInstanceGenerator;
    }

    public GenericParcelable<T> createFromParcel(Parcel in) {
        Gson gson = new GsonBuilder()
                .create();
        String jsonString = in.readString();
        T object = gson.fromJson(jsonString, responseClass);
        return objectInstanceGenerator.getInstance(object);
    }

    public GenericParcelable<T>[] newArray(int size) {
        return (GenericParcelable<T>[]) new ArrayList<>(size).toArray();
    }

    public interface ObjectInstanceGenerator<T> {
        GenericParcelable<T> getInstance(T object);
    }
}

