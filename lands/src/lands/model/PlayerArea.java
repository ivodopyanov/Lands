/**
 * 
 */
package lands.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class PlayerArea
{
    private final int owner;
    private final Map<Color, Land> hand = new HashMap<>();
    private final Map<Color, Land> battlefield = new HashMap<>();
    private final Map<Color, Land> graveyard = new HashMap<>();
    private final LinkedList<Color> library = new LinkedList<>();

    public PlayerArea(int owner)
    {
        this.owner = owner;

    }

    public Map<Color, Land> getBattlefield()
    {
        return battlefield;
    }

    public Map<Color, Land> getGraveyard()
    {
        return graveyard;
    }

    public Map<Color, Land> getHand()
    {
        return hand;
    }

    public LinkedList<Color> getLibrary()
    {
        return library;
    }

    public int getOwner()
    {
        return owner;
    }

}
