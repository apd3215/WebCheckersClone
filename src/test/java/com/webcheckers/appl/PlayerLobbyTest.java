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
     

      /**
       * Checks check_username to ensure input sanitation rules
       * are followed for usernames.
       */
      @Test
      public void test_check_username() {
        assertTrue(CuT.check_username("normalguy97"));
        assertFalse(CuT.check_username("s p a c e"));
        assertFalse(CuT.check_username("ca$hmon3y"))
      }

      /**
       * Checks check_pass to ensure input sanitation rules are
       * followed for passwords.
       */
      @Test
      public void validate_password() {

      }

      /**
       * Check various sign-in combinations.
       */
      @Test
      public void test_sign_in() {

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