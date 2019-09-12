package GameState;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import HUD.InterpolatedPicture;
import Main.Game;
import Main.GamePanel;
import ResourceManagers.Strings;
import TileMap.Background;

public class CharacterState extends GameState{
	
	private GameStateManager gsm;
	private int sel;
	private boolean mode;
	
	private Background bg;
	private InterpolatedPicture[] icons = new InterpolatedPicture[18];
	
	private Font nameFont;
	private Font descFont;
	private Strings strings;
	
	private Dimension size = Game.getSize();
	
	public CharacterState(GameStateManager gsm){
		MOUSE_CONTROL = true;
		
		this.gsm = gsm;
		
		strings = new Strings();
		mode = true;
		
		bg = new Background(Game.res.backgrounds.get("character"), 1.0);
		
		try{
			
			BufferedImage gif = ImageIO.read(
					getClass().getResourceAsStream(
						"/Backgrounds/charactericons.gif"
					)
				);
				
				for(int i = 0; i < icons.length; i++) {
						int x = 13 + i * 33;
						int y = 23;
						if(i > 8){ x -= 297; y += 33;}
							icons[i] = new InterpolatedPicture(x, y, 30, 30, gif.getSubimage(30 * i, 0, 30, 30), 0);
				}
			
			
		}catch(Exception e){
			
		}
		
		nameFont = new Font(
				"Arial",
				Font.PLAIN,
				18);
		
		descFont = new Font(
				"Arial",
				Font.PLAIN,
				10);
		
		sel = 0;
	}

	public void init() {

	}

	public void update() {
		for(int i = 0; i < icons.length; i++){
			if(sel == i) icons[i].grow(8, 5);
			else icons[i].reset();
			icons[i].update();
		}
	}

	public void draw(Graphics2D g) {
		
		bg.draw(g);
				
		g.setColor(Color.WHITE);
		g.setFont(nameFont);
		g.drawString(strings.heroNames[sel], 13, 108);
		
		g.setFont(descFont);
		if(mode == false){
		for(int i = 0; i < strings.heroDescriptions[sel].length; i++){
			if(strings.heroDescriptions[sel][i] == null){
				break;
			}
			g.drawString(strings.heroDescriptions[sel][i], 13, 128 + i*10);
		}
			g.drawString("Highscore: " + String.format("%08d", Game.scores[sel]), 13, 178);
		}else{
			for(int i = 0; i < strings.heroAbilities[sel].length; i++){
				if(strings.heroAbilities[sel][i] == null){
					break;
				}
				g.drawString(strings.heroAbilities[sel][i], 13, 128 + i*10);
			}
		}
		
		for(int i = 0; i < icons.length; i++){
			if(i != sel)icons[i].draw(g);
		}
		icons[sel].draw(g);
		
		if(mode) g.drawString("Press Q to see Lore...",13, 208);
		else g.drawString("Press Q to see Abilities...", 13, 208);
	}
	
	private void select(){
		if(sel != strings.heroNames.length - 1){
			gsm.setState(GameStateManager.LEVELSTATE, sel);
		}else{
			gsm.setState(GameStateManager.LEVELSTATE, new Random().nextInt(strings.heroNames.length - 1));
		}
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_BACK_SPACE){
			gsm.setState(GameStateManager.MENUSTATE);
		}
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_RIGHT){
			sel++;
		}
		if(k == KeyEvent.VK_LEFT){
			sel--;
		}
		if(k == KeyEvent.VK_UP) {
			if(sel > 8){
				sel -= 9;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			if(sel < 9){
				sel += 9;
			}
		}
		if(k == KeyEvent.VK_Q){
			mode = !mode;
		}
		if(sel > strings.heroNames.length - 1){
			sel = strings.heroNames.length - 1;
		}
		if(sel < 0){
			sel = 0;
		}
	}

	public void keyReleased(int k) {

	}
	
	public void mouseClicked(MouseEvent e){
		for(int i=0; i < icons.length; i++){
			if(icons[i].getBound().contains(e.getX()/(size.getWidth()/GamePanel.WIDTH),e.getY()/(size.getHeight()/GamePanel.HEIGHT))){
				sel = i;
				if(e.getButton() == MouseEvent.BUTTON1) select();
				return;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e){
		for(int i=0; i < icons.length; i++){
			if(icons[i].getBound().contains(e.getX()/(size.getWidth()/GamePanel.WIDTH),e.getY()/(size.getHeight()/GamePanel.HEIGHT))){
				sel = i;
			}
		}
	}

	public void unload() {

	}

	public void restart() {

	}

}
