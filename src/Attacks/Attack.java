package Attacks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.GamePanel;
import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;
import Entity.MapObject;

public class Attack extends MapObject{

	Rectangle rect;
	public boolean iterated = false;
	
	public long duration;
	protected int damage;
	protected boolean following;
	protected double gravity = 0;
	protected double resist = 0;
	protected boolean moving = true;
	protected boolean noCollide = false;
	protected boolean linger = true;
	protected Enemy enemy;
	
	protected double xOff;
	protected double yOff;
	
	public ArrayList<String> flags = new ArrayList<>();

	public Attack(TileMap tm) {
		super(tm);
	}
	
	public Attack(TileMap tm, BufferedImage[] anim, double x, double y, long duration, int damage, int width, int height){
		super(tm);
		setPosition(x,y);
		this.duration = duration;
		this.damage = damage;;
		this.animation = new Animation();
		animation.setFrames(anim);
		animation.setDelay(40);
		this.cwidth = this.width = width;
		this.cheight= this.height = height;
		while(this.x + this.width/2 >= 1800){
			this.x--;
		}
		dx = 0;
		dy = 0;
	}
	
	public void setVector(int x, int y){
		dx = x;
		dy = y;
	}
	
	public void setDim(int w, int h){
		cwidth = w;
		cheight = h;
	}
	
	public void setLinger(boolean b){
		linger = b;
	}
	
	public void update(){
		animation.update();
		if(duration > 0){
			duration--;
		}else if(duration == 0){
			remove = true;
		}
		
		if(dx == 0 && !linger){
			remove = true;
		}
		
		dy += gravity;
		if((tileMap.map[(int) (gety()/30) + 1][(int) (getx()/30)] > 3)){
			if(dx > 0){
				dx -= resist;
			}else{
				dx += resist;
			}
		}
		if(noCollide == false){
			checkTileMapCollision();
		}else{
			xtemp = x;
			ytemp = y;
			xtemp += dx;
			ytemp += dy;
		}
		setPosition(xtemp, ytemp);
		if(enemy != null){
			setPosition(enemy.getx() + enemy.getXmap(), enemy.gety() + enemy.getYmap());
		}
		if(y + height/2> 59 * 30 || y - height/2 < 0 || x + width/2 > 59 * 30 || x - width/2 < 0){
			noCollide = false;
		}
	}
	
	public void onHit(ArrayList<Attack> attacks){
		
	}
	
	public void addFlag(String s){
		flags.add(s);
	}
	
	public void setEnemy(Enemy e){
		enemy = e;
	}
	
	public void setOffset(double x, double y){
		xOff = x;
		yOff = y;
	}
	
	public void setNoCollide(boolean b){
		noCollide = b;
	}
	
	public void setGravity(double y){
		gravity = y;
	}
	
	public void setRemove(boolean b){
		remove = b;
	}
	
	public void setResist(double x){
		resist = x;
	}
	
	public void setMoving(boolean b){
		moving = b;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
	
	public boolean isFollowing(){
		return following;
	}
	
	public void setFollowing(boolean b){
		following = b;
	}

	
	

}
