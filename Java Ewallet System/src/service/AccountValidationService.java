package service;

import model.EWallet;

public interface AccountValidationService {

    boolean checkPassword(String password);
    boolean isPasswordStrong(String password);
    boolean checkUsername(String name, EWallet eWallet);
    boolean checkAge(int age);
    boolean isUsernameExist(String username , EWallet eWallet);
    boolean checkPhoneNumber(String phoneNumber , EWallet eWallet);

}
