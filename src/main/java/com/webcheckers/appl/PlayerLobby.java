package com.webcheckers.appl;

import java.util.Dictionary;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collections;

/**
 * PlayerLobby, performs various server side tasks and takes care of
 * state and management of multiple users, games, etc.
 * @author Joe Netti
 * @author Joshua Yoder
 * @author Jonathan Baxley
 * @author Dhaval Shrishrimal
 */
public class PlayerLobby {
    private Dictionary<String, String> Users;
    private Dictionary<String, Player> Players;
    private int num_logged_in;

    /**
     * PlayerLobby constructor, creates all necessary components
     * of the player lobby.
     */
    public PlayerLobby(){
        this.Users = new Hashtable<>();
        this.Players = new Hashtable<>();
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
     * @return INVALID_USER_FORMAT if the username is invalid
     * @return INVALID_PASS_FORMAT if the password is invalid
     * @return NEW_USER_LOGIN if the user/pass combination is valid and it is a new user
     * @return USER_ALREADY_LOGIN if the user/pass combination is already logged in
     * @return EXISTING_USER_LOGIN if the user/pass combination is not logged in 
     * @return WRONG_PASS_OR_USER_EXISTS if wrong password or user already exists
     */
    public LoginStatus sign_in(String username, String password){
        if (!check_name(username)) {
            return LoginStatus.INVALID_USER_FORMAT;
        } if (Users.get(username) == null) {
            if (!check_pass(password)) {
                return LoginStatus.INVALID_PASS_FORMAT;
            }
            Users.put(username, password);
            Player player = new Player(username);
            Players.put(username, player);
            this.num_logged_in++;
            return LoginStatus.NEW_USER_LOGIN;
        } else {
            if(Users.get(username).equals(password)) {
                if (Players.get(username).isLogged()) {
                    return LoginStatus.USER_ALREADY_LOGIN;
                }
                Players.get(username).login();
                this.num_logged_in++;
                return LoginStatus.EXISTING_USER_LOGIN;
            }
        }
        return LoginStatus.WRONG_PASS_OR_USER_EXISTS;
    }

    /**
     * Sign out a given player.
     * @param player the player object to sign out
     */
    public void sign_out(Player player) {
        this.num_logged_in--;
        player.logout();
    }

    /**
     * Gets the number of people logged in.
     * @return the number of people logged in
     */
    public int getNum_logged_in() {
        return this.num_logged_in;
    }

    /**
     * Gets an array list of logged in users
     * @return ArrayList of logged in users
     */
    public ArrayList<String> get_logged_names() {
        ArrayList<String> keys = Collections.list(Users.keys());
        return keys;
    }

    /**
     * Gets an array list of playing users
     * @return ArrayList of playing users
     */
    public ArrayList<String> get_playing() {
        ArrayList<String> keys = Collections.list(Users.keys());
        ArrayList<String> playing = new ArrayList<>();
        for(String name: keys) {
            if (Players.get(name).isLogged() && Players.get(name).isPlaying()) {
                playing.add(name);
            }
        }
        return playing;
    }
}