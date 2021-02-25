package dev.houston.servicetests;

import dev.houston.daos.ClientDAO;
import dev.houston.entities.Client;
import dev.houston.services.ClientService;
import dev.houston.services.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class SearchClientByNameTests {

    @Mock
    ClientDAO clientDAO = null;
    ClientService clientService = null;

    @BeforeEach
    void setUp(){
        Client c1 = new Client("James", "Brown", 0);
        Client c2 = new Client("Richard", "Levitt", 0);
        Client c3 = new Client("Amelia", "Patel", 0);
        Client c4 = new Client("Gary", "Lewis", 0);
        Set<Client> clientSet = new HashSet<Client>();
        clientSet.add(c1);
        clientSet.add(c2);
        clientSet.add(c3);
        clientSet.add(c4);

        Mockito.when(clientDAO.getAllClients()).thenReturn(clientSet);
        this.clientService = new ClientServiceImpl(this.clientDAO);
    }

    @Test
    void search_by_name(){
        Set<Client> clients = this.clientService.getClientsByName("ar");
        System.out.println(clients);
        Assertions.assertEquals(2,clients.size());
    }
}
