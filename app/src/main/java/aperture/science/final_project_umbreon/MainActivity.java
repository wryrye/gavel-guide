package aperture.science.final_project_umbreon;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.Result;
import aperture.science.final_project_umbreon.JSONObjects.Standings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    PagerAdapter adapter;
    public String myString = "top secret!";
    ArrayList<Result> standings;
    ArrayList<Pairing> currentRound;
    boolean mBounded;
    MyService mServer;
    BroadcastReceiver broadcastReceiver;
    private ServiceConnection mConnection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myServiceIntent = new Intent(this, MyService.class); //start API service
        startService(myServiceIntent);

        mConnection = new ServiceConnection() { //start connecting to service

            public void onServiceDisconnected(ComponentName name) {
//                Toast.makeText(MainActivity.this, "Main service is disconnected", Toast.LENGTH_SHORT).show();
                mBounded = false;
                mServer = null;
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
//                Toast.makeText(MainActivity.this, "Main service is connected", Toast.LENGTH_SHORT).show();
                mBounded = true;
                MyService.LocalBinder mLocalBinder = (MyService.LocalBinder) service;
                mServer = mLocalBinder.getServerInstance();

                Intent intent = new Intent("Splash"); //broadcast when service is successfully connected
                intent.putExtra("ServiceMade", "Yes");
                sendBroadcast(intent);
            }
        };
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) { //once connected...

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("Splash")); //register receiver to listen to broadcasts

        //make tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Pairings"));
        tabLayout.addTab(tabLayout.newTab().setText("Results"));
        tabLayout.addTab(tabLayout.newTab().setText("Standings"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                Log.d("GLaDOS",adapter.m1.get(tab.getPosition())+" ");

//                bob.getRV().findViewHolderForAdapterPosition(0).itemView.setTranslationX(300);
                TabFragment3 tf3 = (TabFragment3)adapter.getItem(2);
                Log.d("tf3",(tf3 == null)+"");
//                Log.d("tf3",tf3+"");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                TabFragment3 bob = (TabFragment3)adapter.getItem(2);
//                Log.d("GLaDOS",bob.getRV().getAdapter()+" ");
//                bob.getRV().getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent mIntent = new Intent(this, MyService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void refreshData(View view){
        mServer.callAPI();
    }

    public void viewPairing(View view){

        Intent intent = new Intent(this, ViewPairing.class);
        intent.putExtra("id", "1");
        startActivity(intent);
    }
    public void viewPairing2(View view){

        Intent intent = new Intent(this, ViewPairing.class);
        intent.putExtra("id", "2");
        startActivity(intent);
    }

    public ArrayList<Pairing> getCurrentRoundPairings(){
        return currentRound;
    }
    public void viewPairingInfo(View view){
        TextView currentItem = (TextView)view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

//        savedInstanceState.putBoolean("MyBoolean", true);
//        savedInstanceState.putDouble("myDouble", 1.9);
//        savedInstanceState.putInt("MyInt", 1);
//        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
        super.onSaveInstanceState(savedInstanceState);
    }

}
