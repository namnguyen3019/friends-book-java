package nam.nguyen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MessageScreen {
    DataStorage d;
    User user;

    public MessageScreen(User user, DataStorage d) {
        this.user = user;
        this.d = d;
    }

    public void showMenu() {
        Scanner input = new Scanner(System.in);
        String choice = "";

        while (!choice.equals("x")) {
            System.out.println("Choice an option: ");
            System.out.println("1. Show new messages");
            System.out.println("2. Send a message to friend");
            System.out.println("3. Show message from a friend");
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
        ArrayList<Message> newMessages = d.getNewMessages(user);
        for (int i = 0; i < newMessages.size(); i++) {
            Message m = newMessages.get(i);
            System.out.println(i + 1 + ". From " + m.getSender_id() + ": " + m.getContent());
        }
        System.out.println("End !");
    }

    public void showConverstion(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your friend's username");
        String inputFriend = input.next().toLowerCase();

        // Check if the friend in on database
        User friend = d.getFriendByUsername(user, inputFriend);
        if (friend == null) {
            System.out.println("Can't find your friend");
        } else {
            ArrayList<Message> conversation = d.getConversationFromAFriend(user, friend.getUsername());
            if (conversation.size() == 0) {
                System.out.println("No message yet");
            }
            for (int i = conversation.size() - 1; i >= 0; i--) {
                Message m = conversation.get(i);
                Date date = new Date(m.getCreated_at().getTime());
                String dateString = new SimpleDateFormat("hh:mm:ss MM-dd-yy").format(date);
                System.out.println(m.getSender_id() + ": " + m.getContent() + " at " + dateString);
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
