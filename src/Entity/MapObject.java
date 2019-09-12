package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.Color;
import java.awt.Rectangle;

public abstract class MapObject {
	
	protected final Color hpRed = new Color(223,1,1);
	protected final Color hpGreen = new Color(49, 180, 4);
	
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	public double dx;
	public double dy;
	
	// vehicle stuff
	protected MapObject vehicle;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Animation animation;
	public int currentAction;
	protected int previousAction;
	public boolean facingRight;
	protected boolean remove;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	public boolean stunned;
	
	// constructor
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize(); 
	}
	
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)(x + getXmap() - cwidth/2),
				(int)(y + getYmap() - cheight/2),
				cwidth,
				cheight
		);
	}
	
	public void update(){}
	
	public boolean shouldRemove(){
		return remove;
	}
	
	public void calculateCorners(double x, double y) {
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
	}
	
	public void checkTileMapCollision() {
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x, ydest);
		if(dy < 0) {
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		if(dy > 0) {
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		
		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		
		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
	}
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	
	public void setVehicle(MapObject obj){
		this.vehicle = obj;
	}
	
	public void setVehicle(){
		this.vehicle = null;
	}
	
	public void setPosition(double x, double y) {
		if(vehicle == null){
		this.x = x;
		this.y = y;
		}else{
			this.x = vehicle.x;
			this.y = vehicle.y;
		}
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setYVector(double dy){
		this.dy = dy;
	}
	
	public void setXVector(double dx){
		this.dx = dx;
	}
	
	public void setMapPosition() {
		setXmap(tileMap.getx());
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) { left = b; facingRight = false;}
	public void setRight(boolean b) { right = b; facingRight = true;}
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	
	public boolean notOnScreen() {
		return x + getXmap() + width < 0 ||
			x + getXmap() - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}
	
	public void draw(java.awt.Graphics2D g) {
		setMapPosition();
		try{
		if(facingRight) {
			g.drawImage(
				animation.getImage(),
				(int)(x + getXmap() - width / 2),
				(int)(y + ymap - height / 2),
				width,
				height,
				null
			);
		}
		else {
			g.drawImage(
				animation.getImage(),
				(int)(x + getXmap() - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
		}
		}catch(Exception e){e.printStackTrace();}
		
		//g.draw(getRectangle());
	}

	public double getXmap() {
		return xmap;
	}

	public double getYmap() {
		return ymap;
	}
	
	public void setXmap(double xmap){
		this.xmap = xmap;
	}
	
}
















