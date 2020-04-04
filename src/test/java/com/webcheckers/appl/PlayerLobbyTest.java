package com.webcheckers.appl;

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
     * Mock classes
     */
     

    assertTrue(CuT.check_username("normalguy97"));
    assertFalse(CuT.check_username("s p a c e"));
    assertFalse(CuT.check_username("ca$hmon3y"));

      /**
       * Check various sign-in combinations.
       */
      @Test
      public void test_sign_in() {
        //invalid username examples
        //invalid password examples
        //valid and new user
        //already logged in
        //existing user login
        //wrong password and user already exists
      }
    
      /**
      * Check if dictionary is valid and contains players.
      */
      @Test
      public void get_players() {

      }

      /**
       * Check number of signed-in users.
       */
      @Test
      public void test_getnum() {

      }

      /**
       * Check names of signed-in users.
       */
      @Test
      public void test_get_logged() {

      }

      /**
       * Check playing users.
       */
      @Test
      public void test_get_playing() {

      }

      /**
       * Test successful sign out of players
       */
      @Test
      public void test_sign_out() {

      }

}