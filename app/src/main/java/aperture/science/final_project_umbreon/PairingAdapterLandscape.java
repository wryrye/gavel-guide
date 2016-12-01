package aperture.science.final_project_umbreon;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;

/**
 * Created by Brandon on 11/19/2016.
 */
public class PairingAdapterLandscape extends
        RecyclerView.Adapter<PairingAdapterLandscape.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView roundTextView;
        public TextView team1TextView;
        public TextView team2TextView;
        public TextView judgeTextView;
        public TextView locationTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            roundTextView = (TextView) itemView.findViewById(R.id.round_number);
            team1TextView = (TextView) itemView.findViewById(R.id.team1_name);
            team2TextView = (TextView) itemView.findViewById(R.id.team2_name);
            judgeTextView = (TextView) itemView.findViewById(R.id.judge_name);
            locationTextView = (TextView) itemView.findViewById(R.id.location_name);


        }
    }

    // Store a member variable for the contacts
    private ArrayList<Pairing> pairingData;
    // Store the context for easy access
    private Fragment mContext;

    // Pass in the contact array into the constructor
    public PairingAdapterLandscape(Fragment context, ArrayList<Pairing> list) {
        pairingData = list;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Fragment getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public PairingAdapterLandscape.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View standingsView = inflater.inflate(R.layout.item_pairing_landscape, parent, false);

//        Log.d("GLaDOS", "hello!");

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(standingsView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(PairingAdapterLandscape.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Pairing resulty = pairingData.get(position);

         //Set item views based on your views and data model
        TextView textView = viewHolder.roundTextView;
        textView.setText(resulty.getRoundNumber());

        textView.setTag(R.string.unique,resulty.getId());

        textView.setClickable(true);
        textView.setActivated(true);
        textView.setEnabled(true);
        TextView winView = viewHolder.team1TextView;
        winView.setText(resulty.getTeam1ID().getName());
        TextView lossView = viewHolder.team2TextView;
        lossView.setText(resulty.getTeam2ID().getName());

        TextView judgeView = viewHolder.judgeTextView;
        judgeView.setText(resulty.getJudgeID().getName());

        TextView locationView = viewHolder.locationTextView;
        locationView.setText(resulty.getLocationID().getName());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return pairingData.size();
    }

    public ArrayList<Pairing> getStandingsData(){
        return pairingData;
    }
}
