package aperture.science.final_project_umbreon;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import aperture.science.final_project_umbreon.JSONObjects.Judge;
import aperture.science.final_project_umbreon.JSONObjects.Location;
import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.Result;

/**
 * Created by Brandon on 11/19/2016.
 */
public class ViewPairing extends AppCompatActivity implements OnMapReadyCallback {

    Pairing pairing;
    public Result team1;
    public Result team2;
    public Judge judge;
    public Location location;
    TextView team1Text;
    TextView team2Text;
    TextView judgeText;
    TextView locationText;
    ImageView locationImage;
    TextView winner;
    TextView speaker1Result;
    TextView speaker2Result;
    TextView speaker3Result;
    TextView speaker4Result;
    String id;
    String S3Key;
    private boolean showingMap = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        pairing = (Pairing) intent.getSerializableExtra("Pairing");
        id = pairing.getId();
        team1 = pairing.getTeam1ID();
        team2 = pairing.getTeam2ID();
        judge = pairing.getJudgeID();
        location = pairing.getLocationID();
        //Log.d("S3Key", pairing.getRecordingS3Key());
        if (pairing.getRecordingS3Key() == null) {
            S3Key = "no";
        } else {
            S3Key = pairing.getRecordingS3Key();
        }

        if (pairing.getFinished()) {
            setContentView(R.layout.activity_view_result);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            winner = (TextView) findViewById(R.id.winnerName);
            String winnerName;
            if (pairing.getWinningTeam().equals("1")) {
                winnerName = pairing.getTeam1ID().getName();
            } else {
                winnerName = pairing.getTeam2ID().getName();
            }
            winner.setText(winnerName);

            speaker1Result = (TextView) findViewById(R.id.speaker1Result);
            speaker1Result.setText(pairing.getSpeaker1Score());
            speaker2Result = (TextView) findViewById(R.id.speaker2Result);
            speaker2Result.setText(pairing.getSpeaker2Score());
            speaker3Result = (TextView) findViewById(R.id.speaker3Result);
            speaker3Result.setText(pairing.getSpeaker3Score());
            speaker4Result = (TextView) findViewById(R.id.speaker4Result);
            speaker4Result.setText(pairing.getSpeaker4Score());


        } else {
            setContentView(R.layout.activity_view_pairing);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            team1Text = (TextView) findViewById(R.id.team1);
            team1Text.setText(team1.getName());

            team2Text = (TextView) findViewById(R.id.team2);
            team2Text.setText(team2.getName());

            judgeText = (TextView) findViewById(R.id.judge);
            judgeText.setText(judge.getName());

            locationText = (TextView) findViewById(R.id.location);
            locationText.setText(location.getName());

            locationImage = (ImageView) findViewById(R.id.locationImage);
            if (location.getName().equals("Rotunda")) {
                locationImage.setImageResource(R.drawable.rotunda);
            } else if (location.getName().equals("Olsson 120")) {
                locationImage.setImageResource(R.drawable.olsson);
            } else if (location.getName().equals("Newcomb Auditorium")) {
                locationImage.setImageResource(R.drawable.newcomb);
            } else if (location.getName().equals("New Cabell 444")) {
                locationImage.setImageResource(R.drawable.new_cabell);
            } else {
                locationImage.setImageResource(R.drawable.rice);
            }
            SupportMapFragment m = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map));
            m.getMapAsync(this);

            View gooMap = findViewById(R.id.map);
            gooMap.setVisibility(View.GONE);
        }
//


    }

    public void findDirections(View view) {

        //RICE HALL INFORMATION TECHNOLOGY ENGINEERING BUILDING
        try {
            String uriString = "google.navigation:q=" + location.getAddress() + "&mode=w";
            Uri gmmIntentUri = Uri.parse(uriString);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } catch (NullPointerException e) {
            Context context = getApplicationContext();
            CharSequence text = "Error: Maybe no internet?";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


        }

    }
    public void toggleMap(View view){
        View myImage = findViewById(R.id.locationImage);
        View myMap = findViewById(R.id.map);
        Button myButton = (Button)findViewById(R.id.directionsButton);
        if(!showingMap) {
            myImage.setVisibility(View.GONE);
            myMap.setVisibility(View.VISIBLE);
            myButton.setText("Show Building");
        }else{
            myImage.setVisibility(View.VISIBLE);
            myMap.setVisibility(View.GONE);
            myButton.setText("Show Map");
        }
        showingMap = !showingMap;
    }

    public void launchRecordings(View view) {
        Intent recordingIntent = new Intent(this, AudioRecordTest.class);
        recordingIntent.putExtra("Pairing", pairing);
        recordingIntent.putExtra("id", id);
        if (S3Key.equals("no")) {
            recordingIntent.putExtra("S3Key", false);
        } else {
            recordingIntent.putExtra("S3Key", true);
        }
        startActivity(recordingIntent);
    }

    public void launchSubmitBallot(View view) {
        Intent intent = new Intent(this, SubmitBallotActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("judgeCode", pairing.getJudgeID().getCode());
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-33.867, 151.206);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1337);
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

            map.addMarker(new MarkerOptions()
                    .title("Sydney")
                    .snippet("The most populous city in Australia.")
                    .position(sydney));


//            return;
        }
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }


}
