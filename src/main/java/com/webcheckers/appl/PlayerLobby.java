package com.webcheckers.appl;

import com.webcheckers.model.Game;
import java.util.Dictionary;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collections;

public class PlayerLobby {

    private Dictionary<String, String> Users;
    private Dictionary<String, Player> Players;
    private Dictionary<String, Game> Games;
    private ArrayList<Game> GameArrayList;
    private int num_logged_in;

    public PlayerLobby(){
        this.Users = new Hashtable<>();
        this.Players = new Hashtable<>();
        this.Games = new Hashtable<>();
        this.GameArrayList = new ArrayList<Game>();
        this.num_logged_in = 0;
    }

    public Dictionary<String, Player> getPlayers() {
        return this.Players;
    }

    public void addGame(Game game) {
        String redPlayer = game.getRedPlayer().getName();
        String whitePlayer = game.getWhitePlayer().getName();
        Games.put(redPlayer + "," + whitePlayer, game);
        GameArrayList.add(game);
    }

    public Game getGame(Player redPlayer, Player whitePlayer) {
        Game game = Games.get(redPlayer.getName() + "," + whitePlayer.getName());
        return game;
    }


    public Game getGameByPlayer(Player player) {
        for (Game game : GameArrayList) {
            if (game.isPlayerInGame(player)){
                return game;
            }
        }
        return null;
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
                if (Players.get(username).isLogged()) {
                    return -2;
                }
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


    public ArrayList<String> get_logged_names(){
        ArrayList<String> keys = Collections.list(Users.keys());
        return keys;
    }
}
