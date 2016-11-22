package aperture.science.final_project_umbreon;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by sherriff on 10/25/16.
 */

public class GavelGuideAPIClient {
    public static final String BASE_URL = "http://ec2-54-145-200-199.compute-1.amazonaws.com:1234/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getRequestHeader() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(4, TimeUnit.SECONDS)
                .build();


        return httpClient;
    }

}