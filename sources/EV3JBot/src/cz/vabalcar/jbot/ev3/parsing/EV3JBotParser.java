package cz.vabalcar.jbot.ev3.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cz.vabalcar.jbot.ev3.EV3JBot;
import cz.vabalcar.jbot.ev3.MotorConfigration;
import cz.vabalcar.jbot.ev3.SensorConfiguration;
import cz.vabalcar.jbot.ev3.WheelConfiguration;

/**
 * The Class EV3JBotParser.
 */
public class EV3JBotParser implements ReadingParser<EV3JBot> {
    
    /** The Constant ENTITY_DELIMITER. */
    public static final char ENTITY_DELIMITER = '\n';
    
    /** The Constant TOKEN_DELIMITER. */
    public static final char TOKEN_DELIMITER = ' ';
    
    /** The Constant COMMENT_LINE_MARK. */
    public static final char COMMENT_LINE_MARK = '#';
    
    /** The multi entity parser. */
    private MultiEntityParser multiEntityParser = new MultiEntityParser();
    
    /**
     * Instantiates a new EV 3 Jbot parser.
     */
    public EV3JBotParser() {
        multiEntityParser.addParser("wheel", new WheelConfigurationParser(ENTITY_DELIMITER, TOKEN_DELIMITER));
        multiEntityParser.addParser("motor", new MotorConfigurationParser(ENTITY_DELIMITER, TOKEN_DELIMITER));
        multiEntityParser.addParser("sensor", new SensorConfigurationParser(ENTITY_DELIMITER, TOKEN_DELIMITER));
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.ev3.parsing.Parser#parse(java.lang.String)
     */
    @Override
    public EV3JBot parse(String s) throws FormatException {
        try {
            return parse(new StringReader(s));
        } catch (IOException e) {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see cz.vabalcar.jbot.ev3.parsing.ReadingParser#parse(java.io.Reader)
     */
    @Override
    @SuppressWarnings("unchecked")
    public EV3JBot parse(Reader reader) throws IOException, FormatException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.length() == 0 || trimmedLine.charAt(0) == COMMENT_LINE_MARK) {
                    continue;
                }
                multiEntityParser.parse(line);
            }
        }
        
        List<?> retrievedWheelList = multiEntityParser.getParsedEntities("wheel");
        List<WheelConfiguration> wheelConfigurations;
        if (retrievedWheelList != null) {
            wheelConfigurations = (List<WheelConfiguration>)retrievedWheelList;
        } else {
            wheelConfigurations = new ArrayList<>();
        }
        
        List<?> retrievedOtherMotorsConfigurations = multiEntityParser.getParsedEntities("motor");
        List<MotorConfigration> otherMotorsConfigurations;
        if (retrievedOtherMotorsConfigurations != null) {
            otherMotorsConfigurations = (List<MotorConfigration>)retrievedOtherMotorsConfigurations;
        } else {
            otherMotorsConfigurations = new ArrayList<>();
        }
        
        List<?> retrievedSensorList = multiEntityParser.getParsedEntities("sensor");
        List<SensorConfiguration> sensorConfigurations;
        if (retrievedSensorList != null) {
            sensorConfigurations = (List<SensorConfiguration>)retrievedSensorList;
        } else {
            sensorConfigurations = new ArrayList<>();
        }
        
        return new EV3JBot(wheelConfigurations, otherMotorsConfigurations, sensorConfigurations);
    }
}
