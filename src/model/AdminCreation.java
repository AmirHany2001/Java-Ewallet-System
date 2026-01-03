package model;


public class AdminCreation {
    static EWallet eWallet = EWallet.getInstance();

    public static void createAdmin(){
        eWallet.addAccount(new Account.Builder().setName("admin").setPassword("admin").build());
    }

}
