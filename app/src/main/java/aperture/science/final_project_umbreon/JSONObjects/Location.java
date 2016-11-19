package aperture.science.final_project_umbreon.JSONObjects;

import java.io.Serializable;

/**
 * Created by Brandon on 11/19/2016.
 */
public class Location implements Serializable {
    private String id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
