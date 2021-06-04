package uit.edu.vn.wego.adapter;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelItemUser implements Serializable {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String token;
    private String avatar;
    private ArrayList<String> likedPostId;

    public ModelItemUser(String id, String username, String fullName, String email, String token, String avatar, ArrayList<String> likedPostId) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.token = token;
        this.avatar = avatar;
        this.likedPostId = likedPostId;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getAvatar() {
        return avatar;
    }

    public ArrayList<String> getLikedPostId() {
        return likedPostId;
    }

}


