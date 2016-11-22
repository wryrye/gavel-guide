package aperture.science.final_project_umbreon;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.Result;
import aperture.science.final_project_umbreon.JSONObjects.Standings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends IntentService {

    IBinder mBinder = new LocalBinder();

    private ArrayList<Result> data;
    private ArrayList<Pairing> pairings;
    private ArrayList<Pairing> currentRound;

    public MyService(){
        super("MyService");
    }



    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
//        String dataString = workIntent.getDataString();
        pairings = new ArrayList<Pairing>();
        data = new ArrayList<Result>();
//        callAPI();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public MyService getServerInstance() {
            return MyService.this;
        }
    }




    public void callAPI() {
        data = new ArrayList<Result>();
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
                callCurrentRoundParings();
//                broadcastStandings();
//                storeStandings();
//                Log.e("GavelGuide", data+"");
            }
            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                // Log error here since request failed
                Log.e("GavelGuide", t.toString());
            }
        });
    }
    public void getAllPairings(){
        GavelGuideAPIInterface apiService =
                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);
        Call<PairingResult> call = apiService.pairingAll();
        call.enqueue(new Callback<PairingResult>() {
            @Override
            public void onResponse(Call<PairingResult> call, Response<PairingResult> response) {
                PairingResult result = response.body();
                Log.d("results", result.toString());
                for(Pairing i : result.getResults()){
                    pairings.add(i);
                }
                broadcastPairings();
            }
            @Override
            public void onFailure(Call<PairingResult> call, Throwable t) {
                // Log error here since request failed
                Log.e("GavelGuide", t.toString()+"Splash");
            }
        });
    }

    public void getStandings() {
        data = new ArrayList<Result>();
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

                broadcastStandings2();
                storeStandings();
//                Log.e("GavelGuide", data+"");
            }
            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                // Log error here since request failed
                Log.e("GavelGuide", t.toString());
            }
        });
    }

    public void callCurrentRoundParings() {
        currentRound = new ArrayList<Pairing>();
        GavelGuideAPIInterface apiService =
                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);

        Call<PairingResult> call = apiService.pairingCurrentRound();
        call.enqueue(new Callback<PairingResult>() {
            @Override
            public void onResponse(Call<PairingResult> call, Response<PairingResult> response) {
                PairingResult pairings = response.body();
                for(Pairing i : pairings.getResults()){
                    currentRound.add(i);
                }
                broadcastStandings();
                storeStandings();
//                Log.e("GavelGuide", data+"");
            }
            @Override
            public void onFailure(Call<PairingResult> call, Throwable t) {
                // Log error here since request failed
                Log.e("GavelGuide", t.toString());
            }
        });
    }
    public void storeStandings(){
        try {
            String FILENAME = "standings";
            FileOutputStream fos = getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
        }catch(Exception e) {
            Log.e("StoreFileError", e+"");
        }
        Log.d("Data", "is stored!");
    }

    public void broadcastStandings(){
        Intent intent = new Intent("Standings"); //FILTER is a string to identify this intent
        intent.putExtra("Standings", data);
        intent.putExtra("CurrentRound", currentRound);
        sendBroadcast(intent);
    }

    public void broadcastStandings2(){
        Intent intent = new Intent("Splash"); //FILTER is a string to identify this intent
        intent.putExtra("Standings", data);
        sendBroadcast(intent);
    }

    public void broadcastPairings(){
        Intent intent = new Intent("Splash"); //FILTER is a string to identify this intent
        intent.putExtra("AllPairings", pairings);
        sendBroadcast(intent);
    }

    public void testMethod(){
        Log.d("test", "bind works!");
    }
}
