/**
 * 
 */
package lands.eventbus;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public interface Event<H extends Handler>
{
    void dispatch(H handler);
}