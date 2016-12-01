package aperture.science.final_project_umbreon;

/**
 * Created by ryan on 9/8/16.
 */


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Result;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class StandingAdapterLandscape extends
        RecyclerView.Adapter<StandingAdapterLandscape.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView winTextView;
        public TextView lossTextView;
        public TextView member1TextView;
        public TextView member2TextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.pairing_name);
            winTextView = (TextView) itemView.findViewById(R.id.wins);
            lossTextView = (TextView) itemView.findViewById(R.id.losses);
            member1TextView = (TextView) itemView.findViewById(R.id.member1Name);
            member2TextView = (TextView) itemView.findViewById(R.id.member2Name);
        }
    }

    // Store a member variable for the contacts
    private ArrayList<Result> standingsData;
    // Store the context for easy access
    private Fragment mContext;

    // Pass in the contact array into the constructor
    public StandingAdapterLandscape(Fragment context, ArrayList<Result> list) {
        standingsData = list;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Fragment getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public StandingAdapterLandscape.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View standingsView = inflater.inflate(R.layout.item_standing_landscape, parent, false);

//        Log.d("GLaDOS", "hello!");

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(standingsView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(StandingAdapterLandscape.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Result resulty = standingsData.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(resulty.getName());

        textView.setClickable(true);
        textView.setActivated(true);
        textView.setEnabled(true);
        TextView winView = viewHolder.winTextView;
        winView.setText(resulty.getWins());
        TextView lossView = viewHolder.lossTextView;
        lossView.setText(resulty.getLosses());
        TextView member1View = viewHolder.member1TextView;
        member1View.setText(resulty.getMember1ID().getName());
        TextView member2View = viewHolder.member2TextView;
        member2View.setText(resulty.getMember2ID().getName());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return standingsData == null ? 0 : standingsData.size();
    }

    public ArrayList<Result> getStandingsData(){
        return standingsData;
    }
}