/**
 * 
 */
package lands.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public class SimpleEventBus implements EventBus
{
    private final Map<Class<Event>, List<Handler>> registeredHandlers;
    private LinkedList<Event<?>> eventsStack = new LinkedList<Event<?>>();
    private LinkedList<Event<?>> pendingEventsStack = new LinkedList<Event<?>>();

    public SimpleEventBus()
    {
        registeredHandlers = new HashMap<Class<Event>, List<Handler>>();
    }

    @Override
    public <H extends Handler> void fireEvent(Event<H> event)
    {
        pendingEventsStack.addLast(event);
        if (eventsStack.size() == 0)
        {
            startProcessingEvents();
        }
    }

    @Override
    public <H extends Handler> HandlerRegistration register(Class<? extends Event<H>> eventClass, H handler)
    {
        Class<Event> eventClassConverted = (Class<Event>)(Class<?>)eventClass;
        List<H> handlers = getHandlers(eventClassConverted);
        handlers.add(handler);
        return new HandlerRegistrationImpl<H>(handlers, handler);
    }

    private <H extends Handler> void doProcessEvents(Event<H> event)
    {
        for (H handler : this.<H> getHandlers((Class<Event>)event.getClass()))
        {
            event.dispatch(handler);
        }
    }

    private <H extends Handler> List<H> getHandlers(Class<Event> eventClass)
    {
        List<Handler> result;
        if (registeredHandlers.containsKey(eventClass))
        {
            result = registeredHandlers.get(eventClass);
        }
        else
        {
            result = new ArrayList<Handler>();
            registeredHandlers.put(eventClass, result);
        }
        return (List<H>)result;
    }

    private void startProcessingEvents()
    {
        LinkedList<Event<?>> buf = pendingEventsStack;
        pendingEventsStack = eventsStack;
        eventsStack = buf;
        do
        {
            doProcessEvents(eventsStack.peek());
            eventsStack.poll();
        }
        while (!eventsStack.isEmpty());
        if (!pendingEventsStack.isEmpty())
        {
            startProcessingEvents();
        }
    }
}