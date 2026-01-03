package service.Imp;

import model.Account;
import model.History;
import service.AccountService;
import model.EWallet;


import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;

public class AccountImplementation implements AccountService {

    private static AccountImplementation instance = null;

    private AccountImplementation() {}

    public static AccountImplementation getInstance() {
        if (instance == null) {
            instance = new AccountImplementation();
        }
        return instance;
    }

    Account account =  null;
    EWallet eWallet = EWallet.getInstance();
    AccountValidationImplementation accountValidationImplementation = AccountValidationImplementation.getInstance();



    @Override
    public void deposit(Account account , int amount) {

            if (amount < 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
                account.addBalance(amount);
                account.addHistory(new History.Builder().setTime(LocalTime.now())
                        .setDate(LocalDate.now()).setOperation("deposit with amount " + amount)
                        .setName(account.getName()).build());
    }

    @Override
    public void withdraw(Account account, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }
        if (account.getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance");
        }

        account.getMoney(amount); // deduct money
        account.addHistory(new History.Builder()
                .setOperation("withdraw with amount " + amount)
                .setName(account.getName())
                .setDate(LocalDate.now())
                .setTime(LocalTime.now())
                .build());
    }


    @Override
    public int transferWithdraw(Account senderAccount, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (senderAccount.getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance for transfer");
        }

        senderAccount.getMoney(amount); // deduct money

        return amount;
    }


    @Override
    public boolean transferDeposit(Account senderAccount, String receiverName, int amount) {
        if (receiverName.equals(senderAccount.getName())) {
            throw new IllegalArgumentException("Cannot transfer money to yourself");
        }

        if (!accountValidationImplementation.isUsernameExist(receiverName, eWallet)) {
            throw new IllegalArgumentException("Receiver account does not exist");
        }

        Account receiverAccount = eWallet.getAccountByUsername(receiverName);
        receiverAccount.addBalance(amount);

        receiverAccount.addHistory(new History.Builder()
                .setOperation("Receive money from " + senderAccount.getName() + " with amount " + amount)
                .setName(receiverAccount.getName())
                .setDate(LocalDate.now())
                .setTime(LocalTime.now())
                .build());

        senderAccount.addHistory(new History.Builder()
                .setOperation("transfer with amount " + amount)
                .setName(senderAccount.getName())
                .setDate(LocalDate.now())
                .setTime(LocalTime.now())
                .build());

        return true;
    }


    @Override
    public void printAccountDetails(Account account, boolean includePassword) {
        System.out.println("Your account name: " + account.getName());
        System.out.println("Your age: " + account.getAge());
        System.out.println("Your phone number: " + account.getPhoneNumber());
        System.out.println("Your balance: " + account.getBalance());
        if (includePassword) {
            System.out.println("Your password: " + account.getPassword());
        }
    }

    @Override
    public void changePassword(Account account, String oldPassword, String newPassword) {
        if (!account.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Old password does not match!");
        }

        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as old password.");
        }

        if (!accountValidationImplementation.checkPassword(newPassword)) {
            throw new IllegalArgumentException("Invalid password format!");
        }

        account.setPassword(newPassword);
        account.addHistory(new History.Builder()
                .setName(account.getName())
                .setTime(LocalTime.now())
                .setDate(LocalDate.now())
                .setOperation("changePassword")
                .build());
    }

    @Override
    public String signUpName(String name){

        if (!accountValidationImplementation.checkUsername(name, eWallet) || name.equalsIgnoreCase("admin")) {
            throw  new IllegalArgumentException("Invalid username");
        }
        return name ;
    }

    @Override
    public String signUpPassword(String password){

            if (!accountValidationImplementation.checkPassword(password)) {
                throw  new IllegalArgumentException("Invalid password");
            }
        return password;
    }

    @Override
    public String signUpPhoneNumber(String phoneNumber){

            if (!accountValidationImplementation.checkPhoneNumber(phoneNumber, eWallet)) {
                throw  new IllegalArgumentException("Invalid phoneNumber");
            }
            return phoneNumber;
    }

    @Override
    public int signUpAge(int age){

        if (!accountValidationImplementation.checkAge(age)) {
            throw  new IllegalArgumentException("Invalid age");
        }
        return age;
    }

    @Override
    public String loginName(String name){
        if (!accountValidationImplementation.isUsernameExist(name, eWallet)) {
            throw new IllegalArgumentException("Invalid username");
        }
        return  name;
    }

    @Override
    public Account loginPassword(String password , String name){

        account = eWallet.getAccountByUsername(name);

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        return account;
    }

    @Override
    public List<History> showHistory(Account account){
        return account.getHistory().reversed();
    }


    @Override
    public void adminDeleteAccount(String accountName) {

        if (!accountValidationImplementation.isUsernameExist(accountName, eWallet) || accountName.equals("admin")) {
            throw new IllegalArgumentException("Invalid username");
        }
        account = eWallet.getAccountByUsername(accountName);
        eWallet.deleteAccount(account.getName());
    }


    @Override
    public void adminShowAccountDetails(String accountName) {

        if(!accountValidationImplementation.isUsernameExist(accountName, eWallet) || accountName.equals("admin")) {
            throw new IllegalArgumentException("Invalid username");
        }
        account = eWallet.getAccountByUsername(accountName);
        printAccountDetails(account , true);

    }

    @Override
    public void adminShowAccountHistory(String accountName) {

        if(!accountValidationImplementation.isUsernameExist(accountName, eWallet) || accountName.equals("admin")) {
            throw new IllegalArgumentException("Invalid username");
        }
        account = eWallet.getAccountByUsername(accountName);
        System.out.println(showHistory(account));

    }



}
