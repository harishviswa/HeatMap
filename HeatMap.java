//Programmer: Harish Viswa
//Collaborated with Anwith and Anshul in class

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class HeatMap extends JPanel implements MouseListener {

	private static final Color BACKGROUND = new Color(204, 204, 204);

	private double[][] tempGrid = new double[50][50];
	private double maxTemp = 150;
	private double minTemp = -150;

	// for part 3, leave them alone
	private int clickedRow = -1;
	private int clickedCol = -1;
	private int clickTemp = 0;
	private int x;
	private int y;
	
	public HeatMap() {
		// TODO: initialize the heat map, half cold, half hot
		for(int i = 0; i < tempGrid.length; i++) {
			for(int j = 0; j < tempGrid[i].length; j++) {
				tempGrid[i][j] = maxTemp;
				tempGrid[i][j/2] = minTemp;
			}
		}
		// TODO: uncomment timer start once you get the color methods to work.
		Timer t = new Timer(40, new Listener());
		t.start();

		addMouseListener(this);

		// Tests get colors methods. Prints results to console.
		for (int temp = (int) minTemp; temp < maxTemp; temp += 10) {
			System.out.printf("%5d: %4d, %4d, %4d\n", temp, getRed(temp), getGreen(temp), getBlue(temp));
		}
	}

	// TODO: complete methods - given a temp value return proper R G or B value.
	public int getRed(double temp) {
//		temp = Math.round(temp/10.0) * 10;
//		if(temp >= 76 && temp <= 150) {
//			return 255;
//		}
//		if(temp >= 1 && temp <= 76) {
//			return 255;
//		}
//		if(temp >= -74 && temp <= 0) {
//			return (int)(temp * 3.35) + 255;
//		}
//		if(temp >= -150 && temp <= -75) {
//			return 0;
//		}
//		return -1;
		if (temp > 0) {
			return 255;
		}else if (temp > -75) {
			return (int)(255-3.2*(0-temp));
		} else {
			return 0;
		}
	}

	public int getGreen(double temp) {
//		temp = Math.round(temp/10.0) * 10;
//		if(temp >= 76 && temp <= 150) {
//			return (int) (temp * -3.35) + 506;
//		}
//		if(temp >= 1 && temp <= 76) {
//			return 255;
//		}
//		if(temp >= -74 && temp <= 0) {
//			return 255;
//		}
//		if(temp >= -150 && temp <= -75) {
//			return (int)(temp * 3.35) + 502;
//		}
//		return -1;
		if(temp > 150){
			return 0;
		}else if (temp <= 150 && temp >=75) {
			return (int) (3.2*(150-temp));
		}else if (temp > -75) {
			return 255;
		}else if (temp <= -75 && temp >= -150) {
			return (int)(255-3.2*(-75-temp));
		}else {
			return 0;
		}
	}

	public int getBlue(double temp) {
//		temp = Math.round(temp/10.0) * 10;
//		if(temp >= 76 && temp <= 150) {
//			return 0;
//		}
//		if(temp >= 1 && temp <= 76) {
//			return (int)(temp * -3.35) + 247;
//		}
//		if(temp >= -74 && temp <= 0) {
//			return 255;
//		}
//		if(temp >= -150 && temp <= -75) {
//			return 255;
//		}
//		return -1;
		if (temp > 75) {
			return 0;
		}else if (temp <=75 && temp >0) {
			return (int) (3.2*(75-temp));
		}else {
			return 255;
		}
	}

	// draws squares representing the temp in each cell
	// method completed for you, nothing to do here
	public void paintComponent(Graphics g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());

		int blockHeight = getHeight() / tempGrid.length + 1;
		int blockWidth = getWidth() / tempGrid[0].length + 1;
		for (int r = 0; r < tempGrid.length; r++) {
			for (int c = 0; c < tempGrid[r].length; c++) {

				double tVal = tempGrid[r][c];
				g.setColor(new Color(getRed(tVal), getGreen(tVal), getBlue(tVal)));

				int x = c * blockWidth; // (x,y) is the upper left hand corner of the rectangle
				int y = r * blockHeight;
				g.fillRect(x, y, blockWidth, blockHeight);
			}
		}

		// Display temperatures of both sides
		String avgLeftTempStr = String.format("%7.2f", tempGrid[tempGrid.length / 2][0]);
		String avgRightTempStr = String.format("%7.2f", tempGrid[tempGrid.length / 2][tempGrid[0].length - 1]);

		g.setColor(Color.black);
		g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 60));
		g.drawString("Left Side Temp", 10, 60);
		g.drawString(avgLeftTempStr, 100, 120);
		g.drawString("Right Side Temp", getWidth() - 450, 60); // TODO: you might need to adjust location
		g.drawString(avgRightTempStr, getWidth() - 350, 120);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO: Part 3, complete the method
		// Convert x/y click to row/col and set clickedTemp
		x = event.getX();
		y = event.getY();
		int blockHeight = getHeight() / tempGrid.length + 1;
		int blockWidth = getWidth() / tempGrid[0].length + 1;
		if (event.getButton() == 1) {
			int index = (int) x/blockWidth;
			int index2 = (int) y/blockHeight;
			tempGrid[index2][index] = maxTemp*10;
			tempGrid[index2+1][index] = maxTemp*10;
			tempGrid[index2-1][index] = maxTemp*10;
			tempGrid[index2][index-1] = maxTemp*10;
			tempGrid[index2][index+1] = maxTemp*10;
		}
		if(event.getButton() == 3) {
			int index = (int) x/blockWidth;
			int index2 = (int) y/blockHeight;
			tempGrid[index2][index] = minTemp*10;
			tempGrid[index2+1][index] = minTemp*10;
			tempGrid[index2-1][index] = minTemp*10;
			tempGrid[index2][index-1] = minTemp*10;
			tempGrid[index2][index+1] = minTemp*10;
		}

	}

	

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO: Part 3, reset clickedRow and clickedCol

	}

	// these mouse methods are unneeded for this lab
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private int sum = 1;
	private double heatValue = 0;
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO: recalculate every element of the 2D array
						for (int i = 0; i < tempGrid.length; i++) {
							for (int j = 0; j < tempGrid[0].length; j++) {
								sum = 1;
								heatValue = 0;
								heatValue += tempGrid[i][j];
								if(i+1 < tempGrid.length) {
									sum++;
									heatValue += tempGrid[i+1][j];
								}
								if(i-1 >= 0) {
									sum++;
									heatValue += tempGrid[i-1][j];
								}
								if(j+1 < tempGrid[0].length) {
									sum++;
									heatValue += tempGrid[i][j+1];
								}
								if(j-1 >= 0) {
									sum++;
									heatValue += tempGrid[i][j-1];
								}
								//average
								tempGrid[i][j] = heatValue/sum;
							}
						}
						
						// TODO: in part 3 you will add an if statement here before repaint

						repaint(); // leave this as the last line of code in actionPerformed
					}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Heat Map");
		frame.setSize(1400, 1005);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new HeatMap());
		frame.setVisible(true);
	}
}