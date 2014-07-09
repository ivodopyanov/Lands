/**
 * 
 */
package lands;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class PlayerLostException extends Exception
{
    private static final long serialVersionUID = -2377449879576603073L;
    private final int playerId;
    private final String message;

    public PlayerLostException(int playerId, String message)
    {
        this.playerId = playerId;
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public int getPlayerId()
    {
        return playerId;
    }
}