package aperture.science.final_project_umbreon;

import android.app.Application;

import java.util.ArrayList;

import aperture.science.final_project_umbreon.JSONObjects.Pairing;
import aperture.science.final_project_umbreon.JSONObjects.Result;

/**
 * Created by Brandon on 11/20/2016.
 */
public class PairingArray extends Application {
    private ArrayList<Pairing> pairings;
    private ArrayList<Result> standings;


    public void setUpPairings(ArrayList<Pairing> results){
        pairings = results;
    }
    public void setUpStandings(ArrayList<Result> results){
        standings = results;
    }

    public ArrayList<Result> getStandings(){
        return standings;
    }

    public ArrayList<Pairing> getCurrentRoundPairings(){
        ArrayList<Pairing> current = new ArrayList<>();
        for(int i=0; i< pairings.size(); i++){
            if(!pairings.get(i).getFinished()){
                current.add(pairings.get(i));
            }
        }
        return current;
    }

    public ArrayList<Pairing> getPreviousResults(){
        ArrayList<Pairing> previous = new ArrayList<>();
        for(int i=0; i< pairings.size(); i++){
            if(pairings.get(i).getFinished()){
                previous.add(pairings.get(i));
            }
        }
        return previous;
    }
}
