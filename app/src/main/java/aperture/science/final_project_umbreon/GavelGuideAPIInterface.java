package aperture.science.final_project_umbreon;

/**
 * Created by ryan on 11/4/16.
 */

import aperture.science.final_project_umbreon.JSONObjects.Ballot;
import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.S3Key;
import aperture.science.final_project_umbreon.JSONObjects.Standings;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by sherriff on 10/25/16.
 */

public interface GavelGuideAPIInterface {

    @GET("{getRankedTeamsJoin}")
    Call<Standings> standingsList(@Path("getRankedTeamsJoin") String getRankedTeamsJoin);

//    @GET("getPairing/{id}")
//    Call<PairingResult> pairing(@Path("id") String pairingId);

//    @GET("getCurrentRoundPairings")
//    Call<PairingResult> pairingCurrentRound();

    @GET("getAllPairings")
    Call<PairingResult> pairingAll();

    @POST("addS3Key")
    Call<String> addS3Key(@Body S3Key key);

    @POST("submitDecision")
    Call<String> submitDecision(@Body Ballot ballot);






}