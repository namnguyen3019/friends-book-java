package nam.nguyen;

import java.util.Scanner;

public class UserLoginScreen {

    private DataStorage d;
    private User user;

    public UserLoginScreen(User user, DataStorage d) {
        this.d = d;
        this.user = user;
    }

    public void showMenu() {
        Scanner input = new Scanner(System.in);
        String choice = "";
        while (!choice.equals("x")) {
            System.out.println("===============");
            System.out.println("1. Post screen: Create, update, delete, get post");
            System.out.println("2. Friends Screen: See friends and friend request ");
            System.out.println("3. Messages");
            System.out.println("4. Show friend suggestions");
            System.out.println("5. Send a friend request");
            System.out.println("x. return");

            choice = input.next();

            if (choice.equals("1")) {
                new PostScreen(user, d).showPostScreen();
            } else if (choice.equals("2")) {
                new FriendsScreen(user, d).showMenu();
            }

        }
    }

}
