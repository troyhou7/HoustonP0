package dev.houston.services;

import dev.houston.entities.Client;

import java.util.Set;

public interface ClientService {

    Client registerClient(Client client);

    Set<Client> getAllClients();
    Set<Client> getClientsByName(String name);
    Client getClientsById(int id);

    Client updateClient(Client client);

    boolean deleteClientById(int id);
}
