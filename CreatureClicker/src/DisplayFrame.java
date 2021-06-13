

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class DisplayFrame extends JFrame{

	// JFrames like to be serialized
	private static final long serialVersionUID = 1L;
	
	public DisplayFrame() {
		FightPanel fightPanel = new FightPanel();
		//fightPanel.setNewMonster(new Gleeb());
		pack();
		setTitle("Creature Clicker");
		setSize(515, 738);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(fightPanel);
	}
	
	public static void main(final String[] theArgs) {
		DisplayFrame df = new DisplayFrame();
		df.setLocationRelativeTo(null);
		
		
	}

}
