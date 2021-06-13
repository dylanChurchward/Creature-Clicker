import javax.swing.ImageIcon;

/**
 * Parent class for all Monster Types.
 * @author Dylan
 *
 */
public abstract class Monster {

	/**
	 * The health points that this monster starts with. 
	 */
	private final int myHealthPoints;
	
	/**
	 * The refresh rate in milliseconds used for animating the creature. 
	 */
	private final int myRefreshRate;
	
	/**
	 * Constructor for the Monster abstract class. 
	 * @param theHealthPoints desired health points for this monster. 
	 * @param theRefreshRate desired refresh rate for this monster. 
	 */
	public Monster(final int theHealthPoints, final int theRefreshRate) {
		myHealthPoints = theHealthPoints;
		myRefreshRate = theRefreshRate;
	}
	
	/**
	 * Returns the total health points for this creature. 
	 * @return total health points of this creature. 
	 */
	public int getHealthPoints() {
		return myHealthPoints;
	}
	
	/**
	 * Returns the animation refresh rate for this creature. 
	 * @return animation refresh rate for this creature. 
	 */
	public int getRefreshRate() {
		return myRefreshRate;
	}
	
	/**
	 * 
	 * @return the next ImageIcon in the set of ImageIcons that make up
	 * this creatures animation cycle. 
	 */
	public abstract ImageIcon getNextFrame();
}
