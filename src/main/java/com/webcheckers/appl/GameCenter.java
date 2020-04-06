package com.webcheckers.appl;

import com.webcheckers.model.Game;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * GameCenter, contains globally accessed game database.
 * @author Jonathan Baxley
 */
public class GameCenter {
    private Dictionary<String, Game> Games;
    private ArrayList<Game> GameArrayList;

    /**
     * Constructor, create new games hashtable and arraylist.
     */
    public GameCenter(){
        this.Games = new Hashtable<>();
        this.GameArrayList = new ArrayList<Game>();
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
     * Ends a game that is currently in progress
     * @param game the game to end
     */
    public void endGame(Game game){
        game.getRedPlayer().endGamePlayer();
        game.getWhitePlayer().endGamePlayer();
        GameArrayList.remove(game);
    }

}
