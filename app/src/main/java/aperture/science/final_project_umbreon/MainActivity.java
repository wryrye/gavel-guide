package aperture.science.final_project_umbreon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Result;
import aperture.science.final_project_umbreon.JSONObjects.Standings;


public class MainActivity extends AppCompatActivity {

    PagerAdapter adapter;
    public String myString = "top secret!";
    ArrayList<Result> standings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start API service
        Intent myServiceIntent = new Intent(this, MyService.class);
        startService(myServiceIntent);

        //Listen for response
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<Result> data = (ArrayList<Result>)intent.getSerializableExtra("Standings");
                standings=data; //set data to be retreived by Fragment3
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("Standings"));


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
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                TabFragment3 bob = (TabFragment3)adapter.getItem(2);
                Log.d("GLaDOS",bob.getRV().getAdapter()+" ");
                bob.getRV().getAdapter().notifyDataSetChanged();
            }
        });


    }


}
