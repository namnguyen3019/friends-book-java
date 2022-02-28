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
            System.out.println("4. Notifications ");
            System.out.println("5. Update your profile");
            System.out.println("x. return");

            choice = input.next();

            if (choice.equals("1")) {
                new PostScreen(user, d).showPostScreen();
            } else if (choice.equals("2")) {
                new FriendsScreen(user, d).showMenu();
            } else if (choice.equals("3")) {
                new MessageScreen(user, d).showMenu();
            } else if (choice.equals("4")) {
                // Notifications
            } else if (choice.equals("5")) {
                updateProfile(user, d);
            }

        }
    }

    private void updateProfile(User user, DataStorage d) {
        Scanner input = new Scanner(System.in);
        System.out.println("Update you name: ");
        String inputName = input.nextLine().strip();
        System.out.println("Update your school: ");
        String inputSchool = input.nextLine().strip();

        User updatedUser = new User(user.getUsername(), inputName, inputSchool);
        boolean isUserUpdated = d.updateProfile(updatedUser);
        if (isUserUpdated) {
            System.out.println("Updated successully");
        } else {
            System.out.println("Failed to update");
        }

    }

}
