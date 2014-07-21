/**
 * 
 */
package lands;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class PlayerWonException extends GGException
{
    private static final long serialVersionUID = -8470270977337249400L;

    public PlayerWonException(int playerId, String message)
    {
        super(playerId, message);
    }
}