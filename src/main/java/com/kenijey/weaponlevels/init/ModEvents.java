package com.kenijey.weaponlevels.init;

import com.kenijey.weaponlevels.config.Config;
import com.kenijey.weaponlevels.event.EventInput;
import com.kenijey.weaponlevels.event.EventItemTooltip;
import com.kenijey.weaponlevels.event.EventLivingDeath;
import com.kenijey.weaponlevels.event.EventLivingHurt;
import com.kenijey.weaponlevels.event.EventLivingUpdate;
import com.kenijey.weaponlevels.event.EventPlayerTracking;

import net.minecraftforge.common.MinecraftForge;

/**
 * 
 * @author kenijey
 *
 */
public class ModEvents 
{
	public static void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new EventItemTooltip());
		MinecraftForge.EVENT_BUS.register(new EventLivingUpdate());
		MinecraftForge.EVENT_BUS.register(new EventInput());
		MinecraftForge.EVENT_BUS.register(new EventLivingHurt());
		MinecraftForge.EVENT_BUS.register(new EventLivingDeath());
		
		if (Config.enemyLeveling)
		{
			MinecraftForge.EVENT_BUS.register(new EventPlayerTracking());
		}
	}
}
