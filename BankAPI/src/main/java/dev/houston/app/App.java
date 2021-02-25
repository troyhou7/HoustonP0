package dev.houston.app;

import dev.houston.controllers.AccountController;
import dev.houston.controllers.ClientController;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        ClientController clientController = new ClientController();
        AccountController accountController = new AccountController();

        // GET /clients => return all clients
        // GET /clients?nameContains=jimmy => only returns clients containing that name
        app.get("/clients", clientController.getAllClientsHandler);

        // GET /accounts => return ALL accounts
        // Theoretically for BANK use only
        app.get("/accounts", accountController.getAllAccountsHandler);

        // GET /clients/12 => get client with ID 12
        app.get("/clients/:cid", clientController.getClientByIdHandler);

        // GET /clients/12/accounts => get all of client 12's accounts
        // GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan=400 => get all accounts for client 7 between 400 and 2000
        app.get("/clients/:cid/accounts", accountController.getAccountsByClientIdHandler);

        // GET /clients/12/accounts/13 => get account 13 for client 12
        app.get("/clients/:cid/accounts/:aid", accountController.getAccountByIdHandler);


        // POST /clients => create a new client
        app.post("/clients", clientController.createClientHandler);
        // POST /clients/5/accounts => create a new account for client 5
        app.post("/clients/:cid/accounts", accountController.createAccountHandler);

        // PUT /clients/12 => update client 12
        app.put("/clients/:cid", clientController.updateClientHandler);
        // PUT /clients/12/accounts/2 => update account 2 for client 12
        app.put("clients/:cid/accounts/:aid", accountController.updateAccountHandler);

        // DELETE /clients/11 => delete client with ID 11
        app.delete("clients/:cid", clientController.deleteClientHandler);
        // DELETE /clients/11/accounts/14 => delete account 14 for client 11
        app.delete("/clients/:cid/accounts/:aid", accountController.deleteAccountHandler);

        // possible stretch goal
        // DELETE /clients/11/accounts => delete ALL accounts for client 11

        app.start(); //actually starts web server
    }
}

