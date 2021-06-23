package Creatures;


import javax.swing.ImageIcon;

/**
 * Abstract class to be extended by all creatures in this program. Enables creatures to
 * have typical characteristics such as health points as well as enables them to act as their own
 * nodes in the CreatureList structure that this program utilizes. 
 * @author Dylan
 *
 */
public abstract class CreatureNode {

	/**
	 * Create to the "left" of this creature in the linked structure of creature nodes. 
	 */
	private CreatureNode myPredecessor;
	
	/**
	 * Creature to the "right" of this creature in the linked structure of creature nodes.
	 */
	private CreatureNode mySuccessor;

	/**
	 * Health points the creature spawns with. 
	 */
	private int myHealthPoints;
	
	/**
	 * Refresh rate used for the animation cycle of this creature.
	 */
	private int myRefreshRate;
	
	/**
	 * Used to determine if the user has access to this creature or not. 
	 */
	private boolean myAvailability;
	
	/**
	 * HashMap used to store and cycle through 
	 */
	protected ImageIcon[] myIcons;
	
	
	/**
	 * Public constructor for CreatureNode abstract class. 
	 * @param theHealthPoints desired health points for this creature. 
	 * @param theRefreshRate desired refresh rate for the animation cycle of this creature. 
	 * @param theAvailability whether or not this creature is available to the user. 
	 */
	public CreatureNode(final int theHealthPoints, final int theRefreshRate, final boolean theAvailability) {
		myHealthPoints = theHealthPoints; 
		myRefreshRate = theRefreshRate;
		myAvailability = theAvailability;
	}
	
	/**
	 * Loads the image files so they are ready to be used for animation. 
	 */
	protected abstract void loadAnimationFrames();
	
	/**
	 * Resets the animation frame count to zero, essentially resetting the animation to its starting point 
	 */
	public abstract void reset();
	
	
	// Getters 
	
	/**
	 * Getter for myPredecessor. 
	 */
	public CreatureNode getMyPredecessor() {
		return myPredecessor;
	}
	
	/**
	 * Getter for mySuccessor. 
	 */
	public CreatureNode getMySuccesor() {
		return mySuccessor;
	}
	
	/**
	 * Getter for myHealthPoints. 
	 */
	public int getMyHealthPoints() {
		return myHealthPoints;
	}
	
	/**
	 * Getter for myRefreshRate. 
	 */
	public int getMyRefreshRate() {
		return myRefreshRate;
	}
	
	/**
	 * Getter for myAvailability. 
	 */
	public boolean getMyAvailabilty() {
		return myAvailability;
	}
	
	/**
	 * Getter for the next frame in the animation cycle. 
	 * @return the next frame for the animation cycle. 
	 */
	public abstract ImageIcon getNextFrame();
	
	// Setters

	/**
	 * Setter for myPredecessor.
	 */
	public void setMyPredecessor(final CreatureNode theNode) {
		myPredecessor = theNode;
	}

	/**
	 * Setter for mySuccessor.
	 */
	public void setMySuccesor(final CreatureNode theNode) {
		mySuccessor = theNode;
	}

	/**
	 * Setter for myAvailability.
	 */
	public void setMyAvailabilty(final boolean b) {
		myAvailability = b; 
	}
	
}
