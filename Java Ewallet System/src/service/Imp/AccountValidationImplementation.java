package service.Imp;


import model.EWallet;
import service.AccountValidationService;

public class AccountValidationImplementation implements AccountValidationService {
    @Override
    public boolean checkPassword(String password) {
        return password.length() >= 6 && isPasswordStrong(password);
    }

    @Override
    public boolean checkUsername(String name , EWallet eWallet) {
        return name.length() >= 3 && Character.isUpperCase(name.charAt(0))
                && !eWallet.isUsernameTaken(name) && name.matches("[A-Za-z ]+");
    }


    @Override
    public boolean checkPhoneNumber(String phoneNumber , EWallet eWallet) {
        return phoneNumber.matches("\\+2\\d{11}")
                && !eWallet.isPhoneNumberTaken(phoneNumber);
    }

    @Override
    public boolean checkAge(int age) {
        return age >= 18;
    }

    @Override
    public boolean isPasswordStrong(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!]).+$";
        return password.matches(regex);
    }


    @Override
    public boolean isUsernameExist(String username , EWallet eWallet){
        return eWallet.isUsernameTaken(username);
    }



}
