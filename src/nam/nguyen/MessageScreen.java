package nam.nguyen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class MessageScreen {
    DataStorage d;
    User user;
    NotificationScreen notice;

    public MessageScreen(User user, DataStorage d, NotificationScreen notice) {
        this.user = user;
        this.d = d;
        this.notice = notice;
    }

    public void showMenu() {
        Scanner input = new Scanner(System.in);
        String choice = "";

        while (!choice.equals("x")) {
            System.out.println("Choice an option: ");
            System.out.println("1. Show new messages");
            System.out.println("2. Send a message to friend");
            System.out.println("3. Show last conversation with a friend");
            System.out.println("x. x to quit");
            choice = input.next().toLowerCase();

            if (choice.equals("1")) {
                getNewMessages(user, d);
            } else if (choice.equals("2")) {
                sendMessage(user, d);
            } else if (choice.equals("3")) {
                showConverstion(user, d);
            }

        }
    }

    private void getNewMessages(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        ArrayList<Message> newMessages = d.getNewMessages(user);
        if (newMessages.size() == 0) {
            System.out.println("No new message!");
        } else {
            HashMap<Integer, Message> choices = new HashMap<Integer, Message>();
            for (int i = 0; i < newMessages.size(); i++) {
                Message m = newMessages.get(i);
                System.out.println(i + 1 + ". From " + m.getSender_id() + ": " + m.getContent());
                choices.put(i + 1, m);
            }

            String inputChoice = "";
            while (!inputChoice.toLowerCase().equals("x")) {
                if (newMessages.size() == 0) {
                    break;
                }
                System.out.println("Choose message to show: or 'x' to quit ");
                inputChoice = input.next();
                if (inputChoice.equals("x")) {
                    break;
                }
                int inputInt = Integer.parseInt(inputChoice);
                Message m = choices.get(inputInt);
                if (m != null) {
                    System.out.println("Sender: " + m.getSender_id());
                    System.out.println("Content: " + m.getContent());
                    Date date = new Date(m.getCreated_at().getTime());
                    String dateString = new SimpleDateFormat("hh:mm:ss MM-dd-yy").format(date);
                    System.out.println("sent at: " + dateString);
                    for (int i = 0; i < newMessages.size(); i++) {
                        if (newMessages.get(i).getMessage_id() == m.getMessage_id()) {
                            newMessages.remove(i);
                        }
                    }
                    d.updateReadMessage(user, m.getMessage_id());
                    // Remove the message from notification
                    notice.removeNewMessagNotice(m.getMessage_id());
                    d.deleteNotification(m.getMessage_id());
                }

            }

        }

    }

    public void showConverstion(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your friend's username");
        String inputFriend = input.next().toLowerCase();

        // Check if the friend in on database
        boolean isFriend = d.isFriend(user.getUsername(), inputFriend);
        if (!isFriend) {
            System.out.println("You're not friends yet");
        } else {
            ArrayList<Message> conversation = d.getConversationFromAFriend(user, inputFriend);
            if (conversation.size() == 0) {
                System.out.println("No message yet");
            }
            for (int i = conversation.size() - 1; i >= 0; i--) {
                Message m = conversation.get(i);
                Date date = new Date(m.getCreated_at().getTime());
                String dateString = new SimpleDateFormat("hh:mm:ss MM-dd-yy").format(date);
                System.out.println(dateString + ": " + m.getSender_id() + ": " + m.getContent());
            }
        }
    }

    private void sendMessage(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your friend's username");
        String inputFriend = input.next();
        input.nextLine();
        System.out.println("Enter content: ");
        String content = input.nextLine();

        boolean isMessageSent = d.sendMessage(user, inputFriend, content);
        if (isMessageSent) {
            System.out.println("Message sent! ");
        } else {
            System.out.println("Send message failed! ");
        }
    }
}
