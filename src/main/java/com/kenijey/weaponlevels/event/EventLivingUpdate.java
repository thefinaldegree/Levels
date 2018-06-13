package com.kenijey.weaponlevels.event;

import java.util.Random;

import com.kenijey.weaponlevels.config.Config;
import com.kenijey.weaponlevels.leveling.Experience;
import com.kenijey.weaponlevels.leveling.Rarity;
import com.kenijey.weaponlevels.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author kenijey
 *
 */
public class EventLivingUpdate 
{
	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			
			if (player != null)
			{
				NonNullList<ItemStack> main = player.inventory.mainInventory;
				
				if (!player.world.isRemote)
				{
					for (int i = 0; i < main.size(); i++)
					{
						if (main.get(i) != null)
						{
							Item item = main.get(i).getItem();
							
							if (item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemHoe || item instanceof ItemArmor || item instanceof ItemBow)
							{
								ItemStack stack = main.get(i);
								NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);

								if (nbt != null)
								{
									if (!Experience.isEnabled(nbt))
									{
										int count = 0;
										
										for (int j = 0; j < Config.itemBlacklist.length; j++)
										{
											if (Config.itemBlacklist[j].equals(stack.getItem().getRegistryName().getResourceDomain() + ":" + stack.getItem().getRegistryName().getResourcePath()))
											{
												count++;
											}
										}
										
										if (count == 0)
										{
											Experience.enable(nbt, true);
											Rarity rarity = Rarity.getRarity(nbt);
											Random rand = player.world.rand;
											
											if (rarity == Rarity.DEFAULT)
											{
												rarity = Rarity.getRandomRarity(rand);
												rarity.setRarity(nbt);
												NBTHelper.saveStackNBT(stack, nbt);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
