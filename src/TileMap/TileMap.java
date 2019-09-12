package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.Game;
import Main.GamePanel;

public class TileMap {
	
	private String[] levelTypes = {"grass","castle","space"};
	
	// position
	private double x;
	private double y;
	private double tween;
	private Point start; 
	private Point end;
	
	// map
	public int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross = 4;
	private Tile[][] tiles;
	private String levelType;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	public Point getStart(){
		return start;
	}
	
	public String getType(){
		return levelType;
	}
	public void loadTiles(String s) {		
		tiles = Game.res.tileSets.get(levelType);
	}
	
	
	public void generate() {
		
		Random rand = new Random();
		//evaluating fields
		numCols = 60;
		numRows = 60;
		if(Game.options[4] == 0){
			levelType = levelTypes[rand.nextInt(levelTypes.length)];
		}else{
			levelType = levelTypes[Game.options[4] - 1];
		}
		width = numCols * tileSize;
		height = numRows * tileSize;
		
		xmin = GamePanel.WIDTH - width;
		xmax = 0;
		ymin = GamePanel.HEIGHT - height;
		ymax = 0;
		
		map = new int[60][60];
		
		//outer walls
		for(int i = 0; i < 60; i++){
			map[i][0] = 4;
			map[i][59] = 4;
			map[0][i] = 5;
			map[59][i] = 5;
			
		}
		map[0][59] = 5;
		
		//soultion path generation
		int sy = 58;
		int sx = rand.nextInt(6) + 1;
		int ey = rand.nextInt(6) + 2;
		int ex = rand.nextInt(4) + 55;
		start = new Point(sx,sy);
		setEnd(new Point(ex, ey));
		map[ey][ex] = 2;
		map[ey+1][ex] = 5;
		map[ey+1][ex-1] = 5;
		int cx = sx;
		int cy = sy;
		int dx;
		int dy;
			while(cy-ey > 7 && ex-cx > 7){
				dx = rand.nextInt(6) + 1;
				for(int x = 0; x < dx; ++x){
					cx++;
					if(cy != 58)
					map[cy+1][cx] = 5;
				}
				dy = rand.nextInt(3) + 3;
				for(int y = 0; y <= dy; y++){
					map[cy][cx] = 3;
					cy--;
				}
			}
		for(cx++;cx!=ex-2;cx++){
			map[cy+1][cx] = 5;
		}
		for(int y = cy; y != ey;y--){
			map[y][cx] = 3;
		}
		
		//random platforms/ladders generation
		boolean hasLadder;
		int blocks;
		for(int y = 56; y > 2; y--){
			for(int x = 1; x < 59; x++){
				if(map[y][x] == 0 && rand.nextInt(5) == 0){
					blocks = 1;
					if(map[y+1][x] == 0) hasLadder = false;
					else hasLadder = true;
					while(x<59 && map[y][x] == 0 && map[y+1][x] == 0 && map[y+2][x] == 0 && !(rand.nextInt((10-blocks)) == 0)){
						map[y][x] = 5;
						if(!hasLadder && rand.nextInt(6) == 0 && blocks > 1){
							map[y][x] = 3;
							hasLadder = true;
						}
						blocks++;
						x++;
					}
				}
			}
		}
		
		for(int y = 3; y < 57; y++){
			for(int x = 1; x < 59; x++){
				if(map[y][x] == 3){
					for(int i = 1; i < 8; i++){
						if(map[y+i][x] == 0 && map[y+i][x-1] != 3 && map[y+i][x+1] !=3){
							map[y+i][x] = 3;
						}else break;
					}
				}
				else if(map[y][x] == 5){
					if(rand.nextInt(10) == 9){
						map[y][x]++;
					}else if(rand.nextInt(11) == 10){
						map[y][x] += 2;
					}
				}
				else if(map[y][x] == 0 && map[y+1][x] > 3 && rand.nextInt(60) == 0){
					map[y][x] = 1;
				}
			}
		}
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public void setTween(double d) { tween = d; }
	
	public void setPosition(double x, double y) {
		
		this.x = x;
		this.y = y;
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	private void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g) {
		
		for(
			int row = rowOffset;
			row < rowOffset + numRowsToDraw;
			row++) {
			
			if(row >= numRows) break;
			
			for(
				int col = colOffset;
				col < colOffset + numColsToDraw;
				col++) {
				
				if(col >= numCols) break;
				
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tiles[r][c].getImage(),
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null
				);
				
			}
			
		}
		
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}
	
}



















