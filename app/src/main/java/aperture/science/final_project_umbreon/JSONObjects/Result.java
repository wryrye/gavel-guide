package aperture.science.final_project_umbreon.JSONObjects;

import java.util.HashMap;
import java.util.Map;

public class Result {

    private String id;
    private String name;
    private Member1ID member1ID;
    private Member2ID member2ID;
    private String wins;
    private String losses;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The member1ID
     */
    public Member1ID getMember1ID() {
        return member1ID;
    }

    /**
     *
     * @param member1ID
     * The member1ID
     */
    public void setMember1ID(Member1ID member1ID) {
        this.member1ID = member1ID;
    }

    /**
     *
     * @return
     * The member2ID
     */
    public Member2ID getMember2ID() {
        return member2ID;
    }

    /**
     *
     * @param member2ID
     * The member2ID
     */
    public void setMember2ID(Member2ID member2ID) {
        this.member2ID = member2ID;
    }

    /**
     *
     * @return
     * The wins
     */
    public String getWins() {
        return wins;
    }

    /**
     *
     * @param wins
     * The wins
     */
    public void setWins(String wins) {
        this.wins = wins;
    }

    /**
     *
     * @return
     * The losses
     */
    public String getLosses() {
        return losses;
    }

    /**
     *
     * @param losses
     * The losses
     */
    public void setLosses(String losses) {
        this.losses = losses;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
