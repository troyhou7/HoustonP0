package dev.houston.controllers;

import com.google.gson.Gson;
import dev.houston.daos.AccountDaoLocal;
import dev.houston.daos.AccountDaoPostgres;
import dev.houston.daos.ClientDaoLocal;
import dev.houston.daos.ClientDaoPostgres;
import dev.houston.entities.Account;
import dev.houston.entities.Client;
import dev.houston.services.AccountService;
import dev.houston.services.AccountServiceImpl;
import dev.houston.services.ClientService;
import dev.houston.services.ClientServiceImpl;
import io.javalin.http.Handler;

import java.util.Set;

public class AccountController {

    private AccountService accountService = new AccountServiceImpl(new AccountDaoPostgres());
    // to check if client exists
    private ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private static Gson gson = new Gson();


    public Handler getAllAccountsHandler = (ctx) -> {
        Set<Account> allAccounts = this.accountService.getAllAccounts();
        String accountsJSON = gson.toJson(allAccounts);
        ctx.result(accountsJSON);
    };

    // GET /clients/12/accounts/13 => get account 13 for client 12
    public Handler getAccountByIdHandler = (ctx) -> {
        int accountId = Integer.parseInt(ctx.pathParam("aid"));
        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        Account account = this.accountService.getAccountsById(accountId);
        Client client = this.clientService.getClientsById(clientId);
        // Check if account and client exist
        if(account == null){
            ctx.result("Account not found");
            ctx.status(404);
        }else if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }else{
            // check if account actually belongs to client
            if(account.getClientId() == clientId){
                String accountJSON = gson.toJson(account);
                ctx.result(accountJSON);
            }else{
                ctx.result("Selected account does not belong to selected client");
                ctx.status(400);
            }
        }
    };

    // GET /clients/12/accounts => get all of client 12's accounts
    // GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan=400 => get all accounts for client 7 between 400 and 2000
    public Handler getAccountsByClientIdHandler = (ctx) -> {
        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        Client client = this.clientService.getClientsById(clientId);
        // Check if client exists
        if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }else {
            Double amountLessThan = Double.parseDouble(ctx.queryParam("amountLessThan","-1"));
            Double amountGreaterThan = Double.parseDouble(ctx.queryParam("amountGreaterThan","-1"));
            Set<Account> clientAccounts = this.accountService.getAccountsByClientId(clientId);
            // if no query params, return all of this client's accounts
            if(amountLessThan == -1 && amountGreaterThan == -1){
                String accountsJSON = gson.toJson(clientAccounts);
                ctx.result(accountsJSON);
            }else{ // pass in client accounts, get back accounts within balance range specified
                Set<Account> selectedAccounts = this.accountService.getAccountsOfBalance(clientAccounts, amountLessThan, amountGreaterThan);
                String selectedAccountsJSON = gson.toJson(selectedAccounts);
                if(selectedAccounts == null){
                    ctx.result("Invalid Arguments");
                    ctx.status(400);
                }else{
                    ctx.result(selectedAccountsJSON);
                }
            }
        }
    };

    // POST /clients/5/accounts => create a new account for client 5
    public Handler createAccountHandler = (ctx) -> {
        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        Client client = this.clientService.getClientsById(clientId);
        if( client == null ){
            ctx.result("Client not found");
            ctx.status(404);
        }else {
            String body = ctx.body();
            Account account = gson.fromJson(body, Account.class);
            account.setClientId(clientId);
            this.accountService.createAccount(account);

            String json = gson.toJson(account);
            ctx.result(json);
            ctx.status(201);
        }
    };

    // PUT /clients/12/accounts/2 => update account 2 for client 12
    public Handler updateAccountHandler = (ctx) -> {
        int accountId = Integer.parseInt(ctx.pathParam("aid"));
        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        Account oldAccount = this.accountService.getAccountsById(accountId);
        Client client = this.clientService.getClientsById(clientId);
        // check if account and client exists
        if(oldAccount == null){
            ctx.result("Account not found");
            ctx.status(404);
        }else if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }else {
            String body = ctx.body();
            Account updatedAccount = gson.fromJson(body, Account.class);
            updatedAccount.setId(accountId);
            updatedAccount.setClientId(clientId);
            this.accountService.updateAccount(updatedAccount);
            ctx.result("Account updated");
        }
    };

    // DELETE /clients/11/accounts/14 => delete account 14 for client 11
    public Handler deleteAccountHandler = (ctx) -> {
        int accountId = Integer.parseInt(ctx.pathParam("aid"));
        int clientId = Integer.parseInt(ctx.pathParam("cid"));
        Account account = this.accountService.getAccountsById(accountId);
        Client client = this.clientService.getClientsById(clientId);
        // check if account and client exists
        if(account == null){
            ctx.result("Account not found");
            ctx.status(404);
        }else if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }else {
            boolean deleted = this.accountService.deleteAccountById(accountId);
            if(deleted){
                ctx.result("Account deleted");
            }else{
                ctx.result("Could not delete account " + accountId);
                ctx.status(400);
            }
        }
    };
}
