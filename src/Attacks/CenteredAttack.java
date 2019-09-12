package Attacks;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import TileMap.TileMap;

public class CenteredAttack extends Attack{
	
	private Point center;
	private double a;
	private boolean centerFollow;
	private double theta;

	public CenteredAttack(TileMap tm, BufferedImage[] anim, double x, double y,
			long duration, int damage, int width, int height) {
		super(tm, anim, x, y, duration, damage, width, height);
		this.center = new Point();
		center.setLocation(0, 0);
		this.a = 1;
	}
	
	
	public void update(){
		super.update();
		theta = Math.tan((gety() + getYmap() - center.getY())/(getx() + getXmap() - center.getX()));
		if((getx() + getXmap() - center.getX()) < 0){
			theta -= Math.PI;
		}
		dx += -a * Math.cos(theta);
		dy += -a * Math.sin(theta);
		if(Math.abs(getx() + getXmap() - center.getX()) <= cwidth && Math.abs(gety() + getYmap() - center.getY()) <= cheight){
			remove = true;
		}
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
		//g.fillRect((int)(center.getX() - 5),(int) (center.getY() - 5), 10, 10);
		//g.drawLine((int)(center.getX() - 5),(int) (center.getY() - 5), (int)(center.getX() - 5 + Math.cos(theta)*15),(int) (center.getY() - 5 + Math.sin(theta)*15));
	}
	
	public void setAcceleration(double a){
		this.a = a;
	}
	
	public void setCenter(double x, double y){
		center.setLocation(x, y);
	}
	
	public void setCenterFollow(boolean b){
		centerFollow = b;
	}
	
	public boolean isCenterFollow(){
		return centerFollow;
	}
	

}
