package nam.nguyen;

import java.util.Scanner;

public class AccountCreator {

    private DataStorage data;

    public AccountCreator(DataStorage d) {
        data = d;
    }

    public void register() {
        // REGISTER FUNCTION
        String loginId = "";
        String password = "";
        String name = "";
        String school = "";

        Scanner input = new Scanner(System.in);
        boolean loginReq = false;

        // Check the requirement for login ID
        String usernaemPattern = "^[a-zA-Z]\\w{2,10}$";
        while (!loginReq) {
            System.out.println("Enter loginid from 3 - 10");
            loginId = input.next();
            if (loginId.matches(usernaemPattern)) {
                loginReq = true;
            } else {
                System.out.println("Username not  match");
            }
        }

        // Enter password
        String passwordPattern = "^.{3,}$";
        boolean passwordReq = false;
        while (!passwordReq) {
            System.out.println("Enter password");
            password = input.next();
            if (password.equals(loginId)) {
                System.out.println("password cannot be the same as login ID");
            } else if (password.matches(passwordPattern)) {
                passwordReq = true;
            } else {
                System.out.println(" Password not match requirements");
            }
        }

        System.out.println("Enter name");
        name = input.next();
        System.out.println("Enter your school");
        school = input.next();

        System.out.println("Your account is being created! Please wait");
        data.createAUser(loginId, password, name, school);
    }

}
