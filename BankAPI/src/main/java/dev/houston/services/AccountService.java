package dev.houston.services;

import dev.houston.entities.Account;

import java.util.Set;

public interface AccountService {

    Account createAccount(Account account);

    Set<Account> getAllAccounts();
    // get set of accounts owned by a specific client
    Set<Account> getAccountsByClientId(int clientId);
    Account getAccountsById(int id);
    // get a client's accounts within a balance range
    Set<Account> getAccountsOfBalance(Set<Account> accounts, double max, double min);

    // changes to balance
    Account updateAccount(Account account);

    // delete account by id
    boolean deleteAccountById(int id);

    // Irrelevant due to 'on delete cascade'
    //int deleteAccountsByClientId(int clientId);
}
