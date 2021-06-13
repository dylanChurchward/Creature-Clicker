package Creatures;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GreenGleeb extends CreatureNode{

	/**
	 * Standard health points for the Green Gleeb.
	 */
	private static int HEALTH_POINTS = 100;
	
	/**
	 * Standard refresh rate for the Green Gleeb. 
	 */
	private static int REFRESH_RATE = 80;
	
	/**
	 * Standard number of image files for the Green Gleeb.
	 */
	private static int NUMBER_OF_IMAGES = 8;
	
	/**
	 * As it is the first creature, the Green Gleeb starts out
	 * as available. 
	 */
	private static boolean AVAILABILITY = true;
	
	// The images get shuffled around these variables in order to set/get
	// them effectively.
	private BufferedImage myBufferedImage;
	private Image myImage;
	private ImageIcon myOutputFrame;
	private int frameCount = 0;
	
	
	/**
	 * Constructor for the GreenGleeb creature class. Uses the static fields
	 * of the class to call the constructor of the super class as well as instantiate
	 * the ImageIcon array which stores the animation frames. 
	 */
	public GreenGleeb() {
		super(HEALTH_POINTS, REFRESH_RATE, AVAILABILITY);
		myIcons = new ImageIcon[NUMBER_OF_IMAGES];
		loadAnimationFrames();
	}

	@Override
	protected void loadAnimationFrames() {
		final int monsterSizeInPixels = 345;
		final int numberOfImages = 8;
		
		for (int i = 0; i < numberOfImages; i++) {
			try { // Loads the Monster animation frames
				myBufferedImage = ImageIO.read(new File("src/Images/greenGleeb" + i + ".png"));
			} catch (IOException e) {
				System.out.println("Couldn't find the image at location: " + "src/Images/greenGleeb" + i + ".png");
			}
			myImage = myBufferedImage.getScaledInstance(monsterSizeInPixels, -1, 0);
			myIcons[i] = new ImageIcon(myImage);
		}
		myOutputFrame = myIcons[0];
		if (myOutputFrame == null) {
			System.out.println("There's no image here.");
		}
	}
	
	@Override
	public ImageIcon getNextFrame() {
		myOutputFrame = myIcons[frameCount];
		frameCount++;
		if (frameCount >= myIcons.length) frameCount = 0;
		return myOutputFrame;
	}

	public static void main(String[] args) {
		GreenGleeb g = new GreenGleeb(); 
		for (int i = 0; i < 1000; i++) {
			g.getNextFrame();
		}
	}
}
