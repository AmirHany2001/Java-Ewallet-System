package service.Imp;

import model.Account;
import service.ApplicationService;
import model.EWallet;

import java.util.InputMismatchException;
import java.util.Scanner;



public class ApplicationImplementation implements ApplicationService {

    AccountImplementation accountImplementation = new AccountImplementation();
    Scanner scanner = new Scanner(System.in);


    @Override
    public void exit() {
        System.out.println("See you next time!");
        System.exit(0);
    }

    @Override
    public void startApplicationFeatures(Account account) {
        int choice , trial = 3;

        System.out.println("Welcome to the Application features");

        while(trial > 0){
            try{
                System.out.println("please choose number from the following:");
                System.out.println("""
                    1. Deposit                   2. Withdrawal              3.TransferMoney\s
                    4. History                   5. ChangePassword          6.ShowAccountDetails\s
                    7. DeleteAccount             8. Logout\s""");

                choice = scanner.nextInt();
                scanner.nextLine();

                    switch (choice){
                        case 1:
                            accountImplementation.deposit(account);
                            break;
                        case 2:
                            accountImplementation.withdraw(account);
                            break;
                        case 3:
                            accountImplementation.transfer(account);
                            break;
                        case 4:
                            accountImplementation.showHistory(account);
                            break;
                        case 5:
                            accountImplementation.changePassword(account);
                            break;
                        case 6:
                            accountImplementation.showAccountDetails(account);
                            break;
                        case 7:
                            accountImplementation.deleteAccount(account , this);
                            break;
                        case 8:
                            accountImplementation.logout(this , account);
                            break;
                        default:
                            System.out.println("Invalid choice");
                            trial--;
                            continue;
                    }
                    accountImplementation.anotherService(this ,  account);

            }catch (InputMismatchException e) {
                System.out.println("Invalid input .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        if (trial ==0){
            System.out.println("Too match wrong choices ... login again later");
            accountImplementation.logout(this , account);
        }
    }

    @Override
    public void startApplication() {
        EWallet eWallet = EWallet.getInstance();
        eWallet.addAccount(new Account.Builder().setPassword("ADMIN").setName("admin").build());

        int choice , trial = 3;

        System.out.println("Welcome to" + eWallet.getName());

        while(trial>0){
            try{
                System.out.println("please choose number from the following:");
                System.out.println(" 1. login       2. signUp    3. exitProgram ");

                choice = scanner.nextInt();
                scanner.nextLine();
                    switch (choice) {
                        case 1:
                            accountImplementation.login(this);
                            break;
                        case 2:
                            accountImplementation.signUp(this);
                            break;
                        case 3:
                            exit();
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            trial--;
                    }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        if (trial==0){
            System.out.println("Too match wrong choices .. the program will exit");
            exit();
        }
    }

    @Override
    public void startAdminFeatures(Account account) {
        int choice , trial = 3;
        System.out.println("Welcome to Admin Panel ");

        while(trial>0){
            try{
                System.out.println("please choose number from the following:");
                System.out.println(" 1. DeleteAccount  2. ShowAccountDetails  3.ShowAccountHistory  4. Logout");

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                         accountImplementation.adminDeleteAccount();
                        break;
                    case 2:
                         accountImplementation.adminShowAccountDetails();
                        break;
                    case 3:
                         accountImplementation.adminShowAccountHistory();
                        break;
                    case 4:
                         accountImplementation.adminLogout(this);
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        trial--;
                        continue;
                }
                accountImplementation.adminAnotherService(account , this);
            }catch (InputMismatchException e) {
                System.out.println("Invalid input .. try again");
                trial--;
                scanner.nextLine();
            }
        }
        if(trial==0){
            System.out.println("Too match wrong choices ... login again later");
            accountImplementation.adminLogout(this);
        }
    }
}
