package aperture.science.final_project_umbreon.JSONObjects;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Pairing implements Serializable {

    private String id;
    private String recordingS3Key;
    private Result team1ID;
    private Result team2ID;
    private Judge judgeID;
    private Location locationID;
    private Member1ID speaker1ID;
    private Member1ID speaker2ID;
    private Member1ID speaker3ID;
    private Member1ID speaker4ID;
    private String speaker1Score;
    private String speaker2Score;
    private String speaker3Score;
    private String speaker4Score;
    private Boolean finished;

    public String getRecordingS3Key() {
        return recordingS3Key;
    }

    public void setRecordingS3Key(String recordingS3Key) {
        this.recordingS3Key = recordingS3Key;
    }

    public String getSpeaker4Score() {
        return speaker4Score;
    }

    public void setSpeaker4Score(String speaker4Score) {
        this.speaker4Score = speaker4Score;
    }

    public String getSpeaker1Score() {
        return speaker1Score;
    }

    public void setSpeaker1Score(String speaker1Score) {
        this.speaker1Score = speaker1Score;
    }

    public String getSpeaker2Score() {
        return speaker2Score;
    }

    public void setSpeaker2Score(String speaker2Score) {
        this.speaker2Score = speaker2Score;
    }

    public String getSpeaker3Score() {
        return speaker3Score;
    }

    public void setSpeaker3Score(String speaker3Score) {
        this.speaker3Score = speaker3Score;
    }

    public Member1ID getSpeaker2ID() {
        return speaker2ID;
    }

    public void setSpeaker2ID(Member1ID speaker2ID) {
        this.speaker2ID = speaker2ID;
    }

    public Member1ID getSpeaker1ID() {
        return speaker1ID;
    }

    public void setSpeaker1ID(Member1ID speaker1ID) {
        this.speaker1ID = speaker1ID;
    }

    public Member1ID getSpeaker3ID() {
        return speaker3ID;
    }

    public void setSpeaker3ID(Member1ID speaker3ID) {
        this.speaker3ID = speaker3ID;
    }

    public Member1ID getSpeaker4ID() {
        return speaker4ID;
    }

    public void setSpeaker4ID(Member1ID speaker4ID) {
        this.speaker4ID = speaker4ID;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(String roundNumber) {
        this.roundNumber = roundNumber;
    }

    private String roundNumber;

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