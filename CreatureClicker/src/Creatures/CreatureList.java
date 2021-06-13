package Creatures;

/**
 * A linked list structure constructed of all of the creatures 
 * that exist within the game. The main functions of this class are
 * to initialize and connect all of the creature classes and provide
 * access to the collection. 
 * @author Dylan
 *
 */
public class CreatureList {

	/**
	 * Initialized as a Green Gleeb, used as an access point 
	 * to the creature list. 
	 */
	private CreatureNode myFront;
	
	/**
	 * Constructor for the CreatureList class.
	 */
	public CreatureList() {
		setupCreatures();
	}
	
	/**
	 * Initializes all of the creatures present in the game and connects them 
	 * in a linked list type structure. Sets the myPointer 
	 */
	private void setupCreatures() {
		GreenGleeb greenGleeb = new GreenGleeb();
		RedGleeb redGleeb = new RedGleeb();
		BlueGleeb blueGleeb = new BlueGleeb();

		myFront = greenGleeb;
		
		greenGleeb.setMyPredecessor(null); // for clarity, first creature
		greenGleeb.setMySuccesor(redGleeb);
		
		redGleeb.setMyPredecessor(greenGleeb);
		redGleeb.setMySuccesor(blueGleeb);
		
		blueGleeb.setMyPredecessor(redGleeb);
		blueGleeb.setMySuccesor(null); // for clarity, last creature 
	}
	
	/**
	 * Getter for myPointer CreatureNode. 
	 * @return CreatureNode
	 */
	public CreatureNode getFront() {
		return myFront;
	}
	
}
