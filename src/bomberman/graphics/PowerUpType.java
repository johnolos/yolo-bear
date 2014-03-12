package bomberman.graphics;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PowerUpType implements Serializable {
	BOMB, // Increasing number of bombs that player has.
	
	SPEED, // Increasing the movement speed of that player
	
	THROW, // Enables the player to throw bombs
	
	KICK, // Enables player to kick bombs
	
	MAGNITUDE; // Increases the magnitude range of bombs for that player
	
	
	
	private static final List<PowerUpType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	public static PowerUpType randomPowerUp() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
}