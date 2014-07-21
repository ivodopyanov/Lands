/**
 * 
 */
package lands.eventbus;

import java.util.List;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public class HandlerRegistrationImpl<T extends Handler> implements HandlerRegistration
{
    private final List<T> handlers;
    private final T handler;

    public HandlerRegistrationImpl(List<T> handlers, T handler)
    {
        this.handlers = handlers;
        this.handler = handler;
    }

    @Override
    public void unregister()
    {
        handlers.remove(handler);
    }
}