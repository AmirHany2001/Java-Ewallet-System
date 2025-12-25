package service.Imp;

import model.Account;
import model.History;
import service.AccountService;
import model.EWallet;


import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.LocalDate;

public class AccountImplementation implements AccountService {
    Scanner scanner = new Scanner(System.in);
    Account account =  null;
    EWallet eWallet = EWallet.getInstance();
    AccountValidationImplementation accountValidationImplementation = new AccountValidationImplementation();

    @Override
    public void login(ApplicationImplementation app) {

        String accountName;

        accountName = loginName(app);
        account = loginPassword(accountName , app);

        if(account != null && account.getName().equals("admin")) {
            app.startAdminFeatures(account);
        }

        if (account != null) {
            account.addHistory(new History.Builder().setOperation("newLogin").setName(account.getName())
                    .setTime(LocalTime.now()).setDate(LocalDate.now()).build());
            app.startApplicationFeatures(account);
        }else{
            new History.Builder().setOperation("failedLogin").setName(account.getName())
                    .setTime(LocalTime.now()).setDate(LocalDate.now()).build();
        }

    }

    @Override
    public void signUp(ApplicationImplementation app) {
        String name , password ;
        String phoneNumber;
        int age ;
        System.out.println("Welcome to the sign-up service");

        name = signUpName(app);

        password = signUpPassword(app);

        phoneNumber = signUpPhoneNumber(app);

        age = signUpAge(app);

        account = new Account.Builder().setName(name).setAge(age)
                .setPassword(password).setPhoneNumber(phoneNumber).build();

        eWallet.addAccount(account);

        account.addHistory(new History.Builder().setOperation("signup").setName(account.getName())
                .setTime(LocalTime.now()).setDate(LocalDate.now()).build());

        System.out.println("Your Account has been added successfully");
        app.startApplicationFeatures(account);

    }

    @Override
    public void deposit(Account account) {
        int amount , trial = 3;

        while (trial > 0){
            try{
                System.out.println("Please enter your amount ");
                amount = scanner.nextInt();
                scanner.nextLine();
                if (amount > 0) {
                    account.addBalance(amount);
                    System.out.println("Your account new balance after the deposit is " + account.getBalance());
                    account.addHistory(new History.Builder().setTime(LocalTime.now())
                            .setDate(LocalDate.now()).setOperation("deposit with amount " + amount)
                            .setName(account.getName()).build());
                    break;
                }
                else{
                    System.out.println("Not Valid Amount");
                    trial--;
                }
            }catch (InputMismatchException e){
                System.out.println("Not Valid Amount .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        if(trial == 0){
            System.out.println("Invalid operation");
            account.addHistory(new History.Builder().setTime(LocalTime.now())
                    .setDate(LocalDate.now()).setOperation("failedDeposit")
                    .setName(account.getName()).build());
        }
    }

    @Override
    public void withdraw(Account account) {
        int amount, trial = 3;

        while(trial > 0){
            try{
                System.out.println("Please enter your amount ");
                amount = scanner.nextInt();
                scanner.nextLine();
                if ( amount > 0 && account.getBalance() >= amount) {
                    account.getMoney(amount);
                    System.out.println("Your account new balance after the withdraw is " + account.getBalance());
                    account.addHistory(new History.Builder().setOperation("withdraw with amount " + amount)
                            .setName(account.getName()).setDate(LocalDate.now())
                            .setTime(LocalTime.now()).build());
                    break;
                }
                else{
                    System.out.println("Not Valid Amount or Not Enough Balance");
                    trial--;
                }
            }catch (InputMismatchException e){
                System.out.println("Not Valid Amount .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        if(trial == 0){
            System.out.println("Invalid operation");
            account.addHistory(new History.Builder().setOperation("failedWithdraw")
                    .setName(account.getName()).setDate(LocalDate.now())
                    .setTime(LocalTime.now()).build());
        }
    }

    @Override
    public void transfer(Account senderAccount) {
        int money = 0 ;
        money = transferWithdraw(senderAccount,money);

        if(money > 0){
            if(transferDeposit(money , senderAccount)){
                senderAccount.addHistory(new History.Builder().setOperation("transfer with amount " + money)
                        .setName(senderAccount.getName()).setTime(LocalTime.now())
                        .setDate(LocalDate.now()).build());
            }
        }
    }

    @Override
    public int transferWithdraw(Account senderAccount , int money) {
        int amount  , trial = 3;
        while(trial > 0){
            try{
                System.out.println("Please enter your amount ");
                amount = scanner.nextInt();
                scanner.nextLine();
                if ( amount > 0 && senderAccount.getBalance() >= amount) {
                    senderAccount.getMoney(amount);
                    money = amount;
                    System.out.println("Your account new balance after the transferWithdraw is " + senderAccount.getBalance());
                    break;
                }
                else{
                    System.out.println("Not Valid Amount or Not Enough Balance");
                    trial--;
                }
            }catch (InputMismatchException e){
                System.out.println("Not Valid Amount .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        if (trial == 0){
            System.out.println("Invalid operation");
            senderAccount.addHistory(new History.Builder().setOperation("failedTransfer")
                    .setName(senderAccount.getName()).setTime(LocalTime.now())
                    .setDate(LocalDate.now()).build());
            return 0;
        }
        return money;
    }

    @Override
    public boolean transferDeposit(int money ,  Account senderAccount) {
        String name ;
        int trial = 3;

        while(trial > 0){
            try{
                System.out.println("Please enter the account name that you want to send money to ");
                name = scanner.nextLine();

                if(accountValidationImplementation.isUsernameExist(name,eWallet) && !senderAccount.getName().equals(name)){
                    account = eWallet.getAccountByUsername(name);
                    account.addBalance(money);
                    System.out.println("Your account new balance after the transferDeposit is " + account.getBalance());
                    account.addHistory(new History.Builder()
                            .setOperation("Receive money from " + senderAccount.getName() + " with amount " + money)
                            .setName(account.getName())
                            .setTime(LocalTime.now()).setDate(LocalDate.now()).build());
                    break;
                }
                System.out.println(" Username does not exist .... you can't send money to yourself ");
                trial--;
            }catch (InputMismatchException e){
                System.out.println("Not Valid Amount .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        if(trial == 0){
            System.out.println("Invalid operation");
            senderAccount.addBalance(money);
            System.out.println("The sender balance returns to " +  senderAccount.getBalance());
            return false;
        }
        return true;
    }

    @Override
    public void deleteAccount(Account account , ApplicationImplementation app) {
        eWallet.deleteAccount(account);
        System.out.println("Your account has been deleted successfully");
        app.startApplication();
    }

    @Override
    public void showAccountDetails(Account account) {
        String approve ;

        System.out.println("Do you need to show your Password ?" +"\n" + "yes or no ");
        approve = scanner.nextLine();
        if (approve.equalsIgnoreCase("yes")){
            showDataWithPassword(account);
        }
        else{
            showDataWithOutPassword(account);
        }
    }

    @Override
    public void showDataWithPassword(Account account){
        String password;
        int trial = 3;
        while (trial > 0){
            System.out.println("Enter your Password");
            password = scanner.nextLine();
            if(account.getPassword().equals(password)){
                System.out.println(
                        "Your account name is " + account.getName() +"\n"
                                + "Your age is " + account.getAge() +"\n"
                                + "Your account phone number is " + account.getPhoneNumber() +"\n"
                                + "Your Balance is " + account.getBalance() + "\n"
                                + "Your Password is " + account.getPassword());
                break;
            }
            System.out.println("Password Does Not Match");
            trial--;
        }
        if(trial == 0){
            showDataWithOutPassword(account);
        }

    }

    @Override
    public void showDataWithOutPassword(Account account){
        System.out.println("Your account name is " + account.getName() +"\n"
                + "Your age is " + account.getAge() +"\n"
                + "Your account phone number is " + account.getPhoneNumber() +"\n"
                + "Your Balance is " + account.getBalance());
    }

    @Override
    public void logout(ApplicationImplementation app , Account  account) {
        System.out.println("See you Later :)");
        account.addHistory(new History.Builder().setName(account.getName()).setTime(LocalTime.now())
                .setDate(LocalDate.now()).setOperation("logout").build());
        app.startApplication();
    }

    @Override
    public  void changePassword(Account account) {
        int trial = 3;
        String oldPassword , newPassword ;
        System.out.println("Please enter your old password");
        oldPassword = scanner.nextLine();

        while (trial > 0){
            if(account.getPassword().equals(oldPassword)){
                System.out.println("please enter your new password");
                newPassword = scanner.nextLine();
                if(oldPassword.equals(newPassword)){
                    System.out.println("This Password has been used recently");
                    trial--;
                    continue;
                }
                else if (!accountValidationImplementation.checkPassword(newPassword)) {
                    System.out.println("Invalid password!");
                    trial--;
                    continue;
                }
                account.setPassword(newPassword);
                account.addHistory(new History.Builder().setName(account.getName()).setTime(LocalTime.now())
                        .setDate(LocalDate.now()).setOperation("changePassword").build());
                System.out.println("Your Password has been changed successfully");
            }
            else{
                System.out.println("Passwords do not match!");
                trial--;

                System.out.println("Please enter your old password");
                oldPassword = scanner.nextLine();
                continue;
            }
            break;
        }

        if(trial == 0){
            System.out.println("Passwords didn't change");
            account.addHistory(new History.Builder().setName(account.getName()).setTime(LocalTime.now())
                    .setDate(LocalDate.now()).setOperation("failedChangePassword").build());
        }

    }

    @Override
    public String signUpName(ApplicationImplementation app){
        int trial = 3;
        String name = null;
        while (trial > 0) {
            System.out.println("Please enter your name:");
            name = scanner.nextLine();

            if (!accountValidationImplementation.checkUsername(name, eWallet)) {
                System.out.println("Invalid name");
                trial--;
                continue;
            }
            break;
        }
        checkTrials(trial , app);
        return name ;
    }

    @Override
    public String signUpPassword(ApplicationImplementation app){
        int trial = 3;
        String password = null;

        while (trial > 0) {
            System.out.println("Please enter your password:");
            password = scanner.nextLine();

            if (!accountValidationImplementation.checkPassword(password)) {
                System.out.println("Invalid password!");
                trial--;
                continue;
            }
            break;
        }
        checkTrials(trial , app);
        return password;
    }

    @Override
    public String signUpPhoneNumber(ApplicationImplementation app){
        int trial = 3;
        String phoneNumber = null;

        while (trial > 0) {

            System.out.println("Please enter your phone:");
            phoneNumber = scanner.nextLine().trim();
            phoneNumber = "+2" + phoneNumber ;

            if (!accountValidationImplementation.checkPhoneNumber(phoneNumber, eWallet)) {
                System.out.println("Invalid phone number!");
                trial--;
                continue;
            }
            break;
        }
        checkTrials(trial , app);
        return phoneNumber;
    }

    @Override
    public int  signUpAge(ApplicationImplementation app){
        int trial = 3 , age = 0;
        while (trial > 0) {
            try{
                System.out.println("Please enter your age:");
                age = scanner.nextInt();
                scanner.nextLine();

                if (!accountValidationImplementation.checkAge(age)) {
                    System.out.println("Invalid age! Must be ≥ 18.");
                    trial--;
                    continue;
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("Invalid age! .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        checkTrials(trial , app);
        return age;
    }

    @Override
    public void checkTrials(int trial , ApplicationImplementation app){
        if (trial == 0){
            System.out.println("You have tried 3 times .. try again later");
            app.startApplication();
        }
    }

    @Override
    public String loginName(ApplicationImplementation app){
        int trial = 3;
        String accountName = null;
        while(trial > 0) {
            System.out.println("Enter your account name: ");
            accountName = scanner.nextLine();
            if (!accountValidationImplementation.isUsernameExist(accountName, eWallet)) {
                System.out.println(" Username does not exist!");
                trial--;
                continue;
            }
            break;
        }
        checkTrials(trial , app);
        return  accountName;
    }

    @Override
    public Account loginPassword(String accountName , ApplicationImplementation app){
        int trial = 3;
        String accountPassword ;
        account = eWallet.getAccountByUsername(accountName);

        while(trial > 0) {
            System.out.println("Enter your account password: ");
            accountPassword = scanner.nextLine();

            if (!account.getPassword().equals(accountPassword)) {
                System.out.println(" Incorrect password!");
                trial--;
                continue;
            }
            break;
        }
        checkTrials(trial , app);
        return account;
    }

    @Override
    public void anotherService(ApplicationImplementation app , Account  account){
        System.out.println("Do you want to another service?" + "\n" + "yes or no ");
        String ans = scanner.nextLine();
        if( ans.equalsIgnoreCase("yes") ){
            app.startApplicationFeatures(account);
        }
        logout(app , account);
    }

    @Override
    public void showHistory(Account account){
        System.out.println(account.getHistory().reversed());
    }

    @Override
    public void adminDeleteAccount(){
        int trial = 3;
        String accountName ;
        while(trial > 0) {
            System.out.println(" Enter account name you want to delete: ");
            accountName = scanner.nextLine();
            if (!accountValidationImplementation.isUsernameExist(accountName, eWallet) || accountName.equals("admin")) {
                System.out.println(" Username does not exist!");
                trial--;
                continue;
            }
            account = eWallet.getAccountByUsername(accountName);
            eWallet.deleteAccount(account);
            System.out.println("Account deleted successfully!");
            break;
        }
        if (trial == 0) {
            System.out.println("You have tried 3 times .. no accounts have been deleted ");
        }
    }

    @Override
    public void adminLogout(ApplicationImplementation app){
        System.out.println("See you Later :)");
        app.startApplication();
    }

    @Override
    public void adminShowAccountDetails() {
        int trial = 3;
        String accountName ;
        while(trial > 0) {
            System.out.println("Enter account name you want to show its details: ");
            accountName = scanner.nextLine();
            if(!accountValidationImplementation.isUsernameExist(accountName, eWallet) || accountName.equals("admin")) {
                System.out.println(" Username does not exist!");
                trial--;
                continue;
            }
            account = eWallet.getAccountByUsername(accountName);
            showDataWithOutPassword(account);
            System.out.println("The password of the account you want to show is: " + " " + account.getPassword());
            break;
        }
        if (trial == 0) {
            System.out.println("You have tried 3 times .. no accounts have been shown ");
        }
    }

    @Override
    public void adminShowAccountHistory() {
        int trial = 3;
        String accountName ;
        while(trial > 0) {
            System.out.println("Enter account name you want to show its history: ");
            accountName = scanner.nextLine();
            if(!accountValidationImplementation.isUsernameExist(accountName, eWallet) || accountName.equals("admin")) {
                System.out.println(" Username does not exist!");
                trial--;
                continue;
            }
            account = eWallet.getAccountByUsername(accountName);
            showHistory(account);
            break;
        }
        if (trial == 0) {
            System.out.println("You have tried 3 times .. no accounts have been shown ");
        }
    }

    @Override
    public void adminAnotherService(Account account ,  ApplicationImplementation app){
        System.out.println("Do you want to another service?" + "\n" + "yes or no ");
        String ans = scanner.nextLine();
        if( ans.equalsIgnoreCase("yes") ){
            app.startAdminFeatures(account);
        }
        adminLogout(app);
    }

}
