package nam.nguyen;

import java.sql.Timestamp;

public class Post {

    private int post_id;
    private String content;
    private String owner_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Post(int post_id, String content, String owner_id, Timestamp created_at, Timestamp updated_at) {
        this.post_id = post_id;
        this.content = content;
        this.owner_id = owner_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Post(String content, String username) {
        this.content = content;
        this.owner_id = username;
    }

    public Post(int post_id2, String new_content, String owner_id) {
        this.post_id = post_id2;
        this.content = new_content;
        this.owner_id = owner_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }
}

// public void createPost(String username) {

// Scanner input = new Scanner(System.in);
// String content = "";

// while (content.length() == 0) {
// System.out.println("Enter post content");
// content = input.nextLine();
// }

// data.createPost(username, content);
// }

// public ArrayList<PostModel> getPosts(String username) {
// return data.getPosts(username);
// }