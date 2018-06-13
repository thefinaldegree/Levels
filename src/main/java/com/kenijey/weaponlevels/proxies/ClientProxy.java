package com.kenijey.weaponlevels.proxies;

import org.lwjgl.input.Keyboard;

import com.kenijey.weaponlevels.config.Config;
import com.kenijey.weaponlevels.event.EventRenderOverlay;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * 
 * @author kenijey
 *
 */
public class ClientProxy extends CommonProxy
{
	public static KeyBinding keyBinding;
	
	@Override
	public void preInit()
	{
		if (Config.enemyLeveling)
		{
			MinecraftForge.EVENT_BUS.register(new EventRenderOverlay());
		}
	}
	
	@Override
	public void init()
	{
		keyBinding = new KeyBinding("key.gui.weapon_interface", Keyboard.KEY_K, "key.weaponlevels");
		
		ClientRegistry.registerKeyBinding(keyBinding);
	}
}
