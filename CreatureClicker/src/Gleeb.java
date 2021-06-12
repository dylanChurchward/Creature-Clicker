import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Gleeb extends Monster {
	
	// Storage for the image files
	private Map<Integer, ImageIcon> myIcons;

	// The images get shuffled around these variables in order to set/get them effectively
	private BufferedImage myBufferedImage;
	private Image myImage;
	private ImageIcon myOutputFrame;
	private int frameCount = 8;
	
	/**
	 * @param int - HealthPoints
	 * @param int - Refresh rate for animation in milliseconds
	 */
	public Gleeb() {
		super(100, 80);
		myIcons = new HashMap<>();
		loadFrames();
	}

	/**
	 * Loads the image files associated with this Monster into ImageIcons, loads the ImageIcons into myIcons map.
	 * @throws IOException - If the image files cannot be found
	 */
	public void loadFrames() {
		final int monsterSizeInPixels = 345;
		final int numberOfImages = 8;
		
		for (int i = 1; i <= numberOfImages; i++) {
			try { // Loads the Monster animation frames
				myBufferedImage = ImageIO.read(new File("src/Images/Gleeb Idle" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			myImage = myBufferedImage.getScaledInstance(monsterSizeInPixels, -1, 0);
			myIcons.put(i, new ImageIcon(myImage));
		}
		myOutputFrame = myIcons.get(1);
		if (myOutputFrame == null) {
			System.out.println("There's no image here.");
		}
	}
	
	
	// Selects and returns the next image frame for animation 
	@Override
	public ImageIcon getNextFrame() {
		myOutputFrame = myIcons.get(frameCount);
		frameCount++;
		if (frameCount > myIcons.size()) frameCount = 1;
		return myOutputFrame;
	}



	

}
