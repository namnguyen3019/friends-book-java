package nam.nguyen;

import java.util.ArrayList;

public interface DataStorage {

    // User

    boolean createUser(String username, String password, String name, String school);

    User userLogin(String username, String password);

    boolean updateProfile(User updatedUser);

    // User friends relationship
    ArrayList<String> getFriendList(User user);

    User getFriendByUsername(User user, String friend_username);

    boolean isFriend(String username1, String username2);

    ArrayList<FriendRequest> getFriendRequestList(User user);

    boolean acceptFriendReq(User user, int friend_req_id);

    boolean denyFriendReq(User user, int friend_req_id);

    boolean sendFriendReq(FriendRequest friendRequest);

    FriendRequest getFriendReqById(User user, int id);

    boolean updateReadFriendRequest(User user, FriendRequest friendRequest);

    // Posts and Comment
    boolean createPost(Post post);

    ArrayList<Post> getPosts(String username);

    Post getPostById(int post_id, String owner_id);

    boolean updatePost(Post post);

    boolean deletePost(int post_id, String owner_id);

    boolean addNewComment(User user, Comment newComment);
    // Messages

    Message getMessageById(User user, int messageId);

    boolean updateReadMessage(User user, int messageId);

    ArrayList<Message> getNewMessages(User user);

    ArrayList<Message> getConversationFromAFriend(User user, String user_friend);

    boolean sendMessage(User username, String receiver, String messageContent);

    // Notifications
    ArrayList<Notification> getNotifications(User user);

    boolean insertNotification(Notification notice);

    boolean deleteNotification(int id);
}