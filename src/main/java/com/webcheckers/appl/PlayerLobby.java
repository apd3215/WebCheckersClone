package com.webcheckers.appl;

import java.util.Dictionary;
import java.util.Hashtable;

public class PlayerLobby {

    private Dictionary<String, String> Users;
    private Dictionary<String, Player> Players;
    private int num_logged_in;

    public PlayerLobby(){
        this.Users = new Hashtable<>();
        this.Players = new Hashtable<>();
        this.num_logged_in = 0;
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
            this.num_logged_in++;
            return 1;
        } else{
            if(Users.get(username).equals(password)){
                Players.get(username).login();
                this.num_logged_in++;
                return 2;
            }
        }
        return 3;
    }

    public void sign_out(Player player){
        this.num_logged_in--;
        player.logout();
    }

    public int getNum_logged_in(){
        return this.num_logged_in;
    }
}

