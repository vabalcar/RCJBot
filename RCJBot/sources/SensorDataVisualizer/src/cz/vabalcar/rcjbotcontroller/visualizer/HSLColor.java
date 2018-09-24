package cz.vabalcar.rcjbotcontroller.visualizer;

/**
 * This class extends Color to provide a way to easily define Color using HSL color model.
 */
public class HSLColor extends IntColor {
    
    /**
     * Instantiates a new HSL color.
     *
     * @param hue the hue of the color
     */
    public HSLColor(float hue) {
        this(hue, 1f, 0.5f);
    }

    /**
     * Instantiates a new HSL color.
     *
     * @param hue the hue of the color
     * @param saturation the saturation of the color
     * @param lightness the lightness of the color
     */
    public HSLColor(float hue, float saturation, float lightness) {
        super(countColor(hue, saturation, lightness));
    }
    
    /**
     * Counts int format of given color described by its hue, saturation and lightness.
     *
     * @param hue the hue of the color
     * @param saturation the saturation of the color
     * @param lightness the lightness
     * @return the int containing counted color with given parameters
     */
    private static int countColor(float hue, float saturation, float lightness) {
        float chroma = (1 - Math.abs(2 * lightness - 1)) * saturation;
        float q = hue / 60;
        float x = chroma * (1 - Math.abs(q % 2 - 1));

        float r, g, b;
        if (q >= 0 && q <= 1) {
            r = chroma;
            g = x;
            b = 0;
        } else if (q > 1 && q <= 2) {
            r = x;
            g = chroma;
            b = 0;
        } else if (q > 2 && q <= 3) {
            r = 0;
            g = chroma;
            b = x;
        } else if (q > 3 && q <= 4) {
            r = 0;
            g = x;
            b = chroma;
        } else if (q > 4 && q <= 5) {
            r = x;
            g = 0;
            b = chroma;
        } else if (q > 5 && q <= 6) {
            r = chroma;
            g = 0;
            b = x;
        } else {
            r = 0;
            g = 0;
            b = 0;
        }

        float m = (float)(lightness - 0.5 * chroma);
        r += m;
        g += m;
        b += m;
        
        int R = (int) (r * 255);
        int G = (int) (g * 255);
        int B = (int) (b * 255);

        return new IntColor(R, G, B).getColor();
    }
}
