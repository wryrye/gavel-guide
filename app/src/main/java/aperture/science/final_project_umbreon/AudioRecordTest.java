package aperture.science.final_project_umbreon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.S3Key;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AudioRecordTest extends Activity {


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    private static String mFileNameDownload = null;

    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;

    private TransferUtility transferUtility;

    Pairing pairing;
    private String id;
    private boolean s3key;
    private String S3KeyString;
    int RECORD_AUDIO_PERMISSION;
    Button recordButton;
    Button playButton;
    Button uploadButton;
    Button downloadButton;
    Button playButtonDownload;
    ProgressBar uploadProgress;
    boolean mStartPlaying;
    boolean mStartRecording;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private void onRecord(boolean start) {
        if (start) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION);
//            }
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RECORD_AUDIO_PERMISSION);
//            } else {
//                startRecording();
//            }
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                startRecording();


        } else {
            stopRecording();
        }
    }





    private void onPlay(boolean start) {
        if (start) {
            startPlaying(mFileName);
        } else {
            stopPlaying();
        }
    }

    private void onPlayDownload(boolean start) {
        if (start) {
            startPlaying(mFileNameDownload);
        } else {
            stopPlaying();
        }
    }

    private void startPlaying(String fileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        Log.d("Start Recording now", "Start");
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AudioRecordTest Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://aperture.science.final_project_umbreon/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AudioRecordTest Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://aperture.science.final_project_umbreon/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }




    public AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
        mFileNameDownload = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileNameDownload += "/audiodownload.3gp";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //RECORD_AUDIO_PERMISSION = ((PairingArray) this.getApplication()).getAudioConstant();
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),    /* get the context for the application */
                "us-east-1:2932b4f6-0636-4ed3-9cf3-357ff4a3ee97",    /* Identity Pool ID */
                Regions.US_EAST_1           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        );
        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getApplicationContext());
//        setContentView(R.layout.recording_upload);
//        mRecordButton = (RecordButton) findViewById(R.id.startRecording);
//        mPlayButton = (PlayButton) findViewById(R.id.startPlaying);
//        uploadButton = (UploadButton) findViewById(R.id.uploadS3);
        Intent intent = getIntent();
        pairing = (Pairing) intent.getSerializableExtra("Pairing");
        id = pairing.getId();
        S3KeyString = id + "Recording";
        Log.d("s3KeyString", S3KeyString);
        s3key = intent.getBooleanExtra("S3Key", true);
        Log.d("s3Key", s3key + "");
        mStartPlaying = true;
        mStartRecording = true;
        if (s3key) {
            setContentView(R.layout.download_recording);
            downloadButton = (Button) findViewById(R.id.downloadButton);
            playButtonDownload = (Button) findViewById(R.id.playButtonDownload);
            uploadProgress = (ProgressBar) findViewById(R.id.progress_bar);
        } else {
            setContentView(R.layout.upload_recording);
            playButton = (Button) findViewById(R.id.playButtonUpload);
            recordButton = (Button) findViewById(R.id.recordButtonAudio);
            uploadButton = (Button) findViewById(R.id.uploadAudio);
            uploadProgress = (ProgressBar) findViewById(R.id.progress_bar);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void clickUpload(View view){
        File recording = new File(mFileName);
        GavelGuideAPIInterface apiService =
                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);
        Log.e("Test1", "1");
        S3Key key = new S3Key(id, S3KeyString);
        Call<String> call = apiService.addS3Key(key);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        ((PairingArray) getApplication()).updatePairingS3Key(id, S3KeyString);

        TransferObserver observer = transferUtility.upload(
                "gavelguide2",     /* The bucket to upload to */
                S3KeyString,    /* The key for the uploaded object */
                recording       /* The file where the data to upload exists */
        );
        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d("state", state.toString());
                if(state.toString().equals("COMPLETED")){
                    Toast.makeText(AudioRecordTest.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                    viewPairing();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                uploadProgress.setMax((int) (bytesTotal*1000));
                uploadProgress.setProgress((int) bytesCurrent*1000);
            }

            @Override
            public void onError(int id, Exception ex) {

            }
        });

    }

    public void clickPlay(View view){
        onPlay(mStartPlaying);
        if (mStartPlaying) {
            playButton.setText("Stop playing");
        } else {
            playButton.setText("Start playing");
        }
        mStartPlaying = !mStartPlaying;
    }

    public void clickRecord(View view) {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read Contacts");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale

                ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                        2);

                return;
            }
            ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                    2);
            return;
        }

        clickRecordLogic();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
//            if (!shouldShowRequestPermissionRationale(permission))
//                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    clickRecordLogic();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void clickRecordLogic(){

        onRecord(mStartRecording);
        if (mStartRecording) {
            recordButton.setText("Stop recording");
        } else {
            recordButton.setText("Start recording");
        }
        mStartRecording = !mStartRecording;

    }

    public void clickPlayDownload(View view){
        onPlayDownload(mStartPlaying);
        if (mStartPlaying) {
            playButtonDownload.setText("Stop playing");
        } else {
            playButtonDownload.setText("Start playing");
        }
        mStartPlaying = !mStartPlaying;
    }

    public void clickDownload(View view){
        File recording = new File(mFileNameDownload);
        try {
            recording.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TransferObserver observer = transferUtility.download(
                "gavelguide2",     /* The bucket to upload to */
                S3KeyString,    /* The key for the uploaded object */
                recording       /* The file where the data to upload exists */
        );
        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d("state", state.toString());
                if(state.toString().equals("COMPLETED")){
                    Toast.makeText(AudioRecordTest.this, "Download Complete", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                uploadProgress.setMax((int) bytesTotal);
                uploadProgress.setProgress((int) bytesCurrent);
            }

            @Override
            public void onError(int id, Exception ex) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void viewPairing(){
        pairing.setRecordingS3Key(S3KeyString);
        Intent intent = new Intent(this, ViewPairing.class);
        intent.putExtra("Pairing", pairing);
        startActivity(intent);
    }

}
