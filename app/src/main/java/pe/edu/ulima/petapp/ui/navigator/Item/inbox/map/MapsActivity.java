package pe.edu.ulima.petapp.ui.navigator.Item.inbox.map;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pe.edu.ulima.petapp.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean ubicarPosition=true;
    LatLng ubicacionGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        if(getIntent().getDoubleExtra("longitud",0) != 0) {
            double longitud = getIntent().getDoubleExtra("longitud", 0);
            double latitud = getIntent().getDoubleExtra("latitud", 0);
            ubicacionGPS = new LatLng(latitud, longitud);
            mMap.addMarker(new MarkerOptions().position(ubicacionGPS).title("Mascota"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionGPS,13));
        }else
            iniciarPosition();
    }
    private void iniciarPosition() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (ubicarPosition)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));
                ubicarPosition = false;
            }
        });
        Toast.makeText(MapsActivity.this,"Sin rastro de mascota",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
