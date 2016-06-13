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
    private InstanceGenerator<T> instanceGenerator;


    public GenericParcelCreator(Class<T> responseClass, InstanceGenerator<T> instanceGenerator) {
        this.responseClass = responseClass;
        this.instanceGenerator = instanceGenerator;
    }

    public AbstractGenericParcelable<T> createFromParcel(Parcel in) {
        Gson gson = new GsonBuilder()
                .create();
        String jsonString = in.readString();
        T object = gson.fromJson(jsonString, responseClass);
        return instanceGenerator.getInstance(object);
    }

    public AbstractGenericParcelable<T>[] newArray(int size) {
        return (AbstractGenericParcelable<T>[]) new ArrayList<>(size).toArray();
    }

    public interface InstanceGenerator<T> {
        AbstractGenericParcelable<T> getInstance(T object);
    }
}

