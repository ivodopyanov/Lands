/**
 * 
 */
package lands;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public class GGException extends Exception
{
    private static final long serialVersionUID = 7557107554870882709L;
    private final int playerId;
    private final String message;

    protected GGException(int playerId, String message)
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
