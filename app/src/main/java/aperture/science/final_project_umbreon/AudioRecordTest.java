package aperture.science.final_project_umbreon;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.S3Key;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AudioRecordTest extends Activity
{


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    private static String mFileNameDownload = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;

    private UploadButton uploadButton = null;
    private DownloadButton downloadButton = null;
    private PlayButtonDownload playButtonDownload = null;

    private TransferUtility transferUtility;

    private String id;
    private boolean s3key;
    private String S3KeyString;

    private void onRecord(boolean start) {
        if (start) {
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

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    class PlayButtonDownload extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlayDownload(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButtonDownload(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    class UploadButton extends Button {

    OnClickListener clicker = new OnClickListener() {
        public void onClick(View v) {
            File recording = new File(mFileName);

            TransferObserver observer = transferUtility.upload(
                    "gavelguide2",     /* The bucket to upload to */
                    S3KeyString,    /* The key for the uploaded object */
                    recording       /* The file where the data to upload exists */
            );

            GavelGuideAPIInterface apiService =
                    GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);
            Log.e("Test1", "1");
            S3Key key = new S3Key(id, S3KeyString);
            Call<String> call = apiService.addS3Key(key);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


//
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

            ((PairingArray) getApplication()).updatePairingS3Key(id, S3KeyString);
        }
    };

    public UploadButton(Context ctx) {
        super(ctx);
        setText("Upload to S3");
        setOnClickListener(clicker);
    }
    }

    class DownloadButton extends Button {

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
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


            }
        };

        public DownloadButton(Context ctx) {
            super(ctx);
            setText("Download from S3");
            setOnClickListener(clicker);
        }
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
        id = intent.getStringExtra("id");
        S3KeyString = id + "Recording";
        Log.d("s3KeyString", S3KeyString);
        s3key = intent.getBooleanExtra("S3Key",true);
        Log.d("s3Key", s3key + "");
        if(s3key) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            //ll.setGravity(LinearLayout.TEXT_ALIGNMENT_CENTER);
            downloadButton = new DownloadButton(this);
            ll.addView(downloadButton,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
            playButtonDownload = new PlayButtonDownload(this);
            ll.addView(playButtonDownload,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
            setContentView(ll);
        } else {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            //ll.setGravity(LinearLayout.TEXT_ALIGNMENT_CENTER);
            mRecordButton = new RecordButton(this);
            ll.addView(mRecordButton,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
            mPlayButton = new PlayButton(this);
            ll.addView(mPlayButton,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
            uploadButton = new UploadButton(this);
            ll.addView(uploadButton,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
            setContentView(ll);
        }

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
}
