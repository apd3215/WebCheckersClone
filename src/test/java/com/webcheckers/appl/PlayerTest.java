package com.webcheckers.appl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * PlayerTest is a testing suite for the Player class.
 * @author Jonathan Baxley
 */
public class PlayerTest {
    /**
     * The component under test (CuT).
     */
    private Player CuT;

    /**
     * Mock Classes
     */
    private PlayerLobby playerLobby;
    private String name;

    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setUp(){
        name = "Test";
        playerLobby = new PlayerLobby();
        CuT = new Player("Test");
    }

    /**
     * Test for a player login
     */
    @Test
    public void login(){
        this.playerLobby.sign_in("Test", "Test");
        assert(CuT.isLogged());
    }

    /**
     * Test for a player logout
     */
    @Test
    public void logout(){
        this.playerLobby.sign_out(CuT);
        assert(!CuT.isLogged());
    }

    /**
     * Test for player getName
     */
    @Test
    public void getName(){
        assert(CuT.getName().equals(name));
    }
}
