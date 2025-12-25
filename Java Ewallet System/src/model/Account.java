package model;


import java.util.ArrayList;
import java.util.List;

/*
*   Using Builder design pattern
*/

public class Account {
    private final String name;
    private String password;
    private final int age;
    private int  balance  ;
    private final String phoneNumber ;
    private List<History>  historys =  new ArrayList<>();


    private Account(Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.age = builder.age;
        this.balance = builder.balance;
        this.phoneNumber = builder.phoneNumber;
    }

    public void addHistory(History history){
        historys.add(history);
    }

    public List<History> getHistory(){
        return historys;
    }



    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void addBalance( int amount) {
        balance += amount ;
    }

    public void getMoney( int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder{
        private String name;
        private String password;
        private int age;
        private int  balance = 0 ;
        private String  phoneNumber;



        public Builder setName(String name) {
            this.name = name;
            return this;
        }


        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Account build(){
            return new Account(this);
        }

    }

}
