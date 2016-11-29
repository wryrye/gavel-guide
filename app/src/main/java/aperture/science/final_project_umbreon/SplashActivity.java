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
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.Result;


public class SplashActivity extends AppCompatActivity {

    private ArrayList<Pairing> pairings;
    private ArrayList<Result> standings;
    private APIService mServer;
    boolean mBounded;
    private BroadcastReceiver broadcastReceiver;
    private ServiceConnection mConnection;
    private int returnCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        setTheme(R.style.SplashTheme);

//        ImageView splashImage = (ImageView) findViewById(R.id.splashImage);
//        locationImage.setImageResource(R.drawable.rice);

        pairings = new ArrayList<Pairing>();
        standings = new ArrayList<Result>();

        if (!isNetworkAvailable()) {//if no network available
            try { //try to load backup
                String FILENAME = "standings";
                FileInputStream fis = openFileInput(FILENAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                standings = (ArrayList<Result>) ois.readObject(); //set data to be retreived by Fragment3
                ois.close();
                Log.d("Data-Standings", "is from backup file");

                String FILENAME2 = "pairings";
                FileInputStream fis2 = openFileInput(FILENAME2);
                ObjectInputStream ois2 = new ObjectInputStream(fis2);
                pairings = (ArrayList<Pairing>) ois2.readObject(); //set data to be retreived by Fragment3
                ois2.close();
                Log.d("Data-Pairings", "is from backup file");
                Toast.makeText(SplashActivity.this, "Data from backup file!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) { //if there is an error, inform user and make dummy data object
                Log.e("LoadFileError", e + "");
                standings = new ArrayList<Result>();
                Context context = getApplicationContext();
                CharSequence text = "No internet or backup file!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.d("Data", "is not available!");
            }
            startMainActivity(); //and go to MainActivity

        } else { //if network is available...
            Intent myServiceIntent = new Intent(this, APIService.class); //start API service
            startService(myServiceIntent);

            mConnection = new ServiceConnection() { //start connecting to service

                public void onServiceDisconnected(ComponentName name) {
//                    Toast.makeText(SplashActivity.this, "Splash service is disconnected", Toast.LENGTH_SHORT).show();
                    mBounded = false;
                    mServer = null;
                }

                public void onServiceConnected(ComponentName name, IBinder service) {
//                    Toast.makeText(SplashActivity.this, "Splash service is connected", Toast.LENGTH_SHORT).show();
                    mBounded = true;
                    APIService.LocalBinder mLocalBinder = (APIService.LocalBinder) service;
                    mServer = mLocalBinder.getServerInstance();

                    Intent intent = new Intent("Splash"); //broadcast when service is successfully connected
                    intent.putExtra("ServiceMade", "Yes");
                    sendBroadcast(intent);
                }
            };
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) { //once connected...
                    if (intent.hasExtra("ServiceMade")) {
                        mServer.getAllPairings();
                        mServer.getAllStandings();
                    } else if (intent.hasExtra("Standings")) { //store result from getAllStandings response, startMainActivity() if both requests have completed
                        ArrayList<Result> data = (ArrayList<Result>) intent.getSerializableExtra("Standings");
                        standings = data;
                        if (returnCount == 1) {
                            startMainActivity();
                        }
                        returnCount++;
                    } else {   //store result from getAllPairings response, startMainActivity() if both requests have completed
                        ArrayList<Pairing> data = (ArrayList<Pairing>) intent.getSerializableExtra("AllPairings");
                        pairings = data;
                        if (returnCount == 1) {
                            startMainActivity();
                        }
                        returnCount++;
                    }
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("Splash")); //register receiver to listen to broadcasts
            Toast.makeText(SplashActivity.this, "Data from web server!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() { //bind service to connection on start
        super.onStart();
        Intent mIntent = new Intent(this, APIService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() { //unregister receiver on pause
        super.onPause();
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e){

        }
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
