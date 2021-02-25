package dev.houston.services;

import dev.houston.daos.ClientDAO;
import dev.houston.entities.Client;

import java.util.HashSet;
import java.util.Set;

public class ClientServiceImpl implements ClientService{

    private ClientDAO cdao;

    public ClientServiceImpl(ClientDAO cdao){
        this.cdao = cdao;
    }

    @Override
    public Client registerClient(Client client) {
        return this.cdao.createClient(client);
    }

    @Override
    public Set<Client> getAllClients() {
        return this.cdao.getAllClients();
    }

    // Mock
    @Override
    public Set<Client> getClientsByName(String name) {
        Set<Client> allClients = this.getAllClients();
        Set<Client> selectedClients = new HashSet<Client>();

        for(Client c : allClients){
            if(c.getName().toLowerCase().contains(name.toLowerCase())){
                selectedClients.add(c);
            }
        }
        return selectedClients;
    }

    @Override
    public Client getClientsById(int id) {
        return this.cdao.getClientById(id);
    }

    @Override
    public Client updateClient(Client client) {
        return this.cdao.updateClient(client);
    }

    @Override
    public boolean deleteClientById(int id) {
        return this.cdao.deleteClientById(id);
    }
}
