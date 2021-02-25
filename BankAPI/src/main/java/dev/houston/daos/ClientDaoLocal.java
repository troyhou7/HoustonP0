package dev.houston.daos;

import dev.houston.entities.Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientDaoLocal implements ClientDAO{

    private static Map<Integer, Client> clientTable = new HashMap<Integer, Client>();
    private static int idMaker = 0;

    @Override
    public Client createClient(Client client) {
        client.setId(++idMaker);
        clientTable.put(client.getId(), client);
        return client;
    }

    @Override
    public Client getClientById(int id) {
        return clientTable.get(id);
    }

    @Override
    public Set<Client> getAllClients() {
        Set<Client> allClients = new HashSet<Client>(clientTable.values());
        return allClients;
    }

    @Override
    public Client updateClient(Client client) {
        return clientTable.put(client.getId(), client);
    }

    @Override
    public boolean deleteClientById(int id) {
        Client client = clientTable.remove(id);
        if (client == null){
            return false;
        }else {
            return true;
        }
    }
}
