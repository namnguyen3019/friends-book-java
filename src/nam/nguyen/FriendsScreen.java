package nam.nguyen;

import java.util.ArrayList;
import java.util.Scanner;

public class FriendsScreen {

    private DataStorage d;
    private User user;

    public FriendsScreen(User user, DataStorage d) {
        this.d = d;
        this.user = user;
    }

    public void showMenu() {

        Scanner input = new Scanner(System.in);
        String selection = "";
        while (!selection.equals("x")) {
            System.out.println("==================");
            System.out.println("Choose an option: ");

            System.out.println("1. Show your friend lists");
            System.out.println("2. Show a friend's profile");
            System.out.println("3. Show friend's requests");
            System.out.println("4. Show friend suggestions");
            System.out.println("5. Send a friend request");
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
                case "4":
                    showFriendSuggestions(user, d);
                case "5":
                    sendFriendReq(user, d);
            }
        }
    }

    private void showFriendList(User user, DataStorage d) {
        ArrayList<String> friendList = d.getFriendList(user);
        for (String friend : friendList) {
            System.out.println(friend);
        }
    }

    private void showFriendProfile(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username: ");
        String friend_username = input.next();

        User friend = d.getFriendByUsername(user, friend_username);

        System.out.println("Name: " + friend.getName() + ", school: " + friend.getSchool());
    }

    private void showFriendRequest(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        ArrayList<FriendRequest> friendReqList = d.getFriendRequestList(user);
        if (friendReqList.size() == 0) {
            System.out.println("No friend request");
        } else {
            for (FriendRequest friendRequest : friendReqList) {
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

    private void showFriendSuggestions(User user, DataStorage d) {

        System.out.println("People who might know: ");
        ArrayList<String> suggestedFriendList = d.friendSuggestionList(user);
        for (String friend : suggestedFriendList) {
            System.out.println(friend);
        }
    }

    private void sendFriendReq(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username:");
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
