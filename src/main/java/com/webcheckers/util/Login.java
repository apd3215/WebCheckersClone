package com.webcheckers.util;

import java.util.Dictionary;
import java.util.Hashtable;

public class Login {
    public Dictionary<String, String> Users;


    public Login(){
        this.Users = new Hashtable<>();
    }
    //Returns true if logged in or new account created
    //Returns false if wrong password
    private boolean login(String username, String password){
        if (this.Users.get(username) != null){
            this.Users.put(username, password);
            return true;
        }
        else{
            if (this.Users.get(username).equals(password)){
                return true;
            }
        }
        return false;
    }
}
