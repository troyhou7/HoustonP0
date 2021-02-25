package dev.houston.daos;

import dev.houston.entities.Account;
import dev.houston.utilities.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class AccountDaoPostgres implements AccountDAO{

    private Logger logger = Logger.getLogger(AccountDaoPostgres.class.getName());

    @Override
    public Account createAccount(Account account) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into account (balance,c_id) values(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1,account.getBalance());
            ps.setInt(2,account.getClientId());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("account_id");
            account.setId(key);
            return account;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to create Account");
            return null;
        }
    }

    @Override
    public Account getAccountById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Account account = new Account();
            account.setId(rs.getInt("account_id"));
            account.setBalance(rs.getDouble("balance"));
            account.setClientId(rs.getInt("c_id"));
            return account;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to get account with id " + id);
            return null;
        }
    }

    @Override
    public Set<Account> getAllAccounts() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Set<Account> accounts = new HashSet<Account>();

            while(rs.next()){
                Account account = new Account();
                account.setId(rs.getInt("account_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setClientId(rs.getInt("c_id"));
                accounts.add(account);
            }
            return accounts;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to get all accounts");
            return null;
        }
    }

    @Override
    public Account updateAccount(Account account) {
        try(Connection conn = ConnectionUtil.createConnection()) {

            String sql = "update account set balance = ? where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1,account.getBalance());
            ps.setInt(2,account.getId());
            ps.executeUpdate();
            return account;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to update client " + account.getClientId()+ "'s account id " + account.getId());
            return null;
        }
    }

    @Override
    public boolean deleteAccountById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from account where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("Unable to get account id " + id);
            return false;
        }
    }
}
