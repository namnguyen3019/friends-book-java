package nam.nguyen;

import java.util.ArrayList;
import java.util.Scanner;

public class UserScreen {

    private DataStorage d;
    private User user;
    private NotificationScreen notice;

    public UserScreen(User user, DataStorage d) {
        this.d = d;
        this.user = user;
    }

    public ArrayList<Notification> showNotifications() {
        ArrayList<Notification> notificationList = d.getNotifications(user);
        ArrayList<Notification> newFriendReqList = new ArrayList<Notification>();
        ArrayList<Notification> newMessageList = new ArrayList<Notification>();
        if (notificationList.size() == 0) {
            System.out.println("No new notification ");
        } else {
            int numOfNo = notificationList.size();
            int numOfFriendReq = 0;
            int numOfNewMessage = 0;
            System.out.println("You are having " + numOfNo + " new notifications! ");
            for (Notification n : notificationList) {
                if (n.getType_of_notice().equals("FriendRequest")) {
                    numOfFriendReq += 1;
                    newFriendReqList.add(n);
                } else if (n.getType_of_notice().equals("NewMessage")) {
                    numOfNewMessage += 1;
                    newMessageList.add(n);
                }
            }
            if (numOfFriendReq > 0) {
                System.out.println("You are having " + numOfFriendReq + " friend request(s)");
            }
            if (numOfNewMessage > 0) {
                System.out.println("You are having " + numOfNewMessage + " new message(s)");
            }
        }
        notice = new NotificationScreen(user, d, notificationList, newFriendReqList, newMessageList);
        return notificationList;
    }

    public void showMenu() {
        Scanner input = new Scanner(System.in);
        String choice = "";
        while (!choice.equals("x")) {
            System.out.println("===============");
            System.out.println("1. Post screen: Create, update, delete, get post");
            System.out.println("2. Friends Screen: See friends and friend request ");
            System.out.println("3. Messages");
            System.out.println("4. Notifications");
            System.out.println("5. Show/Update your profile");
            System.out.println("x. return");

            choice = input.next();

            if (choice.equals("1")) {
                new PostScreen(user, d).showPostScreen();
            } else if (choice.equals("2")) {
                new FriendsScreen(user, d, notice).showMenu();
            } else if (choice.equals("3")) {
                new MessageScreen(user, d, notice).showMenu();
            } else if (choice.equals("4")) {
                notice.showNotificationsDetails();
            } else if (choice.equals("5")) {
                showProfile(user);
                updateProfile(user, d);
            }

        }
    }

    private void showProfile(User user) {
        System.out.println("username: " + user.getUsername() + "");
        System.out.println("name: " + user.getName() + "");
        System.out.println("school: " + user.getSchool() + "");
    }

    private void updateProfile(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Update you name: ");
        String inputName = input.nextLine().strip();
        System.out.println("Update your school: ");
        String inputSchool = input.nextLine().strip();
        boolean isUserUpdated = false;
        if (inputName != null && inputSchool != null) {
            User updatedUser = new User(user.getUsername(), inputName, inputSchool);
            isUserUpdated = d.updateProfile(updatedUser);
        } else if (inputName != null) {
            User updatedUser = new User(user.getUsername(), inputName, user.getSchool());
            isUserUpdated = d.updateProfile(updatedUser);

        } else if (inputSchool != null) {
            User updatedUser = new User(user.getUsername(), inputName, user.getSchool());
            isUserUpdated = d.updateProfile(updatedUser);
        }
        if (isUserUpdated) {
            System.out.println("Updated successully");
        } else {
            System.out.println("Failed to update");
        }
    }

}
