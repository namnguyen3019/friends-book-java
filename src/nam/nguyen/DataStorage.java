package nam.nguyen;

public interface DataStorage {

    // Users

    void createUser(String username, String password, String name, String school);

    User userLogin(String username, String password);

    // // User friend relationship
    // ArrayList<String> getFriends(String username);

    // boolean sendFriendReq(FriendRequest friendRequest);

    // ArrayList<String> showFriendsSuggestionList(String username);

    // Posts
    boolean createPost(Post post);

    // ArrayList<PostModel> getPosts(String username);

    // Message

    // ArrayList<MessageModel> getNewMessages(String username);

}