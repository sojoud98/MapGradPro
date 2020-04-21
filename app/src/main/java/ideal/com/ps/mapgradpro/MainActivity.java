package ideal.com.ps.mapgradpro;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static MainActivity instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fuesedLocationProviderClient;
    TextView t;
    public static MainActivity getInstance() {
        return instance;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        t=findViewById(R.id.txt);


        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {

            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {

                updateInfo();


            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

                Toast.makeText(MainActivity.this, "Accept permission", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {


            }
        }).check();
    }

    private void updateInfo() {
        buildLocationRequest();
        fuesedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(Build.VERSION.SDK_INT>=23){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
    }
        fuesedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
    Intent i =new Intent(this,LocationService.class);
    i.setAction(LocationService.LOG);
    return PendingIntent.getBroadcast(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {
    locationRequest=new LocationRequest();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(2000);
    locationRequest.setFastestInterval(1000);
    locationRequest.setSmallestDisplacement(10f);
    }
    public void updateTextView(final String v){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                t.setText(v);
            }
        });

    }
}
