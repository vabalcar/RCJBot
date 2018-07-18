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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
	    return Arrays.hashCode(array);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
	    if (object != null && object instanceof FloatArray) {
	        FloatArray anotherFloatArray = (FloatArray) object;
	        return Arrays.equals(array, anotherFloatArray.array);
	    }
	    return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    if (array.length == 1) {
	        return Float.toString(array[0]);
	    } else {
	        return Arrays.toString(array);
	    }
	}
}
