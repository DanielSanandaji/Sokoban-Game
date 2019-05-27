
	import java.awt.Graphics;

	import javax.swing.ImageIcon;
	import javax.swing.JOptionPane;

	public class SokobanMan {
		
		
		private int xAxis;
		private int yAxis;
		private String imagePath;
		
		public SokobanMan(int xAxis, int yAxis, String imagePath) {
			super();
			this.setImagePath(imagePath);
			this.setxAxis(xAxis);
			this.setyAxis(yAxis);
			
		}
		
		public int getxAxis() {
			return xAxis;
		}
		public void setxAxis(int xAxis) {
			this.xAxis = xAxis;
		}
		public int getyAxis() {
			return yAxis;
		}
		public void setyAxis(int yAxis) {
			this.yAxis = yAxis;
		}
		public String getImagePath() {
			return imagePath;
		}
		public void setImagePath(String imagePath) {
			if(imagePath == null) {
				JOptionPane.showMessageDialog(null, "Invalid string!");
			}
			else if(imagePath.length()==0) {
				JOptionPane.showMessageDialog(null, "String is empty");
			}
			else {
			this.imagePath = imagePath;
			}
		}
		public void drawPlayer(Graphics g) {
			
			ImageIcon img = new ImageIcon(imagePath);
			g.drawImage(img.getImage(), xAxis, yAxis, null);
		}

	}

		