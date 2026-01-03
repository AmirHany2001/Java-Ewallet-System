package service;


import model.Account;
import model.History;
import service.Imp.ApplicationImplementation;

import java.util.List;

public interface AccountService {

     void changePassword(Account account, String oldPassword, String newPassword);
     void deposit(Account account , int amount);
     void withdraw(Account account , int amount) ;


     String signUpName(String name);
     String signUpPassword( String password );
     String signUpPhoneNumber(String phoneNumber);
     int  signUpAge(int  age);


     String loginName(String name);
     Account loginPassword(String password , String name);

    List<History> showHistory(Account account);
    void printAccountDetails(Account account, boolean includePassword);

    int transferWithdraw(Account SenderAccount , int money);
    boolean transferDeposit(Account senderAccount, String receiverName, int amount);

    void adminDeleteAccount(String accountName);
    void adminShowAccountDetails(String accountName);
    void adminShowAccountHistory(String accountName);

}
