package aperture.science.final_project_umbreon;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import aperture.science.final_project_umbreon.JSONObjects.Result;
import aperture.science.final_project_umbreon.JSONObjects.Standings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends IntentService {

    private ArrayList<Result> data;

    public MyService(){
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
//        String dataString = workIntent.getDataString();
        data = new ArrayList<Result>();
        callAPI();
    }


    public void callAPI() {
        GavelGuideAPIInterface apiService =
                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);

        Call<Standings> call = apiService.standingsList("getRankedTeamsJoin");
        call.enqueue(new Callback<Standings>() {
            @Override
            public void onResponse(Call<Standings> call, Response<Standings> response) {
                Standings standings = response.body();
                for(Result i : standings.getResults()){
                    data.add(i);
                }
                Collections.sort(data, new Comparator<Result>() {
                    @Override
                    public int compare(Result r1, Result r2)
                    {
                        return  r2.getWins().compareTo(r1.getWins());
                    }
                });
                broadcastStandings();
//                Log.e("GavelGuide", data+"");
            }
            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                // Log error here since request failed
                Log.e("GavelGuide", t.toString());
            }
        });
    }

    public void broadcastStandings(){
        Intent intent = new Intent("Standings"); //FILTER is a string to identify this intent
        intent.putExtra("Standings", data);
        sendBroadcast(intent);
    }
}
