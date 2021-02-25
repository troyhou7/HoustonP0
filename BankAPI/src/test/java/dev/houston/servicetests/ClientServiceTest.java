package dev.houston.servicetests;

import dev.houston.daos.ClientDaoLocal;
import dev.houston.entities.Client;
import dev.houston.services.ClientService;
import dev.houston.services.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;


public class ClientServiceTest {

    private static ClientService clientService = new ClientServiceImpl(new ClientDaoLocal());

    @Test
    void register_client(){
        Client client = new Client("Jimmy","John", 0);
        clientService.registerClient(client);
        System.out.println(client);

        Assertions.assertNotEquals(0,client.getId());
    }

}
