package aperture.science.final_project_umbreon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Brandon on 11/20/2016.
 */
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 000;
    ArrayList<Pairing> pairings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pairings = new ArrayList<Pairing>();

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
                Log.d("After CurrentRoundCall", pairings.size() + "");
                startMainActivity();

            }
            @Override
            public void onFailure(Call<PairingResult> call, Throwable t) {
                // Log error here since request failed
                Log.e("GavelGuide", t.toString());
            }
        });

    }
    public void startMainActivity(){
        ((PairingArray) this.getApplication()).setUpPairings(pairings);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("CurrentRound", pairings);
        startActivity(i);

        // close this activity
        finish();
    }
}
