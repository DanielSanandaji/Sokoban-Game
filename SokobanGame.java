import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class SokobanGame extends framework{
	
	private int[][] imageArray = {{1, 1, 1, 1, 1, 1, 1, 1, 1 ,1}, 
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
			{1, 2, 2, 2, 3, 4, 2, 2, 2, 1}, 
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
			{1, 2, 2, 2, 2, 2, 2, 2, 2 ,1}, 
			{1, 2, 2, 2, 2, 4, 3, 2, 2 ,1}, 
			{1, 2, 2, 2, 2, 2, 2, 2, 2 ,1}, 
			{1, 2, 2, 2, 3, 4, 2, 2, 2 ,1}, 
			{1, 1, 1, 1, 1, 1, 1, 1, 1 ,1}};
	
	private int[][] resetArray = imageArray;

	private ImageIcon wall = new ImageIcon("wall.png");
	private ImageIcon background = new ImageIcon("blank.png");
	private ImageIcon crate = new ImageIcon("crate.png");
	private ImageIcon cratemarked = new ImageIcon("cratemarked.png");
	private ImageIcon blankmarked = new ImageIcon("blankmarked.png");
	private Clip clip;
	
	private SokobanMan sokoman = new SokobanMan(32, 32 , "player.png");
	private int speed;
	
	private int goalPoints;
	int points;
	
	
	public void reset() {
		int[][] resetArray = {{1, 1, 1, 1, 1, 1, 1, 1, 1 ,1}, 
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
				{1, 2, 2, 2, 3, 4, 2, 2, 2, 1}, 
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
				{1, 2, 2, 2, 2, 2, 2, 2, 2, 1}, 
				{1, 2, 2, 2, 2, 2, 2, 2, 2 ,1}, 
				{1, 2, 2, 2, 2, 4, 3, 2, 2 ,1}, 
				{1, 2, 2, 2, 2, 2, 2, 2, 2 ,1}, 
				{1, 2, 2, 2, 3, 4, 2, 2, 2 ,1}, 
				{1, 1, 1, 1, 1, 1, 1, 1, 1 ,1}};
		imageArray = resetArray;
		sokoman.setxAxis(32);
		sokoman.setyAxis(32);
		points= 0;
		this.repaint();
	}
	
	public void paint(Graphics g) {

		for(int i = 0; i < imageArray.length; i ++) {
			for(int j= 0; j < imageArray.length; j ++) {
				if(imageArray[i][j] == 1) {
				g.drawImage(wall.getImage(), i*32, j*32, null);
				}
				else if(imageArray[i][j] == 2){
					g.drawImage(background.getImage(), i*32, j*32, null);
				}
				else if(imageArray[i][j] == 3){
					g.drawImage(crate.getImage(), i*32, j*32, null);
				}
				else if(imageArray[i][j] == 4)
					g.drawImage(blankmarked.getImage(), i*32, j*32, null);
				else {
					
					g.drawImage(cratemarked.getImage(), i*32, j*32, null);
				}
				
			}
		}
		sokoman.drawPlayer(g);
	
	}

	
	
	


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initiate() {
		setSize(320,340);
		goalPoints = 3;
		points = 0;
		speed = 32;
		
	}
	
	public boolean isWin() {
		if(points == goalPoints) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void victory() {
		clip.stop();
	
			playSound("victorySound.wav");
		
		
		System.out.println("You WIN!");
		reset();
		
	}
	
	
	public void playSound(String soundPath) {
		try {
			clip =  AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		File file = new File(soundPath);
		try {
			clip.open(AudioSystem.getAudioInputStream(file));
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
	}
	


	@Override
	public void createKeyLogic(KeyEvent e) {

		if(e.getKeyCode()==KeyEvent.VK_RIGHT) { //If KeytEventcode is equal rightbutton then...
			//JOptionPane.showMessageDialog(null, "Right key is pressed");
			sokoman.setImagePath("player.png"); //Setting image path
			sokoman.setxAxis(sokoman.getxAxis() + speed); //Moving sokobanman 32 pixels
			if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==1) { //If man goes into wall move back to previous pixel.
				sokoman.setxAxis(sokoman.getxAxis()-32);
			}
			else 
				//Push a box
				if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==3) {//If sokobanman walks into a box...
					if(imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 1 || imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 3
							 || imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 5){//If sokobanman pushes box into wall.
						sokoman.setxAxis(sokoman.getxAxis()-32); //Do nothing
					}
					
					if (imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] = 3; //Move box
					}
					if (imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] = 5; //Move box in mark and become marked box	
						points++;
						if(isWin()) {
							victory();
						}
						
					}
					
			}
				else if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==5) {//If sokobanman walks into a mark box...
					if(imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 1 || imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 3
							|| imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 5){//If sokobanman pushes box into wall.
						sokoman.setxAxis(sokoman.getxAxis()-32); //Do nothing
					}
					
					if (imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] = 3; //Move box change color
						points--;
					}
					if (imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32+1][sokoman.getyAxis()/32] = 5; //Move box in mark and become marked box again	
				
					}
				}
			this.repaint();
		
				playSound("marioJump.wav");
			
			
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			sokoman.setImagePath("player.png");
			sokoman.setxAxis(sokoman.getxAxis()-speed);
			if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==1){
				sokoman.setxAxis(sokoman.getxAxis()+32);
			}
			else 
				//Push a box
				if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==3) {//If sokobanman walks into a box...
					if(imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 1 || imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 3 
							|| imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 5) {//If sokobanman pushes box into wall.
					sokoman.setxAxis(sokoman.getxAxis()+32); //Do nothing
					}
					if (imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] = 3; //Move box
					}
					if (imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] = 5; //Move box in mark and become marked box	
						points++;
						if(isWin()) {
							victory();
						}
					}
			}
				else if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==5) {//If sokobanman walks into a mark box...
					if(imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 1 || imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 3
							|| imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 5){//If sokobanman pushes box into wall.
						sokoman.setxAxis(sokoman.getxAxis()+32); //Do nothing
					}
					
					if (imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] = 3; //Move box change color
						points--;
					}
					if (imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32-1][sokoman.getyAxis()/32] = 5; //Move box in mark and become marked box again	
					}
				}
			this.repaint();
				playSound("marioJump.wav");
			
			
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			sokoman.setImagePath("player.png");
			sokoman.setyAxis(sokoman.getyAxis()-speed);
			if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==1) {
				sokoman.setyAxis(sokoman.getyAxis()+32);
			}
			else 
				//Push a box
				if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==3) {//If sokobanman walks into a box...
					if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 1 || imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 3
							|| imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 5) {//If sokobanman pushes box into wall.
					sokoman.setyAxis(sokoman.getyAxis()+32); //Do nothing
					System.out.println("HEj");
					}
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] = 3; //Move box
					}
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] = 5; //Move box in mark and become marked box	
						points++;
						if(isWin()) {
							victory();
						}
					}
			}
				else if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==5) {//If sokobanman walks into a mark box...
					if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 1 || imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 3
							|| imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 5){//If sokobanman pushes box into wall.
						sokoman.setxAxis(sokoman.getxAxis()+32); //Do nothing
					}
					
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] = 3; //Move box change color
						points--;
					}
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32-1] = 5; //Move box in mark and become marked box again	
					}
				}
			this.repaint();
		
				playSound("marioJump.wav");
			
			
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			sokoman.setImagePath("player.png");
			sokoman.setyAxis(sokoman.getyAxis()+speed);
			if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==1) {
				sokoman.setyAxis(sokoman.getyAxis()-32);
			}
			else 
				//Push a box
				if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==3) {//If sokobanman walks into a box...
					if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 1 || imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 3 
							|| imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 5) {//If sokobanman pushes box into wall.
					sokoman.setyAxis(sokoman.getyAxis()-32); //Do nothing
					}
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] = 3; //Move box
					}
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 2; // Repaint empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] = 5; //Move box in mark and become marked box	
						points++;
						if(isWin()) {
							victory();
						}
					}
			}
				else if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32]==5) {//If sokobanman walks into a mark box...
					if(imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 1 || imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 3
							|| imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 5){//If sokobanman pushes box into wall.
						sokoman.setxAxis(sokoman.getxAxis()-32); //Do nothing
					}
					
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 2){//After box is a empty space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] = 3; //Move box change color
						points--;
					}
					if (imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] == 4){//After box is a mark
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32] = 4; // Repaint mark space
						imageArray[sokoman.getxAxis()/32][sokoman.getyAxis()/32+1] = 5; //Move box in mark and become marked box again	
					}
				}
			this.repaint();
			
				playSound("marioJump.wav");
			
			
		}
		else if(e.getKeyCode() == KeyEvent.VK_R) {
			reset();
		}
	}
	}





	

