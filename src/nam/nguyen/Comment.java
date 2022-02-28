package nam.nguyen;

import java.sql.Timestamp;

public class Comment {

    private int comment_id;
    private int post_id;
    private String owner_username;
    private String content;
    private Timestamp created_at;

    public Comment(int comment_id, int post_id, String owner_username, String content, Timestamp created_at) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.owner_username = owner_username;
        this.content = content;
        this.created_at = created_at;
    }

    public Comment(int post_id, String owner_username, String content) {
        this.post_id = post_id;
        this.owner_username = owner_username;
        this.content = content;
    }

    public int getComment_id() {
        return comment_id;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public int getPost_id() {
        return post_id;
    }

}
