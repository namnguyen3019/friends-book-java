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
                AccountCreator newAcc = new AccountCreator(data);
                newAcc.register();
            } else if (choice.equals("2")) {
                // Somethint here
            }
        }

    }
}
