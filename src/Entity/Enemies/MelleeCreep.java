package Entity.Enemies;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;
import Heroes.Player;
import Main.Game;

public class MelleeCreep extends Enemy{
	
	private BufferedImage[] sprites;

	public MelleeCreep(TileMap tm) {
		super(tm);
		
		score = 100;
		
		moveSpeed = 0.5;
		maxSpeed = 0.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Enemies/"+tileMap.getType() + ".gif"
				)
			);
			
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
		
		right = true;
		facingRight = true;
		
	}
	
	private void getNextPosition() {
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
					dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		// falling
		if(falling) {
			dy += fallSpeed;
		}
		
	}

	public void update(Player p) {
		
		if(health <= 0){
			remove = true;
		}
		
		// check flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 200) {
				flinching = false;
			}
		}
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(tileMap.map[(int) (gety()/30) + 1][(int) (getx()/30)] <= 3 && !falling){
			dx = 0;
		}
		
		// if it hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
			
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
	
	// update animation
	animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		super.draw(g);
	}
	
	
}
