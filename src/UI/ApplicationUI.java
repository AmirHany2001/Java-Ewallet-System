package UI;

import model.Account;
import model.EWallet;
import service.Imp.ApplicationImplementation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ApplicationUI {


    EWallet eWallet = EWallet.getInstance();
    Scanner scanner = new Scanner(System.in);
    ApplicationImplementation accImp = ApplicationImplementation.getInstance();

    public void startApplicationFeaturesUI(Account account){
        int choice , trial = 3;
        boolean flag ;

        System.out.println("Welcome to the Application features");
        while(trial > 0){
            System.out.println("please choose number from the following:");
            System.out.println("""
                    1. Deposit                   2. Withdrawal              3.TransferMoney\s
                    4. History                   5. ChangePassword          6.ShowAccountDetails\s
                    7. DeleteAccount             8. Logout\s""");

            try{
                choice = scanner.nextInt();
                scanner.nextLine();
                flag = accImp.startApplicationFeatures(account , choice);
                if(!flag){
                    startApplicationUI();
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input .. try again");
                trial--;
                scanner.nextLine();
            }
        }

    }

    public void startApplicationUI(){
        int choice , trial = 3;

        System.out.println("Welcome to" + eWallet.getName());
        while(trial > 0){
            System.out.println("please choose number from the following:");
            System.out.println(" 1. login       2. signUp    3. exitProgram ");
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
                accImp.startApplication(choice , this);
                System.out.println("No account was found");
                accImp.exit();
            }catch (InputMismatchException e) {
                System.out.println("Invalid input .. try again");
                trial--;
                scanner.nextLine();
            }
        }
    }

    public void startAdminFeaturesUI(Account account){
        int choice , trial = 3;
        boolean flag ;
        System.out.println("Welcome to Admin Panel ");

        while(trial > 0){
            System.out.println("please choose number from the following:");
            System.out.println(" 1. DeleteAccount  2. ShowAccountDetails  3.ShowAccountHistory  4. Logout");
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
                flag = accImp.startAdminFeatures(account , choice);
                if(!flag){
                    startApplicationUI();
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input .. try again");
                trial--;
                scanner.nextLine();
            }
        }
    }
}
