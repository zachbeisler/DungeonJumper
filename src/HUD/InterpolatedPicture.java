package HUD;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Entity.Animation;

public class InterpolatedPicture {

	private Rectangle initialBound;
	private Rectangle bound;
	
	private double dWidth;
	private double dHeight;
	
	private double dX;
	private double dY;
	
	private int targetX;
	private int targetY;
	
	private int targetWidth;
	private int targetHeight;
	
	private double tempX;
	private double tempY;
	
	private Animation anim;
	
	public InterpolatedPicture(int x, int y, int width, int height, BufferedImage[] frames, int delay) {
		
		bound = new Rectangle(x,y,width,height);
		initialBound = new Rectangle(x,y,width,height);
		
		tempX = bound.getX();
		tempY = bound.getY();
		
		anim = new Animation();
		anim.setFrames(frames);
		anim.setDelay(delay);
	}
	
	public InterpolatedPicture(int x, int y, int width, int height, BufferedImage image, int delay) {
		BufferedImage[] frames = {image};
		
		bound = new Rectangle(x,y,width,height);
		initialBound = new Rectangle(x,y,width,height);
		
		tempX = bound.getX();
		tempY = bound.getY();
		
		anim = new Animation();
		anim.setFrames(frames);
		anim.setDelay(delay);
	}

	public void update(){
		anim.update();
		if(targetWidth == bound.getWidth()) dWidth = 0;
		if(targetHeight == bound.getHeight()) dHeight = 0;
		if(targetX == bound.getX()) dX = 0;
		if(targetY == bound.getY()) dY = 0;
		
		bound.setSize((int)(bound.getWidth() + dWidth), (int)(bound.getHeight() + dHeight));
		tempX -= (dWidth/2) + dX;
		tempY -= (dHeight/2) + dY;
		bound.setLocation((int) tempX, (int) tempY);
	}
	
	public void draw(Graphics2D g){
		g.drawImage(anim.getImage(),(int) bound.getX(),(int) bound.getY(),(int) bound.getWidth(),(int) bound.getHeight(), null);
	}
	
	public Rectangle getBound(){
		return bound;
	}
	
	public void horizGrow(int pixels, int frames){
		dWidth = pixels/frames;
		targetWidth = (int) (initialBound.getWidth() + pixels);
	}
	
	public void vertGrow(int pixels, int frames){
		dHeight = pixels/frames;
		targetHeight = (int) (initialBound.getHeight() + pixels);
	}
	
	public void grow(int pixels, int frames){
		horizGrow(pixels,frames);
		vertGrow(pixels,frames);
	}
	
	public void xTrans(int pixels, int frames){
		dX = pixels/frames;
		targetX = (int) (initialBound.getX() + pixels);
	}
	
	public void yTrans(int pixels, int frames){
		dY = pixels/frames;
		targetY = (int) (initialBound.getY() + pixels);
	}
	
	public void translate(int xPixels, int yPixels, int frames){
		xTrans(xPixels, frames);
		yTrans(yPixels, frames);
	}
	
	public void setAnimating(boolean b){
		anim.pause(b);
	}
	
	public void reset(){
		bound.setSize(initialBound.getSize());
		bound.setLocation(initialBound.getLocation());
		tempX = initialBound.getX();
		tempY = initialBound.getY();
		anim.setFrame(0);
		anim.pause(true);
	}

}
