package Attacks;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entity.Animation;
import Main.GamePanel;
import TileMap.TileMap;

public class OnHitAttack extends Attack{
	
	public ArrayList<Attack> spawnables = new ArrayList<>();

	public OnHitAttack(TileMap tm) {
		super(tm);
		// TODO Auto-generated constructor stub
	}
	
	public OnHitAttack(TileMap tm, BufferedImage[] anim, double x, double y, long duration, int damage, int width, int height){
		super(tm);
		setPosition(x,y);
		this.duration = duration;
		this.damage = damage;;
		this.animation = new Animation();
		animation.setFrames(anim);
		animation.setDelay(40);
		this.cwidth = this.width = width;
		this.cheight= this.height = height;
		dx = 0;
		dy = 0;
	}
	
	public void addSpawnable(Attack a){
		spawnables.add(a);
	}
	
	public void onHit(ArrayList<Attack> attacks){
		for(Attack a : spawnables){
			if(!a.facingRight) a.xOff = -a.xOff;
			if(a.moving) a.setPosition(x + a.xOff, y + a.yOff);
			while(a.gety() + a.getHeight()/2 + a.getYmap() >= 1800){
				a.setPosition(a.getx(), a.gety() - 1);
			}
			while(a.gety() - a.getHeight()/2 + a.getYmap() <= 0){
				a.setPosition(a.getx(), a.gety() + 1);
			}
			while(a.getx() + a.getWidth()/2 + a.getXmap() >= 1800){
				a.setPosition(a.getx() - 1, a.gety());
			}
			while(a.getx() - a.getWidth()/2 + a.getXmap() <= 0){
				a.setPosition(a.getx() + 1, a.gety());
			}
			a.setEnemy(enemy);
			attacks.add(a);
		}
		remove = true;
	}
	
	
	
	

}
