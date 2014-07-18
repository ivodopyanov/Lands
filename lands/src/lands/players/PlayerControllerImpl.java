/**
 * 
 */
package lands.players;

import lands.model.Player;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public abstract class PlayerControllerImpl implements PlayerController
{
    private final Player player;

    public PlayerControllerImpl(Player player)
    {
        this.player = player;
    }

    @Override
    public Player getPlayer()
    {
        return player;
    }
}