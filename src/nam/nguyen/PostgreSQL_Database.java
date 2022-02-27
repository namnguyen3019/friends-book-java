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

            ResultSet rs = statement.executeQuery(queryUser);
            if (rs.next()) {
                System.out.println("user already exists");
                return isUserCreated;
            }
            int insertResult = statement.executeUpdate("INSERT INTO users(username, password, name, school) VALUES ( '"
                    + username + "' , '" + password + "', '" + name + "', '" + school + "')");
            if (insertResult == 1) {
                conn.commit();
                conn.setAutoCommit(true);
                System.out.println("New account has been created");
                isUserCreated = true;
                return isUserCreated;
            } else {
                System.out.println("Oh no cannot create a new account");
                return isUserCreated;
            }

        } catch (SQLException e) {
            // handle the exceptions
            System.out.println("Account creation failed");
            e.printStackTrace();
            return isUserCreated;

        } finally {
            try {
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

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String school = resultSet.getString("school");
                userInfo = new User(username, name, school);

                System.out.println("Welcome to Frendsbook '" + username + "'");
                return userInfo;
            } else {
                System.out.println("User not found or wrong password!");
                return null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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
                System.out.println(" Failed to create a new post");
            }
        } catch (Exception e) {
            System.out.println("Post creation failed");
            e.printStackTrace();
        }

        return postCreated;
    }

    @Override
    public Post getPostById(int post_id, String username) {
        try {
            conn = DriverManager.getConnection(url, db_user, password);
            statement = conn.createStatement();

            String query = "Select * FROM posts WHERE post_id = '" + post_id
                    + "' and owner_id = '" + username + "'";

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                int postId = rs.getInt(1);
                String content = rs.getString(2);
                String owner_id = rs.getString(3);
                Timestamp created_at = rs.getTimestamp(4);
                Timestamp updated_at = rs.getTimestamp(5);
                Post returnPost = new Post(postId, content, owner_id, created_at, updated_at);

                return returnPost;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return null;
    }

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
            // TODO: handle exception
        }

        return currentPosts;
    }

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
            // TODO: handle exception
            e.printStackTrace();
        }
        return isUpdated;
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
        }
        return false;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendList;
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
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
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
            // TODO: handle exception
            e.printStackTrace();
        }

        return friendRequestsList;
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
            // TODO: handle exception
            e.printStackTrace();
            return false;
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
                return isRequestSend;
            }

            String query = "INSERT into user_friends(req_sender, req_receiver, note) VALUES('" + sender + "', '"
                    + receiver + "', '" + note + "')";

            int result = statement.executeUpdate(query);
            if (result == 1) {
                isRequestSend = true;
                return isRequestSend;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return isRequestSend;
        }

        return isRequestSend;
    }

    // // Posts: create a post, get posts, get post, update post, delete post

    // @Override
    // public ArrayList<PostModel> getPosts(String username) {

    // ArrayList<PostModel> Posts = new ArrayList<PostModel>();
    // try {
    // conn = DriverManager.getConnection(url, user, password);
    // statement = conn.createStatement();

    // String query = "Select * FROM posts WHERE owner_id = '" + username + "'";

    // ResultSet rs = statement.executeQuery(query);
    // while (rs.next()) {
    // int post_id = rs.getInt(1);
    // String content = rs.getString(2);
    // String owner_id = rs.getString(3);
    // Timestamp created_at = rs.getTimestamp(4);
    // PostModel post = new PostModel(post_id, content, owner_id, created_at);
    // System.out.println("post_id '" + post_id + "'");
    // System.out.println("content '" + content + "'");
    // System.out.println("owner_id '" + post_id + "'");
    // System.out.println("created_at '" + created_at.toString() + "'");
    // System.out.println("----------------");
    // Posts.add(post);
    // }

    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // return Posts;
    // }

    // // Message: get new message, send message

}
