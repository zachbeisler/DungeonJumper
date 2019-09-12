package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Bottle extends MapObject{

	private BufferedImage sprite;
	
	public Bottle(TileMap tm) {
		super(tm);
		height = 15;
		width = 15;
		cheight = 25;
		cwidth = 25;
		
		
		try {
					
			sprite = ImageIO.read(
				getClass().getResourceAsStream(
					"/Enemies/bottle.gif"
				));
			
			}catch(Exception e){
				e.printStackTrace();
			}
		
		BufferedImage[] bi = {sprite}; 
		
		animation = new Animation();
		animation.setFrames(bi);
		animation.setDelay(-1);
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
	public void update(){}
	
	public void setRemove(boolean b){
		remove = b;
	}
}

	
