/**
 * 
 */
package lands;

import static lands.Constants.LOG;

import java.util.Random;

import lands.model.Color;
import lands.model.Land;
import lands.model.PlayerArea;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public class PlayerAreaController
{
    private final PlayerArea area;

    public PlayerAreaController(PlayerArea area)
    {
        this.area = area;
    }

    public void draw() throws PlayerLostException
    {
        if (area.getLibrary().isEmpty())
        {
            throw new PlayerLostException(area.getOwner(), "emptyLibrary");
        }
        Color nextColor = area.getLibrary().poll();
        LOG.info(String.format("Player has drawn %s land", nextColor));
        area.getHand().get(nextColor).inc();
    }

    public void reset()
    {
        resetHand();
        resetBattlefield();
        resetGraveyard();
        resetLibrary();
        shuffleLibrary();
        try
        {
            for (int i = 0; i < Constants.STARTING_HAND; i++)
            {
                draw();
            }
        }
        catch (PlayerLostException e)
        {
            throw new RuntimeException("Library size is smaller than initial hand size!");
        }
    }

    private void resetBattlefield()
    {
        area.getBattlefield().clear();
        for (Color color : Color.legalColors())
        {
            area.getBattlefield().put(color, new Land(color, 0, area.getOwner()));
        }
    }

    private void resetGraveyard()
    {
        area.getGraveyard().clear();
        for (Color color : Color.legalColors())
        {
            area.getGraveyard().put(color, new Land(color, 0, area.getOwner()));
        }
    }

    private void resetHand()
    {
        area.getHand().clear();
        for (Color color : Color.legalColors())
        {
            area.getHand().put(color, new Land(color, 0, area.getOwner()));
        }
    }

    private void resetLibrary()
    {
        for (Color color : Color.legalColors())
        {
            for (int j = 0; j < Constants.LAND_COUNT_PER_COLOR; j++)
            {
                area.getLibrary().add(color);
            }
        }
    }

    private void shuffleLibrary()
    {
        Random rnd = new Random();
        for (int i = 0; i < Color.legalColors().length * Constants.LAND_COUNT_PER_COLOR; i++)
        {
            int index = rnd.nextInt(Color.legalColors().length * Constants.LAND_COUNT_PER_COLOR);
            Color color1 = area.getLibrary().get(i);
            Color color2 = area.getLibrary().get(index);
            area.getLibrary().set(i, color2);
            area.getLibrary().set(index, color1);
        }
    }
}