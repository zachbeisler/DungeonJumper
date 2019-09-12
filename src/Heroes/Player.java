package Heroes;

import Attacks.Attack;
import Attacks.CenteredAttack;
import Attacks.OnHitAttack;
import Entity.Animation;
import Entity.Enemy;
import Entity.MapObject;
import Main.Game;
import TileMap.*;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {
	
	// player stuff
	public int score;

	protected int maxMana;
	protected int maxHealth;
	protected int damage;
	
	
	public boolean invisible;
	public boolean invulnerable;
	public boolean doInput = true;
	protected int health;
	public int mana;
	protected boolean dead;
	protected boolean flinching;
	protected long flinchTimer;
	protected int healthRegen;
	protected int manaRegen;
	protected boolean onLadder;
	public ArrayList<Attack> attacks = new ArrayList<>();
	
	//ability
	public long qcd = 0;
	public long wcd = 0;
	public long ecd = 0;
	
	// animations
	protected String type = "test";
	protected ArrayList<BufferedImage[]> sprites;
	protected int[] numFrames;
	protected int[] frameWidth;
	
	protected String aboveText = "";
	protected int aboveTextTimer;

	
	// animation actions
	protected final int IDLE = 0;
	protected final int WALKING = 1;
	protected final int JUMPING = 2;
	protected final int CLIMBING = 3;
	protected final int Q = 4;
	public final int W = 5;
	protected final int E = 6;
	
public Player(TileMap tm, int[] numFrames, int[] frameWidth, String type) {
		
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		dead = false;
		invulnerable = false;
		
		moveSpeed = 3.0;
		maxSpeed = 3.0;
		jumpStart = -5.1;
		stopSpeed = 1.0;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 4;
		mana = maxMana = 3;
		damage = 1;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Player/" + type + ".gif"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < numFrames.length; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
						bi[j] = spritesheet.getSubimage(
								j * frameWidth[i],
								i * height,
								frameWidth[i],
								height
						);
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	public Player(TileMap tm) {
		
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		dead = false;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		mana = maxMana = 5;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Player/" + type + ".gif"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < numFrames.length; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
						bi[j] = spritesheet.getSubimage(
								j * frameWidth[i],
								i * height,
								frameWidth[i],
								height
						);
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getMana() { return mana; }
	public int getMaxMana() { return maxMana; }
	
	public void setMaxMana(int i){
		maxMana = i;
	}
	
	public void setHealth(int hp){
		health = hp;
		if(health > maxHealth){
			health = maxHealth;
		}
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void checkAttack(ArrayList<Enemy> enemies) {
		
		// loop through enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			// check enemy collision
			if(intersects(e) && !invulnerable) {
				hit(e.getDamage());
			}
			for(Attack a : attacks){
				a.iterated = true;
				if(a.intersects(e)){
					if(e.getDamage() > 0){
						e.hit(a.getDamage());
					}
					for(String flag : a.flags){
						switch(flag){
						case "switch":
							setPosition(e.getx(), e.gety());
							break;
						case "flinch":
							hit(0);
							break;
						case "stop":
							stop();
							break;
						case "remove":
							a.setRemove(true);
							break;
						case "input":
							doInput = true;
							break;
						case "stick":
							a.setEnemy(e);
							break;
						case "restore":
							if(mana < maxMana)mana++;
							break;
						}
						a.onHit(attacks);
					if(facingRight){
						e.setVector(3,-2);
					}else{
						e.setVector(-3,-2);
					}
				}
			}
		}
	}
	}
	
	public void hit(int damage) {
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	private void getNextPosition() {
		
		// movement
	if(!onLadder){
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
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		// falling
		if(falling) {
			dy += fallSpeed;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > 0) jumping = false;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
	}else{
		if(left){
			dx = -maxSpeed/2;
		}
		else if(right){
			dx = maxSpeed/2;
		}else{dx = 0;}
		
		if(up){
			dy = -maxFallSpeed/2;
		}else if(down){
			dy = maxFallSpeed/2;
		}else{
			dy = 0;
		}
	}
	}
	
	public void update() {
		
		if(score > 99999999){
			score = 99999999;
		}
		
		if(aboveTextTimer > 0) aboveTextTimer--;
		else if(aboveTextTimer == 0) aboveText = "";
		
		if(invisible) return;
		
		if(qcd > 0) qcd--;
		if(wcd > 0) wcd--;
		if(ecd > 0) ecd--;
		
		// update position
		getNextPosition();

		health += healthRegen;
		if(health <= 0){
			dead = true;
		}else{dead = false;};
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		mana += manaRegen;
		if(mana > maxMana) mana = maxMana;
		
		// check done flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000) {
				flinching = false;
			}
		}
		
		// set animation
		setAnimation();
		
		animation.update();
		
		//update attacks
		for(int i = 0; i < attacks.size(); i++){
			Attack a = attacks.get(i);
			if(a instanceof CenteredAttack){
				if(((CenteredAttack)a).isCenterFollow()) ((CenteredAttack)a).setCenter(getx() + getXmap(), gety() + getYmap());
			}
			a.update();
			if(a.shouldRemove() && a.iterated){
				attacks.remove(i);
				if(a instanceof OnHitAttack){
					for(String flag : a.flags){
						switch(flag){
						case "trigger":
							a.onHit(attacks);
							break;
						case "input":
							doInput = true;
							System.out.println("input");
							break;
						}
					}
					
				}
				i--;
			}
			if(a.isFollowing()){
				a.setPosition(x, y);
			}
		}
		
		if(!(tileMap.map[(int) (gety()/30)][(int) (getx()/30)] == 3)){
			if(onLadder){
			setLadder(false);
			dy = jumpStart;
			}
		}
		
		// set direction
			if(right) facingRight = true;
			if(left) facingRight = false;
	}

	public void addScore(int score){
		if(aboveTextTimer == 0){
		aboveText = "+" + Integer.toString(score);
		}else{
			aboveText = "+" + Integer.toString(Integer.parseInt(aboveText.substring(1)) + score);
		}
		aboveTextTimer += 60;
		this.score += score;
	}
	
	private void setAnimation(){
		
	if((currentAction == Q || currentAction == W || currentAction == E) && animation.hasPlayedOnce()){
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		width = 30;
	}else if((currentAction == Q || currentAction == W || currentAction == E)){
		animation.pause(false);
		return;
	}else if(!onLadder){
		if(dy < 0) {
			if(currentAction != JUMPING){
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		}
		else if(left || right) {
			if(currentAction != WALKING){
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(75);
				width = 30;
			}
		}
		else if(currentAction != IDLE && currentAction != Q && currentAction != W && currentAction != E) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
	}
	else if(currentAction != CLIMBING){
			currentAction = CLIMBING;
			animation.setFrames(sprites.get(CLIMBING));
			width = 30;
			animation.setDelay(80);
		}
	else if(dy == 0 && dx == 0){
			animation.pause(true);
		}else{
			animation.pause(false);
		}
	}
	
	public void draw(Graphics2D g) {
		
		if(invisible) return;
		
		setMapPosition();
		
		// draw player
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		super.draw(g);
		
		//draw text
		g.setColor(Color.BLACK);
		if(tileMap.getType() == "space") g.setColor(Color.WHITE);
		g.drawString(aboveText, (int) (getx() + getXmap() - 10), (int) (gety() + getYmap() - 20));
		
		//draw Attacks
		for(Attack a : attacks){
			a.setMapPosition();
			a.draw(g);
		}
	}
	
	public void QAbility(){
		
	}
	
	public void WAbility(){
		
	}
	
	public void EAbility(){
		
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public void setLadder(boolean b){
		onLadder = b;
		animation.pause(b);
	}
	
	public boolean isOnLadder(){
		return onLadder;
	}
	
	public void newTileMap(TileMap tm){
		tileMap = tm;
	}
	
	public void stop(){
		dx = 0;
		if(this instanceof Druid){
			((Druid) this).leapTimer = 1;
		}
	}
	
	public void setFacingRight(boolean b){
		facingRight = b;
	}

	public void setMaxHealth(int i) {
		maxHealth = i;
		
	}
	
	public String getType(){
		return type;
	}
	
	public void reset(){};
	
}

















