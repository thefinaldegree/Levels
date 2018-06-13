package com.kenijey.weaponlevels;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenijey.weaponlevels.capabilities.CapabilityEnemyLevel;
import com.kenijey.weaponlevels.commands.WPCommandExpLevel;
import com.kenijey.weaponlevels.commands.WPCommandRarity;
import com.kenijey.weaponlevels.config.Config;
import com.kenijey.weaponlevels.init.ModEvents;
import com.kenijey.weaponlevels.network.PacketEnemyLevel;
import com.kenijey.weaponlevels.network.PacketGuiAbility;
import com.kenijey.weaponlevels.proxies.CommonProxy;
import com.kenijey.weaponlevels.util.GuiHandler;
import com.kenijey.weaponlevels.util.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 
 * @author kenijey
 *
 * A simple Minecraft mod focused on the aspect of leveling certain areas
 * of the game. In its simplest state, it will add weapon leveling systems
 * on top of armor, bow, and enemy leveling in the near future. On top of
 * that, other interesting leveling systems are planned to enhance the
 * overall feel of Minecraft, while sticking to a primarily vanilla feel.
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class WeaponLevels 
{
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	@Instance(Reference.MODID)
	public static WeaponLevels instance;
	public static final Logger LOGGER = LogManager.getLogger("WeaponLevels");
	public static SimpleNetworkWrapper network;
	private static File configDir;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		configDir = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		configDir.mkdirs();
		Config.init(configDir);
		
		ModEvents.registerEvents();
		proxy.preInit();
		
		if (Config.enemyLeveling)
			CapabilityEnemyLevel.register();
		
		network = NetworkRegistry.INSTANCE.newSimpleChannel("weaponlevels");
		network.registerMessage(PacketGuiAbility.Handler.class, PacketGuiAbility.class, 0, Side.SERVER);
		network.registerMessage(PacketEnemyLevel.Handler.class, PacketEnemyLevel.class, 1, Side.CLIENT);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
	
	public static File getConfigDir()
	{
		return configDir;
	}
	
	@EventHandler
	public static void serverInit(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new WPCommandRarity());
		event.registerServerCommand(new WPCommandExpLevel());
	}
}
