package nam.nguyen;

import java.util.ArrayList;

public interface DataStorage {

    // Users

    boolean createUser(String username, String password, String name, String school);

    User userLogin(String username, String password);

    // User friends relationship
    ArrayList<String> getFriendList(User user);

    User getFriendByUsername(User user, String friend_username);

    ArrayList<FriendRequest> getFriendRequestList(User user);

    boolean acceptFriendReq(User user, int friend_req_id);

    boolean denyFriendReq(User user, int friend_req_id);

    boolean sendFriendReq(FriendRequest friendRequest);

    ArrayList<String> friendSuggestionList(User user);

    // Posts
    boolean createPost(Post post);

    ArrayList<Post> getPosts(String username);

    Post getPostById(int post_id, String owner_id);

    boolean updatePost(Post post);

    boolean deletePost(int post_id, String owner_id);

    // Messages

    ArrayList<Message> getNewMessages(User user);

    ArrayList<Message> getConversationFromAFriend(User user, String user_friend);

    boolean sendMessage(User username, String receiver, String messageContent);

}