package dev.houston.controllers;

import com.google.gson.Gson;
import dev.houston.daos.AccountDaoPostgres;
import dev.houston.daos.ClientDaoLocal;
import dev.houston.daos.ClientDaoPostgres;
import dev.houston.entities.Client;
import dev.houston.services.AccountService;
import dev.houston.services.AccountServiceImpl;
import dev.houston.services.ClientService;
import dev.houston.services.ClientServiceImpl;
import io.javalin.http.Handler;

import java.util.Set;

public class ClientController {
    private static Gson gson = new Gson();
    private ClientService  clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private AccountService accountService = new AccountServiceImpl(new AccountDaoPostgres());

    // GET /clients
    public Handler getAllClientsHandler = (ctx) -> {
        String nameContains = ctx.queryParam("nameContains", "NONE");
        if(nameContains.equals("NONE")){
            Set<Client> allClients = this.clientService.getAllClients();
            String clientsJSON = gson.toJson(allClients);
            ctx.result(clientsJSON);
        }else{
            Set<Client> clients = this.clientService.getClientsByName(nameContains);
            String selectedClientsJSON = gson.toJson(clients);
            ctx.result(selectedClientsJSON);
        }
    };

    // GET /clients/:id
    public Handler getClientByIdHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("cid"));
        Client client = this.clientService.getClientsById(id);
        if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }else {
            String clientJSON = gson.toJson(client);
            ctx.result(clientJSON);
        }
    };

    // POST /clients
    public Handler createClientHandler = (ctx) ->{
        String body = ctx.body();
        Client client = gson.fromJson(body, Client.class); // turn that JSON into a Java Book Object
        this.clientService.registerClient(client);
        String json = gson.toJson(client);
        ctx.result(json);
        ctx.status(201);
    };

    // PUT /clients/20 => updates client 20
    public Handler updateClientHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("cid"));
        Client client = this.clientService.getClientsById(id);
        if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }else {
            String body = ctx.body();
            Client client1 = gson.fromJson(body, Client.class);
            client1.setId(id); //often redundant but the path id takes precedent
            this.clientService.updateClient(client1);

            ctx.result("Client updated");
        }
    };

    // DELETE /clients/21 => deletes client 21
    public Handler deleteClientHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("cid"));
        // on delete cascade foreign key constraint on the account table deletes accounts associated with client being deleted
        // There cannot exist an account without a client
        Client client = this.clientService.getClientsById(id);
        if(client == null){
            ctx.result("Client not found");
            ctx.status(404);
        }else {
            int deletedAccounts = this.accountService.getAccountsByClientId(id).size();
            boolean deleted = this.clientService.deleteClientById(id);
            if (deleted) {
                if (deletedAccounts > 0) {
                    ctx.result("Client was deleted, along with their " + deletedAccounts + " account(s).");
                } else {
                    ctx.result("Client was deleted");
                }
            } else {
                ctx.result("Could not delete");
                ctx.status(404);
            }
        }
    };

}

