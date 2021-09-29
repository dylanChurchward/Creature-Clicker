import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import Creatures.CreatureList;
import Creatures.CreatureNode;

public class FightPanel extends JPanel implements MouseListener {

	// JPanels like to be serialized
	private static final long serialVersionUID = 1L;
	
	private Image myImage;
	private CreatureNode myCreature;
	private ImageIcon myBackground;
	private ImageIcon myCreatureImageIcon;
	private Timer myTimer;
	private Timer myTimer2;
	private UserProfile myPlayer;
	private int scaledAttackDamage;
	
	// deal with this !!!TODO
	private int healthBarYCoordinate = 85;
	private int healthBarHeight = 530;
	private int timerBarYCoordinate = 85;
	private int timerBarHeight = 530;
	private boolean barsEnabled = false;
	private String play = "Play";
	
	// used to toggle repeat on and off
	private boolean repeat = false; 
	private boolean start = false;
	private boolean auto = false;
	
	private Map<String, JButton> myButtons;
	
	/**
	 * 
	 */
	public FightPanel() {
		try { // Loads the background image
			BufferedImage myBufferedImage = ImageIO.read(new File("src/images/dungeon background.png"));
			myImage = myBufferedImage.getScaledInstance(500, 700, 0);
			myBackground = new ImageIcon(myImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Adds the mouse listener to track clicks
		addMouseListener(this);
		// Set layout null allows us to arrange buttons precisely.
		setLayout(null);
		// Sets up all buttons
		myButtons = new HashMap<>();
		setupButtons();
		myPlayer = new UserProfile();
		CreatureList cl = new CreatureList(); 
		deployCreature(cl.getFront());
	}

	// Displays the background, Monster animation, and status bars on the JPanel
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Paints the background
		myBackground.paintIcon(this, g, 0, 0);
		
		// Paints the current animation frame
		if (myCreatureImageIcon != null) {
		 myCreatureImageIcon.paintIcon(this, g, 75, 185);
		}
		
		
		if (barsEnabled) {
			// draws health bar
			g.setColor(Color.green);
			g.fillRect(10, healthBarYCoordinate, 54, healthBarHeight);

			// draws time bar
			g.setColor(Color.white);
			g.fillRect(436, timerBarYCoordinate, 54, timerBarHeight);
		}
	}
	
	/**
	 * Scales the health of the monster and the attack damage of the player to match the pixel size of the green health bar. 
	 */
	public void scaleDamage() {
		final int percentage = 605 / myCreature.getMyHealthPoints();
		scaledAttackDamage = myPlayer.getCurrentDamage() * percentage;
	}
	
	/**
	 *  Action Listener class that works with myTimer to paint animation frames. Each time this action listener is called, it generates the next animation frame.  
	 */
	private class CreatureAnimator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			myCreatureImageIcon = myCreature.getNextFrame();
			repaint();
			revalidate();
		}
	}
	
	private class TimerBarAnimator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			timerBarYCoordinate += 1;
			timerBarHeight -= 1;
			repaint();
			revalidate();
			if (timerBarYCoordinate > 615) {
				pause();
				deployCreature(myCreature);
			}
		}
	}
	
	/**
	 * Creates all of the buttons and their respective action listeners. 
	 */
	public void setupButtons() {
		
		// Play button setup
		final JButton playButton = new JButton(play);
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				start = !start;
				if (start) {
					play();
				// This is called when the Start Button is pressed while in "Stop" mode
				} else {
					pause();
				}
				// Avoids possible negative int issues
				barsEnabled = true;
			}
		});
		playButton.setBackground(Color.DARK_GRAY);
		playButton.setForeground(Color.white);
		playButton.setBounds(210, 8, 80, 65);
		playButton.setEnabled(true);
		add(playButton);
		myButtons.put(play, playButton);
		
		// When enabled, allows the user to battle the same creature repeatedly
		final JButton repeatButton = new JButton("Repeat");
		repeatButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				repeat = !repeat;
				if (repeat) {
					repeatButton.setForeground(Color.green);
				} else {
					repeatButton.setForeground(Color.white);
				}
			}
		});
		repeatButton.setBackground(Color.DARK_GRAY);
		repeatButton.setForeground(Color.white);
		repeatButton.setBounds(392, 8, 98, 65);
		repeatButton.setEnabled(true);
		add(repeatButton);
		
		// Move focus to the next creature in the list if it is available to the user. (If they have beaten the current creature).
		final JButton nextButton = new JButton("Next >>");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				if (myCreature.getMySuccesor() != null && myCreature.getMySuccesor().getMyAvailabilty() != false) {
					deployCreature(myCreature.getMySuccesor());
				}
			}
		});
		nextButton.setBackground(Color.DARK_GRAY);
		nextButton.setForeground(Color.white);
		nextButton.setBounds(299, 8, 79, 65);
		nextButton.setEnabled(true);
		add(nextButton);

		// Move focus to the next creature in the list if it is available to the user.
		// (If they have beaten the current creature).
		final JButton previousButton = new JButton("<< Prev");
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				if (myCreature.getMyPredecessor() != null) {
					deployCreature(myCreature.getMyPredecessor());
				} 
			}
		});
		previousButton.setBackground(Color.DARK_GRAY);
		previousButton.setForeground(Color.white);
		previousButton.setBounds(122, 8, 79, 65);
		previousButton.setEnabled(true);
		add(previousButton);
		
		// Automatically starts the next creature battle without having to press the start button. 
		final JButton autoButton = new JButton("Auto");
		autoButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				auto = !auto;
				if (auto) {
					autoButton.setForeground(Color.green);
				} else {
					autoButton.setForeground(Color.white);
				}
			}
		});
		autoButton.setBackground(Color.DARK_GRAY);
		autoButton.setForeground(Color.white);
		autoButton.setBounds(10, 8, 100, 65);
		autoButton.setEnabled(true);
		add(autoButton);
	}
	
	/**
	 * Sets the input monster as the focus of the fight panel.
	 * @param theCreature
	 */
	public void deployCreature(final CreatureNode theCreature) {
		if (myCreature != null) dismisCreature();
		myCreature = theCreature;
		myCreatureImageIcon = theCreature.getNextFrame();
		myTimer = new Timer(theCreature.getMyRefreshRate(), new CreatureAnimator());
		myTimer2 = new Timer(25, new TimerBarAnimator());
		scaleDamage();
		barsEnabled = true;
		repaint();
		revalidate();
		myButtons.get(play).setEnabled(true);
	}
	
	/**
	 * Removes the current monster from the focus of the fight panel.
	 */
	public void dismisCreature() {
		myCreature.reset();
		myCreature = null;
		myCreatureImageIcon = null;
		myTimer.stop();
		myTimer2.stop();
		myButtons.get("Play").setText(play);
		myButtons.get("Play").setForeground(Color.white);
		start = false;
		barsEnabled = false;
		healthBarYCoordinate = 85;
		healthBarHeight = 530;
		timerBarYCoordinate = 85;
		timerBarHeight = 530;
		myButtons.get(play).setEnabled(false);
	}
	
	public void play() {
		myButtons.get(play).setForeground(Color.green);
		myButtons.get(play).setText("Pause");
		myTimer.start();
		myTimer2.start();
		start = true;
	}
	
	public void pause() {
		if (myTimer != null) myTimer.stop();
		if (myTimer2 != null) myTimer2.stop();
		myButtons.get(play).setForeground(Color.white);
		myButtons.get("Play").setText("Play");
		start = false;
	}
	
	private void chooseCreature() {
		if (repeat) {
			deployCreature(myCreature);
		} else if (myCreature.getMySuccesor() != null) {
			deployCreature(myCreature.getMySuccesor());
		} else if (myCreature.getMySuccesor() == null) {
			dismisCreature();
			auto = false;
			repeat = false;
			start = false;
		}
		if (auto) {
			play();
		}
	}
	
	/**\
	 * The mouse clicked event. Used as an "attack" against the Creature currently in focus.  
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Only notices mouse clicks when the Start Button is activated. 
		if (start) {
			healthBarYCoordinate = healthBarYCoordinate + scaledAttackDamage;
			healthBarHeight -= scaledAttackDamage;
			
			if (healthBarYCoordinate > 615) {
				if (myCreature.getMySuccesor() != null) {
					myCreature.getMySuccesor().setMyAvailabilty(true);
				}
				chooseCreature();
			}
			repaint();
			revalidate();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
}
