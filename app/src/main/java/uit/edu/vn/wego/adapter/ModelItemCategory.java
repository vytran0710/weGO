package uit.edu.vn.wego.adapter;

import java.io.Serializable;

public class ModelItemCategory implements Serializable {
    private String name;
    private String imgURL;
    private String tag;

    public ModelItemCategory(String name, String imgURL, String tag) {
        this.name = name;
        this.imgURL = imgURL;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getTag() {
        return tag;
    }
}
