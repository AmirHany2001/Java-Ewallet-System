package service;

import model.Account;
import UI.ApplicationUI;

public interface ApplicationService {
     boolean startApplicationFeatures(Account account , int choice) ;
     void exit();
     void startApplication(int choice , ApplicationUI ui);

     boolean startAdminFeatures(Account account , int choice);

}
