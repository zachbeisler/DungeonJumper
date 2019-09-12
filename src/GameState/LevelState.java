package GameState;

import Main.Game;
import Main.GamePanel;
import ResourceManagers.Strings;
import TileMap.*;
import Attacks.Attack;
import Entity.*;
import Entity.Enemies.*;
import HUD.HUD;
import Heroes.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class LevelState extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private boolean hasDied = false;
	private boolean paused = false;
	private int levelScale = 0;
	private int skillPoints = 2;
	private int sel = 0;
	private int skillTimer = 60;
	private int windowTimer = 0;
	private int deathTimer = 60;
	
	private Font font;
	
	private ArrayList<Attack> hostileAttacks;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bottle> bottles;
	
	private HUD hud;
	private int index;
	
	public LevelState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public LevelState(GameStateManager gsm, int param) {
		this.gsm = gsm;
		init(param);
	}
	
	public void unload() {
		// Game.res.music.get("background").stop();
		
		//save highscores
		if(player.score > Game.scores[index]){
			Game.scores[index] = player.score;
		}
		GamePanel.encryptScores();
	}
	
	public void restart(){}
	
	public void init(){}
	
	public void init(int index) {
		
		font = new Font("Arial", Font.PLAIN, 9);
		
		hostileAttacks = new ArrayList<Attack>();
		bottles = new ArrayList<Bottle>();
		enemies = new ArrayList<Enemy>();
		
		generateLevel();
		
		switch(index){
		case 0:
			player = new Oklahoma(tileMap);
			break;
		case 1:
			player = new Paladin(tileMap);
			break;
		case 2:
			player = new Mage(tileMap);
			break;
		case 3:
			player = new Ninja(tileMap);
			break;
		case 4:
			player = new Druid(tileMap);
			break;
		case 5:
			player = new ArchMage(tileMap);
			break;
		case 6:
			player = new Scout(tileMap);
			break;
		case 7:
			player = new Link(tileMap);
			break;
		case 8:
			player = new Knight(tileMap);
			break;
		case 9:
			player = new TimeWalker(tileMap);
			break;
		case 10:
			player = new Monk(tileMap);
			break;
		}
		this.index = index;
		
		player.setPosition(tileMap.getStart().getX()*30+15, tileMap.getStart().getY()*30+15);
		tileMap.setPosition(player.getXmap(),player.getYmap());
		
		bg = new Background(Game.res.backgrounds.get(tileMap.getType()), 0.1);
		
		hud = new HUD(player);
		
	}
	
	private void generateLevel() {
		Random rand = new Random();
		tileMap = new TileMap(30);
		tileMap.generate();
		tileMap.loadTiles("/Tilesets/" + tileMap.getType() + "tileset.gif");
		tileMap.setTween(1);
		hostileAttacks = new ArrayList<Attack>();
		enemies = new ArrayList<Enemy>();
		bottles = new ArrayList<Bottle>();
		Enemy e;
		for(int x = 1; x < 59; x++){
			for(int y = 1; y < 59; y++){
				if(tileMap.map[y][x] == 0 && tileMap.map[y + 1][x] == 6){
					e = new MelleeCreep(tileMap);
					e.setPosition(x * 30 + 15, y * 30);
					e.setHealth(e.getHealth() + levelScale + Game.options[5]);
					e.setDamage(e.getDamage() + (int)Math.floor(levelScale/(4 - Game.options[5])));
					e.setScore((int) (e.getScore() + (levelScale + Game.options[5]) * 50));
					enemies.add(e);
				}
				if(tileMap.map[y][x] == 0 && tileMap.map[y + 1][x] == 7){
					e = new Wizard(tileMap);
					e.setPosition(x * 30 + 15, y * 30 + 18);
					e.setHealth(e.getHealth() + (int)Math.floor(levelScale/(3 - Game.options[5])));
					e.setDamage(e.getDamage() + (int)Math.floor(levelScale/(5 - Game.options[5])));
					e.setScore((int) (e.getScore() + (levelScale + Game.options[5]) * 75));
					enemies.add(e);
				}
			}
		}
		if(rand.nextInt(5) <= Game.options[5] - 1){
			e = new Dragon(tileMap);
			e.setHealth((int)(7 + levelScale * (0.5 + Game.options[5])));
			e.setDamage((int)(2 + levelScale/(3 - Game.options[5])));
			e.setPosition(tileMap.getEnd().getX(), tileMap.getEnd().getY());
			e.setScore((int) (e.getScore() * (levelScale + 1 + Game.options[5]) * 1.75));
			enemies.add(e);
		}
	}
	
	private void newLevel(){
		levelScale++;
		generateLevel();
		player.newTileMap(tileMap);
		player.setPosition(tileMap.getStart().getX()*30+15, tileMap.getStart().getY()*30+15);
		player.setHealth(player.getHealth() + levelScale + 1);
		player.reset();
		tileMap.setPosition(player.getXmap(),player.getYmap());
		
		bg = new Background(Game.res.backgrounds.get(tileMap.getType()), 0.1);
		
		skillTimer = 60;
		skillPoints++;
	}
	
	public void update() {
		if(windowTimer > 0) windowTimer--;
		if(deathTimer > 0 && player.isDead()) deathTimer--;
		if(skillTimer > 0) skillTimer--;
		if(skillPoints == 0 || skillTimer > 0){
			Enemy e;
			Attack a;
			Bottle b;
			//audio
			// Game.res.music.get("background").loop();
			
			// update player
			player.update();
			
			// attack enemies
			player.checkAttack(enemies);
			
			// update all enemies
			for(int i = 0; i < enemies.size(); i++) {
				e = enemies.get(i);
				e.update(player);
				if(e.shouldRemove() == true) {
					player.addScore(e.getScore());
					if(e instanceof Wizard){
						b = new Bottle(tileMap);
						b.setPosition(e.getx(), e.gety());
						bottles.add(b);
					}else if(e instanceof Dragon){
						skillTimer += 60;
						skillPoints++;
					}
					enemies.remove(i);
					i--;
				}
				if(e.hasProjectile()){
					hostileAttacks.add(e.getProjectile());
					e.setProjectile(null);
				}
			}
			
			//check bottles
			for(int i = 0; i < bottles.size(); i++){
				b = bottles.get(i);
				if(b.intersects(player)){
					player.mana++;
					if(player.mana > player.getMaxMana()){
						player.mana = player.getMaxMana();
					}
					bottles.remove(i);
					i--;
				}
			}
			
			
			//update hostile attacks
			for(int i = 0; i < hostileAttacks.size(); i++){
				a = hostileAttacks.get(i);
				a.update();
				if(a.shouldRemove() || a.dx == 0 || a.dy == 0){
					hostileAttacks.remove(i);
					i--;
				}
				else if(a.intersects(player) && !player.invulnerable){
					player.hit(a.getDamage());
					hostileAttacks.remove(i);
					i--;
				}
			}
		}else{
			return;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		if(paused){
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawImage(Game.res.backgrounds.get("pause"), null, 0 ,0);
			g.drawString(Integer.toString(player.getDamage()), 186, 93);
			g.drawString(Integer.toString(player.getMaxMana()), 186, 123);
			g.drawString(Integer.toString(player.getMaxHealth()), 186, 153);
			g.setColor(Color.RED);
			if(sel == 0){
				g.drawLine(116, 186, 165, 186);
			}else{
				g.drawLine(178, 186, 205, 186);
			}
			return;
		}
		if(!player.isDead() && (skillPoints == 0 || skillTimer > 0) && windowTimer == 0){
		// draw bg
		bg.setPosition(0, 0);
		bg.draw(g);

		// draw tilemap
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
			);
		tileMap.draw(g);

		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw bottles
		for(int i = 0; i < bottles.size(); i++) {
			bottles.get(i).draw(g);
		}
		
		// draw player
		player.draw(g);
		
		//drawAttacks
		for(int i = 0; i < hostileAttacks.size(); i++){
			hostileAttacks.get(i).draw(g);
		}

		// draw hud
		hud.draw(g);
		} else if(skillPoints == 0 && windowTimer == 0){
			if(!hasDied){	
				hasDied = true;
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
				g.setColor(Color.RED);
				g.drawString(Strings.deathMessages[new Random().nextInt(Strings.deathMessages.length)], 80, 120);
				g.drawString("Score:  " + Integer.toString(player.score), 80, 150);
			}else if(deathTimer == 0){
				g.drawString("Press the any key...", 80, 200);
			}
			}
		else if((skillPoints > 0 && skillTimer == 0) || windowTimer > 0){
			g.setColor(Color.WHITE);
			g.setFont(font);
			g.drawImage(Game.res.backgrounds.get("skill"), null, 0, 0);
			g.drawString(Integer.toString(player.getDamage()), 186, 93);
			g.drawString(Integer.toString(player.getMaxMana()), 186, 123);
			g.drawString(Integer.toString(player.getMaxHealth()), 186, 153);
			g.drawString(Integer.toString(skillPoints), 197, 175);
			g.setColor(Color.RED);
			g.drawRect(179, 79 + sel*30, 21, 21);
			
		}
			
		}
		
			
		
	
	public void keyPressed(int k) {
		if(hasDied && deathTimer == 0){gsm.setState(GameStateManager.MENUSTATE); return;}
		if(skillPoints > 0){
			if(k == KeyEvent.VK_UP){
				sel--;
				if(sel < 0) sel = 2;
			}
			if(k == KeyEvent.VK_DOWN){
				sel++;
				if(sel > 2) sel = 0;
			}
			if(k == KeyEvent.VK_ENTER && skillPoints > 0){
				switch(sel){
					case 0:
						player.setDamage(player.getDamage() + 1);
						skillPoints--;
						break;
					case 1:
						player.setMaxMana(player.getMaxMana() + 1);
						player.setMana(player.getMana() + 1);
						skillPoints--;
						break;
					case 2:
						player.setMaxHealth(player.getMaxHealth() + 1);
						player.setHealth(player.getHealth() + 1);
						skillPoints--;
						break;
					}
				windowTimer += 40;
			}
			return;
		}
		if(paused){
			if(k == KeyEvent.VK_RIGHT){
				sel++;
				if(sel > 1) sel = 1;
			}
			if(k == KeyEvent.VK_LEFT){
				sel--;
				if(sel < 0) sel = 0;
			}
			if(k == KeyEvent.VK_ENTER){
				if(sel == 0){
					paused = false;
				}else{
					gsm.setState(GameStateManager.MENUSTATE);
				}
			}
			return;
		}
		if(k == KeyEvent.VK_F1){player.setPosition(tileMap.getStart().getX()*30+15, tileMap.getStart().getY()*30+15);}
		if(k == KeyEvent.VK_LEFT && player.doInput) {player.setLeft(true); player.setFacingRight(false);}
		if(k == KeyEvent.VK_RIGHT && player.doInput) {player.setRight(true); player.setFacingRight(true);}
		if(k == KeyEvent.VK_UP && player.doInput){
			if(tileMap.map[(int) (player.gety()/30)][(int) (player.getx()/30)] == 3){
				player.setLadder(true);
			}
			if(tileMap.map[(int) (player.gety()/30)][(int) (player.getx()/30)] == 2){
				player.addScore(1000 * (levelScale + 1));
				newLevel();
				return;
			}
			if(tileMap.map[(int) (player.gety()/30)][(int) (player.getx()/30)] == 1){
				tileMap.map[(int) (player.gety()/30)][(int) (player.getx()/30)] = 0;
				player.addScore(1000 + levelScale*200);
				return;
			}
			player.setUp(true);
		}
		if(k == KeyEvent.VK_DOWN && player.doInput){ player.setDown(true);
			if(tileMap.map[(int) (player.gety()/30)][(int) (player.getx()/30)] == 3){
				player.setLadder(true);
			}
		}
		if(k == KeyEvent.VK_UP && !player.isOnLadder() && player.doInput) player.setJumping(true);
		if(k == KeyEvent.VK_Q && player.qcd <= 0 && player.doInput) player.QAbility();
		if(k == KeyEvent.VK_W && player.wcd <= 0 && player.doInput) player.WAbility();
		if(k == KeyEvent.VK_E && player.ecd <= 0 && player.doInput) player.EAbility();
		if(k == KeyEvent.VK_ESCAPE) paused = true;
	}
	
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_UP) player.setJumping(false);
	}
	
}












