package service.Imp;

import model.Account;
import model.History;
import UI.AdminUI;
import UI.ApplicationUI;
import service.ApplicationService;
import model.EWallet;
import UI.UserUI;

import java.time.LocalDate;
import java.time.LocalTime;


public class ApplicationImplementation implements ApplicationService {

    private static ApplicationImplementation instance = null;

    private ApplicationImplementation() {}

    public static ApplicationImplementation getInstance() {
        if (instance == null) {
            instance = new ApplicationImplementation();
        }
        return instance;
    }


    UserUI userUI = new UserUI();
    AdminUI adminUI = new AdminUI();
    EWallet eWallet = EWallet.getInstance();





    @Override
    public void exit() {
        System.out.println("See you next time!");
        System.exit(0);
    }

    @Override
    public boolean startApplicationFeatures(Account account , int choice) {
        boolean flag = true ;

        switch (choice){
            case 1:
                userUI.depositUI(account);
                break;
            case 2:
                userUI.withdrawUI(account);
                break;
            case 3:
                userUI.transferUI(account);
                break;
            case 4:
                userUI.showHistoryUI(account);
                break;
            case 5:
                userUI.changePasswordUI(account);
                break;
            case 6:
                userUI.showAccountDetailsUI(account);
                break;
            case 7:
                flag = false ;
                eWallet.deleteAccount(account.getName());
                System.out.println("Your account has been deleted successfully");
                break;
            case 8:
                flag = false ;
                System.out.println("See you Later :)");
                account.addHistory(new History.Builder().setName(account.getName()).setTime(LocalTime.now())
                        .setDate(LocalDate.now()).setOperation("logout").build());
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        return flag;
    }

    @Override
    public void startApplication(int choice , ApplicationUI ui) {
        Account account = null ;

        switch (choice) {
            case 1:
                account = userUI.loginUI();
                break;
            case 2:
                account = userUI.signUpUI();
                break;
            case 3:
                exit();
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        if(account == null){
            return;
        }
        if(account.getName().equals("admin")){
            ui.startAdminFeaturesUI(account);
        }

        ui.startApplicationFeaturesUI(account);
    }

    @Override
    public boolean startAdminFeatures(Account account , int choice) {
        boolean flag = true ;

        switch (choice) {
            case 1:
                 adminUI.adminDeleteAccountUI();
                break;
            case 2:
                 adminUI.adminShowAccountDetailsUI();
                break;
            case 3:
                adminUI.adminShowAccountHistoryUI();
                break;
            case 4:
                flag = false ;
                System.out.println("See you Later :)");
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        return flag;
    }
}
