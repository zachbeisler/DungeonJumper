package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JPanel;
















import GameState.GameStateManager;
import ResourceManagers.Encryption;

@SuppressWarnings("serial")
public class GamePanel extends JPanel 
	implements Runnable, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	// dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static int xSCALE = 3;
	public static int ySCALE = 3;
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	public GameStateManager gsm;
	
	// image
	private BufferedImage image;
	private static Graphics2D g;
	
	
	public Graphics2D graphicsObject(){
		return g;
	}
	
	public GamePanel() {
		super();
			setPreferredSize(new Dimension(WIDTH*xSCALE,HEIGHT*ySCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			addMouseWheelListener(this);
			startThread();
		}
	}
	
	public void startThread(){
		thread.start();
	}
	
	
	private void init() {
		
		image = new BufferedImage(
					WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB
				);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	public void setScale(int s){
		xSCALE = s;
		ySCALE = s;
		setPreferredSize(
				new Dimension(WIDTH * xSCALE, HEIGHT * ySCALE));
	}
	
	public void setXScale(int s){
		xSCALE = s;
		setPreferredSize(
				new Dimension(WIDTH * xSCALE, HEIGHT * ySCALE));
	}
	
	public void setYScale(int s){
		ySCALE = s;
		setPreferredSize(
				new Dimension(WIDTH * xSCALE, HEIGHT * ySCALE));
	}
	
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void encryptScores(){
		PrintWriter p = null;
		try{
			File file = new File("scores.txt");
			FileWriter f = new FileWriter(file);
			p = new PrintWriter(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for(int i = 0;i<Game.scores.length;i++){
			p.println(Encryption.encrypt(Integer.toString(Game.scores[i])));
		}
		p.close();
	}
	
	public void decryptScores(){
		BufferedReader r = null;
		try{
			File file = new File("scores.txt");
			FileReader f = new FileReader(file);
			r = new BufferedReader(f);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String score = "";
		for(int i = 0;i<Game.scores.length;i++){
			try {
				score = r.readLine();
			} catch (IOException e) {}
			score = Encryption.encrypt(score);
			if(Encryption.isNumeric(score)){
				Game.scores[i] = Integer.parseInt(score);
			}else{
				Game.scores[i] = 0;
			}
		}
		try {
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getOptions(){
		try{
			File file = new File("options.txt");
			BufferedReader r = new BufferedReader(new FileReader(file));
			for(int i = 0; i < Game.options.length; i++){
				Game.options[i] = Integer.parseInt(r.readLine());
			}
			r.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		Game.resize();
	}
	
	private void update() {
		gsm.update();
	}
	private void draw() {
		gsm.draw(g);
	}
	public void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,
				WIDTH * xSCALE, HEIGHT * ySCALE,
				null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		if(gsm != null){
			gsm.keyPressed(key.getKeyCode());
		}
	}
	public void keyReleased(KeyEvent key) {
		if(gsm != null){
		gsm.keyReleased(key.getKeyCode());
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(gsm != null)gsm.mouseClicked(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		if(gsm != null)gsm.mouseMoved(e);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e){
		if(gsm != null)gsm.mouseWheelMoved(e);
	}
	
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

}
















