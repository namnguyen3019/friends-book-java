package nam.nguyen;

import java.util.Scanner;

public class FriendsBook {

    public static void main(String[] args) {

        DataStorage data = new PostgreSQL_Database();
        Scanner input = new Scanner(System.in);

        String choice = "";

        while (!choice.equals("x")) {
            System.out.println("Choose an option");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. x for quit");
            choice = input.nextLine();
            System.out.println(choice);
            if (choice.equals("1")) {
                // Register
                new UserCreator(data).register();
            } else if (choice.equals("2")) {
                User curUser = new UserCreator(data).login();
                System.out.println(curUser.getSchool());

                new UserLoginScreen(curUser, data).showMenu();
                // while (!nextChoice.equals("x")) {

                // System.out.println("x. to return");

                // nextChoice = input.next();
                // if (nextChoice.equals("1")) {
                // Post new_post = new Post(data);
                // new_post.createPost(userInfo.get(0));
                // } else if (nextChoice.equals("2")) {
                // Post post = new Post(data);
                // post.getPosts(userInfo.get(0));
                // } else if (nextChoice.equals("3")) {
                // ArrayList<String> userFriends = userLogin.getFriends(userInfo.get(0));
                // userLogin.printFriends(userFriends);
                // } else if (nextChoice.equals("4")) {
                // userLogin.showFriendsSuggestionList(userInfo.get(0));
                // } else if (nextChoice.equals("5")) {
                // String friend_username = "";
                // System.out.println("Your friend username ?");
                // friend_username = input.next();
                // userLogin.sendFriendReq(userInfo.get(0), friend_username);
                // }

                // }
            }
        }

    }
}