package aperture.science.final_project_umbreon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import aperture.science.final_project_umbreon.JSONObjects.Judge;
import aperture.science.final_project_umbreon.JSONObjects.Location;
import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.Result;
import aperture.science.final_project_umbreon.JSONObjects.Standings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Brandon on 11/19/2016.
 */
public class ViewPairing extends AppCompatActivity {

    public Result team1;
    public Result team2;
    public Judge judge;
    public Location location;
    TextView team1Text;
    TextView team2Text;
    TextView judgeText;
    TextView locationText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pairing);
        GavelGuideAPIInterface apiService =
                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);
        Log.e("Test1", "1");
        Call<PairingResult> call = apiService.pairing("1");
        Log.e("Test1", "1");
        call.enqueue(new Callback<PairingResult>() {
            @Override
            public void onResponse(Call<PairingResult> call, Response<PairingResult> response) {
                PairingResult result = response.body();
                List<Pairing> pairings = result.getResults();
                Log.e("Test1", pairings.toString());
                Pairing pairing = pairings.get(0);
                Log.e("Test1", pairing.toString());
                team1 = pairing.getTeam1ID();
                team2 = pairing.getTeam2ID();
                judge = pairing.getJudgeID();
                location = pairing.getLocationID();

                team1Text = (TextView) findViewById(R.id.team1);
                team1Text.setText(team1.getName());

                team2Text = (TextView) findViewById(R.id.team2);
                team2Text.setText(team2.getName());

                judgeText = (TextView) findViewById(R.id.judge);
                judgeText.setText(judge.getName());

                locationText = (TextView) findViewById(R.id.location);
                locationText.setText(location.getName());



//
//              GsonBuilder builder = new GsonBuilder();
//                Gson gson = builder.create();
//                Log.e("Test1", "1");
//                try {
//                    Log.e("Test1", "2");
//                    JSONObject obj = new JSONObject(response.body());
//                    Log.e("Test1", "3");
//                    JSONArray array = obj.getJSONArray("results");
//                    Log.e("Test1", "4");
//                    Log.e("Team 1",array.getJSONObject(0).getString("team1ID") );
//                    team1 = gson.fromJson(array.getJSONObject(0).getString("team1ID"), Result.class);
//                    //team1 = array.getJSONObject(0).getString("team1ID");
//                    team2 = gson.fromJson(array.getJSONObject(0).getString("team2ID"), Result.class);
//                    judge = gson.fromJson(array.getJSONObject(0).getString("judgeID"), Judge.class);
//                    location = gson.fromJson(array.getJSONObject(0).getString("locationID"), Location.class);
//
//
//
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                team1 = response.body().getTeam1ID();
//                team2 = response.body().getTeam2ID();
//                judge = response.body().getJudgeID();
//                location = response.body().getLocationID();
                //Standings standings = response.body();
                //Log.e("Response Body: ", String.valueOf(response.body()));
                Log.e("Team 1: ", team1.getName());
                Log.e("Team 2: ", team2.getName());
                Log.e("Judge: ", judge.getName());
                Log.e("Location: ", location.getName());

//                for(Result i : standings.getResults()){
//                    //data.add(i);
//                }
////                Collections.sort(data, new Comparator<Result>() {
//                    @Override
//                    public int compare(Result r1, Result r2)
//                    {
//                        return  r2.getWins().compareTo(r1.getWins());
//                    }
//                });
                //broadcastStandings();
                //storeStandings();
//                Log.e("GavelGuide", data+"");
            }
            @Override
            public void onFailure(Call<PairingResult> call, Throwable t) {
                // Log error here since request failed
                Log.e("GavelGuide", t.toString());
            }
        });




    }


}
