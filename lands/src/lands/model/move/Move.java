/**
 * 
 */
package lands.model.move;

import java.util.HashMap;
import java.util.Map;

import lands.model.Color;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class Move
{
    private static final Map<Color, Map<Integer, Move>> cache = new HashMap<>();

    public static Move create(Color color, int owner)
    {
        if (!cache.containsKey(color))
        {
            Move result = new Move(color, owner);
            Map<Integer, Move> playerMovesCache = new HashMap<>();
            playerMovesCache.put(owner, result);
            cache.put(color, playerMovesCache);
            return result;
        }
        Map<Integer, Move> playerMovesCache = cache.get(color);
        if (!playerMovesCache.containsKey(owner))
        {
            Move result = new Move(color, owner);
            playerMovesCache.put(owner, result);
            return result;
        }
        return playerMovesCache.get(owner);
    }

    private final Color color;
    private final int owner;

    private Move(Color color, int owner)
    {
        super();
        this.color = color;
        this.owner = owner;
    }

    public Color getColor()
    {
        return color;
    }

    public int getOwner()
    {
        return owner;
    }
}