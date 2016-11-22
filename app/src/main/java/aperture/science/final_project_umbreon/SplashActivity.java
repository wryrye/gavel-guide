package aperture.science.final_project_umbreon;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Brandon on 11/20/2016.
 */
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 000;
    private ArrayList<Pairing> pairings;
    private ArrayList<Result> standings;
    private MyService mServer;
    boolean mBounded;
    private BroadcastReceiver broadcastReceiver;
    private ServiceConnection mConnection;
    private int returnCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pairings = new ArrayList<Pairing>();
        standings = new ArrayList<Result>();

        Intent myServiceIntent = new Intent(this, MyService.class); //start service
        startService(myServiceIntent);

        mConnection = new ServiceConnection() { //start connecting to service

            public void onServiceDisconnected(ComponentName name) {
                Toast.makeText(SplashActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
                mBounded = false;
                mServer = null;
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                Toast.makeText(SplashActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
                mBounded = true;
                MyService.LocalBinder mLocalBinder = (MyService.LocalBinder)service;
                mServer = mLocalBinder.getServerInstance();
                Log.d("Service", "has been made!");

                Intent intent = new Intent("Splash"); //broadcast when service is successfully connected
                intent.putExtra("ServiceMade", "Yes");
                sendBroadcast(intent);
            }
        };



        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) { //once connected...
                if(intent.hasExtra("ServiceMade")){
                    if(isNetworkAvailable()){ //make API calls if network
                        mServer.getAllPairings();
                        mServer.getStandings();
                    }
                    else{ //otherwise go to MainActivity
                        startMainActivity();
                    }
                }
                else if(intent.hasExtra("Standings")){ //store result from getStandings response, startMainActivity() if both requests have completed
                    ArrayList<Result> data = (ArrayList<Result>) intent.getSerializableExtra("Standings");
                    standings = data;
                    Log.d("Returnct", returnCount+"");
                    if(returnCount == 1){
                        startMainActivity();
                    }
                    returnCount++;
                }
                else{   //store result from getAllPairings response, startMainActivity() if both requests have completed
                    ArrayList<Pairing> data = (ArrayList<Pairing>) intent.getSerializableExtra("AllPairings");
                    pairings = data;
                    Log.d("Returnct", returnCount+"");
                    if(returnCount ==1){
                        startMainActivity();
                    }
                    returnCount++;
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("Splash")); //register receiver to listen to broadcasts
    }

    @Override
    protected void onStart() { //bind service to connection on start
        super.onStart();
        Intent mIntent = new Intent(this, MyService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
        Log.d("Service test", mServer+"");
    }

    @Override
    protected void onPause() { //unregister receiver on pause
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStop() { //unbind service on stop
        super.onStop();
        if(mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }
    public void startMainActivity(){
        Log.d("Splash","start1");
        ((PairingArray) this.getApplication()).setUpPairings(pairings); //set pairings
        ((PairingArray) this.getApplication()).setUpStandings(standings); //set standings
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("CurrentRound", pairings);
        Log.d("Splash","start2");
        startActivity(i);

        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
