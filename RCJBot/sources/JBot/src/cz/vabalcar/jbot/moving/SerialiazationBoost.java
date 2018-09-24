package cz.vabalcar.jbot.moving;

public class SerialiazationBoost implements Movement {

	private static final long serialVersionUID = 1L;
	
	private AccelerationChange accelerationChange = new AccelerationChange(0);
	private Locomotion locomotion = new Locomotion(Direction.FORWARD);
	private MaxSpeedChange maxSpeedChange = new MaxSpeedChange(0);
	private MotorMovement motorMovement = new MotorMovement(0, null);
	private Rotation rotation = new Rotation(Direction.RIGHT);
	private Stop stop = new Stop();

	public AccelerationChange getAccelerationChange() {
		return accelerationChange;
	}

	public Locomotion getLocomotion() {
		return locomotion;
	}

	public MaxSpeedChange getMaxSpeedChange() {
		return maxSpeedChange;
	}

	public MotorMovement getMotorMovement() {
		return motorMovement;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public Stop getStop() {
		return stop;
	}

	@Override
	public void accept(MovementVisitor visitor) throws UnsupportedMovementException {
		throw new UnsupportedMovementException(this);
	}

}
