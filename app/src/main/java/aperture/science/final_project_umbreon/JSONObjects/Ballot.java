package aperture.science.final_project_umbreon.JSONObjects;

/**
 * Created by Brandon on 11/27/2016.
 */
public class Ballot {
    private String _id;
    private String winningTeam;
    private String speaker1Score;
    private String speaker2Score;
    private String speaker3Score;
    private String speaker4Score;

    public Ballot(String id, String winner, String speak1, String speak2, String speak3, String speak4){
        this._id = id;
        this.winningTeam = winner;
        this.speaker1Score = speak1;
        this.speaker2Score = speak2;
        this.speaker3Score = speak3;
        this.speaker4Score = speak4;
    }
}
