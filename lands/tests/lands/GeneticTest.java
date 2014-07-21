/**
 * 
 */
package lands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lands.model.Player;
import lands.players.GeneticPlayerController;
import lands.players.PlayerController;

import org.junit.Test;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public class GeneticTest
{
    @Test
    public void testGeneticPlayers()
    {
        Random rnd = new Random();
        int playerCount = 100;
        Player[] players = new Player[playerCount];
        GeneticPlayerController[] controllers = new GeneticPlayerController[playerCount];
        for (int i = 0; i < playerCount; i++)
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
            int[] wons = new int[playerCount];
            for (int k = 0; k < 100; k++)
            {
                runRound(wons, rnd, playerCount, controllers);
            }
            updatePopulation(wons, playerCount, controllers, rnd);
        }
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

    private void runRound(int[] wons, Random rnd, int playerCount, PlayerController[] controllers)
    {
        boolean[] played = new boolean[playerCount];
        for (int j = 0; j < playerCount / 2; j++)
        {
            int pos1 = chooseNextPlayer(rnd, played, playerCount);
            played[pos1] = true;
            int pos2 = chooseNextPlayer(rnd, played, playerCount);
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

    private void updatePopulation(int[] wons, int playerCount, GeneticPlayerController[] controllers, Random rnd)
    {

        List<Integer> list = new ArrayList<>();
        for (int won : wons)
        {
            list.add(won);
        }
        int bestIndividual = Collections.max(list);
        for (int i = 0; i < wons.length; i++)
        {
            if (wons[i] < 2)//Bad individuals get replaced by the best one
            {
                for (int j = 0; j < controllers[i].getNeuralNet().length; j++)
                {
                    System.arraycopy(controllers[bestIndividual].getNeuralNet()[j], 0,
                            controllers[i].getNeuralNet()[j], 0, controllers[i].getNeuralNet()[j].length);
                }
            }
        }

        double neuronConnectionCount = playerCount * controllers[bestIndividual].getNeuralNet().length
                * controllers[bestIndividual].getNeuralNet()[0].length;
        double mutationLimit = 1 - 5 / neuronConnectionCount;
        for (GeneticPlayerController controller : controllers)
        {
            for (int i = 0; i < controller.getNeuralNet().length; i++)
            {
                for (int j = 0; j < controller.getNeuralNet()[i].length; j++)
                {
                    if (rnd.nextDouble() > mutationLimit)
                    {
                        double delta = rnd.nextDouble() - 0.5;
                        controller.getNeuralNet()[i][j] += delta;
                    }
                }
            }
        }
    }

}
