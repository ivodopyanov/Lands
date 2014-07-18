/**
 * 
 */
package lands.players;

import java.util.Iterator;
import java.util.Random;
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
public class RandomPlayerController extends PlayerControllerImpl
{
    private final Random rnd;

    public RandomPlayerController(Player player, int seed)
    {
        super(player);
        rnd = new Random(seed);
    }

    @Override
    public EffectTarget selectEffectTarget(Move move, Set<EffectTarget> effectTargets, Game game)
    {
        int num = rnd.nextInt(effectTargets.size());
        Iterator<EffectTarget> iterator = effectTargets.iterator();
        for (int i = 0; i < num - 1; i++)
        {
            iterator.next();
        }
        return iterator.next();
    }

    @Override
    public Move selectMove(Set<Move> moves, Game game)
    {
        int num = rnd.nextInt(moves.size());
        Iterator<Move> iterator = moves.iterator();
        for (int i = 0; i < num - 1; i++)
        {
            iterator.next();
        }
        return iterator.next();
    }
}