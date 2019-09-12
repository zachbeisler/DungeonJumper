package Entity.Enemies;

import Attacks.Attack;
import Entity.*;
import Heroes.Player;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Wizard extends Enemy {
	
	private BufferedImage[] sprites;
	private double deltaY;
	private double deltaX;
	private double angle;
	
	private long fireTimer;
	private BufferedImage[] fireball;
	
	public Wizard(TileMap tm) {
		
		super(tm);
		
		score = 150;
		fireTimer = 120;
		
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 1;
		damage = 1;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Enemies/wizardenemy.gif"
				)
			);
			
			sprites = new BufferedImage[1];
			sprites[0] = spritesheet;
			
			fireball = new BufferedImage[3];
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/Attacks/fireball.gif"));
			for(int i = 0;i < fireball.length; i++){
				fireball[i] = spritesheet.getSubimage(20*i, 0, 20, 20);}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(-1);
		
		right = true;
		facingRight = true;
		
	}
	
	public void update() {
		
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
		
		// update animation
		animation.update();
		
	}
	
	public void update(Player p) {
		
		fireTimer--;
		if(fireTimer < 0) fireTimer = 0;
		
		deltaX = p.getx() - getx();
		deltaY = p.gety() - gety();
		
		if(p.getx() - getx() >= 0){
			facingRight = true;
		}else{
			facingRight = false;
		}
		
		angle = Math.atan(deltaY/Math.abs(deltaX));
		
		
		if(fireTimer <= 0 && Math.abs(deltaX) < 160 && Math.abs(angle) < 1.3){
			projectile = new Attack(tileMap, fireball, x, y - 8, 100, damage, 10, 10);
			projectile.setDim(7, 7);
			if(facingRight){
				projectile.setVector(3*Math.cos(angle), 3*Math.sin(angle));
			}else{
				projectile.setVector(-3*Math.cos(angle), 3*Math.sin(angle));
			}
			fireTimer = 150;
		}
		
		update();
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		super.draw(g);
	}
	
}











