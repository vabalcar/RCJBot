package cz.vabalcar.jbot.moving;

/**
 * The Interface MovementProcessor.
 */
public interface MovementProcessor {
    
    /**
     * Process.
     *
     * @param movementProcessorAction the movement processor action
     * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
     */
    void process(MovementProcessorAction movementProcessorAction) throws UnsupportedMovementProcessorActionException;
    
	/**
	 * Process.
	 *
	 * @param movement the movement
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void process(Movement movement) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Process.
	 *
	 * @param arcTranslation the arc translation
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void process(ArcTranslation arcTranslation) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Process.
	 *
	 * @param linearTranslation the linear translation
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void process(LinearTranslation linearTranslation) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Process.
	 *
	 * @param rotation the rotation
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void process(Rotation rotation) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Process.
	 *
	 * @param stop the stop
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void process(Stop stop) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Process.
	 *
	 * @param motorMovement the motor movement
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void process(MotorMovement motorMovement) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Apply.
	 *
	 * @param change the change
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void apply(MovementProcessorChange change) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Apply.
	 *
	 * @param angularAccelerationChange the angular acceleration change
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void apply(AngularAccelerationChange angularAccelerationChange) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Apply.
	 *
	 * @param angularSpeedChange the angular speed change
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void apply(AngularSpeedChange angularSpeedChange) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Apply.
	 *
	 * @param linearAccelerationChange the linear acceleration change
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void apply(LinearAccelerationChange linearAccelerationChange) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Apply.
	 *
	 * @param linearSpeedChange the linear speed change
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void apply(LinearSpeedChange linearSpeedChange) throws UnsupportedMovementProcessorActionException;
	
	/**
	 * Apply.
	 *
	 * @param minRadiusChange the min radius change
	 * @throws UnsupportedMovementProcessorActionException the unsupported movement processor action exception
	 */
	void apply(MinRadiusChange minRadiusChange) throws UnsupportedMovementProcessorActionException;
}
