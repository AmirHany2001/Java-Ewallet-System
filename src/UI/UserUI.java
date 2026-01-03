package UI;

import model.Account;
import model.History;
import model.EWallet;
import service.Imp.AccountImplementation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;


public class UserUI {

    Scanner scanner = new Scanner(System.in);
    AccountImplementation accountImplementation = AccountImplementation.getInstance();
    EWallet eWallet = EWallet.getInstance();
    Account account;


    public void depositUI(Account account) {
        int trial = 3;
        while (trial > 0) {
            try {
                System.out.println("Please enter your amount: ");
                int amount = scanner.nextInt();
                scanner.nextLine();
                accountImplementation.deposit(account, amount); // call service method
                System.out.println("New balance: " + account.getBalance());
                return;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, try again.");
                scanner.nextLine();
                trial--;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }
        account.addHistory(new History.Builder().setTime(LocalTime.now())
                .setDate(LocalDate.now()).setOperation("failedDeposit")
                .setName(account.getName()).build());
        System.out.println("Deposit failed after 3 attempts.");
    }

    public void withdrawUI(Account account) {
        int trial = 3;
        while (trial > 0) {
            try {
                System.out.println("Please enter your amount: ");
                int amount = scanner.nextInt();
                scanner.nextLine();

                accountImplementation.withdraw(account, amount); // call the service method
                System.out.println("New balance: " + account.getBalance());
                return; // success, exit loop

            } catch (InputMismatchException e) {
                System.out.println("Invalid input, try again.");
                scanner.nextLine();
                trial--;

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }

        // If all trials fail
        System.out.println("Withdraw failed after 3 attempts.");
        account.addHistory(new History.Builder()
                .setOperation("failedWithdraw")
                .setName(account.getName())
                .setDate(LocalDate.now())
                .setTime(LocalTime.now())
                .build());
    }

    public void transferUI(Account senderAccount) {
        int trial = 3 , amount = 0;
        while (trial > 0) {
            try {
                System.out.println("Enter transfer amount: ");
                amount = scanner.nextInt();
                scanner.nextLine();

                amount = accountImplementation.transferWithdraw(senderAccount, amount);

                System.out.println("Enter receiver account name: ");
                String receiverName = scanner.nextLine();

                if (accountImplementation.transferDeposit(senderAccount, receiverName, amount)) {
                    System.out.println("Transfer successful. Sender balance: " + senderAccount.getBalance());
                    return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, try again.");
                scanner.nextLine();
                trial--;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }

        System.out.println("Transfer failed after 3 attempts.");

        senderAccount.addBalance(amount);

        System.out.println("Your money has been restored");

        senderAccount.addHistory(new History.Builder()
                .setOperation("failedTransfer")
                .setName(senderAccount.getName())
                .setDate(LocalDate.now())
                .setTime(LocalTime.now())
                .build());
    }

    public void showAccountDetailsUI(Account account){
        String approve , password;;
        int trial = 3 ;
        while (trial > 0) {
            System.out.println("Do you need to show your Password ?" +"\n" + "yes or no ");
            approve = scanner.nextLine().trim();
            if (approve.equalsIgnoreCase("yes")){
                System.out.println("Enter your Password");
                password = scanner.nextLine();
                if(account.getPassword().equals(password)){
                    accountImplementation.printAccountDetails(account, true);
                    return;
                }
                else {
                    System.out.println("Password doesn't match");
                    trial--;
                }
            }
            else {
                accountImplementation.printAccountDetails(account , false);
                return;
            }
        }
    }

    public void changePasswordUI(Account account) {
        int trial = 3;
        System.out.println("Enter your old password:");
        String oldPassword = scanner.nextLine();

        while (trial > 0) {
            System.out.println("Enter your new password:");
            String newPassword = scanner.nextLine();

            try {
                accountImplementation.changePassword(account, oldPassword, newPassword); // call business logic
                System.out.println("Your password has been changed successfully.");
                return; // success, exit loop
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }

        System.out.println("Password change failed after 3 attempts.");
        account.addHistory(new History.Builder()
                .setName(account.getName())
                .setTime(LocalTime.now())
                .setDate(LocalDate.now())
                .setOperation("failedChangePassword")
                .build());
    }

    public void showHistoryUI(Account account){
        System.out.println(accountImplementation.showHistory(account));
    }

    public Account signUpUI() {
        String name = null, password = null, phoneNumber = null ;
        int age = 0;

        // --- Name ---
        int trial = 3;
        while (trial > 0) {
            System.out.println("Enter your name:");
            name = scanner.nextLine();
            try {
                name = accountImplementation.signUpName(name);
                break; // valid, exit loop
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }
        if (trial == 0) {
            System.out.println("Too many failed attempts for name.");
            return null;
        }

        // --- Password ---
        trial = 3;
        while (trial > 0) {
            System.out.println("Enter your password:");
            password = scanner.nextLine();
            try {
                password = accountImplementation.signUpPassword(password);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }
        if (trial == 0) {
            System.out.println("Too many failed attempts for password.");
            return null;
        }

        // --- Phone ---
        trial = 3;
        while (trial > 0) {
            System.out.println("Enter your phone number:");
            phoneNumber = "+2" + scanner.nextLine();
            try {
                phoneNumber = accountImplementation.signUpPhoneNumber(phoneNumber);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }
        if (trial == 0) {
            System.out.println("Too many failed attempts for phone number.");
            return null;
        }

        // --- Age ---
        trial = 3;
        while (trial > 0) {
            System.out.println("Enter your age:");
            try {
                age = scanner.nextInt();
                age = accountImplementation.signUpAge(age);
                break;
            } catch (IllegalArgumentException e ) {
                scanner.nextLine();
                System.out.println(e.getMessage());
                trial--;
            }catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Not valid age");
                trial--;
            }
        }
        if (trial == 0) {
            System.out.println("Too many failed attempts for age.");
            return null;
        }

        // --- Build Account ---
        Account account = new Account.Builder()
                .setName(name)
                .setAge(age)
                .setPassword(password)
                .setPhoneNumber(phoneNumber)
                .build();

        eWallet.addAccount(account);
        account.addHistory(new History.Builder()
                .setOperation("signup")
                .setName(account.getName())
                .setTime(LocalTime.now())
                .setDate(LocalDate.now())
                .build());

        System.out.println("Your Account has been added successfully");
        return account;
    }

    public Account loginUI(){
        int trial = 3;
        String name , accountName , password;

        while (trial > 0) {
            System.out.println("Enter your name:");
            name = scanner.nextLine();
            try{
                accountName = accountImplementation.loginName(name);

                System.out.println("Enter your Password");
                password = scanner.nextLine();
                account =  accountImplementation.loginPassword(password , accountName);

                if(account.getName().equals("admin")){
                    return eWallet.getAccountByUsername("admin");
                }

                account.addHistory(new History.Builder().setOperation("newLogin").setName(account.getName())
                        .setTime(LocalTime.now()).setDate(LocalDate.now()).build());

                return account;
            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                trial--;
            }
        }
        return null;
    }



}
