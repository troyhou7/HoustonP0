package dev.houston.services;

import dev.houston.daos.AccountDAO;
import dev.houston.entities.Account;

import java.util.HashSet;
import java.util.Set;

public class AccountServiceImpl implements AccountService{

    private AccountDAO adao;

    public AccountServiceImpl(AccountDAO adao){
        this.adao = adao;
    }

    @Override
    public Account createAccount(Account account) {

        return this.adao.createAccount(account);
    }

    @Override
    public Set<Account> getAllAccounts() {
        return this.adao.getAllAccounts();
    }

    @Override
    public Set<Account> getAccountsByClientId(int clientId) {
        Set<Account> clientAccounts = new HashSet<Account>();
        for(Account a : this.adao.getAllAccounts()){
            if(a.getClientId() == clientId){
                clientAccounts.add(a);
            }
        }
        return clientAccounts;
    }

    @Override
    public Account getAccountsById(int id) {
        return this.adao.getAccountById(id);
    }

    // Gets/returns accounts of balance between a max and min value from set passed in
    @Override
    public Set<Account> getAccountsOfBalance(Set<Account> accounts, double max, double min){

        Set<Account> balanceQuery = new HashSet<Account>();
        // There is a max and a min
        if( max >= 0 && min >= 0 ){
            if(max >= min){
                for(Account a : accounts){
                    if(a.getBalance() >= min && a.getBalance() <= max){
                        balanceQuery.add(a);
                    }
                }
            }else{
                System.out.println("Invalid max and min balance values.  Min cannot be higher than Max");
                return null;
            }
        }else if( max >= 0 && min < 0 ){    // There is just a max
            for(Account a : accounts){
                if(a.getBalance() < max){
                    balanceQuery.add(a);
                }
            }
        }else if( min >= 0 && max < 0 ){    // There is just a min
            for(Account a : accounts){
                if(a.getBalance() > min){
                    balanceQuery.add(a);
                }
            }
        }else{
            System.out.println("Invalid max and min values");
            return null;
        }
        return balanceQuery;
    }


    @Override
    public Account updateAccount(Account account) {
        return this.adao.updateAccount(account);
    }

    @Override
    public boolean deleteAccountById(int id) {
        return this.adao.deleteAccountById(id);
    }

    /*
    // This is irrelevant due to 'on delete cascade'
    // for use when a client is being deleted. All of their accounts must be deleted
    @Override
    public int deleteAccountsByClientId(int ClientId) {
        int deletedAccounts = 0;
        for(Account a : this.adao.getAllAccounts()){
            if(a.getClientId() == ClientId){
                if(this.adao.deleteAccountById(a.getId())) {
                    deletedAccounts++;
                }
            }
        }
        return deletedAccounts;
    }
     */
}
