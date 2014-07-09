/**
 * 
 */
package lands.model.move;

import lands.model.Land;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class Effect
{
    private final Land target;

    public Effect(Land target)
    {
        this.target = target;
    }

    public Land getTarget()
    {
        return target;
    }
}