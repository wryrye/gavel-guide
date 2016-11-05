package aperture.science.final_project_umbreon.JSONObjects;

import java.util.HashMap;
import java.util.Map;

public class Member2ID {

    private Integer id;
    private String name;
    private String school;
    private String speaks;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(Integer id) {
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
     * The school
     */
    public String getSchool() {
        return school;
    }

    /**
     *
     * @param school
     * The school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     *
     * @return
     * The speaks
     */
    public String getSpeaks() {
        return speaks;
    }

    /**
     *
     * @param speaks
     * The speaks
     */
    public void setSpeaks(String speaks) {
        this.speaks = speaks;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
