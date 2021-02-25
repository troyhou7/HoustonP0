package dev.houston.daotests;

import dev.houston.daos.ClientDAO;
import dev.houston.daos.ClientDaoLocal;
import dev.houston.entities.Client;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTest {

    private static ClientDAO cdao = new ClientDaoLocal();
    private static Client testClient = null;

    @Test
    @Order(1)
    void create_client(){
        Client c1 = new Client("James", "Brown",0);

        cdao.createClient(c1);
        testClient = c1;
        System.out.println(c1);
        Assertions.assertNotEquals(0, c1.getId());
    }

    @Test
    @Order(2)
    void get_all_clients(){
        Client c1 = new Client("James","Jameson", 0);
        Client c2 = new Client("Roger", "Rogerson",0);
        Client c3 = new Client("Amelia","Earhart", 0);

        cdao.createClient(c1);
        cdao.createClient(c2);
        cdao.createClient(c3);

        Set<Client> clientSet = new HashSet<Client>();

        clientSet = cdao.getAllClients();

        System.out.println(clientSet);

        Assertions.assertTrue(clientSet.size() > 2);
    }

    @Test
    @Order(3)
    void get_client_by_id(){
        int id = testClient.getId();
        Client client = cdao.getClientById(id);
        Assertions.assertEquals(testClient.getId(), client.getId());
        System.out.println("The client we retrieved was " + client.getName());
    }

    @Test
    @Order(4)
    void update_client(){
        Client client = cdao.getClientById(testClient.getId());
        client.setFname("Jimothy");
        cdao.updateClient(client);

        Client updatedClient = cdao.getClientById(testClient.getId());
        Assertions.assertEquals("Jimothy", updatedClient.getFname());

        System.out.println(cdao.getClientById(testClient.getId()));
    }

    @Test
    @Order(5)
    void delete_client_by_id(){
        boolean deleted = cdao.deleteClientById(testClient.getId());

        Assertions.assertTrue(deleted);
    }

}
