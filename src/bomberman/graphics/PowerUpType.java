package bomberman.graphics;

/**
 * implements Serializable
 */
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

	private static final List<PowerUpType> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	/**
	 * The type of powerUp
	 * Weighted probability of the power-ups chosen randomly
	 * @return
	 */
	public static PowerUpType randomPowerUp() {
		Random r = new Random();
		int chooser = r.nextInt(100);
		if (chooser <= 28)
			return BOMB;
		if (chooser > 28 && chooser <= 38)
			return SPEED;
		if (chooser > 38 && chooser <= 54)
			return THROW;
		if (chooser > 54 && chooser <=70)
			return KICK;
		if (chooser > 70)
			return MAGNITUDE;
		return VALUES.get(RANDOM.nextInt(SIZE)); //just in case
	}

}