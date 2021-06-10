package uit.edu.vn.wego.adapter;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelItemReviewPost implements Serializable {
    private String id;
    private String update_date;
    private String tag;
    private String title;
    private String locationURL;
    private String content;
    private int like;
    private ArrayList<String> comment;
    private ArrayList<String> imgURL;

    public ModelItemReviewPost(String id, String update_date, String tag, String title, String locationURL, String content, int like, ArrayList<String> comment, ArrayList<String> imgURL) {
        this.id = id;
        this.update_date = update_date;
        this.tag = tag;
        this.title = title;
        this.locationURL = locationURL;
        this.content = content;
        this.like = like;
        this.comment = comment;
        this.imgURL = imgURL;
    }

    public String getId() {
        return id;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getLocationURL() {
        return locationURL;
    }

    public String getContent() {
        return content;
    }

    public int getLike() {
        return like;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public ArrayList<String> getImgURL() {
        return imgURL;
    }

    public void setComment(ArrayList<String> comment) {
        this.comment = comment;
    }
}
