/**
 * 
 */
package lands.players;

import java.util.Set;

import lands.model.Game;
import lands.model.Player;
import lands.model.move.EffectTarget;
import lands.model.move.Move;

/**
 * @author ivodopynov
 * @since 17 июля 2014 г.
 *
 */
public interface PlayerController
{
    Player getPlayer();

    EffectTarget selectEffectTarget(Move move, Set<EffectTarget> effectTargets, Game game);

    Move selectMove(Set<Move> moves, Game game);
}