package com.kenijey.levels.init;

import com.kenijey.levels.config.Config;
import com.kenijey.levels.event.EventInput;
import com.kenijey.levels.event.EventItemTooltip;
import com.kenijey.levels.event.EventLivingDeath;
import com.kenijey.levels.event.EventLivingHurt;
import com.kenijey.levels.event.EventLivingUpdate;
import com.kenijey.levels.event.EventPlayerTracking;

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
