package model;

import java.util.HashMap;
import java.util.Map;

/*
 * Using singleton design pattern
 */
public class EWallet {
    private final String name = "EraaSoft EWallet";
    private Map<String, Account> accounts = new HashMap<>();

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

    // Add account by username
    public void addAccount(Account account) {
        accounts.put(account.getName(), account);
    }

    // Delete account by username
    public void deleteAccount(String username) {
        accounts.remove(username);
    }

    // Check if username exists
    public boolean isUsernameTaken(String username) {
        return accounts.containsKey(username);
    }

    // Get account by username
    public Account getAccountByUsername(String username) {
        return accounts.get(username);
    }

    // Check if phone number exists
    public boolean isPhoneNumberTaken(String phone) {
        return accounts.values().stream()
                .anyMatch(account -> phone.equals(account.getPhoneNumber()));
    }
}