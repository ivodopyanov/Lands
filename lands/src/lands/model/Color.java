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
    White, Blue, Black, Green, Red, NoColor;

    private static final Color[] LEGAL_COLORS = new Color[] { White, Blue, Black, Red, Green };

    public static Color[] legalColors()
    {
        return LEGAL_COLORS;
    }
}