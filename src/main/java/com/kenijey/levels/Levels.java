package com.kenijey.levels;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kenijey.levels.capabilities.CapabilityEnemyLevel;
import com.kenijey.levels.commands.WPCommandExpLevel;
import com.kenijey.levels.commands.WPCommandRarity;
import com.kenijey.levels.config.Config;
import com.kenijey.levels.init.ModEvents;
import com.kenijey.levels.network.PacketEnemyLevel;
import com.kenijey.levels.network.PacketGuiAbility;
import com.kenijey.levels.proxies.CommonProxy;
import com.kenijey.levels.util.GuiHandler;
import com.kenijey.levels.util.Reference;

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
public class Levels 
{
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	@Instance(Reference.MODID)
	public static Levels instance;
	public static final Logger LOGGER = LogManager.getLogger("Levels");
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
		
		network = NetworkRegistry.INSTANCE.newSimpleChannel("levels");
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
