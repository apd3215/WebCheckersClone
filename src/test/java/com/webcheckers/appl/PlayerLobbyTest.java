package com.webcheckers.appl;

import com.webcheckers.appl.LoginStatus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Dictionary;
import java.util.ArrayList;
import java.util.Collections;

/**
 * PlayerLobbyTest is a testing suite for PlayerLobby
 * @author Joshua Yoder
 */
public class PlayerLobbyTest {
    /**
     * The component under test (CuT).
     */
    private PlayerLobby CuT; 

    /**
     * Setup by logging in example users.
     */
     @BeforeEach
     public void setup() {
        CuT = new PlayerLobby();
        CuT.sign_in("user1", "pass1");
        CuT.sign_in("user2", "pass2");
     }

      /**
       * Check sign-in user/pass combinations.
       * Focuses on invalid username and password formating.
       */
      @Test
      public void test_sign_in_format() {
        //invalid username examples
        assertEquals(LoginStatus.INVALID_USER_FORMAT, CuT.sign_in("u$ern@me", "password"));
        //invalid password examples
        assertEquals(LoginStatus.INVALID_PASS_FORMAT, CuT.sign_in("username", ""));
        assertEquals(LoginStatus.INVALID_PASS_FORMAT, CuT.sign_in("username", " "));
      }
    
      /**
      * Check if dictionary is valid and contains players.
      */
      @Test
      public void test_get_players() {
        Dictionary<String, Player> playerDict = CuT.getPlayers();
        assertNotNull(playerDict);
        assertEquals(2, playerDict.size());
        assertNotNull(playerDict.get("user1"));
        assertNotNull(playerDict.get("user2"));
      }

      /**
       * Check number of signed-in users.
       */
      @Test
      public void test_getnum() {
        assertEquals(2, CuT.getNum_logged_in());
      }

      /**
       * Check names of signed-in users.
       */
      @Test
      public void test_get_logged() {
        ArrayList<String> loggedNames = CuT.get_logged_names();
        assertNotNull(loggedNames);
        assertEquals(2, loggedNames.size());
        assertTrue(loggedNames.contains("user1"));
        assertTrue(loggedNames.contains("user2"));
      }

      /**
       * Check playing users.
       */
      @Test
      public void test_get_playing() {
        ArrayList<String> playing = CuT.get_playing();
        assertNotNull(playing);
        assertEquals(0, playing.size());
        assertFalse(playing.contains("user1"));
        assertFalse(playing.contains("user2"));
      }

      /**
       * Sign out players
       */
      @Test
      public void test_sign_out() {
        CuT.sign_out(CuT.getPlayers().get("user1"));
        CuT.sign_out(CuT.getPlayers().get("user2"));
      }

      /**
       * Tests an example sign in/out/back in scenario.
       */
      @Test
      public void test_sign_in_example() {
        //valid and new user
        assertEquals(LoginStatus.NEW_USER_LOGIN, CuT.sign_in("username", "password"));
        //already logged in
        assertEquals(LoginStatus.USER_ALREADY_LOGIN, CuT.sign_in("username", "password"));
        //sign out a user
        CuT.sign_out(CuT.getPlayers().get("username"));
        //wrong password or user already exists
        assertEquals(LoginStatus.WRONG_PASS_OR_USER_EXISTS, CuT.sign_in("username", "anotherpass"));
        //existing user login
        assertEquals(LoginStatus.EXISTING_USER_LOGIN, CuT.sign_in("username", "password"));
      }
}