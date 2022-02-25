package nam.nguyen;

import java.util.ArrayList;

public class User {

    // User attributes
    private String username;
    private String password;
    private String name;
    private String school;

    public User(String username, String name, String school) {
        this.username = username;
        this.name = name;
        this.school = school;
    }

    public User(String username, String password, String name, String school) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.school = school;
    }

    public boolean createPost(Post post, DataStorage d) {
        return d.createPost(post);
    }

    public Post getPostById(int post_id, DataStorage d) {
        return d.getPostById(post_id, username);
    }

    public ArrayList<Post> getPosts(DataStorage d) {
        return d.getPosts(username);
    }

    public boolean updatePost(Post new_post, DataStorage d) {
        return d.updatePost(new_post);
    }

    public boolean deletePost(int post_id, String username, DataStorage d) {
        return d.deletePost(post_id, username = this.getUsername());
    }

    // public ArrayList<String> getFriends(String username) {
    // return data.getFriends(username);
    // }

    // public FriendRequest sendFriendReq(String sender, String receiver) {
    // FriendRequest friendRequest = new FriendRequest(sender, receiver);

    // if (data.sendFriendReq(friendRequest)) {
    // return friendRequest;
    // } else {
    // return null;
    // }

    // }

    // public void printFriends(ArrayList<String> userFriends) {
    // for (String friend : userFriends) {
    // System.out.println(friend);
    // }
    // }

    // public void showFriendsSuggestionList(String username) {
    // data.showFriendsSuggestionList(username);
    // }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUsername(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getUsername() {
        return username;
    }
}
