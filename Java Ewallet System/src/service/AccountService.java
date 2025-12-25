package service;


import model.Account;
import service.Imp.ApplicationImplementation;

public interface AccountService {
     void login(ApplicationImplementation app);
     void signUp(ApplicationImplementation app);
     void changePassword(Account account);
     void deposit(Account account);
     void withdraw(Account account) ;
     void transfer(Account account) ;
     void deleteAccount(Account account , ApplicationImplementation app) ;
     void logout(ApplicationImplementation app ,  Account account);
     void showAccountDetails(Account account);

     String signUpName(ApplicationImplementation app);
     String signUpPassword(ApplicationImplementation app);
     String signUpPhoneNumber(ApplicationImplementation app);
     int  signUpAge(ApplicationImplementation app);
     void checkTrials(int trial , ApplicationImplementation app);

     String loginName(ApplicationImplementation app);
     Account loginPassword(String accountName ,ApplicationImplementation app);

     void anotherService(ApplicationImplementation app , Account account);

     void showDataWithPassword(Account account);
     void showDataWithOutPassword(Account account);

    int transferWithdraw(Account SenderAccount , int money);
    boolean transferDeposit(int money , Account SenderAccount );

    void showHistory(Account account);

    void adminDeleteAccount();
    void adminLogout(ApplicationImplementation app);
    void adminShowAccountDetails();
    void adminShowAccountHistory();
    void adminAnotherService(Account account , ApplicationImplementation app);
}
