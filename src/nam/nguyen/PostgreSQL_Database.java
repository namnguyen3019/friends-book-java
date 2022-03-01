package nam.nguyen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PostgreSQL_Database implements DataStorage {

    final String url = "jdbc:postgresql://localhost:5432/friendsbook";
    final String db_user = "namnguyen";
    final String password = "123456";
    Connection conn = null;
    Statement statement = null;
    ResultSet rs = null;

    /*
     * User functions: createAuser, userLogin
     * Post: CRUD a post
     */
    @Override
    public boolean createUser(String username, String password, String name, String school) {
        boolean isUserCreated = false;
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();
            conn.setAutoCommit(false);
            String queryUser = "SELECT 1 FROM users WHERE username = '" + username.toLowerCase() + "'";

            rs = statement.executeQuery(queryUser);
            if (rs.next()) {
                System.out.println("user already exists");
                return isUserCreated;
            }
            int insertResult = statement.executeUpdate("INSERT INTO users(username, password, name, school) VALUES ( '"
                    + username + "' , '" + password + "', '" + name + "', '" + school + "')");
            if (insertResult == 1) {
                conn.commit();
                conn.setAutoCommit(true);
                isUserCreated = true;
                return isUserCreated;
            } else {
                return isUserCreated;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return isUserCreated;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User userLogin(String username, String password) {
        User userInfo = null;
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();
            String query = "SELECT username, name, school from users WHERE users.username = '" + username
                    + "' AND users.password = '" + password + "' LIMIT 1";

            rs = statement.executeQuery(query);
            if (rs.next()) {
                username = rs.getString("username");
                String name = rs.getString("name");
                String school = rs.getString("school");
                userInfo = new User(username, name, school);
                return userInfo;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateProfile(User updatedUser) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String updateQuery = "UPDATE users set name = '" + updatedUser.getName() + "', school = '"
                    + updatedUser.getSchool() + "' WHERE username = '" + updatedUser.getUsername() + "'";

            int rs = statement.executeUpdate(updateQuery);
            if (rs == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }

    public boolean createPost(Post post) {
        boolean postCreated = false;
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "INSERT INTO posts(owner_id, content) VALUES('" + post.getOwner_id() + "', '"
                    + post.getContent() + "')";
            int result = statement.executeUpdate(query);
            if (result == 1) {
                System.out.println("New post created");
                postCreated = true;
                return postCreated;
            } else {
                return postCreated;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return postCreated;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Post getPostById(int post_id, String username) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "Select * FROM posts WHERE post_id = '" + post_id
                    + "' and owner_id = '" + username + "'";

            rs = statement.executeQuery(query);
            if (rs.next()) {
                int postId = rs.getInt(1);
                String content = rs.getString(2);
                String owner_id = rs.getString(3);
                Timestamp created_at = rs.getTimestamp(4);
                Timestamp updated_at = rs.getTimestamp(5);
                Post Post = new Post(postId, content, owner_id, created_at, updated_at);
                return Post;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Post> getPosts(String username) {
        ArrayList<Post> currentPosts = new ArrayList<Post>();

        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "SELECT * FROM posts WHERE owner_id = '" + username + "' LIMIT 5";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Integer post_id = rs.getInt(1);
                String content = rs.getString(2);
                String owner_id = rs.getString(3);
                Timestamp created_at = rs.getTimestamp(4);
                Timestamp updated_at = rs.getTimestamp(5);
                Post post = new Post(post_id, content, owner_id, created_at, updated_at);
                currentPosts.add(post);
            }

            return currentPosts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updatePost(Post post) {
        boolean isUpdated = false;
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "UPDATE posts SET content = '" + post.getContent()
                    + "', updated_at = CURRENT_TIMESTAMP WHERE post_id = '" + post.getPost_id() + "' AND owner_id = '"
                    + post.getOwner_id() + "'";

            int result = statement.executeUpdate(query);
            if (result == 1) {
                System.out.println("updated a post");
                isUpdated = true;
                return isUpdated;
            } else {
                System.out.println("You can't update this post or post not found");
                return isUpdated;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deletePost(int post_id, String owner_id) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "DELETE FROM posts WHERE post_id = '" + post_id + "' AND owner_id = '" + owner_id + "'";

            int result = statement.executeUpdate(query);
            if (result == 1) {
                System.out.println("Delete successfully");
                return true;
            } else {
                System.out.println("Failed to delete this post");
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * User friends: get friend list, get a friend's infor, get friend request..
     * Accept or deny a friend request
     */
    @Override
    public ArrayList<String> getFriendList(User curUser) {
        ArrayList<String> friendList = new ArrayList<String>();
        String username = curUser.getUsername();
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();
            String query = "SELECT req_sender, req_receiver FROM user_friends WHERE (req_sender = '" + username
                    + "' or req_receiver = '" + username + "') AND status = 'approved'";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String user_1 = rs.getString(1);
                String user_2 = rs.getString(2);
                if (user_1.equals(username)) {
                    friendList.add(user_2);
                } else {
                    friendList.add(user_1);
                }
            }
            return friendList;
        } catch (Exception e) {
            e.printStackTrace();
            return friendList;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User getFriendByUsername(User curUser, String friend_username) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();
            ArrayList<String> friendList = getFriendList(curUser);
            String friendListString = "";
            for (String username : friendList) {
                friendListString += "'" + username + "',";
            }
            String s = friendListString.substring(0, friendListString.length() - 1);
            String query = "SELECT * FROM users WHERE username = '" + friend_username + "' and username in(" + s + " )";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                String username = rs.getString(1);
                String name = rs.getString(2);
                String school = rs.getString(3);
                User friend = new User(username, name, school);
                return friend;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<FriendRequest> getFriendRequestList(User curUser) {

        ArrayList<FriendRequest> friendRequestsList = new ArrayList<FriendRequest>();
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "SELECT * FROM user_friends WHERE req_receiver = '" + curUser.getUsername()
                    + "' AND status = 'pending'";

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int user_friends_id = rs.getInt(1);
                String sender = rs.getString(2);
                String receiver = rs.getString(3);
                String status = rs.getString(4);
                String note = rs.getString(5);
                boolean isSeen = rs.getBoolean(6);
                Timestamp created_at = rs.getTimestamp(7);
                Timestamp updated_at = rs.getTimestamp(8);

                FriendRequest friendRequest = new FriendRequest(user_friends_id, sender, receiver, status, note, isSeen,
                        created_at,
                        updated_at);

                friendRequestsList.add(friendRequest);
            }

            return friendRequestsList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean acceptFriendReq(User user, int friend_req_id) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "Update user_friends set status = 'approved', isSeen = true WHERE user_friends_id = '"
                    + friend_req_id + "'";

            int result = statement.executeUpdate(query);

            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean denyFriendReq(User user, int friend_req_id) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "Update user_friends set status = 'denied', isSeen = true WHERE user_friends_id = '"
                    + friend_req_id + "'";

            int result = statement.executeUpdate(query);

            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> friendSuggestionList(User curUser) {
        ArrayList<String> friendSuggestionList = new ArrayList<String>();
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();
            ArrayList<String> friends = getFriendList(curUser);

            for (String friend : friends) {
                // SELECT a friend from current user's friends
                String query = "SELECT * FROM users WHERE username = '" + friend + "'";
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    String username = rs.getString(1);
                    String name = rs.getString(2);
                    String school = rs.getString(3);
                    User friendOfUserFriend = new User(username, name, school);
                    ArrayList<String> friendsFromFriend = getFriendList(friendOfUserFriend);
                    // From friends of user's friend: add to the list
                    // exclude current User.
                    for (int i = 0; i < friendsFromFriend.size(); i++) {
                        if (!friendsFromFriend.get(i).equals(curUser.getUsername())) {
                            friendSuggestionList.add(friendsFromFriend.get(i));
                        }
                        if (i == 3) {
                            break;
                        }
                    }
                }
            }
            return friendSuggestionList;
        } catch (Exception e) {
            e.printStackTrace();
            return friendSuggestionList;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean sendFriendReq(FriendRequest friendRequest) {
        boolean isRequestSend = false;

        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            // Check if two are friends or already request

            String sender = friendRequest.getSender();
            String receiver = friendRequest.getReceiver();
            String note = friendRequest.getNote();

            String friend_req_check = "SELECT * FROM user_friends WHERE (req_sender = '" + sender
                    + "' AND req_receiver = '" + receiver + "') OR (req_sender = '" + receiver
                    + "' AND req_receiver = '" + sender + "')";

            ResultSet rs = statement.executeQuery(friend_req_check);
            if (rs.next()) {
                String status = rs.getString("status");
                if (status.equals("approved")) {
                    System.out.println("You are friends already");
                } else if (status.equals("pending")) {
                    System.out.println("Friend request sent already");
                } else if (status.equals("denied")) {
                    System.out.println("The request has been denied. Sorry!");
                }
                isRequestSend = true;
                return isRequestSend;
            }
            String query = "INSERT into user_friends(req_sender, req_receiver, note) VALUES('" + sender + "', '"
                    + receiver + "', '" + note + "')";

            int result = statement.executeUpdate(query);
            if (result == 1) {
                isRequestSend = true;
                return isRequestSend;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return isRequestSend;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // // Message: get new message, send message
    @Override
    public ArrayList<Message> getNewMessages(User user) {
        ArrayList<Message> newMessages = new ArrayList<Message>();
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "Select * FROM message WHERE receiver = '" + user.getUsername() + "' AND read = false";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int message_id = rs.getInt(1);
                String sender = rs.getString(2);
                String receiver = rs.getString(3);
                int parent_message_id = rs.getInt(4);
                String content = rs.getString(5);
                boolean read = rs.getBoolean(6);
                Timestamp created_at = rs.getTimestamp(7);

                Message message = new Message(message_id, sender, receiver, content, parent_message_id, read,
                        created_at);
                newMessages.add(message);
            }
            return newMessages;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Message> getConversationFromAFriend(User user, String user_friend) {
        ArrayList<Message> conversation = new ArrayList<Message>();
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();
            String curUsername = user.getUsername();

            // Get 5 most recent messages between 2 users
            String query = "SELECT * FROM message WHERE (sender = '" + curUsername + "' AND receiver = '" + user_friend
                    + "') OR (sender = '" + user_friend + "' AND receiver = '" + curUsername
                    + "')ORDER BY created_at DESC LIMIT 5";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String sender_id = rs.getString(2);
                String receiver_id = rs.getString(3);
                int parent_message_id = rs.getInt(4);
                String content = rs.getString(5);
                boolean read = rs.getBoolean(6);
                Timestamp created_at = rs.getTimestamp(7);
                Message message = new Message(id, sender_id, receiver_id, content, parent_message_id, read, created_at);

                conversation.add(message);
            }

            return conversation;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean sendMessage(User user, String receiver, String messageContent) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String queryLastMessage = "SELECT * FROM message WHERE (sender = '" + user.getUsername()
                    + "' AND receiver = '" + receiver + "') OR (sender = '" + receiver + "' AND receiver = '"
                    + user.getUsername() + "') ORDER BY created_at DESC LIMIT 1";
            ResultSet rs = statement.executeQuery(queryLastMessage);
            Integer parent_message_id = -1;
            if (rs.next()) {
                parent_message_id = rs.getInt("id");
            }
            String insertMessage = "INSERT into message(sender, receiver, parent_message_id, content) VALUES ('"
                    + user.getUsername() + "', '" + receiver + "', '" + parent_message_id + "', '"
                    + messageContent + "')";

            int result = statement.executeUpdate(insertMessage);
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean addNewComment(User user, Comment newComment) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String insertQuery = "INSERT INTO comment(post_id, owner_username, content) VALUES ('"
                    + newComment.getPost_id() + "', '" + newComment.getOwner_username() + "', '"
                    + newComment.getContent() + "')";

            int result = statement.executeUpdate(insertQuery);
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // close the database
            try {
                rs.close();
                statement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
