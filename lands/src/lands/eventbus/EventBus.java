/**
 * 
 */
package lands.eventbus;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public interface EventBus
{
    <H extends Handler> void fireEvent(Event<H> event);

    <H extends Handler> HandlerRegistration register(Class<? extends Event<H>> eventClass, H handler);
}