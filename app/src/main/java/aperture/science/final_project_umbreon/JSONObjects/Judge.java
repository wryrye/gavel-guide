package aperture.science.final_project_umbreon.JSONObjects;

import java.io.Serializable;

/**
 * Created by Brandon on 11/19/2016.
 */
public class Judge implements Serializable{
    private String id;
    private String name;
    private String code;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
