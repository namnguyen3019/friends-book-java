package nam.nguyen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class NotificationScreen {

    private DataStorage d;
    private User user;
    private ArrayList<Notification> notificationList;
    private ArrayList<Notification> friendRequestList;
    private ArrayList<Notification> newMessageList;

    public NotificationScreen(User user, DataStorage d, ArrayList<Notification> notificationList,
            ArrayList<Notification> friendRequestList, ArrayList<Notification> newMessageList) {
        this.d = d;
        this.user = user;
        this.notificationList = notificationList;
        this.friendRequestList = friendRequestList;
        this.newMessageList = newMessageList;
    }

    public ArrayList<Notification> showNotifications() {
        notificationList = d.getNotifications(user);

        if (notificationList.size() == 0) {
            System.out.println("No new notifications ");
        } else {
            int numOfNo = notificationList.size();
            int numOfFriendReq = 0;
            int numOfNewMessage = 0;
            System.out.println("You are having " + numOfNo + " new notifications! ");
            for (Notification n : notificationList) {
                if (n.getType_of_notice().equals("FriendRequest")) {
                    numOfFriendReq += 1;
                    friendRequestList.add(n);
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
        return notificationList;
    }

    public void showNotificationsDetails() {
        Scanner input = new Scanner(System.in);

        String inputChoice = "";
        while (inputChoice.equals("") | inputChoice.equals("1") | inputChoice.equals("2")) {
            System.out.println("Choose an option");
            System.out.println("1. You are having " + friendRequestList.size() + " friend request(s)");
            System.out.println("2. You are having " + newMessageList.size() + " new message(s)");
            System.out.println("3. other key to quit");
            inputChoice = input.next();
            if (inputChoice.equals("1")) {
                showFriendReq(friendRequestList, input);
            } else if (inputChoice.equals("2")) {
                showNewMessages(newMessageList, input);
            }
        }

    }

    private void showFriendReq(ArrayList<Notification> friendRequestList, Scanner input) {
        int i = 0;
        Set<Integer> friendReqIdSset = new HashSet<Integer>();
        for (; i < friendRequestList.size(); i++) {
            Notification friendReq = friendRequestList.get(i);
            String noticeType = friendReq.getType_of_notice();
            int friendReqId = friendReq.getMessage_or_friend_req_id();
            System.out.println(i + 1 + ". " + noticeType + ", id: "
                    + friendReqId);
            friendReqIdSset.add(friendReqId);
        }

        String reqIdChoice = "";
        while (!reqIdChoice.equals("x")) {
            System.out.println("Enter Friend Request ID to see or 'x' to quit ");
            reqIdChoice = input.next();
            if (reqIdChoice.toLowerCase().equals("x")) {
                break;
            }
            int id = Integer.parseInt(reqIdChoice);
            if (friendReqIdSset.contains(id)) {
                FriendRequest friendRequest = d.getFriendReqById(user, id);
                System.out.println("Sender:" + friendRequest.getSender());
                System.out.println("Note: " + friendRequest.getNote());
                int n = friendRequestList.size();

                // remove notice from list
                for (int j = 0; j < n; j++) {
                    if (friendRequestList.get(j).getMessage_or_friend_req_id().equals(id)) {
                        friendRequestList.remove(j);
                        break;
                    }
                }
                friendReqIdSset.remove(id);
            }

            if (friendReqIdSset.size() == 0) {
                System.out.println("No more friend request");
                break;
            }
        }
    }

    private void showNewMessages(ArrayList<Notification> newMessages, Scanner input) {
        int i = 0;
        Set<Integer> messageIdSet = new HashSet<Integer>();
        for (; i < newMessages.size(); i++) {
            Notification newMessage = newMessages.get(i);
            String noticeType = newMessage.getType_of_notice();
            int messageId = newMessage.getMessage_or_friend_req_id();
            messageIdSet.add(messageId);
            System.out.println(i + 1 + ". " + noticeType + ", id: "
                    + messageId);
        }
        String messageId = "";

        while (!messageId.equals("x")) {
            System.out.println("Enter message id to read or 'x' to quit");
            messageId = input.next();
            if (messageId.toLowerCase().equals("x")) {
                break;
            }
            int id = Integer.parseInt(messageId);
            if (messageIdSet.contains(id)) {
                Message m = d.getMessageById(user, id);
                System.out.println("Sender: " + m.getSender_id());
                System.out.println("Message: " + m.getContent());
                // Remove the message to the list
                int n = newMessageList.size();
                for (int j = 0; j < n; j++) {
                    if (newMessageList.get(j).getMessage_or_friend_req_id() == id) {
                        newMessageList.remove(j);
                        break;
                    }
                }
                messageIdSet.remove(id);
            }

            if (messageIdSet.size() == 0) {
                System.out.println("No new message");
                break;
            }
        }
    }

    // Getter and Setter
    public ArrayList<Notification> getFriendRequestList() {
        return friendRequestList;
    }

    public ArrayList<Notification> getNewMessageList() {
        return newMessageList;
    }

    public ArrayList<Notification> getNotificationList() {
        return notificationList;
    }

    public void setFriendRequestList(ArrayList<Notification> friendRequestList) {
        this.friendRequestList = friendRequestList;
    }

    public void setNewMessageList(ArrayList<Notification> newMessageList) {
        this.newMessageList = newMessageList;
    }

    public void setNotificationList(ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public void removeNewMessagNotice(int messageId) {
        ArrayList<Notification> newMessageList = this.getNewMessageList();

        for (int i = 0; i < newMessageList.size(); i++) {
            if (newMessageList.get(i).getMessage_or_friend_req_id() == messageId) {
                newMessageList.remove(i);
                break;
            }
        }
        this.setNewMessageList(newMessageList);
        this.removeNoticeItem(messageId);
    }

    public void removeFriendReqNotice(int friendReqId) {
        ArrayList<Notification> friendRequestList = this.getFriendRequestList();

        for (int i = 0; i < friendRequestList.size(); i++) {
            if (friendRequestList.get(i).getMessage_or_friend_req_id() == friendReqId) {
                friendRequestList.remove(i);
                break;
            }
        }
        this.setFriendRequestList(friendRequestList);
        this.removeNoticeItem(friendReqId);
    }

    private void removeNoticeItem(int id) {
        ArrayList<Notification> notificationList = this.getNotificationList();
        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).getMessage_or_friend_req_id() == id) {
                notificationList.remove(i);
                break;
            }
        }
        this.setNotificationList(notificationList);
    }

}
