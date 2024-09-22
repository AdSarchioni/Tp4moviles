
package com.example.menuconmapa.ui.mapa;
import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.menuconmapa.MainActivity;
import com.example.menuconmapa.model.Farmacia;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapaFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<MapaActual> mMapaActual;
    private FusedLocationProviderClient fused;
    private Context context;

    public MapaFragmentViewModel(@NonNull Application application) {
        super(application);
        fused = LocationServices.getFusedLocationProviderClient(application);
        context = application.getApplicationContext();
    }

    public LiveData<MapaActual> getMmapaActual() {
        if (mMapaActual == null) {
            mMapaActual = new MutableLiveData<>();
        }
        return mMapaActual;
    }

    public void obtenerMapa() {
        mMapaActual.setValue(new MapaActual());
    }

    public class MapaActual implements OnMapReadyCallback {
        ArrayList<Farmacia> farmacias = MainActivity.listaFarmacias;
        private Marker marcadorYo;

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            // Agregar marcadores para cada farmacia de la lista
            for (Farmacia farmacia : farmacias) {
                LatLng posicionFarmacia = new LatLng(farmacia.getLat(), farmacia.getLon());
                googleMap.addMarker(new MarkerOptions()
                        .position(posicionFarmacia)
                        .title(farmacia.getNombre())
                        .snippet(farmacia.getDireccion() + " - Horario: " + farmacia.getInfo()));
            }

            // Verificar permisos para ubicación
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            // Configurar la solicitud de ubicación
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(5000); // Intervalo de 5 segundos
            locationRequest.setFastestInterval(3000); // Intervalo más rápido de 3 segundos
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            // Callback para recibir actualizaciones de ubicación
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }

                    // Obtener la última ubicación del usuario
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        LatLng yo = new LatLng(location.getLatitude(), location.getLongitude());

                        // Si ya existe el marcador, actualizarlo; de lo contrario, crearlo
                        if (marcadorYo != null) {
                            marcadorYo.setPosition(yo);
                        } else {
                            marcadorYo = googleMap.addMarker(new MarkerOptions()
                                    .position(yo)
                                    .title("Yo")
                                    .snippet("Mi ubicación actual"));
                        }

                        // Mover la cámara a la ubicación del usuario con un zoom de 15
                        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yo, 15));
                    }
                }
            };

            // Solicitar actualizaciones de ubicación
            fused.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }
}

