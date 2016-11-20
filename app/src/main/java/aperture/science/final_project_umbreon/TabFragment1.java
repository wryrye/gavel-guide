package aperture.science.final_project_umbreon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.PairingResult;
import aperture.science.final_project_umbreon.JSONObjects.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private PairingAdapter adapter;
    private View view;
    private ArrayList<Pairing> pairings;
    private MainActivity bigGuy;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        pairings = new ArrayList<Pairing>();
        bigGuy = (MainActivity)getActivity();
//        GavelGuideAPIInterface apiService =
//                GavelGuideAPIClient.getClient().create(GavelGuideAPIInterface.class);
//        Call<PairingResult> call = apiService.pairingCurrentRound();
//        call.enqueue(new Callback<PairingResult>() {
//            @Override
//            public void onResponse(Call<PairingResult> call, Response<PairingResult> response) {
//                PairingResult result = response.body();
//                Log.d("results", result.toString());
//                for(Pairing i : result.getResults()){
//                    pairings.add(i);
//                }
//                Log.d("After CurrentRoundCall", pairings.size() + "");
//
//            }
//            @Override
//            public void onFailure(Call<PairingResult> call, Throwable t) {
//                // Log error here since request failed
//                Log.e("GavelGuide", t.toString());
//            }
//        });
        pairings = bigGuy.currentRound;
        //Log.d("After CurrentRoundCall", pairings.size() + "");

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pairings = new ArrayList<Pairing>();
        bigGuy = (MainActivity)getActivity();
        pairings = bigGuy.getCurrentRoundPairings();
        view =  inflater.inflate(R.layout.tab_fragment_1, container, false);
        rvTab1 = (RecyclerView) view.findViewById(R.id.rvTab1);
        //Log.d("In TabFragment1", pairings.toString());
        // Create adapter passing in the sample user data (only first time)
        if(adapter == null) {
            Log.d("Pairings", pairings.toString());
            adapter = new PairingAdapter(this, pairings);
        }
        // Attach the adapter to the recyclerview to populate items
        rvTab1.setAdapter(adapter);
        // Set layout manager to position the items

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        return view;
    }

    public View setUpRecyclerView(){
        if(adapter == null) {
            adapter = new PairingAdapter(this, pairings);
        }
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

    public void updateData(){
        pairings = bigGuy.currentRound;
        adapter.notifyDataSetChanged();
        Log.d("yooo", "hello");


    }

    public RecyclerView getRV(){
        return rvTab1;
    }

    @Override
    public String toString() {
        String test ="";
        try{
            test = pairings.toString();
        }catch(NullPointerException e){
            Log.d("GavelGuide", "Not ready!");
        }
        return test;
    }


}
