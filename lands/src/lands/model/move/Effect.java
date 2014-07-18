/**
 * 
 */
package lands.model.move;


/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class Effect
{
    private final EffectTarget effectTarget;
    private final Move move;

    public Effect(EffectTarget effectTarget, Move move)
    {
        this.effectTarget = effectTarget;
        this.move = move;
    }

    public EffectTarget getEffectTarget()
    {
        return effectTarget;
    }

    public Move getMove()
    {
        return move;
    }
}