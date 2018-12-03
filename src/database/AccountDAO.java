package database;

import Account.User;
import account.Administrator;

public interface AccountDAO {
    Administrator findAccount(String username, String password);
    void createAccount(String username, String password, String name);
}
