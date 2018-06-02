package com.kenijey.levels.config;

import java.io.File;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/**
 * 
 * @author kenijey
 *
 */
public class Config 
{
	// configuration files
	private static Configuration main;
	private static Configuration abilities;
	private static Configuration abilitychances;
	private static Configuration rarities;
	
	/*
	 * MAIN
	 */
	
	// overall
	public static boolean enemyLeveling = true;
	
	// experience
	public static int maxLevel = 10;
	public static int level1Exp = 40;
	public static double expExponent = 2.4D;
	public static int expMultiplier = 20;
	
	// misc
	public static boolean showDurability = true;
	public static String[] itemBlacklist = new String[] { "modid:item" };
	public static String stringPosition = "default";
	
	/*
	 * ABILITIES
	 */

	public static int maxAbilities = 3;
	
	// abilities
	public static boolean fire = true;
	public static boolean frost = true;
	public static boolean poison = true;
	public static boolean bloodlust = true;
	public static boolean chained = true;
	public static boolean voida = true;
	public static boolean light = true;
	public static boolean ethereal = true;
	
	public static boolean molten = true;
	public static boolean frozen = true;
	public static boolean toxic = true;
	public static boolean absorb = true;
	public static boolean beastial = true;
	public static boolean enlightened = true;
	public static boolean hardened = true;
	
	//ABILITY CHANCES
	
	//weapon
	public static int firechance = 4;
	public static int frostchance = 4;
	public static int poisonchance = 4;
	public static int bloodlustchance = 7;
	public static int chainedchance = 10;
	public static int voidachance = 20;
	public static int lightchance = 7;
	public static int etherealchance = 7;
	//armor
	public static int moltenchance = 4;
	public static int frozenchance = 4;
	public static int toxicchance = 4;
	public static int absorbchance = 7;
	public static int enlightenedchance = 7;
	public static int hardenedchance = 10;
	
	
	
	/*
	 * RARITIES
	 */
	
	public static double commonChance = 0.5D;
	public static double uncommonChance = 0.2D;
	public static double rareChance = 0.12D;
	public static double ultraRareChance = 0.06D;
	public static double legendaryChance = 0.03D;
	public static double archaicChance = 0.01D;
	
	public static double commonDamage = 0;
	public static double uncommonDamage = 0.3D;
	public static double rareDamage = 0.7D;
	public static double ultraRareDamage = 1.0D;
	public static double legendaryDamage = 1.5D;
	public static double archaicDamage = 2.0D;
	
	public static void init(File dir)
	{
		main = new Configuration(new File(dir.getPath(), "weaponlevels.cfg"));
		abilities = new Configuration(new File(dir.getPath(), "abilities.cfg"));
		abilitychances = new Configuration(new File(dir.getPath(), "abilitychances.cfg"));
		rarities = new Configuration(new File(dir.getPath(), "rarities.cfg"));
		
		sync();
	}
	
	private static void sync()
	{
		syncMain();
		syncAbilities();
		syncAbilityChances();
		syncRarities();
	}
	
	private static void syncMain()
	{
		String category = "main";
		List<String> propOrder = Lists.newArrayList();
		Property prop;
		
		/*
		 * Overall
		 */
		prop = main.get(category, "enemyLeveling", enemyLeveling);
		prop.setComment("Determines whether or not Enemy Leveling will be enabled. Default: true");
		enemyLeveling = prop.getBoolean();
		propOrder.add(prop.getName());
		
		/*
		 * Experience
		 */
		prop = main.get(category, "maxLevel", maxLevel);
		prop.setComment("Sets the maximum level cap for weapons and armor. Default: 10");
		maxLevel = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = main.get(category, "level1Experience", level1Exp);
		prop.setComment("Sets the amount of experience needed to level up the FIRST time. Default: 40");
		level1Exp = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = main.get(category, "experienceExponent", expExponent);
		prop.setComment("Sets the exponent of the experience algorithm. Default: 2.4");
		expExponent = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = main.get(category, "experienceMultiplier", expMultiplier);
		prop.setComment("Sets the multiplier of the experience algorithm: Default: 20");
		expMultiplier = prop.getInt();
		propOrder.add(prop.getName());
		
		/*
		 * Miscellaneous
		 */
		prop = main.get(category, "showDurabilityInTooltip", showDurability);
		prop.setComment("Determines whether or not durability will be displayed in tooltips. Default: true");
		showDurability = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = main.get(category, "itemBlacklist", itemBlacklist);
		prop.setComment("Items in this blacklist will not gain the leveling systems. Useful for very powerful items or potential conflicts. Style should be 'modid:item'");
		itemBlacklist = prop.getStringList();
		propOrder.add(prop.getName());
		
		prop = main.get(category, "enemyLevelStringPosition", stringPosition);
		prop.setComment("Determines the location of the enemy level display. Can either be 'default', 'topleft', 'topright', 'bottomleft', 'bottomright', or 'cursor'. Default: 'default'");
		stringPosition = prop.getString();
		propOrder.add(prop.getName());
		
		main.setCategoryPropertyOrder(category, propOrder);
		main.save();
	}
	
	private static void syncAbilities()
	{
		String category = "abilities";
		List<String> propOrder = Lists.newArrayList();
		Property prop;

		prop = abilities.get(category, "maxAbilitiesPerItem", maxAbilities);
		prop.setComment("Sets the maximum amount of weapons that can be applied on a given item. Default: 3");
		maxAbilities = prop.getInt();
		propOrder.add(prop.getName());
		
		/*
		 * Abilities
		 */
		// weapons
		prop = abilities.get(category, "fireAbility", fire);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		fire = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "frostAbility", frost);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		frost = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "poisonAbility", poison);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		poison = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "bloodlustAbility", bloodlust);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		bloodlust = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "chainedAbility", chained);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		chained = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "voidAbility", voida);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		voida = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "lightAbility", light);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		light = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "etherealAbility", ethereal);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		ethereal = prop.getBoolean();
		propOrder.add(prop.getName());
		
		// armor
		prop = abilities.get(category, "moltenAbility", molten);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		molten = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "frozenAbility", frozen);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		frozen = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "toxicAbility", toxic);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		toxic = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "absorbAbility", absorb);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		absorb = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "beastialAbility", beastial);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		beastial = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "enlightenedAbility", enlightened);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		enlightened = prop.getBoolean();
		propOrder.add(prop.getName());
		
		prop = abilities.get(category, "hardenedAbility", hardened);
		prop.setComment("Determines whether or not the specific ability will be present in-game. Default: true");
		hardened = prop.getBoolean();
		propOrder.add(prop.getName());
		
		abilities.setCategoryPropertyOrder(category, propOrder);
		abilities.save();
	}
	
	private static void syncAbilityChances() 
	{
		String category = "abilitychances";
		List<String> propOrder = Lists.newArrayList();
		Property prop;
		
		prop = abilitychances.get(category, "firechance", firechance);
		prop.setComment("Determines how rare the Fire ability will occur. (Higher values=lower occurance) Default: 4");
		firechance = prop.getInt();
		propOrder.add(prop.getName());

		prop = abilitychances.get(category, "frostchance", frostchance);
		prop.setComment("Determines how rare the Frost ability will occur. (Higher values=lower occurance) Default: 4");
		frostchance = prop.getInt();
		propOrder.add(prop.getName());

		prop = abilitychances.get(category, "poisonchance", poisonchance);
		prop.setComment("Determines how rare the Poison ability will occur. (Higher values=lower occurance) Default: 4");
		poisonchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "bloodlustchance", bloodlustchance);
		prop.setComment("Determines how rare the Bloodlust ability will occur. (Higher values=lower occurance) Default: 7");
		bloodlustchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "chainedchance", chainedchance);
		prop.setComment("Determines how rare the Chained ability will occur. (Higher values=lower occurance) Default: 10");
		chainedchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "voidachance", voidachance);
		prop.setComment("Determines how rare the Void ability will occur. (Higher values=lower occurance) Default: 20");
		voidachance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "lightchance", lightchance);
		prop.setComment("Determines how rare the Light ability will occur. (Higher values=lower occurance) Default: 7");
		lightchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "etherealchance", etherealchance);
		prop.setComment("Determines how rare the Ethereal ability will occur. (Higher values=lower occurance) Default: 7");
		etherealchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "moltenchance", moltenchance);
		prop.setComment("Determines how rare the Molten ability will occur. (Higher values=lower occurance) Default: 4");
		moltenchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "frozenchance", frozenchance);
		prop.setComment("Determines how rare the Frozen ability will occur. (Higher values=lower occurance) Default: 4");
		frozenchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "toxicchance", toxicchance);
		prop.setComment("Determines how rare the Toxic ability will occur. (Higher values=lower occurance) Default: 4");
		toxicchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "absorbchance", absorbchance);
		prop.setComment("Determines how rare the Absorb ability will occur. (Higher values=lower occurance) Default: 7");
		absorbchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "enlightenedchance", enlightenedchance);
		prop.setComment("Determines how rare the Enlightened ability will occur. (Higher values=lower occurance) Default: 7");
		enlightenedchance = prop.getInt();
		propOrder.add(prop.getName());
		
		prop = abilitychances.get(category, "hardenedchance", hardenedchance);
		prop.setComment("Determines how rare the Hardened ability will occur. (Higher values=lower occurance) Default: 10");
		hardenedchance = prop.getInt();
		propOrder.add(prop.getName());
		
		
		abilitychances.setCategoryPropertyOrder(category, propOrder);
		abilitychances.save();
	}
	
	private static void syncRarities()
	{
		String category = "rarities";
		List<String> propOrder = Lists.newArrayList();
		Property prop;

		/*
		 * Chances 
		 */
		prop = rarities.get(category, "commonChance", commonChance);
		prop.setComment("Sets the chance the given rarity will be applied. Default: 0.5");
		commonChance = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "uncommonChance", uncommonChance);
		prop.setComment("Sets the chance the given rarity will be applied. Default: 0.2");
		uncommonChance = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "rareChance", rareChance);
		prop.setComment("Sets the chance the given rarity will be applied. Default: 0.12");
		rareChance = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "ultraRareChance", ultraRareChance);
		prop.setComment("Sets the chance the given rarity will be applied. Default: 0.06");
		ultraRareChance = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "legendaryChance", legendaryChance);
		prop.setComment("Sets the chance the given rarity will be applied. Default: 0.03");
		legendaryChance = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "archaicChance", archaicChance);
		prop.setComment("Sets the chance the given rarity will be applied. Default: 0.01");
		archaicChance = prop.getDouble();
		propOrder.add(prop.getName());
		
		/*
		 * Damage Multipliers
		 */
		prop = rarities.get(category, "commonDamage", commonDamage);
		prop.setComment("Sets the damage multiplier for the given rarity. Default: 0");
		commonDamage = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "uncommonDamage", uncommonDamage);
		prop.setComment("Sets the damage multiplier for the given rarity. Default: 0.3");
		uncommonDamage = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "rareDamage", rareDamage);
		prop.setComment("Sets the damage multiplier for the given rarity. Default: 0.7");
		rareDamage = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "ultraRareDamage", ultraRareDamage);
		prop.setComment("Sets the damage multiplier for the given rarity. Default: 1.0");
		ultraRareDamage = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "legendaryDamage", legendaryDamage);
		prop.setComment("Sets the damage multiplier for the given rarity. Default: 1.5");
		legendaryDamage = prop.getDouble();
		propOrder.add(prop.getName());
		
		prop = rarities.get(category, "archaicDamage", archaicDamage);
		prop.setComment("Sets the damage multiplier for the given rarity. Default: 2");
		archaicDamage = prop.getDouble();
		propOrder.add(prop.getName());
		
		rarities.setCategoryPropertyOrder(category, propOrder);
		rarities.save();
	}
}
