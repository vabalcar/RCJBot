package cz.vabalcar.jbot.ev3.parsing;

/**
 * The Class IntParser.
 */
public class IntParser implements Parser<Integer> {

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
     */
    @Override
    public Integer parse(String s) throws FormatException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new FormatException();
        }
    }

}
