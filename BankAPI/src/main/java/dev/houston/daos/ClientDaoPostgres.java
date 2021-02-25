package dev.houston.daos;

import dev.houston.entities.Client;
import dev.houston.utilities.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class ClientDaoPostgres implements ClientDAO{

    private Logger logger = Logger.getLogger(ClientDaoPostgres.class.getName());

    @Override
    public Client createClient(Client client) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into client (fname,lname) values(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,client.getFname());
            ps.setString(2,client.getLname());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("client_id");
            client.setId(key);
            return client;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to create client");
            return null;
        }
    }

    @Override
    public Client getClientById(int id) {

        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Client client = new Client();
            client.setId(rs.getInt("client_id"));
            client.setFname(rs.getString("fname"));
            client.setLname(rs.getString("lname"));
            return client;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to retrieve Client id " + id);
            return null;
        }
    }

    @Override
    public Set<Client> getAllClients() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Set<Client> clients = new HashSet<Client>();
            while(rs.next()){
                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setFname(rs.getString("fname"));
                client.setLname(rs.getString("lname"));
                clients.add(client);
            }
            return clients;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to get all clients");
            return null;
        }
    }

    @Override
    public Client updateClient(Client client) {
        try(Connection conn = ConnectionUtil.createConnection()) {

            String sql = "update client set fname = ?, lname = ? where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,client.getFname());
            ps.setString(2,client.getLname());
            ps.setInt(3,client.getId());
            ps.executeUpdate();
            return client;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to update client id " + client.getId());
            return null;
        }
    }

    @Override
    public boolean deleteClientById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from client where client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to get client id " + id);
            return false;
        }
    }
}
