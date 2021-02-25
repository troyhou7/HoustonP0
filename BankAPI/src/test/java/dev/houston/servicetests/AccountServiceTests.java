package dev.houston.servicetests;

import dev.houston.daos.AccountDaoLocal;
import dev.houston.entities.Account;
import dev.houston.services.AccountService;
import dev.houston.services.AccountServiceImpl;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTests {
    static Account testAcc = null;
    static AccountService accountService = new AccountServiceImpl(new AccountDaoLocal());

    @Test
    @Order(1)
    void create_account(){
        Account a1 = new Account(0,11,1);
        accountService.createAccount(a1);
        testAcc = a1;
        System.out.println(testAcc);
        Assertions.assertNotEquals(0,testAcc.getId());
    }

    @Test
    @Order(2)
    void get_all_accounts(){
        Account a1 = new Account(0,120,1);
        Account a2 = new Account(0,5,1);
        Account a3 = new Account(0,100,1);
        Account a4 = new Account(0,90,1);
        Account a5 = new Account(0,10,1);
        Account a6 = new Account(0,100,3);
        Account a7 = new Account(0,54,4);
        accountService.createAccount(a1);
        accountService.createAccount(a2);
        accountService.createAccount(a3);
        accountService.createAccount(a4);
        accountService.createAccount(a5);
        accountService.createAccount(a6);
        accountService.createAccount(a7);

        Set<Account> accounts = new HashSet<Account>();
        accounts = accountService.getAllAccounts();

        System.out.println("Accounts Set::: " + accounts);
        Assertions.assertTrue(accounts.size() > 2);
    }

    @Test
    @Order(3)
    void update_account(){
        Account a1 = new Account(testAcc.getId(),testAcc.getBalance(), testAcc.getClientId());

        //cannot deposit or withdraw 0 or negative amount
        // testAcc.deposit(-2);
        // testAcc.withdraw(-2);

        //testAcc.withdraw(4);
        testAcc.deposit(4);
        testAcc = accountService.updateAccount(testAcc);

        System.out.println("Updated Balance::: " + testAcc.getBalance());
        Assertions.assertNotEquals(a1.getBalance(),testAcc.getBalance());
    }

    @Test
    @Order(4)
    void get_accounts_of_balance(){
        Set<Account> client1Accounts = accountService.getAccountsByClientId(1);
        //System.out.println("Size of set before query::: "+ client1Accounts.size());
        // balance between
        //Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, 200,100);

        // Just greater than
        //Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, -1,101);

        // Just less than
        Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, 20,-1);

        // Invalid args, returns null
        //Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, 100,200);
        //System.out.println("Size of set after query::: "+ queriedAccounts.size());

        Assertions.assertEquals(3,queriedAccounts.size());
        //Assertions.assertNull(queriedAccounts);
    }

    // Delete and Get accounts by ID are tested in the DAO. Their implementation did not change in service layer

    /*
    // This was made irrelevant because of 'on delete cascade'
    @Test
    @Order(5)
    void delete_accounts_by_client_id(){
        // shows that 6 accounts were deleted for client 1
        System.out.println("CLIENT 1 SET BEFORE DELETE::: "+ accountService.getAccountsByClientId(1));
        int x = accountService.deleteAccountsByClientId(1);
        System.out.println("CLIENT 1 SET AFTER DELETE::: " + accountService.getAccountsByClientId(1));

        Assertions.assertEquals(6,x);
    }

     */
}
