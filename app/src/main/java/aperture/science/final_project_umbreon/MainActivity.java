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
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Result;
import aperture.science.final_project_umbreon.JSONObjects.Standings;


public class MainActivity extends AppCompatActivity {

    PagerAdapter adapter;
    public String myString = "top secret!";
    ArrayList<Result> standings;
    boolean mBounded;
    MyService mServer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //listen for messages from API MyService
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<Result> data = (ArrayList<Result>) intent.getSerializableExtra("Standings");
                standings = data; //set data to be retreived by Fragment3
                Log.d("Data","is from API");
//                TabFragment3 tf3 = (TabFragment3)adapter.getItem(2);
//                if(tf3 != null)
//                    tf3.updateData();
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("Standings"));

        //if network is available...
        if (isNetworkAvailable()) {
            //start API MyService
            Intent myServiceIntent = new Intent(this, MyService.class);
            startService(myServiceIntent);
        }
        else{ //search for a backup file
            try {
                String FILENAME = "standings";
                FileInputStream fis = openFileInput(FILENAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                standings =(ArrayList<Result>) ois.readObject(); //set data to be retreived by Fragment3
                ois.close();
                Log.d("Data","is from backup file");
            }catch(Exception e) { //if there is an error, inform user and make dummy data object
                Log.e("LoadFileError", e+"");
                standings = new ArrayList<Result>();
                Context context = getApplicationContext();
                CharSequence text = "No internet or backup file!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.d("Data","is not available!");
            }

        }


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

//
    }

    ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            mBounded = false;
            mServer = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
            mBounded = true;
            MyService.LocalBinder mLocalBinder = (MyService.LocalBinder)service;
            mServer = mLocalBinder.getServerInstance();
        }
    };

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
        startActivity(intent);
    }

}
