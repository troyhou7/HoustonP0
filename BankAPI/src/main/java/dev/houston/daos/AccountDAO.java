package dev.houston.daos;

import dev.houston.entities.Account;

import java.util.Set;

public interface AccountDAO {

    Account createAccount(Account account);

    Account getAccountById(int id);
    Set<Account> getAllAccounts();

    Account updateAccount(Account account);

    boolean deleteAccountById(int id);

}
