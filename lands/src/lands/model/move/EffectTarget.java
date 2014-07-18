/**
 * 
 */
package lands.model.move;

import java.util.HashMap;
import java.util.Map;

import lands.model.Color;

/**
 * @author ivodopynov
 * @since 17 июля 2014 г.
 *
 */
public class EffectTarget
{
    private static final Map<Color, Map<Integer, EffectTarget>> cache = new HashMap<>();

    public static EffectTarget create(Color color, int owner)
    {
        if (!cache.containsKey(color))
        {
            EffectTarget result = new EffectTarget(color, owner);
            Map<Integer, EffectTarget> playerEffectsCache = new HashMap<>();
            playerEffectsCache.put(owner, result);
            cache.put(color, playerEffectsCache);
            return result;
        }
        Map<Integer, EffectTarget> playerEffectsCache = cache.get(color);
        if (!playerEffectsCache.containsKey(owner))
        {
            EffectTarget result = new EffectTarget(color, owner);
            playerEffectsCache.put(owner, result);
            return result;
        }
        return playerEffectsCache.get(owner);
    }

    private final Color color;
    private final int owner;

    private EffectTarget(Color color, int owner)
    {
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
