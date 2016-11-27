package aperture.science.final_project_umbreon.JSONObjects;

/**
 * Created by Brandon on 11/26/2016.
 */
public class S3Key {
    private String _id;
    private String recordingS3Key;

    public S3Key(String id, String key){
        this._id = id;
        this.recordingS3Key = key;
    }
}
