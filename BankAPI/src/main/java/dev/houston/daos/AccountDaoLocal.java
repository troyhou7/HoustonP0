package dev.houston.daos;

import dev.houston.entities.Account;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccountDaoLocal implements AccountDAO{

    private Map<Integer,Account> accountTable = new HashMap<Integer,Account>();
    private static int idMaker = 0;

    @Override
    public Account createAccount(Account account) {
        account.setId(++idMaker);
        accountTable.put(account.getId(),account);
        return account;
    }

    @Override
    public Account getAccountById(int id) {
        return accountTable.get(id);
    }

    @Override
    public Set<Account> getAllAccounts() {
        Set<Account> accSet = new HashSet<Account>(accountTable.values());
        return accSet;
    }

    @Override
    public Account updateAccount(Account account) {
        return accountTable.put(account.getId(),account);
    }

    @Override
    public boolean deleteAccountById(int id) {
        Account account = accountTable.remove(id);
        if(account == null){
            return false;
        }else{
            return true;
        }
    }
}
