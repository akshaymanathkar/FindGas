package findgas.com.findgas.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import findgas.com.findgas.R;
import findgas.com.findgas.station.Station;
import findgas.com.findgas.station.StationAdapter;
import findgas.com.findgas.station.StationData;
import findgas.com.findgas.station.StationParcelable;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class FindGasActivity extends Activity implements LocationListener {
    private final static String LOG_AREA = "FindGasActivity";
    public static final String KEY_STATION = "KeyStation";
    public static final String API_KEY = "wcplzhec0w";
    public static final String API_URL = "http://api.mygasfeed.com/";

    private ProgressDialog progressDialog;
    private ListView listView;
    private LocationManager locationManager;
    private RadioGroup radioGroup;
    private RadioButton sortByDistance, sortByPrice;
    private Location location;
    private boolean isSortByDistance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_gas);

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int googlePlayStat = googleApiAvailability.isGooglePlayServicesAvailable(
                FindGasActivity.this);
        if (googlePlayStat != ConnectionResult.SUCCESS) {
            Log.d(LOG_AREA, "Google play status is " + googlePlayStat);
            try {
                googleApiAvailability.getErrorDialog(FindGasActivity.this, googlePlayStat, 0).show();
            } catch (Exception e) {
                Log.d(LOG_AREA, e.toString());
            }
        } else {
            Log.d(LOG_AREA, "OnCompleted");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    launchApp();
                }
            });
        }
    }

    private void launchApp() {
        listView = (ListView) findViewById(R.id.list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        radioGroup = (RadioGroup) findViewById(R.id.sort_by_radio_group);
        radioGroup.check(R.id.sort_by_price);

        sortByDistance = (RadioButton) findViewById(R.id.sortBy_distance);
        sortByDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!progressDialog.isShowing()){
                    progressDialog.show();
                }
                isSortByDistance = true;
                GasStationsFetcher gasStationsFetcher = new GasStationsFetcher();
                gasStationsFetcher.execute();
            }
        });
        sortByPrice = (RadioButton) findViewById(R.id.sort_by_price);
        sortByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!progressDialog.isShowing()){
                    progressDialog.show();
                }
                isSortByDistance = false;
                GasStationsFetcher gasStationsFetcher = new GasStationsFetcher();
                gasStationsFetcher.execute();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location newLocation) {
        if(this.location == null){
            this.location = newLocation;
            GasStationsFetcher gasStationsFetcher = new GasStationsFetcher();
            gasStationsFetcher.execute();
        } else {
            if (distance(location.getLatitude(), location.getLongitude(),
                    newLocation.getLatitude(), newLocation.getLongitude()) > 0.1) { // if distance < 0.1 miles we take locations as equal
                this.location = newLocation;
                GasStationsFetcher gasStationsFetcher = new GasStationsFetcher();
                gasStationsFetcher.execute();
            }
        }
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class GasStationsFetcher extends AsyncTask<Void, Void, String> {

        public GasStationsFetcher(){
        }

        protected String doInBackground(Void... urls) {
            try {
                String sortBy = "price";
                if(isSortByDistance){
                    sortBy = "distance";
                }


                URL url = new URL(API_URL + "stations/radius/" +  location.getLatitude()+ "/" + location.getLongitude() + "/5/reg/" + sortBy + "/"+ API_KEY + ".json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "";
                return;
            }

            //Got a value of Diesel as this so added a temporary fix
            response = response.replace("N\\/A", "-1");
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            Log.i("response", response);

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            StationData stationData = gson.fromJson(response, StationData.class);

            List<Station> stations = stationData.getStations();

            final StationAdapter stationAdapter = new StationAdapter(FindGasActivity.this, stations, isSortByDistance);

            listView.setAdapter(stationAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Station station = (Station) listView.getItemAtPosition(position);

                    Intent intent = new Intent(FindGasActivity.this, GasStationDetailsActivity.class);
                    intent.putExtra(KEY_STATION, new StationParcelable(station));
                    startActivity(intent);
                }

            });

        }
    }

}
