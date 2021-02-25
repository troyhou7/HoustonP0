package dev.houston.daotests;

import dev.houston.daos.AccountDAO;
import dev.houston.daos.AccountDaoLocal;
import dev.houston.daos.AccountDaoPostgres;
import dev.houston.entities.Account;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTest {

    private static AccountDAO adao = new AccountDaoLocal();
    private static Account testAcc = null;

    @Test
    @Order(1)
    void create_account(){
        Account a1 = new Account(0, 100, 0);
        adao.createAccount(a1);

        testAcc = a1;

        Assertions.assertNotEquals(0,a1.getId());
        //System.out.println(a1);
    }

    @Test
    @Order(2)
    void get_all_accounts(){
        Account a1 = new Account(0, 100, 0);
        Account a2 = new Account(0, 500, 0);
        Account a3 = new Account(0, 200, 0);

        adao.createAccount(a1);
        adao.createAccount(a2);
        adao.createAccount(a3);

        Set<Account> accountSet = adao.getAllAccounts();

        Assertions.assertTrue(accountSet.size() > 2);

        System.out.println(accountSet);
    }

    @Test
    @Order(3)
    void get_account_by_id(){
        int id = testAcc.getId();
        Account account = adao.getAccountById(id);
        Assertions.assertEquals(testAcc.getId(), account.getId());

        System.out.println(account);
    }

    @Test
    @Order(4)
    void update_account(){
        Account a1 = new Account(testAcc.getId(),testAcc.getBalance(), testAcc.getClientId());
        testAcc.withdraw(54);
        testAcc = adao.updateAccount(testAcc);
        Assertions.assertNotEquals(a1.getBalance(),testAcc.getBalance());
    }

    @Test
    @Order(5)
    void delete_account_by_id(){
        boolean deleted = adao.deleteAccountById(testAcc.getId());
        Assertions.assertTrue(deleted);
    }

}
