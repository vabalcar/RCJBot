package cz.vabalcar.jbot.moving;

import java.io.Serializable;

public interface Movement extends Serializable {
	public void accept(MovementVisitor visitor) throws UnsupportedMovementException;
}
