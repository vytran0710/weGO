package uit.edu.vn.wego.model;

public class NewAvatarData {
    private final String message;
    private final String avatar;

    public NewAvatarData(String message, String avatar) {
        this.message = message;
        this.avatar = avatar;
    }
    public String getMessage() {
        return message;
    }
    public String getAvatar() {
        return avatar;
    }
}
