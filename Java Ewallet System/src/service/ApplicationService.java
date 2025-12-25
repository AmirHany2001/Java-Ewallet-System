package service;

import model.Account;

public interface ApplicationService {
     void startApplicationFeatures(Account account) ;
     void exit();
     void startApplication();

     void startAdminFeatures(Account account);
}
