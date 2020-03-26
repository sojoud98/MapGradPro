package ideal.com.ps.mapgradpro;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class LocationService extends BroadcastReceiver {

    public static final String LOG="location service log";
    public LocationService() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null){
            final String action =intent.getAction();
            if(LOG.equals(action)){
                LocationResult result=LocationResult.extractResult(intent);
                if(result!=null){
                    Location location=result.getLastLocation();
                    StringBuilder locationString=new StringBuilder("Location: "+location.getLatitude()+", "+location.getLongitude());
                    try {
                        MainActivity.getInstance().updateTextView(locationString.toString());

                    }catch (Exception e){
                        Toast.makeText(context,locationString.toString(),Toast.LENGTH_LONG).show();
                    }
                    }
                }
            }
        }
    }

