package aperture.science.final_project_umbreon.JSONObjects;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Pairing implements Serializable {


    private Result team1ID;
    private Result team2ID;
    private Judge judgeID;
    private Location locationID;

    public Judge getJudgeID() {
        return judgeID;
    }

    public void setJudgeID(Judge judgeID) {
        this.judgeID = judgeID;
    }

    public Result getTeam1ID() {
        return team1ID;
    }

    public void setTeam1ID(Result team1ID) {
        this.team1ID = team1ID;
    }

    public Result getTeam2ID() {
        return team2ID;
    }

    public void setTeam2ID(Result team2ID) {
        this.team2ID = team2ID;
    }

    public Location getLocationID() {
        return locationID;
    }

    public void setLocationID(Location locationID) {
        this.locationID = locationID;
    }

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();



    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}