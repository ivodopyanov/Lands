/**
 * 
 */
package lands.players;

import lands.eventbus.Event;
import lands.model.move.Move;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public class MoveSelectedEvent implements Event<MoveSelectedHandler>
{
    private final Move move;

    public MoveSelectedEvent(Move move)
    {
        this.move = move;
    }

    @Override
    public void dispatch(MoveSelectedHandler handler)
    {
        handler.onMoveSelected(this);
    }

    public Move getMove()
    {
        return move;
    }
}