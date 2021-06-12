import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FightPanel extends JPanel implements MouseListener{

	// JPanels like to be serialized
	private static final long serialVersionUID = 1L;
	
	
	private Image myImage;
	private Monster myMonster;
	private ImageIcon myBackground;
	private ImageIcon myMonsterImageIcon;
	private Timer myTimer;
	private Timer myTimer2;
	int startButtonCounter;
	
	private UserProfile myPlayer;
	private int scaledAttackDamage;
	
	// deal with this !!!TODO
	private int healthBarYCoordinate = 85;
	private int healthBarHeight = 530;
	private int timerBarYCoordinate = 85;
	private int timerBarHeight = 530;
	private boolean barsEnabled = false;
	private String startButtonText = "Start";
	private boolean showClickAnimation = false;
	private Point clickLocation;
	
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
	}

	
	
	// Displays the background, Monster animation, and status bars on the JPanel
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Paints the background
		myBackground.paintIcon(this, g, 0, 0);
		
		// Paints the current animation frame
		if (myMonsterImageIcon != null) {
		 myMonsterImageIcon.paintIcon(this, g, 75, 185);
		}
		
		
		if (barsEnabled) {
			// draws health bar
			g.setColor(Color.green);
			g.fillRect(10, healthBarYCoordinate, 54, healthBarHeight);

			// draws time bar
			g.setColor(Color.white);
			g.fillRect(436, timerBarYCoordinate, 54, timerBarHeight);
		}
		
		if (showClickAnimation) {
			g.setColor(Color.red);
			//g.fillOval((int) clickLocation.getY() - 183, (int) clickLocation.getY() - 183, 15, 15);
			g.setFont(new Font("Helvetica", Font.BOLD, 30));
			g.drawString(String.valueOf(myPlayer.getCurrentDamage()), (int) clickLocation.getX() - 715, (int) clickLocation.getY() - 183);
			

		}
		
	}
	
	/**
	 * Scales the health of the monster and the attack damage of the player to match the pixel size of the green health bar. 
	 */
	public void scaleDamage() {
		final int percentage = 605 / myMonster.getHealthPoints();
		scaledAttackDamage = myPlayer.getCurrentDamage() * percentage;
	}
	
	/**
	 *  Action Listener class that works with myTimer to paint animation frames. Each time this action listener is called, it generates the next animation frame.  
	 */
	private class MonsterAnimator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			myMonsterImageIcon = myMonster.getNextFrame();
			repaint();
			revalidate();
		}
	}
	
	private class TimerBarAnimator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			timerBarYCoordinate += 1;
			timerBarHeight -= 1;
			if (timerBarYCoordinate > 615) dismisMonster();
			repaint();
			revalidate();
		}
	}
	
	private class clickAnimator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}

	
	
	
	
	/**
	 * Creates all of the buttons and their respective action listeners. 
	 */
	public void setupButtons() {
		
		// Start or "Play" button setup
		final JButton startButton = new JButton(startButtonText);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				// This is called when the Start Button is pressed while in "Start" mode
				if (startButtonCounter % 2 == 0) {
					start();
				// This is called when the Start Button is pressed while in "Stop" mode
				} else {
					pause();
				}
				// Avoids possible negative int issues
				if (startButtonCounter == 10000) {
					startButtonCounter = 0;
				}
				barsEnabled = true;
			}
		});
		startButton.setBackground(Color.DARK_GRAY);
		startButton.setForeground(Color.white);
		startButton.setBounds(210, 8, 80, 65);
		startButton.setEnabled(false);
		add(startButton);
		
		myButtons.put("Start", startButton);
		
		//
		JFrame monsterMenu = new JFrame("Monsters");
		monsterMenu.setSize(300, 300);
		monsterMenu.setResizable(false);
		monsterMenu.setLayout(new GridLayout(3, 3));
		monsterMenu.setVisible(false);
		monsterMenu.setLocationRelativeTo(null);
		monsterMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final JButton monsterSelectionButton = new JButton("Monsters");
		monsterSelectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				monsterMenu.setVisible(true);
				pause();
			}
		});
		monsterSelectionButton.setBackground(Color.DARK_GRAY);
		monsterSelectionButton.setForeground(Color.white);
		monsterSelectionButton.setBounds(9, 8, 100, 65);
		add(monsterSelectionButton);
		
		
		/**
		 * Buttons on the Monster Selection JFrame
		 */
		
		// Gleeb Button
		final JButton gleebButton = new JButton();
		try { // Loads gleeb icon image for the monster menu.
			BufferedImage myBufferedImage = ImageIO.read(new File("src/images/Gleeb Idle1.png"));
			Image gleebIcon = myBufferedImage.getScaledInstance(60, 70, 0);
			gleebButton.setIcon(new ImageIcon(gleebIcon));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gleebButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent theEvent) {
				deployMonster(new Gleeb());
				monsterMenu.setVisible(false);
			}
		});
		monsterMenu.add(gleebButton);
		
	}
	
	/**
	 * Sets the input monster as the focus of the fight panel.
	 * @param theMonster
	 */
	public void deployMonster(final Monster theMonster) {
		if (myMonster != null) dismisMonster();
		myMonster = theMonster;
		myMonsterImageIcon = theMonster.getNextFrame();
		myTimer = new Timer(theMonster.getRefreshRate(), new MonsterAnimator());
		myTimer2 = new Timer(25, new TimerBarAnimator());
		scaleDamage();
		barsEnabled = true;
		repaint();
		revalidate();
		myButtons.get("Start").setEnabled(true);
	}
	
	/**
	 * Removes the current monster from the focus of the fight panel.
	 */
	public void dismisMonster() {
		myMonster = null;
		myMonsterImageIcon = null;
		myTimer.stop();
		myTimer2.stop();
		myButtons.get("Start").setText("Start");
		startButtonCounter = 0;
		barsEnabled = false;
		healthBarYCoordinate = 85;
		healthBarHeight = 530;
		timerBarYCoordinate = 85;
		timerBarHeight = 530;
		myButtons.get("Start").setEnabled(false);
	}
	
	public void start() {
		myButtons.get("Start").setText("Pause");
		myTimer.start();
		myTimer2.start();
		startButtonCounter++;
	}
	
	public void pause() {
		if (myTimer != null) myTimer.stop();
		if (myTimer2 != null) myTimer2.stop();
		if (startButtonCounter % 2 == 0) startButtonCounter++;
		myButtons.get("Start").setText("Start");
		startButtonCounter++;
	}
	
	
	
	/**\
	 * The mouse clicked event. Used as an "attack" against the Monster currently in focus.  
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Only notices mouse clicks when the Start Button is activated. 
		if (startButtonCounter % 2 != 0) {
			healthBarYCoordinate = healthBarYCoordinate + scaledAttackDamage;
			healthBarHeight -= scaledAttackDamage;
			
			showClickAnimation = true;
			clickLocation = e.getLocationOnScreen();
			
			// Stops the monster animation and resets health bar when the monster is defeated. 
			if (healthBarYCoordinate > 615) {
				dismisMonster();
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
		showClickAnimation = false;
		repaint();
		revalidate();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	
	
}
