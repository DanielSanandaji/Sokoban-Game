import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class framework extends JPanel implements KeyListener{

	private JFrame window = new JFrame("Frame");

	//template metohd
	public void play() {
		initiate();
		this.setFocusable(true);
		this.addKeyListener(this);
	
		window.add(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
			createKeyLogic(e);
	}
	
	public abstract void initiate();
	public abstract void createKeyLogic(KeyEvent e) ;

	
	public  void setSize(int x , int y) {
		window.setSize(x, y);
	}

	 
		
}

