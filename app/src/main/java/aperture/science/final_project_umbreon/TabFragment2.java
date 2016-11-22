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

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;

public class TabFragment2 extends Fragment {

    private static final int SPAN_COUNT = 2;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;


    private RecyclerView rvTab2;
    private PairingAdapter adapter;
    private View view;
    private ArrayList<Pairing> pairings;
    private MainActivity bigGuy;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.d("TF2", pairings+"");

        //Log.d("After CurrentRoundCall", pairings.size() + "");

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        pairings = new ArrayList<Pairing>();
//        bigGuy = (MainActivity)getActivity();
//        pairings = bigGuy.getCurrentRoundPairings();
        view =  inflater.inflate(R.layout.tab_fragment_2, container, false);
        Log.d("View", view.toString());
        rvTab2 = (RecyclerView) view.findViewById(R.id.rvTabPreviousResult);
        Log.d("rvTab2", rvTab2.toString());

        pairings = ((PairingArray) getActivity().getApplication()).getPreviousResults();

        Log.d("TF2 aft", pairings+"");
        //Log.d("In TabFragment1", pairings.toString());
        // Create adapter passing in the sample user data (only first time)
        if(adapter == null) {
            Log.d("Pairings", pairings.toString());
            adapter = new PairingAdapter(this, pairings);
        }
        // Attach the adapter to the recyclerview to populate items
        rvTab2.setAdapter(adapter);
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
        rvTab2.setAdapter(adapter);
        // Set layout manager to position the items

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        return view;
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (rvTab2.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) rvTab2.getLayoutManager())
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

        rvTab2.setLayoutManager(mLayoutManager);
        rvTab2.scrollToPosition(scrollPosition);
    }

    public void updateData(){
        pairings = bigGuy.currentRound;
        adapter.notifyDataSetChanged();
        Log.d("yooo", "hello");


    }

    public RecyclerView getRV(){
        return rvTab2;
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
