/**
 * 
 */
package lands;

import java.util.HashSet;
import java.util.Set;

import lands.model.Color;
import lands.model.Game;
import lands.model.Land;
import lands.model.move.EffectTarget;
import lands.model.move.Move;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class MoveHelper
{
    private final Game game;

    public MoveHelper(Game game)
    {
        this.game = game;
    }

    public Set<Move> getAvailableMovesForPlayer(int player)
    {
        Set<Move> result = new HashSet<>();
        if (isWhiteMoveAvailable(player) && game.getPriority() == player)
        {
            result.add(Move.create(Color.White, player));
        }
        if (isBlueCounterMoveAvailable(player) && game.getPriority() != player)
        {
            result.add(Move.create(Color.Blue, player));
        }
        if (isBlueMoveAvailable(player) && game.getPriority() == player)
        {
            result.add(Move.create(Color.Blue, player));
        }
        if (isBlackMoveAvailable(player) && game.getPriority() == player)
        {
            result.add(Move.create(Color.Black, player));
        }
        if (isRedMoveAvailable(player) && game.getPriority() == player)
        {
            result.add(Move.create(Color.Red, player));
        }
        if (isGreenMoveAvailable(player) && game.getPriority() == player)
        {
            result.add(Move.create(Color.Green, player));
        }
        if (game.getPriority() != player)
        {
            result.add(Move.create(Color.NoColor, player));
        }
        return result;
    }

    public Set<EffectTarget> getAvailableTargetsForMove(Move move)
    {
        Set<EffectTarget> result = new HashSet<>();
        switch (move.getColor())
        {
        case White:
            result.add(EffectTarget.create(Color.NoColor, move.getOwner()));
            break;
        case Blue:
            for (Move enemyMove : game.getStack())
            {
                if (enemyMove.getOwner() == move.getOwner())
                {
                    continue;
                }
                result.add(EffectTarget.create(enemyMove.getColor(), enemyMove.getOwner()));
            }
            if (result.isEmpty())
            {
                result.add(EffectTarget.create(Color.NoColor, move.getOwner()));
            }
            break;
        case Black:
            for (int player = 0; player < game.getPlayerAreas().size(); player++)
            {
                if (player == move.getOwner())
                {
                    continue;
                }
                boolean noLands = true;
                for (Land land : game.getPlayerAreas().get(player).getHand().values())
                {
                    if (land.getQuantity() > 0)
                    {
                        result.add(EffectTarget.create(land.getColor(), land.getOwner()));
                        noLands = false;
                    }
                }
                if (noLands)
                {
                    result.add(EffectTarget.create(Color.NoColor, player));
                }
            }
            break;
        case Red:
            for (int player = 0; player < game.getPlayerAreas().size(); player++)
            {
                if (player == move.getOwner())
                {
                    continue;
                }
                boolean noLands = true;
                for (Land land : game.getPlayerAreas().get(player).getBattlefield().values())
                {
                    if (land.getQuantity() > 0)
                    {
                        result.add(EffectTarget.create(land.getColor(), land.getOwner()));
                        noLands = false;
                    }
                }
                if (noLands)
                {
                    result.add(EffectTarget.create(Color.NoColor, player));
                }
            }
            break;
        case Green:
            boolean noLands = true;
            for (Land land : game.getPlayerAreas().get(move.getOwner()).getGraveyard().values())
            {
                if (land.getQuantity() > 0)
                {
                    result.add(EffectTarget.create(land.getColor(), land.getOwner()));
                    noLands = false;
                }
            }
            if (noLands)
            {
                result.add(EffectTarget.create(Color.NoColor, move.getOwner()));
            }
            break;
        default:
            break;
        }
        return result;
    }

    private int cardsInHand(int player, Color color)
    {
        return game.getPlayerAreas().get(player).getHand().get(color).getQuantity();
    }

    private boolean isBlackMoveAvailable(int player)
    {
        return cardsInHand(player, Color.Black) > 0;
    }

    private boolean isBlueCounterMoveAvailable(int player)
    {
        if (cardsInHand(player, Color.Blue) == 0)
        {
            return false;
        }
        for (Move moveOnStack : game.getStack())
        {
            if (cardsInHand(player, moveOnStack.getColor()) > (moveOnStack.getColor().equals(Color.Blue) ? 1 : 0))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isBlueMoveAvailable(int player)
    {
        return cardsInHand(player, Color.Blue) > 0;
    }

    private boolean isGreenMoveAvailable(int player)
    {
        return cardsInHand(player, Color.Green) > 0;
    }

    private boolean isRedMoveAvailable(int player)
    {
        return cardsInHand(player, Color.Red) > 0;
    }

    private boolean isWhiteMoveAvailable(int player)
    {
        return cardsInHand(player, Color.White) > 0;
    }

}
