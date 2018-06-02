package com.kenijey.levels.event;

import com.kenijey.levels.leveling.Ability;
import com.kenijey.levels.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author kenijey
 *
 */
public class EventPlayerDrops 
{
	@SubscribeEvent
	public void onPlayerDeath(PlayerDropsEvent event)
	{
		EntityPlayer player = event.getEntityPlayer();
		Item item;
		
		for (int i = 0; i < event.getDrops().size(); i++)
		{
			item = event.getDrops().get(i).getItem().getItem();
			
			if (item != null && (item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemHoe || item instanceof ItemArmor || item instanceof ItemBow))
			{
				ItemStack stack = event.getDrops().get(i).getItem();
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
			}
		}
	}
}
