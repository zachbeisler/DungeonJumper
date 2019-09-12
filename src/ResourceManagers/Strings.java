package ResourceManagers;

public class Strings {
	
	public final String[] heroNames = {"Oklahoma Johns","The Angel of Vengeance", "Erook", "Hong Dong", "Fyndra","Eyorn the Red",
			"Scott the Swift Scout","The Hero of Time","Sir Maxx", "Tsao Hu", "Random"};
	
	public final String[][] heroDescriptions;
	public final String[][] heroAbilities;
	
	public static final String[] deathMessages = {
		"You have died of dysentery.", "FATALITY",
		"wasted", "M-M-M-M-Monster Kill",
		"U W07 M9?", "3spooky5me",
		"An Ally Has Been Slain", "Aced",
		"2 B05U 5 U", "HASAKI!"
	};
	
	public Strings(){
		
		heroDescriptions = new String[heroNames.length][4];
		heroAbilities = new String[heroNames.length][8];
		
		heroDescriptions[0][0] = "  A former Calculus Teacher gone rogue, Oklahoma ";
			heroDescriptions[0][1] = "Johns is out of her element. Armed with only a sword ";
			heroDescriptions[0][2] = "and whip, she hopes to find the lost ArcLength of the ";
			heroDescriptions[0][3] = "Cycloid, and apprehend the raiders who stole it.";
		heroDescriptions[1][0] = "  Recently descendent from the council of 7, the";
			heroDescriptions[1][1] = "Angel of Vengeance has no name. His one task: to";
			heroDescriptions[1][2] = "bring justice to the worldly evil that corrupts";
			heroDescriptions[1][3] = "the land.";
		heroDescriptions[2][0] = "  After being denied access to Pigwarts University";
			heroDescriptions[2][1] = "of Minecraft and Sorcery, Erook seeks to prove his ";
			heroDescriptions[2][2] = "prowess in all things arcane. Even if he can't ";
			heroDescriptions[2][3] = "pronouce Wingardium Levios-ahhhh.";
		heroDescriptions[3][0] = "  Hong learnt at a young age the price that must be paid";
			heroDescriptions[3][1] = "for raising a donger. At the tender age of 4, his";
			heroDescriptions[3][2] = "parents were whisked away by Lord Santana. He has";
			heroDescriptions[3][3] = "made it his life goal to retreive his rightful dongers.";
		heroDescriptions[4][0] = "  *Meow*";
			heroDescriptions[4][1] = "";
			heroDescriptions[4][2] = "";
			heroDescriptions[4][3] = "";
		heroDescriptions[5][0] = "  A most learned man, Eyorn is a wizened old wizard";
			heroDescriptions[5][1] = "that has lived for thousands of years. He has seen";
			heroDescriptions[5][2] = "the rise and fall of entire nations. Don't be hasty";
			heroDescriptions[5][3] = "to come to conclusions, he is quite spry for his age.";
		heroDescriptions[6][0] = "  The average man would look over Scott and deem him";
			heroDescriptions[6][1] = "unfit for war; A runt of the litter. Little would they";
			heroDescriptions[6][2] = "know, Scott's speed and finese are unmatched, and ";
			heroDescriptions[6][3] = "hes always willing to scout ahead. Hut, 2-3-4!";
		heroDescriptions[7][0] = "  The Hero of Time is reborn yet again to combat";
			heroDescriptions[7][1] = "the rising evil in the world, and he will yet";
			heroDescriptions[7][2] = "again seal away his ancient foe with the power";
			heroDescriptions[7][3] = "bestowed upon him by the gods of light.";
		heroDescriptions[8][0] = "  Sir Maxx has a deep secret that isn't obvious";
			heroDescriptions[8][1] = "at first glance. In fact, Sir Maxx isn't a \"sir\"";
			heroDescriptions[8][2] = "at all. She wishes to prove to the world that";
			heroDescriptions[8][3] = "women just do it better.";
		heroDescriptions[9][0] = "  Years of training and meditation have all led him here.";
			heroDescriptions[9][1] = "Quick as a fox but strong as a bear. Every move by";
			heroDescriptions[9][2] = "strict design, no mistakes. His chi flows freely,";
			heroDescriptions[9][3] = "feeling no constraints of mana. He is Tsao Hu.";
		heroDescriptions[10][0] = "  Allow RNGesus to choose a random hero for you.";
			heroDescriptions[10][1] = "It is said that as reward, RNGesus will bestow";
			heroDescriptions[10][2] = "a great power upon the player that dares select";
			heroDescriptions[10][3] = "his button. But no one believes old childrens' tales.";
			
		//abilites	
			
		heroAbilities[0][0] = "(Q)Stab: Oklahoma stabs forward with her sword.";
			heroAbilities[0][1] = "";
			heroAbilities[0][2] = "(W)Whip: Oklahoma cracks her whip, dealing high damage. ";
			heroAbilities[0][3] = "		3 second cd.";
			heroAbilities[0][4] = "";
			heroAbilities[0][5] = "(E)Grenade: Tosses a grenade that explodes on impact, ";
			heroAbilities[0][6] = "dealing heavy damage in an area. Costs one mana";
			heroAbilities[0][7] = "and has no cooldown.";
		heroAbilities[1][0] = "(Q)Hammer of Vengeance: Summons a mystical hammer to";
			heroAbilities[1][1] = "damage enemies.";
			heroAbilities[1][2] = "";
			heroAbilities[1][3] = "(W)Light of Dawn: Realease a wave of light that deals";
			heroAbilities[1][4] = "damage based on max health. Costs one mana.";
			heroAbilities[1][5] = "";
			heroAbilities[1][6] = "(E)Divine Shield: Become invulnerable for a short time";
			heroAbilities[1][7] = "based on max health. Costs one mana; 8 second cd.";
		heroAbilities[2][0] = "(Q)Magic Missile: Fires a small arcane projectile.";
			heroAbilities[2][1] = "";
			heroAbilities[2][2] = "(W)Blink: Teleports a short distance in front of Erook.";
			heroAbilities[2][3] = "Cannot go through walls. Usually.";
			heroAbilities[2][4] = "";
			heroAbilities[2][5] = "(E)Frost Nova: Conjures a powerful ring of frost at";
			heroAbilities[2][6] = "Erook's feet, dealing heavy damage and granting";
			heroAbilities[2][7] = "invulnerability. Costs one mana.";
		heroAbilities[3][0] = "(Q)Slash: Hong performs a quick slash with his Katana.";
			heroAbilities[3][1] = "";
			heroAbilities[3][2] = "(W)Shuriken Salvo: Throws three shurikens in quick";
			heroAbilities[3][3] = "sucession. Costs one mana. 3 second cd.";
			heroAbilities[3][4] = "";
			heroAbilities[3][5] = "(E)Shadowstep: Appear behind a nearby enemy, dealing";
			heroAbilities[3][6] = "massive damage. Costs one mana. 3 second cd.";
			heroAbilities[3][7] = "";
		heroAbilities[4][0] = "(Q)Wrath/Swipe: Fires a ball of energy, costing one mana;";
			heroAbilities[4][1] = "Fyndra swipes at enemies with her claws.";
			heroAbilities[4][2] = "";
			heroAbilities[4][3] = "(W)Moonfire/Pounce: Blasts a nearby enemy with a moon";
			heroAbilities[4][4] = "beam; Leaps forward mauling the first enemy hit.";
			heroAbilities[4][5] = "";
			heroAbilities[4][6] = "(E)Shapeshift: Fyndra changes into her cat form, or vice";
			heroAbilities[4][7] = "versa. Gives access to new (Q) and (W) abilities.";
		heroAbilities[5][0] = "(Q)Fireball: Conjures a ball of fire dealing damage to";
			heroAbilities[5][1] = "the first enemy hit. 1 second cd.";
			heroAbilities[5][2] = "";
			heroAbilities[5][3] = "(W)Meteor: Summons a meteor to fall on a nearby";
			heroAbilities[5][4] = "enemy. Costs one mana. 3 second cd.";
			heroAbilities[5][5] = "";
			heroAbilities[5][6] = "(E)Rocket Jump: Shoots a fireball downwards,";
			heroAbilities[5][7] = "propelling Eyorn upwards. Costs one mana.";
		heroAbilities[6][0] = "(Q)Snipe: Scott pulls back his bow and fires an arrow";
			heroAbilities[6][1] = "";
			heroAbilities[6][2] = "(W)Marked for Death: Signals to an ally sniper that";
			heroAbilities[6][3] = "a nearby enemy should be taken out. The enemy is then";
			heroAbilities[6][4] = "headshotted, dealing massive damage. Costs one mana.";
			heroAbilities[6][5] = "";
			heroAbilities[6][6] = "(E)Action Roll: Scott ducks his head and rolls";
			heroAbilities[6][7] = "forward, dodging attacks. 1 second cd.";
		heroAbilities[7][0] = "(Q)Tri-slash: Unleashes a series of three slashes,";
			heroAbilities[7][1] = "with the third being the most damaging.";
			heroAbilities[7][2] = "";
			heroAbilities[7][3] = "(W)Boomerang: Throws an enchanted boomerang that";
			heroAbilities[7][4] = "damages enemies. Costs one mana. 3 second cd.";
			heroAbilities[7][5] = "";
			heroAbilities[7][6] = "(E)Arrow of Light: Shoots an arrow infused with";
			heroAbilities[7][7] = "light, dealing heavy damage. Costs one mana.";
		heroAbilities[8][0] = "(Q)Spear Shot: Maxx's spear propagates forward,";
			heroAbilities[8][1] = "dealing average damage to enemies.";
			heroAbilities[8][2] = "";
			heroAbilities[8][3] = "(W)Defensive Stance: Use the shield to become";
			heroAbilities[8][4] = "immune to all damage but have restricted";
			heroAbilities[8][5] = "movement. 1 second cd. Toggleable.";
			heroAbilities[8][6] = "";
			heroAbilities[8][7] = "(E)Charge: Charges forward. Costs one mana.";
		heroAbilities[9][0] = "(Q)Jab: Punches forward with lightning speed, damaging";
			heroAbilities[9][1] = "enemies and restoring one chi.";
			heroAbilities[9][2] = "(W)Shockwave: Charges up then slams the ground";
			heroAbilities[9][3] = "sending out two shockwaves. Costs one chi.";
			heroAbilities[9][4] = "(E)Flying Back-Kick: Performs a backflip and kicks,";
			heroAbilities[9][5] = "sending out a shockwave. Costs one chi.";
			heroAbilities[9][6] = "";
			heroAbilities[9][7] = "(Passive): Uses chi that regenerates quickly over time.";
		heroAbilities[10][0] = "(Q): ???";
			heroAbilities[10][1] = "";
			heroAbilities[10][2] = "(W): ???";
			heroAbilities[10][3] = "";
			heroAbilities[10][4] = "(E): ???";
			heroAbilities[10][5] = "";
			heroAbilities[10][6] = "";
			heroAbilities[10][7] = "";
	}
	
}
