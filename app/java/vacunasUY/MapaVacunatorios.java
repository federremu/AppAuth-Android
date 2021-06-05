package vacunasUY;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.openid.appauthdemo.R;

public class MapaVacunatorios extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //gps
    Double latitude = -32.522779;
    Double longitude = -55.765835;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            update();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_vacunatorios);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //ActivityCompat.requestPermissions(MapaVacunatorios.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MapaVacunatorios.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            //return;
        }

 /*       if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100,
                    5, mLocationListener);
        }*/

        if (mLocationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, mLocationListener);

        if (mLocationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, mLocationListener);





    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng vacunatorio1 = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(vacunatorio1).title("Vacunatorio1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vacunatorio1));
        mMap.setMaxZoomPreference(5000);
    }

    public void update(){
        BitmapDescriptor iconPersona = BitmapDescriptorFactory.fromResource(R.drawable.outline_person_pin_black_24);
        BitmapDescriptor iconVacunatorio = BitmapDescriptorFactory.fromResource(R.drawable.outline_local_hospital_black_24);

        // longitud latitud
        LatLng yo = new LatLng(latitude, longitude);
        LatLng vacunatorio1= new LatLng(-34.9092129, -56.1774683);
        LatLng vacunatorio2= new LatLng(-34.9122708, -56.1881965);

        mMap.addMarker(new MarkerOptions().position(yo).title("Tu estás aquí").icon(iconPersona));
        mMap.addMarker(new MarkerOptions().position(vacunatorio1).title("Vacunatorio Circulo Catolico").icon(iconVacunatorio));
        mMap.addMarker(new MarkerOptions().position(vacunatorio2).title("Vacunatorio Barrio Sur").icon(iconVacunatorio));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(vacunatorio1));
        float zoomLevel = 16.5f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yo, zoomLevel));

    }



}
