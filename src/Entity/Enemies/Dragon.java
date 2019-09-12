package Entity.Enemies;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Attacks.Attack;
import Entity.Animation;
import Entity.Enemy;
import Heroes.Player;

public class Dragon extends Enemy{
	
	private long fireTimer;
	private long diveTimer = 500;
	private final int DIVETIME = 110;
	private BufferedImage[] sprites;
	private BufferedImage[] fireball;
	private Random rand = new Random();
	
	//AI nonsense
	private double deltaY;
	private double deltaX;
	private double angle;

	public Dragon(TileMap tm) {
		super(tm);
		
		
		
		score =	1000;
		fireTimer = 240;
		
		moveSpeed = 4;
		maxSpeed = 4;
		fallSpeed = 0;
		
		width = 100;
		height = 60;
		cwidth = 85;
		cheight = 40;
		
		health = maxHealth = 7;
		damage = 2;
		
		try{
		
		BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Enemies/dragon.gif"
				)
			);
			
			sprites = new BufferedImage[4];
			
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(60*i, 0, 60, 55);
			}
			
			fireball = new BufferedImage[1];
			fireball[0] = spritesheet.getSubimage(0, 55, 25, 25);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(110);
		
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
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		// update animation
		animation.update();
		
	}
	
	
public void getNextPosition(){
	
	if(diveTimer >= 0){
		if(deltaY <= 10){
			dy = -moveSpeed/2;
		}else if(deltaY < 100){
			dy = -moveSpeed/8;
		}else if(deltaY < 110){
			dy = 0;
		}else if(deltaY < 200 && deltaY > 110){
			dy = moveSpeed/4;
		}else if(deltaY < 1200){
			dy = moveSpeed/2;
		}
		
		if(deltaX > 75 && deltaX < 300){
			dx = moveSpeed/2;
		}else if(deltaX < -150 && deltaX > -200){
			dx = -moveSpeed/2;
		}else if(deltaX < 50 && deltaX >= 0){
			dx = -moveSpeed/4;
		}else if(deltaX > -50 && deltaX < 0){
			dx = moveSpeed/4;
		}else if(deltaX < 400 || deltaX > -400){
			dx = 0;
		}
		else{dx = Math.signum(deltaX)*moveSpeed;}
	}else{
		if(diveTimer > -DIVETIME/2){
			dy = moveSpeed/2 * Math.abs(diveTimer - DIVETIME/2)/DIVETIME/2*(Math.abs(deltaY/110)+1.5);
		}else{
			dy = -moveSpeed/2 * Math.abs(diveTimer - DIVETIME/2)/DIVETIME/2*(Math.abs(deltaY/110));
		}
		if(facingRight) dx = moveSpeed/2*(0.5 - Math.abs(deltaX/300));
		else dx = -moveSpeed/2;
	}
	
	if(y > 58 * 30){
		dy = -moveSpeed/2;
		diveTimer = 1200;
	}
	if(x > 58 * 30){
		dx = -moveSpeed/2;
		diveTimer = 1200;
	}
	if(y < 1 * 30){
		dy = moveSpeed/2;
		diveTimer = 1200;
	}
	if(x < 1 * 30){
		dx = moveSpeed/2;
		diveTimer = 1200;
	}
	
	xtemp += dx;
	ytemp += dy;
}
	
public void update(Player p) {
		
	
	
		fireTimer--;
		if(fireTimer < 0) fireTimer = 0;
		
		
		deltaX = p.getx() - getx();
		deltaY = p.gety() - gety();
		
		if(Math.abs(deltaX) < 400){
			diveTimer--;
			if(diveTimer == -1) fireTimer = 0;
			if(diveTimer < 0){ 
				animation.setDelay(75);
			}
			if(diveTimer < -DIVETIME){
				diveTimer = 500;
				animation.setDelay(110);
			}
		}
		
		if(diveTimer >= 0){
			if(deltaX >= 0){
				facingRight = true;
			}else{
				facingRight = false;
			}
		
		
			angle = Math.atan(deltaY/Math.abs(deltaX));
		
		
			if(fireTimer <= 0 && Math.abs(deltaY) < 400 && Math.abs(deltaX) < 400){
				if(facingRight) projectile = new Attack(tileMap, fireball, x + width/2 - 15, y - 8, 100, damage - 1, 25, 25);
				else projectile = new Attack(tileMap, fireball, x - width/2 + 15, y - 8, 100, damage - 1, 25, 25);
				projectile.setNoCollide(true);
			if(deltaX > 0){
				projectile.setVector(3*Math.cos(angle - Math.PI/360*rand.nextInt(10)), 3*Math.sin(angle));
			}else{
				projectile.setVector(-3*Math.cos(angle + Math.PI/360*rand.nextInt(10)), 3*Math.sin(angle));
			}
			fireTimer = 210;
			}
		}else if(fireTimer == 0){
			if(facingRight) projectile = new Attack(tileMap, fireball, x + width/2 - 15, y - 8, 80, damage - 1, 25, 25);
			else projectile = new Attack(tileMap, fireball, x - width/2 + 15, y - 8, 80, damage - 1, 25, 25);
			projectile.setNoCollide(true);
			if(Math.round(Math.random()) == 0)projectile.setVector(4*moveSpeed/6*Math.signum(dx), 3*Math.sin(Math.PI/360*rand.nextInt(20)));
			else projectile.setVector(4*moveSpeed/6*Math.signum(dx), -3*Math.sin(Math.PI/360*rand.nextInt(20)));
			fireTimer = rand.nextInt(10);
		}
		
		
		update();
		getNextPosition();
		setPosition(xtemp,ytemp);
		
	}



}


