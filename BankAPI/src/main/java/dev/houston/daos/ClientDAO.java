package dev.houston.daos;

import dev.houston.entities.Client;

import java.util.Set;

public interface ClientDAO {

    Client createClient(Client client);

    Client getClientById(int id);
    Set<Client> getAllClients();

    Client updateClient(Client client);

    boolean deleteClientById(int id);



}
