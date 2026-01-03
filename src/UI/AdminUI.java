package UI;

import service.Imp.AccountImplementation;

import java.util.Scanner;

public class AdminUI {
    Scanner scanner = new Scanner(System.in);
    AccountImplementation accountImplementation = AccountImplementation.getInstance();


    public void adminDeleteAccountUI(){
       int trial = 3;
       String accountName ;
       while(trial > 0){
           System.out.println(" Enter account name you want to delete: ");
           accountName = scanner.nextLine();
           try{
               accountImplementation.adminDeleteAccount(accountName);
               System.out.println("Account deleted successfully!");
               return;
           }catch (IllegalArgumentException e){
               System.out.println(e.getMessage());
               trial-- ;
           }
       }
       System.out.println("No accounts found to delete ");
    }

    public void adminShowAccountDetailsUI(){
        int trial = 3;
        String accountName ;
        while(trial > 0){
            System.out.println("Enter account name you want to show its details: ");
            accountName = scanner.nextLine();
            try{
                accountImplementation.adminShowAccountDetails(accountName);
                return;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                trial-- ;
            }
        }
        System.out.println("No accounts found to get its details");
    }

    public void adminShowAccountHistoryUI(){
        int trial = 3;
        String accountName ;
        while(trial > 0){
            System.out.println("Enter account name you want to show its history: ");
            accountName = scanner.nextLine();
            try{
                accountImplementation.adminShowAccountHistory(accountName);
                return;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                trial-- ;
            }
        }
        System.out.println("No accounts found to get its history");
    }



}
