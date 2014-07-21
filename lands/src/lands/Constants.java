/**
 * 
 */
package lands;

import java.util.logging.Logger;

import lands.eventbus.EventBus;
import lands.eventbus.SimpleEventBus;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public interface Constants
{
    Logger LOG = Logger.getLogger("Logger");
    EventBus GLOBAL_EVENT_BUS = new SimpleEventBus();
    int LAND_COUNT_PER_COLOR = 10;
    int STARTING_HAND = 7;
}