import java.awt.Toolkit;

import javax.swing.JFrame;


public class golfBallFrame 
{
	public static void main(String[] args)
	{
		JFrame frm = new JFrame("Golf Ball Detector By Rahul Chidurala");
		//golfBallDetector panel = new golfBallDetector();
		Toolkit frameTool = Toolkit.getDefaultToolkit();
		int width = frameTool.getScreenSize().width;
		int height = frameTool.getScreenSize().height;
		frm.setSize(width,height);
		//frm.add(panel);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
	}
}
