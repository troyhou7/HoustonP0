package dev.houston.entities;

public class Account {

    private int id;
    private double balance;
    private int clientId;


    public Account(int id, double balance, int clientId) {
        this.id = id;
        this.balance = balance;
        this.clientId = clientId;
    }
    public Account(){
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance){ this.balance = balance; }

    public void withdraw(double amount) {
        if(this.balance - amount >= 0 && amount > 0) {
            this.balance -= amount;
        }
        else{
            System.out.println("Cannot withdraw that much from this account");
        }
    }
    public void deposit(double amount){
        if(amount > 0){
            this.balance += amount;
        }else{
            System.out.println("Cannot deposit $0 or less");
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", clientId=" + clientId +
                '}';
    }
}
