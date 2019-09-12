package Main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import ResourceManagers.ResourceLoader;
import ResourceManagers.Strings;


public class Game {
	
	static JFrame window;
	private static GamePanel gamePanel;
	public static ResourceLoader res;
	public static Strings strings;
	public static int[] options = new int[6];
	public static int[] scores;
	
	public static void main(String[] args) {
		
		gamePanel = new GamePanel();
		
		window = new JFrame("Dungeon Jumper v0.3");
		window.setContentPane(gamePanel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		res = new ResourceLoader(gamePanel);
		
		strings = new Strings();
		scores = new int[strings.heroNames.length];
		
		gamePanel.decryptScores();
		gamePanel.getOptions();
		resize();
	}
	
	public static void resize(){
		if(gamePanel != null){
			if(options[0] != 6){
				gamePanel.setXScale(options[0]);
				gamePanel.setYScale(options[0]);
			}else{
				Toolkit tk = Toolkit.getDefaultToolkit();
				gamePanel.setXScale((int)(tk.getScreenSize().getWidth()/gamePanel.WIDTH));
				gamePanel.setYScale((int)(tk.getScreenSize().getHeight()/gamePanel.HEIGHT));	
			}
			gamePanel.setPreferredSize(
					new Dimension(gamePanel.WIDTH * gamePanel.xSCALE, gamePanel.HEIGHT * gamePanel.ySCALE));
			window.setPreferredSize(new Dimension(gamePanel.WIDTH*gamePanel.xSCALE, gamePanel.HEIGHT*gamePanel.ySCALE));
			window.pack();
			window.setLocationRelativeTo(null);
		}
	}
	
	public static Dimension getSize(){
		return gamePanel.getPreferredSize();
	}
	
	public static void loadResources(){
		gamePanel.getOptions();
		resize();
		res = new ResourceLoader(gamePanel);
	}
	
}
