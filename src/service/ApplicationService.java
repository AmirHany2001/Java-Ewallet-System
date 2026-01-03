package service;

import model.Account;
import model.UI.ApplicationUI;
import service.Imp.ApplicationImplementation;

public interface ApplicationService {
     boolean startApplicationFeatures(Account account , int choice) ;
     void exit();
     void startApplication(int choice , ApplicationUI ui);

     boolean startAdminFeatures(Account account , int choice);

}
