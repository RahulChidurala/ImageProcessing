//Rahul Chidurala

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

public class golfBallDetector extends JPanel
{
	static BufferedImage image;
	static int picHeight, picWidth;
	static  int[] pixelValues;
	static int[][] surrPixArray, pixArray;
	static int lowerBound = 250, upperBound = 255;
	static final int absoluteLow = 230;
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the path of the jpg image: \n(example: C:/Users/Rahul/Desktop/exampleImage.jpg)");
		String imagePath = scan.nextLine();
		System.out.println();
		
		System.out.println("Please enter the path of the output image: \n(example: C:/Users/Rahul/Desktop/output.jpg)");
		String outputPath = scan.nextLine();
		System.out.println();

		
		try
		{
			image = ImageIO.read(new File(imagePath));
			picWidth = image.getWidth();
			picHeight = image.getHeight();
			surrPixArray = new int[picWidth][picHeight];
			pixArray = new int[picWidth][picHeight];
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//pixelValues = image.getRGB(0, 0, picWidth, picHeight, null, 0, picWidth);
		int tempColor;
		Color conv;
		Color[][] convArray = new Color[picWidth][picHeight];
		
		for(int x=0; x<picWidth; x++)
		{
			for(int y=0; y<picHeight; y++)
			{
				tempColor = image.getRGB(x,y);
				conv = new Color(tempColor, true);
				convArray[x][y] = conv;
				if(conv.getRed() <= upperBound && conv.getRed() >=lowerBound && conv.getBlue() <= upperBound && conv.getBlue() >=lowerBound && conv.getGreen() <= upperBound && conv.getGreen() >=lowerBound)
				{
					//image.setRGB(x, y, 16711680); //highlights golf ball bright red
					pixArray[x][y] += 1;
				}
			}
		}
		
		
		
		getSurrPixels(pixArray, surrPixArray); //use this to draw box around golf ball; shows weight
		
		boolean success = true;
		
		while(!checkIfAnyBallsFound(surrPixArray))
		{
			if(lowerBound > absoluteLow)
			{
				lowerBound--;
				for(int x=0; x<picWidth; x++)
				{
					for(int y=0; y<picHeight; y++)
					{
						tempColor = image.getRGB(x,y);
						conv = new Color(tempColor, true);
						if(conv.getRed() <= upperBound && conv.getRed() >=lowerBound && conv.getBlue() <= upperBound && conv.getBlue() >=lowerBound && conv.getGreen() <= upperBound && conv.getGreen() >=lowerBound)
						{
							//image.setRGB(x, y, 16711680); //highlights golf ball bright red
							pixArray[x][y] += 1;
						}
					}
					for(int r=0; r<surrPixArray.length; r++)
					{
						for(int c=0; c<surrPixArray[r].length; c++)
						{
							surrPixArray[r][c] = 0;
						}
					}
					getSurrPixels(pixArray, surrPixArray);
				}
			}
			else
			{
				System.out.println("None found!");
				success = false;
				break;
			}
		}
		
		/*int count = 0;
		
		for(int i=0; i<surrPixArray.length; i++)
		{
			for(int j=0; j<surrPixArray[i].length;j++)
			{
				while(surrPixArray[i][j] == 1 || surrPixArray[i][j] == 2 || surrPixArray[i][j] == 3 || surrPixArray[i][j] == 4 || surrPixArray[i][j] == 5 || surrPixArray[i][j] == 6 || surrPixArray[i][j] == 7 || surrPixArray[i][j] == 8)
				{
					count++;
				}
			}
		}
		
		if(count == 0)
		{
			if(absoluteLow > lowerBound)
			{
				lowerBound = lowerBound -5;
			}
			
			for(int x=0; x<picWidth; x++)
			{
				for(int y=0; y<picHeight; y++)
				{
					tempColor = image.getRGB(x,y);
					conv = new Color(tempColor, true);
					if(conv.getRed() <= upperBound && conv.getRed() >=lowerBound && conv.getBlue() <= upperBound && conv.getBlue() >=lowerBound && conv.getGreen() <= upperBound && conv.getGreen() >=lowerBound)
					{
						//image.setRGB(x, y, 16711680); //highlights golf ball bright red
						pixArray[x][y] += 1;
					}
				}
			}
		}
		*/
		
		/*
		for(int x=0; x<picWidth; x++)
		{
			for(int y=0; y<picHeight; y++)
			{
				// || surrPixArray[x][y] == 3 || surrPixArray[x][y] == 4 || surrPixArray[x][y] == 5 || surrPixArray[x][y] == 6 || surrPixArray[x][y] == 7 || surrPixArray[x][y] == 8
				if(surrPixArray[x][y] == 1 || surrPixArray[x][y] == 2 )
				{
					image.setRGB(x, y, 16711680); //highlights golf ball bright red
				}
			}
		}
		*/
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.RED);
		boolean surrGreen = true;
		
		for(int x=0; x<picWidth; x++)
		{
			for(int y=0; y<picHeight; y++)
			{
				tempColor = image.getRGB(x,y);
				conv = new Color(tempColor, true);
				/*if(conv.getRed() <= upperBound && conv.getRed() >=lowerBound && conv.getBlue() <= upperBound && conv.getBlue() >=lowerBound && conv.getGreen() <= upperBound && conv.getGreen() >=lowerBound)
				{
					image.setRGB(x, y, 16711680); //highlights golf ball bright red
				}
				
				if(surrPixArray[x][y] == 1 || surrPixArray[x][y] == 2 || surrPixArray[x][y] == 3 || surrPixArray[x][y] == 4 || surrPixArray[x][y] == 5 || surrPixArray[x][y] == 6 || surrPixArray[x][y] == 8|| surrPixArray[x][y] == 8 )
				{
					image.setRGB(x, y, 16711680);
				}*/
				
				if(surrPixArray[x][y] == 3 || surrPixArray[x][y] == 6 || surrPixArray[x][y] == 7 || surrPixArray[x][y] == 8 )
				{
					
					/*if(x+10 < picWidth && y+10 < picHeight && convArray[x+10][y+10] == Color.green)
					{
						surrGreen = true;
					}
					else
					{
						surrGreen = false;
					} */
					//if(surrGreen)
					{
					image.setRGB(x, y, 16711680); //highlights golf ball bright red
					if((x-5) < picWidth && (y-5) < picHeight && (x+5) < picWidth && (y+5) < picHeight)
					{
						g.fillRect(x-5, y-5, 5, 5);
					}
					}
				}
			}
		}
		
		try 
		{
			File out = new File(outputPath);
			ImageIO.write(image, "jpg", out);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(success)
		{
			System.out.println("Program Done! Golf ball highlighted in the file: "+outputPath);
		}
	}
	
	public static boolean checkIfAnyBallsFound(int[][] array)
	{
		boolean exitLoop = false;
		for(int x=0; x<array.length; x++)
		{
			for(int y=0; y<array[x].length; y++)
			{
				if(surrPixArray[x][y] == 6 || surrPixArray[x][y] == 7 || surrPixArray[x][y] == 8)
				{
					exitLoop = true;
				}
			}
		}
		return exitLoop;
	}
	
	//my minesweeper's getSurroundingMines method implemented
	public static void getSurrPixels(int[][] originalPix, int[][] saveToArr)
	{
		for(int r=originalPix.length-1; r>=0; r--)
		{
			for(int c=originalPix[r].length-1; c>=0; c--)
			{
				try
				{
					//(r+1<=data.height-1 && r-1>=0 && c+1<=data.width-1 && c-1>=0) modify these conditions on each if statement
					//to avoid array errors and to avoid skipping some counts
					if(c+1<=originalPix[r].length-1 && originalPix[r][c+1]==1)//check right
					{
						saveToArr[r][c]++;
					}
					if(r+1<=originalPix.length-1 && c+1<=originalPix[r].length-1 && originalPix[r+1][c+1]==1)//check top right
					{
						saveToArr[r][c]++;
					}
					if(r+1<=originalPix.length-1 && originalPix[r+1][c]==1)//check top
					{
						saveToArr[r][c]++;
					}
					if(r+1<=originalPix.length-1 && c-1>=0 && originalPix[r+1][c-1]==1)//check top left
					{
						saveToArr[r][c]++;
					}
					if(c-1>=0 && originalPix[r][c-1]==1)//check left
					{
						saveToArr[r][c]++;
					}
					if(r-1>=0 && c-1>=0 && originalPix[r-1][c-1]==1)//check bottom left
					{
						saveToArr[r][c]++;
					}
					if(r-1>=0 && originalPix[r-1][c]==1)//check bottom
					{
						saveToArr[r][c]++;
					}
					if(r-1>=0 && c+1<=originalPix[r].length-1 && originalPix[r-1][c+1]==1)//check bottom right
					{
						saveToArr[r][c]++;
					}
				}
				catch(Exception e)
				{
					System.out.println("r:"+r+" c:"+c);
				}
			}
		}
	}
}
