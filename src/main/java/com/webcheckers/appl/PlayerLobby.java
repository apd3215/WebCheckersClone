package com.webcheckers.appl;

import com.webcheckers.Application;
import com.webcheckers.util.Player;

import java.util.Dictionary;
import java.util.Hashtable;

public class PlayerLobby {

    private Dictionary<String, String> Users;
    private Dictionary<String, Player> Players;

    public PlayerLobby(){
        this.Users = new Hashtable<>();
        this.Players = new Hashtable<>();
    }

    public Dictionary<String, Player> getPlayers() {
        return this.Players;
    }

    private boolean check_name(String username){
        boolean first = false;
        for(int i = 0; i < username.length(); i++){
            if (Character.isLetterOrDigit(username.charAt(i))){
                first = true;
            } else if (username.charAt(i) == ' '){
                continue;
            } else {
                return false;
            }
        }
        return first;
    }

    private boolean check_pass(String password){
        for (int i = 0; i < password.length(); i++){
            if (password.charAt(i) != ' '){
                return true;
            }
        }
        return false;

    }
    public int sign_in(String username, String password){
        if (!check_name(username)){
            return 0;
        } if (Users.get(username) == null){
            if (!check_pass(password)){
                return -1;
            }
            Users.put(username, password);
            Player player = new Player(username);
            Players.put(username, player);
            return 1;
        } else{
            if(Users.get(username).equals(password)){
                Players.get(username).login();
                return 2;
            }
        }
        return 3;
    }
}

