/**
 * 
 */
package lands;

import java.util.Random;

import lands.model.Player;
import lands.players.GeneticPlayerController;
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
    public void testGeneticPlayers()
    {
        Random rnd = new Random();
        int PLAYER_COUNT_TOTAL = 100;
        Player[] players = new Player[PLAYER_COUNT_TOTAL];
        PlayerController[] controllers = new PlayerController[PLAYER_COUNT_TOTAL];
        for (int i = 0; i < PLAYER_COUNT_TOTAL; i++)
        {
            players[i] = new Player("Player" + String.valueOf(i), "Player" + String.valueOf(i));
            GeneticPlayerController controller = new GeneticPlayerController(players[i], 2);
            for (int j = 0; j < controller.getNeuralNet().length; j++)
            {
                for (int k = 0; k < controller.getNeuralNet()[j].length; k++)
                {
                    controller.getNeuralNet()[j][k] = rnd.nextDouble() * 2 - 1;
                }
            }
            controllers[i] = controller;
        }
        for (int i = 0; i < 1000000; i++)
        {
            int[] wons = new int[PLAYER_COUNT_TOTAL];
            for (int k = 0; k < 100; k++)
            {
                boolean[] played = new boolean[PLAYER_COUNT_TOTAL];
                for (int j = 0; j < PLAYER_COUNT_TOTAL / 2; j++)
                {

                    int pos1 = chooseNextPlayer(rnd, played, PLAYER_COUNT_TOTAL);
                    played[pos1] = true;
                    int pos2 = chooseNextPlayer(rnd, played, PLAYER_COUNT_TOTAL);
                    played[pos2] = true;

                    GameController game = new GameController();
                    game.addPlayer(controllers[pos1]);
                    game.addPlayer(controllers[pos2]);
                    int result = game.startGame();
                    if (result == 1 || result == -2)
                    {
                        wons[pos1]++;
                    }
                    else if (result == 2 || result == -1)
                    {
                        wons[pos2]++;
                    }
                }
            }

        }
    }

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

    private int chooseNextPlayer(Random rndGen, boolean[] played, int playerCount)
    {
        int rnd = rndGen.nextInt(playerCount);
        int pos = 0;
        do
        {
            while (played[pos])
            {
                pos++;
                if (pos == playerCount)
                {
                    pos = 0;
                }
            }
            rnd--;
        }
        while (rnd != 0);
        return pos;
    }
}