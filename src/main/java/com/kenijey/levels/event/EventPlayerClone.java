package com.kenijey.levels.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author kenijey
 *
 */
public class EventPlayerClone 
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		if (event.isWasDeath())
		{
			EntityPlayer player = event.getEntityPlayer();
			player.inventory.copyInventory(event.getOriginal().inventory);
		}
	}
}
