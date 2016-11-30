package aperture.science.final_project_umbreon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import aperture.science.final_project_umbreon.JSONObjects.Ballot;
import aperture.science.final_project_umbreon.JSONObjects.S3Key;
import aperture.science.final_project_umbreon.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Brandon on 11/27/2016.
 */
public class SubmitBallotActivity extends Activity implements AdapterView.OnItemSelectedListener{
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    EditText judgeCodeInput;
    String speaker1Score;
    String speaker2Score;
    String speaker3Score;
    String speaker4Score;
    String winner;
    String id;
    String judgeCode;
    String judgeCodeEntered;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_ballot);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        judgeCode = intent.getStringExtra("judgeCode");
        Log.d("JudgeCode", judgeCode);
        judgeCodeInput = (EditText) findViewById(R.id.judgeCodeInput);
        spinner1 = (Spinner) findViewById(R.id.spinnerSpeaker1);
        spinner2 = (Spinner) findViewById(R.id.spinnerSpeak2);
        spinner3 = (Spinner) findViewById(R.id.spinnerSpeak3);
        spinner4 = (Spinner) findViewById(R.id.spinnerSpeak4);


        List<String> speaks = new ArrayList<String>();
        speaks.add("24");
        speaks.add("24.25");
        speaks.add("24.5");
        speaks.add("24.75");
        speaks.add("25");
        speaks.add("25.25");
        speaks.add("25.5");
        speaks.add("25.75");
        speaks.add("26");
        speaks.add("26.25");
        speaks.add("26.5");
        speaks.add("26.75");
        speaks.add("27");
        speaks.add("27.25");
        speaks.add("27.5");
        speaks.add("27.75");
        speaks.add("28");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speaks);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speaks);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speaks);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speaks);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter4);



    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_gov:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_opp:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void submitForm(View view){
        judgeCodeEntered = judgeCodeInput.getText().toString();
        if(!judgeCode.equals(judgeCodeEntered)){
            Toast.makeText(SubmitBallotActivity.this, "Incorrect Judge Code", Toast.LENGTH_LONG).show();
            return;
        }

        speaker1Score = spinner1.getSelectedItem().toString();
        Log.d("speaker1Score", speaker1Score);

        speaker2Score = spinner2.getSelectedItem().toString();
        Log.d("speaker2Score",speaker2Score);

        speaker3Score = spinner3.getSelectedItem().toString();
        Log.d("speaker3Score", speaker3Score);

        speaker4Score = spinner4.getSelectedItem().toString();
        Log.d("speaker4Score", speaker4Score);

        RadioButton govRadioButton = (RadioButton) findViewById(R.id.radio_gov);
        boolean radioResponse = govRadioButton.isChecked();
        if(radioResponse){
            winner = "1";
        } else {
            winner = "2";
        }
        Log.d("Winner", winner);
        GavelGuideAPIInterface apiService =
                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);
        Ballot ballot = new Ballot(id, winner, speaker1Score, speaker2Score, speaker3Score, speaker4Score);
        Call<String> call = apiService.submitDecision(ballot);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
        ((PairingArray) this.getApplication()).updatePairingResult(id, winner, speaker1Score, speaker2Score, speaker3Score, speaker4Score);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);



    }
}
