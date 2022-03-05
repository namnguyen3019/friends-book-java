package nam.nguyen;

import java.util.Scanner;

public class LogInScreen {
    private DataStorage data;

    public LogInScreen(DataStorage d) {
        data = d;
    }

    public boolean register() {
        String loginId = "";
        String password = "";
        String name = "";
        String school = "";

        Scanner input = new Scanner(System.in);
        boolean loginReq = false;

        /**
         * Username: from 3- 19 character, start with a character, at least 1 number
         */
        String usernaemPattern = "^[a-zA-Z]\\w*\\d+{2,10}$";
        while (!loginReq) {
            System.out.println("Enter loginid from 3 - 10");
            System.out.println("Start with a character, at least 1 number");
            loginId = input.next().toLowerCase();
            if (loginId.matches(usernaemPattern)) {
                loginReq = true;
            } else {
                System.out.println("Username not  match");
            }
        }
        /**
         * Minimum eight characters,
         * at least one uppercase letter,
         * one lowercase letter and one number, 1 special character
         */
        String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        boolean passwordReq = false;
        while (!passwordReq) {
            System.out.println("Enter password");
            System.out
                    .println(
                            "Required: at least 8 characters, 1 lowercase, 1 uppsercase, 1 one number, 1 special character");
            password = input.next();
            if (password.equals(loginId)) {
                System.out.println("password cannot be the same as login ID");
            } else if (password.matches(passwordPattern)) {
                passwordReq = true;
            } else {
                System.out.println(" Password not match the requirements");
            }
        }

        System.out.println("Enter name: ");
        input.nextLine();
        name = input.nextLine();
        System.out.println("Enter your school: ");
        school = input.nextLine();
        System.out.println("Your account is being created! Please wait");

        boolean isUserCreated = data.createUser(loginId, password, name, school);
        if (isUserCreated) {
            System.out.println("New user created");
        } else {
            System.out.println("Failed to create a new user");
        }

        return isUserCreated;
    }

    public User login() {
        Scanner input = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("Eneter your username");
        username = input.next();
        System.out.println("Enter your password");
        password = input.next();

        User userInfo = data.userLogin(username, password);
        return userInfo;
    }
}
