package dev.houston.servicetests;

import dev.houston.daos.AccountDAO;
import dev.houston.daos.ClientDAO;
import dev.houston.entities.Account;
import dev.houston.entities.Client;
import dev.houston.services.AccountService;
import dev.houston.services.AccountServiceImpl;
import dev.houston.services.ClientService;
import dev.houston.services.ClientServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetAccountByClientIdTests {

    @Mock
    AccountDAO accountDAO = null;
    AccountService accountService = null;
    static Account testAcc = new Account(1,11,1);

    @BeforeEach
    void setUp(){
        Account a1 = new Account(1, 0, 1);
        Account a2 = new Account(2, 2, 1);
        Account a3 = new Account (3, 10,1);
        Account a4 = new Account (4, 50, 1);
        Account a5 = new Account(3,1000, 3);
        Account a6 = new Account(4,100, 2);
        Set<Account> accountSet = new HashSet<Account>();
        accountSet.add(a1);
        accountSet.add(a2);
        accountSet.add(a3);
        accountSet.add(a4);
        accountSet.add(a5);
        accountSet.add(a6);

        Mockito.when(accountDAO.getAllAccounts()).thenReturn(accountSet);
        this.accountService = new AccountServiceImpl(this.accountDAO);
    }
    @Test
    @Order(1)
    void get_accounts_by_client_id(){
        Set<Account> clientAccounts = new HashSet<Account>();
        clientAccounts = this.accountService.getAccountsByClientId(1);

        System.out.println(clientAccounts);
        Assertions.assertEquals(4,clientAccounts.size());
    }

    @Test
    @Order(2)
    void get_accounts_of_balance(){
        Set<Account> client1Accounts = accountService.getAccountsByClientId(1);
        System.out.println("Size of set before query::: "+ client1Accounts.size());
        //Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, 20,1);
        //Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, -1,101);
        Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, 20,-1);
        //Set<Account> queriedAccounts = accountService.getAccountsOfBalance(client1Accounts, 100,200);
        System.out.println("Size of queried set::: "+ queriedAccounts.size());

        Assertions.assertEquals(3,queriedAccounts.size());
        //Assertions.assertNull(queriedAccounts);
    }
}
