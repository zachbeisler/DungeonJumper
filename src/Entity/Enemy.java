package Entity;

import java.awt.Color;
import java.awt.Graphics2D;

import Attacks.Attack;
import Heroes.Player;
import TileMap.TileMap;

public class Enemy extends MapObject {
	
	protected Attack projectile;
	
	protected int combatText = 0;
	protected int textTimer = 0;
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected int score;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int i){
		score = i;
	}
	
	public boolean isDead() { return dead; }
	
	public int getDamage() { return damage; }
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		combatText = damage;
		textTimer = 100;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update(Player p) {}

	public void draw(Graphics2D g){
		
		if(getHealth() < maxHealth && !notOnScreen()){	
			g.setColor(hpRed);
			g.fillRoundRect((int)(x + getXmap() - width / 2), (int)(y + ymap - height / 2) - 3, width, 3, 1, 1);
			g.setColor(hpGreen);
			g.fillRoundRect((int)(x + getXmap() - width / 2), (int)(y + ymap - height / 2) - 3, (int)((getHealth()/(double)maxHealth) * width), 3, 1, 1);
		}
		
		if(textTimer == 0) combatText = 0;
		if(combatText > 0){
			g.setColor(Color.RED);
			g.drawString("-" + combatText, (int)(x + getXmap() - width / 4), (int)(y + ymap - height / 2) - 10);
			textTimer--;
		}
			
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		super.draw(g);
	}

	public boolean hasProjectile() {
		if(projectile != null) return true;
		return false;
	}
	
	public Attack getProjectile(){
		return projectile;
	}
	
	public void setDamage(int i){
		damage = i;
	}
	
	public void setProjectile(Attack a){
		projectile = a;
	}
	
	public void setHealth(int hp){
		health = hp;
		if(health > maxHealth){
			maxHealth = health;
		}
	}
	
	public int getHealth(){
		return health;
	}
	}
	















