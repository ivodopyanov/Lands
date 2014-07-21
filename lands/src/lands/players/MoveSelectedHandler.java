/**
 * 
 */
package lands.players;

import lands.eventbus.Handler;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public interface MoveSelectedHandler extends Handler
{
    void onMoveSelected(MoveSelectedEvent event);
}