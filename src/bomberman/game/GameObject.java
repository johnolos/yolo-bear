package bomberman.game;

import java.io.Serializable;
/**
 * Enum GameObject implements Serializable
 */
public enum GameObject implements Serializable{
		PLAYER,
		BOMB,
		KICK,
		THROW,
		POWERUP_CONSUMED,
		POWERUP,
		DIED;
}
