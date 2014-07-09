/**
 * 
 */
package lands.model;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class Player
{
    private final String code;
    private final String name;

    public Player(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

}
