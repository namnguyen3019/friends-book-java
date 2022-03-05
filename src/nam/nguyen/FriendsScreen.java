package nam.nguyen;

import java.util.ArrayList;
import java.util.Scanner;

public class FriendsScreen {

    private DataStorage d;
    private User user;
    private ArrayList<String> friendList = new ArrayList<String>();
    NotificationScreen notice;

    public FriendsScreen(User user, DataStorage d, NotificationScreen notice) {
        this.d = d;
        this.user = user;
        this.friendList = d.getFriendList(user);
        this.notice = notice;
    }

    public void showMenu() {

        Scanner input = new Scanner(System.in);
        String selection = "";
        while (!selection.equals("x")) {
            System.out.println("==================");
            System.out.println("Choose an option: ");

            System.out.println("1. Show your friend lists");
            System.out.println("2. Show a friend's profile");
            System.out.println("3. Show friend requests");
            System.out.println("4. Send a friend request");
            System.out.println("x. Return ");

            selection = input.next();

            switch (selection) {
                case "1":
                    showFriendList(user, d);
                    break;
                case "2":
                    showFriendProfile(user, d);
                    break;
                case "3":
                    showFriendRequest(user, d);
                    break;
                case "4":
                    sendFriendReq(user, d);
                    break;
            }
        }
    }

    private void showFriendList(User user, DataStorage d) {
        friendList = d.getFriendList(user);
        if (friendList.size() == 0) {
            System.out.println(
                    "You have no friend yet. Add new friends to see more");
        } else {
            System.out.println("Some friends in your list");
            for (int i = 0; i <= 5 && i < friendList.size(); i++) {
                System.out.println(friendList.get(i));
            }

        }
    }

    private void showFriendProfile(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username: ");
        String friend_username = input.next();

        User friend = d.getFriendByUsername(user, friend_username);
        if (friend != null) {
            System.out.println("Name: " + friend.getName() + ", school: " + friend.getSchool());
        } else {
            System.out.println(friend_username + " is not in your friend list");
        }

    }

    private void showFriendRequest(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        ArrayList<FriendRequest> friendReqList = d.getFriendRequestList(user);
        if (friendReqList.size() == 0) {
            System.out.println("No friend request");
        } else {
            for (FriendRequest friendRequest : friendReqList) {
                // Remove from notificaitons
                notice.removeFriendReqNotice(friendRequest.getFriend_req_id());
                // set read = true in database
                d.updateReadFriendRequest(user, friendRequest);
                // Delete friend req in database
                d.deleteNotification(friendRequest.getFriend_req_id());
                System.out.println("A req from : '" + friendRequest.getSender() + "' at '"
                        + friendRequest.getCreated_at().toString() + "'");

                System.out.println("type 'yes': acccept | 'no': deny| any other key to continue | 'x' to quit");
                String userInput = input.next().toLowerCase();
                if (userInput.equals("yes")) {
                    // Accept a friend request
                    boolean isAccepted = d.acceptFriendReq(user, friendRequest.getFriend_req_id());
                    if (isAccepted) {
                        System.out.println("You are friends now");
                    } else {
                        System.out.println("Failed");
                    }
                } else if (userInput.equals("no")) {
                    boolean isDenied = d.denyFriendReq(user, friendRequest.getFriend_req_id());
                    if (isDenied) {
                        System.out.println("You denied a friend request");
                    } else {
                        System.out.println("Something went wrong");
                    }
                } else if (userInput.equals("x")) {
                    break;
                }
            }
        }
    }

    private void sendFriendReq(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Add new friend with username:");
        String req_receiver = input.next();
        System.out.println("Send a note: ");
        String note = input.next();
        FriendRequest friendRequest = new FriendRequest(user.getUsername(), req_receiver, note);
        boolean isReqSent = d.sendFriendReq(friendRequest);
        if (isReqSent) {
            System.out.println("Friend's request sent successfully");
        } else {
            System.out.println("Failed to send");
        }
    }

}
