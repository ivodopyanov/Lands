/**
 * 
 */
package lands;

import lands.model.Player;
import lands.players.PlayerController;
import lands.players.RandomPlayerController;

import org.junit.Test;

/**
 * @author ivodopynov
 * @since 17 июля 2014 г.
 *
 */
public class GameControllerTest
{
    @Test
    public void testRandomPlayers()
    {
        Player player1 = new Player("first", "first");
        Player player2 = new Player("second", "second");
        PlayerController playerController1 = new RandomPlayerController(player1, 1);
        PlayerController playerController2 = new RandomPlayerController(player2, 2);
        GameController game = new GameController();
        game.addPlayer(playerController1);
        game.addPlayer(playerController2);
        game.startGame();
    }
}