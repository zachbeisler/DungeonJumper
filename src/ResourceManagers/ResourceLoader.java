package ResourceManagers;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.Game;
import Main.GamePanel;
import TileMap.Background;
import TileMap.Tile;

public class ResourceLoader {
	
	Background bg;
	Graphics2D g;
	private Font font = new Font("Arial", Font.PLAIN, 12);
	private final Rectangle loadingBar = new Rectangle(30, 190, GamePanel.WIDTH-60, 15);
	private int count = 0;
	
	private Random rand = new Random();
	
	private final String[][] audioFiles_Player = {};
	
	private final String[][] audioFiles_Music =  {};
	
	private final String[][] audioFiles_Misc =   {};
	
	private final String[] gfxFiles_tileSets = {"grass", "castle", "space"};
	
	private final String[] gfxFiles_backgrounds = {"options","character","skill", "pause", "grass", "castle", "space"};
	
	private final String[] gfxFiles_hud = {"hud","menuoptions"};
	
	private int totalFiles = audioFiles_Player.length + audioFiles_Music.length + audioFiles_Misc.length + gfxFiles_tileSets.length
			+ gfxFiles_backgrounds.length + gfxFiles_hud.length;
	
	public HashMap<String, AudioPlayer> playerSfx;
	public HashMap<String, AudioPlayer> music;
	public HashMap<String, AudioPlayer> miscSfx;
	
	public HashMap<String, Tile[][]> tileSets;
	public HashMap<String, BufferedImage> backgrounds;
	public HashMap<String, BufferedImage> hud;
	
	
	public ResourceLoader(GamePanel panel){
		
		g = panel.graphicsObject();
		
		try{
			bg = new Background(ImageIO.read(getClass().getResourceAsStream("/Backgrounds/loadingbg.gif")), 0.1);
		}catch(Exception e){}
		
		draw(g, panel);
		
		//playerSfx
		playerSfx = new HashMap<String, AudioPlayer>();
		for(int i=0;i<audioFiles_Player.length;i++){
			playerSfx.put(audioFiles_Player[i][0], new AudioPlayer(audioFiles_Player[i][1],"sfx"));
			count+=1;
			draw(g, panel);
		}
		
		//music
		music = new HashMap<String, AudioPlayer>();
		for(int j=0;j<audioFiles_Music.length;j++){
			music.put(audioFiles_Music[j][0], new AudioPlayer(audioFiles_Music[j][1],"music"));
			count+=1;
			draw(g, panel);
		}
		
		//miscSfx
		miscSfx = new HashMap<String, AudioPlayer>();
		for(int k=0;k<audioFiles_Misc.length;k++){
			miscSfx.put(audioFiles_Misc[k][0], new AudioPlayer(audioFiles_Misc[k][1],"sfx"));
			count+=1;
			draw(g, panel);
		}
		
		//tilesets
		tileSets = new HashMap<String, Tile[][]>();
		int numTilesAcross;
		BufferedImage tileset;
		int tileSize = 30;
		Tile[][] tiles;
		for(int k=0;k<gfxFiles_tileSets.length;k++){
			try{
			
			tileset = ImageIO.read(
					getClass().getResourceAsStream("/Tilesets/" + gfxFiles_tileSets[k] + "tileset.gif")
				);
				numTilesAcross = tileset.getWidth() / tileSize;
				tiles = new Tile[2][numTilesAcross];
				
				BufferedImage subimage;
				for(int col = 0; col < numTilesAcross; col++) {
					subimage = tileset.getSubimage(
								col * tileSize,
								0,
								tileSize,
								tileSize
							);
					tiles[0][col] = new Tile(subimage, Tile.NORMAL);
					subimage = tileset.getSubimage(
								col * tileSize,
								tileSize,
								tileSize,
								tileSize
							);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
				}
				tileSets.put(gfxFiles_tileSets[k], tiles);
			}catch(Exception e){
				e.printStackTrace();
			}
			count+=1;
			draw(g, panel);
		}
		
		//backgrounds
		backgrounds = new HashMap<String, BufferedImage>();
		BufferedImage image;
		for(int k=0;k<gfxFiles_backgrounds.length;k++){
			try{
			image = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/" + gfxFiles_backgrounds[k] + "bg.gif"));
			backgrounds.put(gfxFiles_backgrounds[k], image);
			}catch(Exception e){
				e.printStackTrace();
			}
			count+=1;
			draw(g, panel);
		}
		
		//hud
		hud = new HashMap<String, BufferedImage>();
		for(int k=0;k<gfxFiles_hud.length;k++){
			try{
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/" + gfxFiles_hud[k] + ".gif"));
			hud.put(gfxFiles_hud[k], image);
			}catch(Exception e){
				e.printStackTrace();
			}
			count+=1;
			draw(g, panel);
		}

	}
	
	public void draw(Graphics2D g, GamePanel panel){
		// draw bg
		bg.draw(g);
		
		//draw loading bar
		g.setColor(Color.WHITE);
		g.draw(loadingBar);
		g.setColor(Color.GREEN);
		g.fillRect(loadingBar.x+1, loadingBar.y+1,(int)((loadingBar.width - 1) / totalFiles) * count, loadingBar.height-1);
		
		//draw label
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(((Integer) Math.round(100 * count/totalFiles)).toString() + "%", 30, 220);
		
		//draw to screen
		panel.drawToScreen();
	}
	
	public void randomSound(String key, int max){
		
		Integer randInt = (Integer)rand.nextInt(max) + 1;
		
		if(playerSfx.containsKey(key + randInt.toString())){
			playerSfx.get(key + randInt.toString()).play();
		}
		if(music.containsKey(key + randInt.toString())){
			music.get(key + randInt.toString()).play();
		}
		if(miscSfx.containsKey(key + randInt.toString())){
			miscSfx.get(key + randInt.toString()).play();
		}
		
	}
	
}
