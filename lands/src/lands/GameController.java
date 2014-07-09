/**
 * 
 */
package lands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lands.model.Color;
import lands.model.Game;
import lands.model.PlayerArea;
import lands.model.move.Effect;
import lands.model.move.Move;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class GameController
{

    private final Game game = new Game();
    private final List<PlayerAreaController> areaControllers = new ArrayList<>();
    private final List<PlayerController> playerControllers = new ArrayList<>();
    private final MoveHelper moveHelper = new MoveHelper(game);
    boolean[] madeMove;

    public void addPlayer(PlayerController playerController)
    {
        PlayerArea area = new PlayerArea(game.getPlayers().size());
        game.getPlayers().add(playerController.getPlayer());
        game.getPlayerAreas().add(area);
        areaControllers.add(new PlayerAreaController(area));
        playerControllers.add(playerController);
    }

    public void startGame()
    {
        for (PlayerAreaController areaController : areaControllers)
        {
            areaController.reset();
        }
        game.setPriority(0);
        madeMove = new boolean[playerControllers.size()];
        try
        {
            while (true)
            {
                processTurn();
            }
        }
        catch (PlayerLostException e)
        {
            //GG
        }
    }

    private void processEffect(Effect effect)
    {

    }

    private void processResponses()
    {
        boolean someoneResponded;
        do
        {
            someoneResponded = false;
            for (int i = 0; i < playerControllers.size(); i++)
            {
                if (madeMove[i])
                {
                    continue;
                }
                Set<Move> availableResponses = moveHelper.getAvailableMovesForPlayer(i);
                Move response = playerControllers.get(game.getPriority()).selectMove(availableResponses, game);
                if (response.getColor() == Color.NoColor)
                {
                    continue;
                }
                game.getStack().addLast(response);
                madeMove[i] = true;
                someoneResponded = true;
            }
        }
        while (someoneResponded);
    }

    private void processTurn() throws PlayerLostException
    {
        areaControllers.get(game.getPriority()).draw();
        Arrays.fill(madeMove, false);
        Set<Move> availableMoves = moveHelper.getAvailableMovesForPlayer(game.getPriority());
        game.getStack().addLast(playerControllers.get(game.getPriority()).selectMove(availableMoves, game));
        madeMove[game.getPriority()] = true;

        while (!game.getStack().isEmpty())
        {
            processResponses();
            Move nextMove = game.getStack().pollLast();
            Effect effect = playerControllers.get(nextMove.getOwner()).selectEffect(nextMove, game);
            processEffect(effect);
        }
        game.incPriority();
    }

}