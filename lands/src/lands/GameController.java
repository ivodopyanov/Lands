/**
 * 
 */
package lands;

import static lands.Constants.LOG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lands.model.Color;
import lands.model.Game;
import lands.model.PlayerArea;
import lands.model.move.Effect;
import lands.model.move.EffectTarget;
import lands.model.move.Move;
import lands.players.PlayerController;

/**
 * Main controller of the game - initializes game, processes game cycle
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
        LOG.info("Game has began!");
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
            LOG.info(String.format("Player %d lost", e.getPlayerId()));
            //GG
        }
    }

    private void processEffect(Effect effect) throws PlayerLostException
    {
        switch (effect.getMove().getColor())
        {
        //White land draws a card
        case White:
            areaControllers.get(effect.getMove().getOwner()).draw();
            break;
        //Blue land acts as Force of Will
        case Blue:
            Iterator<Move> iterator = game.getStack().iterator();
            while (iterator.hasNext())
            {
                Move moveOnStack = iterator.next();
                if (moveOnStack.getColor() == effect.getEffectTarget().getColor()
                        && moveOnStack.getOwner() == effect.getEffectTarget().getOwner())
                {
                    LOG.info(String.format("%s land on stack of player %d is countered", moveOnStack.getColor(),
                            moveOnStack.getOwner()));
                    iterator.remove();
                    break;
                }

            }
            game.getPlayerAreas().get(effect.getMove().getOwner()).getHand().get(effect.getEffectTarget().getColor())
                    .dec();
            break;
        //Black land acts as Thoughtseize
        case Black:
            LOG.info(String.format("%s land in hand of player %d is discarded", effect.getEffectTarget().getColor(),
                    effect.getEffectTarget().getOwner()));
            if (effect.getEffectTarget().getColor() != Color.NoColor)
            {
                game.getPlayerAreas().get(effect.getEffectTarget().getOwner()).getHand()
                        .get(effect.getEffectTarget().getColor()).dec();
            }
            break;
        //Red land destroys a land on battlefield
        case Red:
            LOG.info(String.format("%s land in the battlefield of player %d is destroyed", effect.getEffectTarget()
                    .getColor(), effect.getEffectTarget().getOwner()));
            if (effect.getEffectTarget().getColor() != Color.NoColor)
            {
                game.getPlayerAreas().get(effect.getEffectTarget().getOwner()).getBattlefield()
                        .get(effect.getEffectTarget().getColor()).dec();
                game.getPlayerAreas().get(effect.getEffectTarget().getOwner()).getGraveyard()
                        .get(effect.getEffectTarget().getColor()).inc();
            }
            break;
        //Green land returns a land from owner's graveyard to hand
        case Green:
            LOG.info(String.format("%s land is returned from the graveyard of player %d into his hand", effect
                    .getEffectTarget().getColor(), effect.getEffectTarget().getOwner()));
            if (effect.getEffectTarget().getColor() != Color.NoColor)
            {
                game.getPlayerAreas().get(effect.getEffectTarget().getOwner()).getGraveyard()
                        .get(effect.getEffectTarget().getColor()).dec();
                game.getPlayerAreas().get(effect.getEffectTarget().getOwner()).getHand()
                        .get(effect.getEffectTarget().getColor()).inc();
            }
            break;
        default:
            break;
        }
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
                    //Player can make move (if he has a priority) or respond (if he has not) once per turn
                    continue;
                }
                Set<Move> availableResponses = moveHelper.getAvailableMovesForPlayer(i);
                Move response = playerControllers.get(game.getPriority()).selectMove(availableResponses, game);
                if (response.getColor() == Color.NoColor)
                {
                    continue;
                }
                LOG.info(String.format("%d player is responding move with %s land", response.getOwner(),
                        response.getColor()));
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
        Move move = playerControllers.get(game.getPriority()).selectMove(availableMoves, game);
        game.getStack().addLast(move);
        LOG.info(String.format("%d player is making move with %s land", move.getOwner(), move.getColor()));
        madeMove[game.getPriority()] = true;
        while (!game.getStack().isEmpty())
        {
            processResponses();
            //Move resolves only if no players have responses
            Move nextMove = game.getStack().pollLast();
            Set<EffectTarget> availableEffectTargets = moveHelper.getAvailableTargetsForMove(nextMove);
            EffectTarget effectTarget = playerControllers.get(nextMove.getOwner()).selectEffectTarget(nextMove,
                    availableEffectTargets, game);
            processEffect(new Effect(effectTarget, nextMove));
        }
        game.incPriority();
    }

}