package findgas.com.findgas.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import findgas.com.findgas.R;
import findgas.com.findgas.fragment.TextWithSubTextFragment;
import findgas.com.findgas.station.Station;
import findgas.com.findgas.station.StationParcelable;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class GasStationDetailsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Station station;
    private LinearLayout activity_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        activity_layout = (LinearLayout) findViewById(R.id.activity_layout);

        mapFragment.getMapAsync(this);

        StationParcelable stationParcelable = getIntent().getParcelableExtra(FindGasActivity.KEY_STATION);
        station = stationParcelable.getObject();
        TextWithSubTextFragment countryFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.country), station.getCountry());
        addFragmentToView(countryFragment);
        TextWithSubTextFragment regPriceFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.regular_price), String.valueOf(station.getRegPrice()));
        addFragmentToView(regPriceFragment);
        TextWithSubTextFragment midPriceFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.mid_price), String.valueOf(station.getMidPrice()));
        addFragmentToView(midPriceFragment);
        TextWithSubTextFragment prePriceFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.pre_price), String.valueOf(station.getPrePrice()));
        addFragmentToView(prePriceFragment);
        TextWithSubTextFragment addressFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.address), station.getAddress());
        addFragmentToView(addressFragment);
        TextWithSubTextFragment dieselFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.diesel), String.valueOf(station.getDiesel()));
        addFragmentToView(dieselFragment);
        TextWithSubTextFragment idFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.id), String.valueOf(station.getId()));
        addFragmentToView(idFragment);
        TextWithSubTextFragment latFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.latitude), String.valueOf(station.getLat()));
        addFragmentToView(latFragment);
        TextWithSubTextFragment lngFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.longitude), String.valueOf(station.getLng()));
        addFragmentToView(lngFragment);
        TextWithSubTextFragment stationFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.station), station.getStation());
        addFragmentToView(stationFragment);
        TextWithSubTextFragment regionFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.region), station.getRegion());
        addFragmentToView(regionFragment);
        TextWithSubTextFragment cityFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.city), station.getCity());
        addFragmentToView(cityFragment);
        TextWithSubTextFragment redDateFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.reg_date), station.getRegDate());
        addFragmentToView(redDateFragment);
        TextWithSubTextFragment midDateFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.mid_date), station.getMidDate());
        addFragmentToView(midDateFragment);
        TextWithSubTextFragment preDateFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.pre_date), station.getPreDate());
        addFragmentToView(preDateFragment);
        TextWithSubTextFragment dieselDateFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.diesel_date), station.getDieselDate());
        addFragmentToView(dieselDateFragment);
        TextWithSubTextFragment distanceFragment = TextWithSubTextFragment.newInstance(getResources().getString(R.string.distance), station.getDistance());
        addFragmentToView(distanceFragment);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng gasStation = new LatLng(station.getLat(), station.getLng());
        mMap.addMarker(new MarkerOptions().position(gasStation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gasStation, 15));
    }

    private void addFragmentToView(Fragment fragment) {
            getSupportFragmentManager().beginTransaction().add(activity_layout.getId(), fragment).commit();
            getSupportFragmentManager().executePendingTransactions();
    }
}
