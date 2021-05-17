package uit.edu.vn.wego.adapter;

import java.util.ArrayList;

public class ModelItemReviewPost {
    private String id;
    private String tag;
    private String title;
    private String content;
    private int like;
    private ArrayList<String> comment;
    private ArrayList<String> imgURL;

    public ModelItemReviewPost(String id, String tag, String title, String content, int like, ArrayList<String> comment, ArrayList<String> imgURL) {
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.content = content;
        this.like = like;
        this.comment = comment;
        this.imgURL = imgURL;
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
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
}
