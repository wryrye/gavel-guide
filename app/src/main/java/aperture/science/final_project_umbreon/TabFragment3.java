package aperture.science.final_project_umbreon;

import android.content.res.Configuration;
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

import aperture.science.final_project_umbreon.JSONObjects.Result;

import java.util.ArrayList;

public class TabFragment3 extends Fragment {

    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;

    private EditText nameField;
    private RecyclerView rvTab3;
    private StandingAdapter adapter;
    private StandingAdapterLandscape adapterLandscape;
    private View view;
    private ArrayList<Result> standings;
    private MainActivity bigGuy;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//        standings = new ArrayList<Result>();
//        bigGuy = (MainActivity)getActivity();
//        standings = bigGuy.standings;
//        Log.d("TF3100yrs",standings+"");

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            view =  inflater.inflate(R.layout.tab_fragment_3, container, false);
        } else {
            view =  inflater.inflate(R.layout.tab_fragment_3_lanscape, container, false);
        }
        rvTab3 = (RecyclerView) view.findViewById(R.id.rvTab3);
        rvTab3.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        standings = ((PairingArray) getActivity().getApplication()).getStandings();


//        bigGuy = (MainActivity)getActivity();
//        standings = bigGuy.standings;
//        Log.d("Data TF3", standings+ "");
        // Create adapter passing in the sample user data (only first time)
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if(adapter == null) {
//            Log.d("Data TF3", standings+ "");
                adapter = new StandingAdapter(this, standings);
            }
            // Attach the adapter to the recyclerview to populate items
            rvTab3.setAdapter(adapter);
        } else {
            if(adapterLandscape == null) {
//            Log.d("Data TF3", standings+ "");
                adapterLandscape = new StandingAdapterLandscape(this, standings);
            }
            // Attach the adapter to the recyclerview to populate items
            rvTab3.setAdapter(adapterLandscape);
        }

        // Set layout manager to position the items

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        return view;
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (rvTab3.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) rvTab3.getLayoutManager())
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

        rvTab3.setLayoutManager(mLayoutManager);
        rvTab3.scrollToPosition(scrollPosition);
    }

    public void updateData(){
        standings = bigGuy.standings;
        adapter.notifyDataSetChanged();
        Log.d("yooo", "hello");


    }

    public RecyclerView getRV(){
        return rvTab3;
    }

    @Override
    public String toString() {
        String test ="";
        try{
            test = standings.toString();
        }catch(NullPointerException e){
            Log.d("GavelGuide", "Not ready!");
        }
        return test;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }
}
