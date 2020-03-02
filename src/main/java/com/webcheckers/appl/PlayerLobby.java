package com.webcheckers.appl;

import com.webcheckers.model.Game;
import java.util.Dictionary;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collections;

/**
 * PlayerLobby, performs various server side tasks and takes care of
 * state and management of multiple users, games, etc.
 */
public class PlayerLobby {
    private Dictionary<String, String> Users;
    private Dictionary<String, Player> Players;
    private Dictionary<String, Game> Games;
    private ArrayList<Game> GameArrayList;
    private int num_logged_in;

    /**
     * PlayerLobby constructor, creates all necessary components
     * of the player lobby.
     */
    public PlayerLobby(){
        this.Users = new Hashtable<>();
        this.Players = new Hashtable<>();
        this.Games = new Hashtable<>();
        this.GameArrayList = new ArrayList<Game>();
        this.num_logged_in = 0;
    }

    /**
     * Accessor, returns the dictionary representing players logged in
     * to the webcheckers game.
     * @return dictionary of players
     */
    public Dictionary<String, Player> getPlayers() {
        return this.Players;
    }

    /**
     * Adds a new game to the games list.
     */
    public void addGame(Game game) {
        String redPlayer = game.getRedPlayer().getName();
        String whitePlayer = game.getWhitePlayer().getName();
        Games.put(redPlayer + "," + whitePlayer, game);
        GameArrayList.add(game);
    }

    /**
     * Get a game instance with the corresponding/specific red and white player.
     * @param redPlayer the red player object
     * @param whitePlayer the white player object
     * @return a game with the specified players
     */
    public Game getGame(Player redPlayer, Player whitePlayer) {
        Game game = Games.get(redPlayer.getName() + "," + whitePlayer.getName());
        return game;
    }

    /**
     * Searches for and returns the game object corresponding to a single player object.
     * @param player single player object
     * @return the game corresponding to a given single player
     */
    public Game getGameByPlayer(Player player) {
        for (Game game : GameArrayList) {
            if (game.isPlayerInGame(player)){
                return game;
            }
        }
        return null;
    }

    /**
     * Checks if a given username follows security/input sanitation parameters.
     * @param username potential username of a user
     * @return weather the username follows security parameters
     */
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

    /**
     * Checks if a given password follows security/input sanitation parameters.
     * @param password potential password of a user
     * @return weather the password follows security parameters
     */
    private boolean check_pass(String password){
        for (int i = 0; i < password.length(); i++){
            if (password.charAt(i) != ' '){
                return true;
            }
        }
        return false;

    }

    /**
     * Signs in a given user with username and password. Checks if username
     * and password are both valid and then adds them to the user table. Signs in
     * user if they have previously signed in, or creates a new account.
     * @return 0 if the username is invalid
     * @return -1 if the password is invalid
     * @return 1 if the user/pass combination is valid and it is a new user
     * @return -2 if the user/pass combination is already logged in
     * @return 2 if the user/pass combination is not logged in 
     * @return 3 if wrong password or user already exists
     */
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

    /**
     * Sign out a given player.
     * @param player the player object to sign out
     */
    public void sign_out(Player player){
        this.num_logged_in--;
        player.logout();
    }

    /**
     * Gets the number of people logged in.
     * @return the number of people logged in
     */
    public int getNum_logged_in(){
        return this.num_logged_in;
    }

    /**
     * Gets an array list of logged in users
     * @return ArrayList of logged in users
     */
    public ArrayList<String> get_logged_names(){
        ArrayList<String> keys = Collections.list(Users.keys());
        return keys;
    }
}
