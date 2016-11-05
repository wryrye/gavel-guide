package aperture.science.final_project_umbreon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends Fragment {

    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;

    private EditText nameField;
    private RecyclerView rvTab1;
    private View view;
    private ArrayList<String> sampleData;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sampleData = new ArrayList<String>();
        callAPI();

        view =  inflater.inflate(R.layout.tab_fragment_1, container, false);
        rvTab1 = (RecyclerView) view.findViewById(R.id.rvTab1);

        sampleData.add("A");


        // Create adapter passing in the sample user data
        PairingsAdapter adapter = new PairingsAdapter(this, sampleData);
        // Attach the adapter to the recyclerview to populate items
        rvTab1.setAdapter(adapter);
        // Set layout manager to position the items


        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);



        return view;
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (rvTab1.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) rvTab1.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        rvTab1.setLayoutManager(mLayoutManager);
        rvTab1.scrollToPosition(scrollPosition);
    }

    public RecyclerView getRV(){
        return rvTab1;
    }

    public void callAPI() {

        GavelGuideAPIInterface apiService =
                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);


        Call<Pairing> call = apiService.nasaList("apod");
        call.enqueue(new Callback<Pairing>() {
            @Override
            public void onResponse(Call<Pairing> call, Response<Pairing> response) {
                Pairing pairing = response.body();
                sampleData.add(pairing.getCopyright());

            }

            @Override
            public void onFailure(Call<Pairing> call, Throwable t) {
                // Log error here since request failed
                Log.e("GabelGuide", t.toString());
            }
        });

    }
}
