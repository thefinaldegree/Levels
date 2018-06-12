package com.kenijey.levels.event;

import net.minecraft.advancements.AdvancementList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author kenijey
 *
 */
public class EventAchievement 
{
	@SubscribeEvent
	public void onAchievement(AdvancementEvent event)
	{
		if (event.getAdvancement().equals(AdvancementList.class))
		{
			EntityPlayer player = event.getEntityPlayer();
			
			player.sendMessage(new TextComponentString(TextFormatting.GOLD + "=========={ Weapon Levels}=========="));
			player.sendMessage(new TextComponentString(""));
			player.sendMessage(new TextComponentString(TextFormatting.GOLD + "- " + TextFormatting.GRAY + "Press 'L' with a weapon/armor in hand to get started!"));
			player.sendMessage(new TextComponentString(""));
			player.sendMessage(new TextComponentString(TextFormatting.GOLD + "- " + TextFormatting.GRAY + "For more information, visit our wiki on our GitHub repository!"));
			player.sendMessage(new TextComponentString(""));
			player.sendMessage(new TextComponentString(TextFormatting.GOLD + "=============================="));
			player.sendMessage(new TextComponentString(""));
		}
	}
}
