package cz.vabalcar.rcjbotcontroller.visualizer;

/**
 * This class is a visualizer of data from JBot's proximity sensor.
 */
public class ProximityVisualizer {
    
    /**
     * The Class ProximityMarkParameters.
     */
    private class ProximityMarkParameters {
        
        /** the outer radius of proximity mark */
        private float outerRadius;
        
        /** the inner radius of proximity mark */
        private float innerRadius;
        
        /**
         * Instantiates a new proximity mark parameters.
         *
         * @param radius the radius of the ProximityVisualizer
         * @param stages the stages of the ProximityVisualizer 
         * @param number of annular sectors is the ProximityVisualizer working with
         * @param relativeShift the relative shift of the ProximityVisualizer
         * @param i the index of currently counted proximity mark's parameters
         */
        public ProximityMarkParameters(float radius, int stages, float parts, float relativeShift, int i) {
            double shiftedI = i + stages * relativeShift;
            outerRadius = (float) (radius * ((shiftedI + 1) / parts));
            innerRadius = (float) (radius * ((shiftedI + 0.25) / parts));
        }
        
        /**
         * Gets the outer radius of the ProximityMark.
         *
         * @return the outer radius of the ProximityMark
         */
        public float getOuterRadius() {
            return outerRadius;
        }
        
        /**
         * Gets the inner radius of the ProximityMark.
         *
         * @return the inner radius of the ProximityMark
         */
        public float getInnerRadius() {
            return innerRadius;
        }
    }
    
    /**
     * A factory for creating the array of proximity mark parameters, one for each proximity mark
     */
    private class ProximityMarkParametersFactory {
        
        /** the relative shift. */
        private final float relativeShift;
        
        /** the stages. */
        private final int stages;
        
        /** the parts of the ProximityViualizer */
        private final float parts;
        
        /** the radius of the  */
        private float radius;
        
        /**
         * Instantiates a new proximity mark parameters factory.
         *
         * @param radius the radius of the proximity mark
         * @param relativeShift the relative shift of the proximity mark
         * @param stages the number of stages
         */
        public ProximityMarkParametersFactory(float radius, float relativeShift, int stages) {
            this.radius = radius;
            this.relativeShift = relativeShift;
            this.stages = stages;
            parts = stages + stages * relativeShift;
        }
        
        /**
         * Sets the stored radius of the ProximityVisualizer.
         *
         * @param radius the radius of the ProximityVisualizer
         */
        public void setRadius(float radius) {
            this.radius = radius;
        }
        
        /**
         * Creates the array of proximity mark parameters, one for each proximity mark.
         *
         * @return the array of proximity mark parameters, one for each proximity mark
         */
        public ProximityMarkParameters[] create() {
            ProximityMarkParameters[] proximityMarksParameters = new ProximityMarkParameters[stages];
            for (int i = 0; i < proximityMarksParameters.length; i++) {
                proximityMarksParameters[i] = new ProximityMarkParameters(radius, stages, parts, relativeShift, i);
            }
            return proximityMarksParameters;
        }
    }
    
    /** the proximity mark parameters factory */
    private final ProximityMarkParametersFactory proximityMarkParametersFactory;

    /** the proximity marks */
    private final AnnularSector[] proximityMarks;
    
    /** current proximity */
    private double proximity;
    
    /**
     * Instantiates a new proximity visualizer.
     *
     * @param center the center of the ProximityVisualizer
     * @param radius the radius of the ProximityVisualizer
     * @param relativeShift the relative shift of the most inner proximityMark beginning
     * @param start the start angle in degrees of each proximity mark
     * @param extent the extent angle in degrees of each proximity mark
     * @param stages the number of stages of proximity (= total number of proximity marks)
     */
    public ProximityVisualizer(Point center, float radius, float relativeShift, float start, float extent, int stages) {
        if (relativeShift < 0 || relativeShift > 1) {
            throw new IllegalArgumentException();
        }
        
        proximityMarkParametersFactory = new ProximityMarkParametersFactory(radius, relativeShift, stages);
        proximityMarks = new AnnularSector[stages];
        
        ProximityMarkParameters[] proximityMarksParameters = proximityMarkParametersFactory.create();
        for(int i = 0; i < proximityMarks.length; i++) {
            
            proximityMarks[i] = new AnnularSector(center, 
                    proximityMarksParameters[i].getOuterRadius(), 
                    proximityMarksParameters[i].getInnerRadius(), 
                    start, extent);
            
            float hue = 90 - (120 / (float)stages) * i;
            proximityMarks[i].setColor(new HSLColor(hue));
        }
    }
    
    /**
     * Gets the visualized proximity.
     *
     * @return the visualized proximity
     */
    public double getProximity() {
        return proximity;
    }
    
    /**
     * Sets the proximity value to visualize.
     *
     * @param proximity double from [0,1], where 0 means that nearest object is far and 1 means that it's very near. 
     */
    public void setProximity(double proximity) {
        if (proximity < 0 || proximity > 1) {
            throw new IllegalArgumentException();
        }
        this.proximity = proximity;
    }
    
    /**
     * Updates position and size of the circle.
     *
     * @param center the center of the ProximityVisualizer
     * @param radius the radius of the ProximityVisualizer
     */
    public void updateModel(Point center, float radius) {
        proximityMarkParametersFactory.setRadius(radius);
        ProximityMarkParameters[] proximityMarksParameters = proximityMarkParametersFactory.create();
        for (int i = 0; i < proximityMarks.length; i++) {
            proximityMarks[i].update(center, 
                    proximityMarksParameters[i].getOuterRadius(), 
                    proximityMarksParameters[i].getInnerRadius());
        }
    }
    
    /**
     * Paints the ProximityVisualizer.
     *
     * @param g the Graphics2D to paint the ProximityVisualizer with
     * @param background the background color to use during painting the ProximityVisualizer
     */
    public void paint(Graphics g, IntColor background) {
        int proximityMarksToPaint = (int)Math.round(proximityMarks.length * proximity);
        for(int i = proximityMarksToPaint; i > 0; i--) {
            proximityMarks[i - 1].paint(g, background);
        }
    }
}
