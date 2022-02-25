package nam.nguyen;

import java.util.ArrayList;

public interface DataStorage {

    // Users

    boolean createUser(String username, String password, String name, String school);

    User userLogin(String username, String password);

    // // User friend relationship
    // ArrayList<String> getFriends(String username);

    // boolean sendFriendReq(FriendRequest friendRequest);

    // ArrayList<String> showFriendsSuggestionList(String username);

    // Posts
    boolean createPost(Post post);

    ArrayList<Post> getPosts(String username);

    Post getPostById(int post_id, String owner_id);

    boolean updatePost(Post post);

    boolean deletePost(int post_id, String owner_id);

    // ArrayList<PostModel> getPosts(String username);

    // Message

    // ArrayList<MessageModel> getNewMessages(String username);

}