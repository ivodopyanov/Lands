/**
 * 
 */
package lands.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lands.model.move.Move;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class Game
{
    private final List<Player> players = new ArrayList<>();
    private final List<PlayerArea> playerAreas = new ArrayList<>();
    private LinkedList<Move> stack = new LinkedList<>();
    private int activePlayer = 0;
    private int priority = 0;

    public int getActivePlayer()
    {
        return activePlayer;
    }

    public List<PlayerArea> getPlayerAreas()
    {
        return playerAreas;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public int getPriority()
    {
        return priority;
    }

    public LinkedList<Move> getStack()
    {
        return stack;
    }

    public void incActivePlayer()
    {
        activePlayer++;
        if (activePlayer == players.size())
        {
            activePlayer = 0;
        }
    }

    public void incPriority()
    {
        priority++;
        if (players.size() == priority)
        {
            priority = 0;
        }
    }

    public void resetPriority()
    {
        priority = activePlayer;
    }

    public void setActivePlayer(int activePlayer)
    {
        this.activePlayer = activePlayer;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

}
