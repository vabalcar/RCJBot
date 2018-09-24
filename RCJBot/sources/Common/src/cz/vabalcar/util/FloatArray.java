package cz.vabalcar.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * The Class FloatArray.
 */
public class FloatArray implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
	/** The array. */
	private final float[] array;
	
	/**
	 * Instantiates a new float array.
	 *
	 * @param length the length
	 */
	public FloatArray(int length) {
		array = new float[length];
	}
	
	/**
	 * Gets the array.
	 *
	 * @return the array
	 */
	public float[] getArray() {
		return array;
	}
	
	@Override
	public int hashCode() {
	    return Arrays.hashCode(array);
	}
	
	@Override
	public boolean equals(Object object) {
	    if (object != null && object instanceof FloatArray) {
	        FloatArray anotherFloatArray = (FloatArray) object;
	        return Arrays.equals(array, anotherFloatArray.array);
	    }
	    return false;
	}
	
	@Override
	public String toString() {
	    if (array.length == 1) {
	        return Float.toString(array[0]);
	    } else {
	        return Arrays.toString(array);
	    }
	}
}
