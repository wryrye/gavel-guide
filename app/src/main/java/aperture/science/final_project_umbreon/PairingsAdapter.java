package aperture.science.final_project_umbreon;

/**
 * Created by ryan on 9/8/16.
 */


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class PairingsAdapter extends
        RecyclerView.Adapter<PairingsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.pairing_name);

        }
    }

    // Store a member variable for the contacts
    private ArrayList<String> pairingsData;
    // Store the context for easy access
    private Fragment mContext;

    // Pass in the contact array into the constructor
    public PairingsAdapter(Fragment context, ArrayList<String> list) {
        pairingsData = list;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Fragment getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public PairingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View pairingsView = inflater.inflate(R.layout.item_pairing, parent, false);

//        Log.d("GLaDOS", "hello!");

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(pairingsView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(PairingsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String stringy = pairingsData.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(stringy);

        textView.setClickable(true);
        textView.setActivated(true);
        textView.setEnabled(true);



    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return pairingsData.size();
    }

    public ArrayList<String> getPairings(){
        return pairingsData;
    }
}