package aperture.science.final_project_umbreon;

/**
 * Created by ryan on 11/4/16.
 */
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sherriff on 10/25/16.
 */

public interface GavelGuideAPIInterface {

    @GET("{apod}?api_key=KKiuWgOeJfiN3co7VWAleI3UHylnXTdYDDyHnXLb")
    Call<Pairing> nasaList(@Path("apod") String apod);

}