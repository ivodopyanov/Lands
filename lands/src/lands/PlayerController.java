/**
 * 
 */
package lands;

import java.util.Set;

import lands.model.Game;
import lands.model.Player;
import lands.model.move.Effect;
import lands.model.move.Move;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class PlayerController
{
    private final Player player;

    public PlayerController(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Effect selectEffect(Move move, Game game)
    {
        return null;
    }

    public Move selectMove(Set<Move> moves, Game game)
    {
        return null;
    }
}
