import javax.swing.ImageIcon;

/**
 * Parent class for all Monster Types.
 * @author Dylan
 *
 */


public abstract class Monster {

	private static int HEALTH_POINTS;
	
	private static int REFRESH_RATE_IN_MS;
	
	
	
	public Monster(final int theHealthPoints, final int theRefreshRate) {
		HEALTH_POINTS = theHealthPoints;
		REFRESH_RATE_IN_MS = theRefreshRate;
	}
	
	
	public int getHealthPoints() {
		return HEALTH_POINTS;
	}
	
	public int getRefreshRate() {
		return REFRESH_RATE_IN_MS;
	}
	
	public abstract ImageIcon getNextFrame();
	
	
}
