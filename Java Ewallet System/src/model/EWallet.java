package model;

import java.util.ArrayList;
import java.util.List;

/*
* Using singleton design pattern
*
*/

public class EWallet {
    private final String name = "EraaSoft EWallet";
    private List<Account> accounts = new ArrayList<>();



    private static EWallet instance = null;

    private EWallet() {}

    public static EWallet getInstance() {
        if (instance == null) {
            instance = new EWallet();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void deleteAccount(Account account) {
        accounts.remove(account);
    }

    public boolean isUsernameTaken(String name) {
        return accounts.stream()
                .anyMatch(account -> account.getName().equals(name));
    }

    public Account getAccountByUsername(String name) {
        return accounts.stream()
                .filter(account -> account.getName().equals(name))
                .findFirst().orElse(null);
    }


    public boolean isPhoneNumberTaken(String phone) {
        return accounts.stream()
                .anyMatch(account -> phone.equals(account.getPhoneNumber()));
    }


}

