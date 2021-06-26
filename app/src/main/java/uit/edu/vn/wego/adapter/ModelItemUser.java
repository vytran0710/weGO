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

    public void setLikedPostId(ArrayList<String> likedPostId) {
        this.likedPostId = likedPostId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}


