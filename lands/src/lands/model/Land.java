/**
 * 
 */
package lands.model;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class Land
{
    private final Color color;
    private int quantity;
    private final int owner;

    public Land(Color color, int quantity, int owner)
    {
        this.color = color;
        this.quantity = quantity;
        this.owner = owner;
    }

    public void dec()
    {
        quantity--;
    }

    public Color getColor()
    {
        return color;
    }

    public int getOwner()
    {
        return owner;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void inc()
    {
        quantity++;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return String.format("Land [Color=%s, quanity=%d, player=%d]", color, quantity, owner);
    }
}