package farewell.authentication_module.utils;

public class PasswordValidator {

    public static boolean isValid(String password) {
        String regex = "^(?=.*[0-9])(?=.\\S+$).*[A-Za-z0-9].{8,20}$";
        return password.matches(regex);
    }
}


