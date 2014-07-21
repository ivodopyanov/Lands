/**
 * 
 */
package lands;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class PlayerLostException extends GGException
{
    private static final long serialVersionUID = -6567770573599850508L;

    public PlayerLostException(int playerId, String message)
    {
        super(playerId, message);
    }
}