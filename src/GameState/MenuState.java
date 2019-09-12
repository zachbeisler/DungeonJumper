package GameState;

import HUD.InterpolatedPicture;
import Main.Game;
import Main.GamePanel;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class MenuState extends GameState {
	
	private Background bg;
	private Dimension size = Game.getSize();
	
	private int currentChoice = 0;
	private BufferedImage[][] buttons = {{Game.res.hud.get("menuoptions").getSubimage(0, 0, 110, 30)},
										 {Game.res.hud.get("menuoptions").getSubimage(0, 30, 110, 30)},
										 {Game.res.hud.get("menuoptions").getSubimage(0, 60, 110, 30)}};
	private String[] options = {
		"New Game",
		"Options",
		"Quit"
	};
	
	private InterpolatedPicture[] optionAreas = new InterpolatedPicture[3];
	
	private Font titleFont;
	
	private Font font;
	private Font bigFont;
	
	public MenuState(GameStateManager gsm) {
		MOUSE_CONTROL = true;
		this.gsm = gsm;
		
		try {
			optionAreas[0] = new InterpolatedPicture(GamePanel.WIDTH/2 - 55, 75, 110, 30, buttons[0], 0);
			optionAreas[1] = new InterpolatedPicture(GamePanel.WIDTH/2 - 55, 115, 110, 30, buttons[1], 0);
			optionAreas[2] = new InterpolatedPicture(GamePanel.WIDTH/2 - 55, 155, 110, 30, buttons[2], 0);
			
			bg = new Background(ImageIO.read(getClass().getResourceAsStream("/Backgrounds/menubg.gif")),1);
			bg.setVector(.5, 0);
			
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					28);
			
			font = new Font("Arial", Font.PLAIN, 12);
			bigFont = new Font("Arial", Font.PLAIN, 15);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void unload(){}
	
	public void init() {}
	
	public void restart() {}
	
	public void update() {
		bg.update();
		
		for(int i = 0; i < optionAreas.length; i++){
			if(currentChoice == i) optionAreas[i].grow(10, 5);
			else optionAreas[i].reset();
			optionAreas[i].update();
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		//draw bg
		bg.draw(g);
		
		//draw title
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("Dungeon Jumper", 40, 40);
		
		//draw menu items
		for(int i = 0; i < optionAreas.length; i++){
			optionAreas[i].draw(g);
		}
		
		
	}
	
	private void select() {
			if(currentChoice == 0) {
				gsm.setState(GameStateManager.CHARACTERSTATE);
			}
			if(currentChoice == 1){
				gsm.setState(GameStateManager.OPTIONSTATE);
			}
			if(currentChoice == 2) {
				System.exit(0);
			}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
	
	
	public void mouseClicked(MouseEvent e){
		for(int i=0; i < optionAreas.length; i++){
			if(optionAreas[i].getBound().contains(e.getX()/(size.getWidth()/GamePanel.WIDTH),e.getY()/(size.getHeight()/GamePanel.HEIGHT))){
				currentChoice = i;
				if(e.getButton() == MouseEvent.BUTTON1) select();
				return;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e){
		for(int i=0; i < optionAreas.length; i++){
			if(optionAreas[i].getBound().contains(e.getX()/(size.getWidth()/GamePanel.WIDTH),e.getY()/(size.getHeight()/GamePanel.HEIGHT))){
				currentChoice = i;
			}
		}
	}
}










