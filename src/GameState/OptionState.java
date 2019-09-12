package GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.Game;
import Main.GamePanel;
import ResourceManagers.Strings;
import TileMap.Background;

public class OptionState extends GameState{

	private Dimension size = Game.getSize();
	
	private GameStateManager gsm;
	private int sel;
	private int slider;
	private int y;
	
	private Background bg;
	
	private Font nameFont;
	private Font descFont;
	private String[] tileOptions = {"Random", "Grass", "Castle", "Space"};
	private String[] diffOptions = {"Easy", "Normal", "Hard"};
	
	private Rectangle[] optionAreas = {
		new Rectangle(0, (int)Math.round((57)*size.getHeight()/GamePanel.HEIGHT), (int) size.getWidth(), (int)Math.round((16)*size.getHeight()/GamePanel.HEIGHT) ),
		new Rectangle(0, (int)Math.round((57 + 16)*size.getHeight()/GamePanel.HEIGHT), (int) size.getWidth(),(int)Math.round((16)*size.getHeight()/GamePanel.HEIGHT) ),
		new Rectangle(0, (int)Math.round((57 + 32)*size.getHeight()/GamePanel.HEIGHT), (int) size.getWidth(), (int)Math.round((16)*size.getHeight()/GamePanel.HEIGHT) ),
		new Rectangle(0, (int)Math.round((57 + 48)*size.getHeight()/GamePanel.HEIGHT), (int) size.getWidth(), (int)Math.round((16)*size.getHeight()/GamePanel.HEIGHT) ),
		new Rectangle(0, (int)Math.round((57 + 80)*size.getHeight()/GamePanel.HEIGHT), (int) size.getWidth(),(int)Math.round((16)*size.getHeight()/GamePanel.HEIGHT) ),
		new Rectangle(0, (int)Math.round((57 + 96)*size.getHeight()/GamePanel.HEIGHT), (int) size.getWidth(), (int)Math.round((16)*size.getHeight()/GamePanel.HEIGHT) ),
		new Rectangle(0, (int)Math.round((57 + 140)*size.getHeight()/GamePanel.HEIGHT), (int) size.getWidth(), (int)Math.round((16)*size.getHeight()/GamePanel.HEIGHT) ),
	};
	
	
public OptionState(GameStateManager gsm){
		
		MOUSE_CONTROL = true;
		this.gsm = gsm;
		
		bg = new Background(Game.res.backgrounds.get("options"), 1.0);
		
		nameFont = new Font(
				"Arial",
				Font.PLAIN,
				18);
		
		descFont = new Font(
				"Arial",
				Font.PLAIN,
				10);
		
		sel = 0;
		slider = Game.options[0];
	}

	public void init() {

	}

	public void update() {
		
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		
		g.setFont(descFont);
		if(sel == 0) g.setColor(Color.RED);
		else g.setColor(Color.WHITE);
			if(Game.options[0] == 6){
				g.drawString("Fullscreen", 140, 65);
			}else{
				g.drawString((Game.options[0] * 320) + "x" + (Game.options[0] * 240), 140, 65);
			}
		if(sel == 1) g.setColor(Color.RED);
		else g.setColor(Color.WHITE);
			g.drawString(Game.options[1] + "%", 140, 81);
		if(sel == 2) g.setColor(Color.RED);
		else g.setColor(Color.WHITE);		
			g.drawString(Game.options[2] + "%", 140, 97);
		if(sel == 3) g.setColor(Color.RED);
		else g.setColor(Color.WHITE);
			g.drawString(Game.options[3] + "%", 140, 113);
		if(sel == 4) g.setColor(Color.RED);
		else g.setColor(Color.WHITE);
			g.drawString(tileOptions[Game.options[4]], 140, 145);
		if(sel == 5) g.setColor(Color.RED);
		else g.setColor(Color.WHITE);
			g.drawString(diffOptions[Game.options[5]], 140, 161);
		if(sel == 6) g.setColor(Color.RED);
		else g.setColor(Color.WHITE);
				g.drawString("Return to Main Menu", 105, 205);
		
		if(sel != 6){	
			g.setColor(Color.RED);
			y = 65 + sel*16;
			if(sel > 3) y+=16;
			g.drawString("<", 130, y);
			g.drawString(">", 193, y);
		}
			
		
	}
	
	private void select(){
		if(sel == 6){
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_RIGHT){
			slider++;
		}
		if(k == KeyEvent.VK_LEFT){
			slider--;
		}
		if(k == KeyEvent.VK_DOWN) {
			if(sel != 6)Game.options[sel] = slider;
			sel++;
			if(sel > 6) sel = 0;
			if(sel != 6)slider = Game.options[sel];
		}
		if(k == KeyEvent.VK_UP) {
			if(sel != 6)Game.options[sel] = slider;
			sel--;
			if(sel < 0) sel = 6;
			if(sel != 6)slider = Game.options[sel];
		}
		
			if(sel == 0){
				if(slider > 6) slider = 6;
				if(slider < 1) slider = 1;
			}else if(sel > 0 && sel < 4){
				if(slider > 100) slider = 100;
				if(slider < 0) slider = 0;
			}else if(sel == 4){
				if(slider > tileOptions.length - 1) slider = tileOptions.length - 1;
				if(slider < 0) slider = 0;
			}else if(sel == 5){
				if(slider > diffOptions.length - 1) slider = diffOptions.length - 1;
				if(slider < 0) slider = 0;
			}
			if(sel != 6){
				Game.options[sel] = slider;
			}
		
	}

	public void keyReleased(int k) {
		
	}

	public void unload() {
		try{
			File file = new File("options.txt");
			PrintWriter writer = new PrintWriter(file);
			for(int i = 0; i < Game.options.length; i++){
				writer.println(Game.options[i]);
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		Game.resize();
	}

	public void restart() {

	}
	
	public void mouseWheelMoved(MouseWheelEvent e){
		
		if(sel > 0 && sel < 4){
			slider -= 5 * e.getWheelRotation();
		}else{
			slider -= e.getWheelRotation();
		}
		
		
		if(sel == 0){
			if(slider > 6) slider = 6;
			if(slider < 1) slider = 1;
		}else if(sel > 0 && sel < 4){
			if(slider > 100) slider = 100;
			if(slider < 0) slider = 0;
		}else if(sel == 4){
			if(slider > tileOptions.length - 1) slider = tileOptions.length - 1;
			if(slider < 0) slider = 0;
		}else if(sel == 5){
			if(slider > diffOptions.length - 1) slider = diffOptions.length - 1;
			if(slider < 0) slider = 0;
		}
		if(sel != 6){
			Game.options[sel] = slider;
		}
	}
	
	public void mouseClicked(MouseEvent e){
		for(int i=0; i < optionAreas.length; i++){
			if(optionAreas[i].contains(e.getPoint())){
				if(e.getButton() == MouseEvent.BUTTON1) select();
				return;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e){
		for(int i=0; i < optionAreas.length; i++){
			if(optionAreas[i].contains(e.getPoint())){
				sel = i;
				return;
			}
		}
	}

}
