package nam.nguyen;

import java.util.Scanner;

public class FriendsBook {
    public static void main(String[] args) {

        DataStorage data = new MySQL_Database();
        Scanner input = new Scanner(System.in);
        String choice = "";

        while (!choice.equals("x")) {
            System.out.println("Choose an option");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. x for quit");
            choice = input.next();
            System.out.println(choice);
            if (choice.equals("1")) {
                // Register
                new LogInScreen(data).register();
            } else if (choice.equals("2")) {
                User curUser = new LogInScreen(data).login();
                if (curUser != null) {
                    UserScreen currentUser = new UserScreen(curUser, data);
                    currentUser.showNotifications();
                    currentUser.showMenu();
                } else {
                    System.out.println("user not found or wrong password");
                }
            }
        }
        input.close();
    }
}