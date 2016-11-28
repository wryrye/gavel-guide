package aperture.science.final_project_umbreon;

import android.app.Application;
import android.util.Log;

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

    public void updatePairingResult(String id, String winner, String speaker1, String speaker2, String speaker3, String speaker4){
        for(int i=0; i < pairings.size(); i++){
            Pairing pairing = pairings.get(i);
            Log.d("pairingId", pairing.toString());
            if(pairing.getId().equals(id)){
                pairing.setFinished(true);
                pairing.setWinningTeam(winner);
                pairing.setSpeaker1Score(speaker1);
                pairing.setSpeaker2Score(speaker2);
                pairing.setSpeaker3Score(speaker3);
                pairing.setSpeaker4Score(speaker4);

                Result team1 = pairing.getTeam1ID();
                for(int j=0; j < standings.size(); j++){
                    if(standings.get(j).getId().equals(team1.getId())){
                        if(winner.equals("1")){
                            standings.get(j).setWins((Integer.parseInt(standings.get(j).getWins())+1) + "");
                        } else {
                            standings.get(j).setLosses((Integer.parseInt(standings.get(j).getLosses())+1) + "");
                        }
                        break;
                    }
                }
                Result team2 = pairing.getTeam2ID();
                for(int k=0; k < standings.size(); k++){
                    if(standings.get(k).getId().equals(team2.getId())){
                        if(winner.equals("1")){
                            standings.get(k).setLosses((Integer.parseInt(standings.get(k).getLosses())+1) + "");
                        } else {
                            standings.get(k).setWins((Integer.parseInt(standings.get(k).getWins())+1) + "");
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    public void updatePairingS3Key(String id, String S3Key) {
        for(int i=0; i < pairings.size(); i++){
            if(pairings.get(i).getId().equals(id)){
                pairings.get(i).setRecordingS3Key(S3Key);
                break;
            }
        }
    }

    public Pairing findPairing(String team1, String team2){
        for(int i=0; i < pairings.size(); i++){
            if(pairings.get(i).getTeam1ID().getName().equals(team1)){
                if(pairings.get(i).getTeam2ID().getName().equals(team2)){
                    return pairings.get(i);
                }
            }
        }
        return null;
    }
}
