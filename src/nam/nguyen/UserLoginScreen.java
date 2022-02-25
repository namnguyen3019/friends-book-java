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
            System.out.println("1. Create a post");
            System.out.println("2. See your current posts");
            System.out.println("3. Show your friends list");
            System.out.println("4. Show friend suggestions");
            System.out.println("5. Send a friend request");
            System.out.println("x. return");

            choice = input.next();

        }
    }

}
