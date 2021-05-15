package uit.edu.vn.wego.adapter;

import java.util.ArrayList;

public class ModelItemReviewPost {
    private String id;
    private String title;
    private String content;
    private Boolean like;
    private ArrayList<String> comment;
    private ArrayList<String> imgURL;
    public ModelItemReviewPost(String id,String title,String content, Boolean like){
        this.id = id;
        this.title = title;
        this.content = content;
        this.like = like;
//        this.comment = cmt;
//        this.imgURL = imgURL;

    };
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public Boolean getLike()
    {
        return like;
    }
    public void setLike(Boolean like)
    {
        this.like = like;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
}
