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
    White("W"), Blue("U"), Black("B"), Green("G"), Red("R"), NoColor("-");

    private static final Color[] LEGAL_COLORS = new Color[] { White, Blue, Black, Red, Green };

    public static Color[] legalColors()
    {
        return LEGAL_COLORS;
    }

    private final String shortName;

    private Color(String shortName)
    {
        this.shortName = shortName;
    }

    public String shortName()
    {
        return shortName;
    }
}