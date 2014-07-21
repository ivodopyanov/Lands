/**
 * 
 */
package lands.model;

/**
 * @author ivodopynov
 * @since 09 июля 2014 г.
 *
 */
public enum Color
{
    White("W", 1), Blue("U", 2), Black("B", 3), Green("G", 4), Red("R", 5), NoColor("-", 0);

    private static final Color[] LEGAL_COLORS = new Color[] { White, Blue, Black, Red, Green };

    public static Color getColorById(int id)
    {
        switch (id)
        {
        case 1:
            return White;
        case 2:
            return Blue;
        case 3:
            return Black;
        case 4:
            return Red;
        case 5:
            return Green;
        default:
            return NoColor;
        }
    }

    public static Color[] legalColors()
    {
        return LEGAL_COLORS;
    }

    private final String shortName;
    private final int id;

    private Color(String shortName, int id)
    {
        this.shortName = shortName;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public String shortName()
    {
        return shortName;
    }
}