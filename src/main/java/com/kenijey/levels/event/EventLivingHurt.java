package com.kenijey.levels.event;

import java.util.Iterator;
import java.util.List;

import com.kenijey.levels.config.Config;
import com.kenijey.levels.leveling.Ability;
import com.kenijey.levels.leveling.Experience;
import com.kenijey.levels.leveling.Rarity;
import com.kenijey.levels.util.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 
 * @author kenijey
 *
 */
public class EventLivingHurt 
{
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event)
	{
		if (event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource().getTrueSource() instanceof FakePlayer))
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			EntityLivingBase enemy = event.getEntityLiving();
			ItemStack stack = player.inventory.getCurrentItem();
			
			if (stack != null && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemAxe || stack.getItem() instanceof ItemHoe))
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (nbt != null)
				{
					updateExperience(nbt);
					useRarity(event, stack, nbt);
					useWeaponAbilities(event, player, enemy, nbt);
					updateLevel(player, stack, nbt);
				}
			}
			else if (stack != null && stack.getItem() instanceof ItemBow)
			{
				NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
				
				if (nbt != null)
				{
					updateExperience(nbt);
					useRarity(event, stack, nbt);
					useWeaponAbilities(event, player, enemy, nbt);
					updateLevel(player, stack, nbt);
				}
			}
		}
		else if (event.getSource().getTrueSource() instanceof EntityLivingBase && event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			EntityLivingBase enemy = (EntityLivingBase) event.getSource().getTrueSource();
			
			for (ItemStack stack : player.inventory.armorInventory)
			{
				if (stack != null)
				{
					if (stack.getItem() instanceof ItemArmor)	
					{
						NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
						
						if (nbt != null)
						{
							updateExperience(nbt);
							useRarity(event, stack, nbt);
							useArmorAbilities(event, player, enemy, nbt);
							updateLevel(player, stack, nbt);
						}
					}
				}
			}
		}
		else if (event.getSource().getTrueSource() instanceof EntityArrow)
		{
			EntityArrow arrow = (EntityArrow) event.getSource().getTrueSource();
			
			if (arrow.shootingEntity instanceof EntityLivingBase && event.getEntityLiving() instanceof EntityPlayer)
			{
				EntityLivingBase enemy = (EntityLivingBase) arrow.shootingEntity;
				EntityPlayer player = (EntityPlayer) event.getEntityLiving();
				
				for (ItemStack stack : player.inventory.armorInventory)
				{
					if (stack != null)
					{
						if (stack.getItem() instanceof ItemArmor)	
						{
							NBTTagCompound nbt = NBTHelper.loadStackNBT(stack);
							
							if (nbt != null)
							{
								updateExperience(nbt);
								useRarity(event, stack, nbt);
								useArmorAbilities(event, player, enemy, nbt);
								updateLevel(player, stack, nbt);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Called everytime an enemy is hurt. Used to add experience to weapons dealing damage.
	 * @param nbt
	 */
	private void updateExperience(NBTTagCompound nbt)
	{
		if (Experience.getLevel(nbt) < Config.maxLevel)
		{
			boolean isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
			
			if (isDev)
				Experience.setExperience(nbt, Experience.getExperience(nbt) + Experience.getNeededExpForNextLevel(nbt) + 1);
			else
				Experience.setExperience(nbt, Experience.getExperience(nbt) + 1);
		}
	}
	
	/**
	 * Called everytime an enemy is hurt. Used to add rarity bonuses, such as dealing more damage or adding more experience.
	 * @param nbt
	 */
	private void useRarity(LivingHurtEvent event, ItemStack stack, NBTTagCompound nbt)
	{
		Rarity rarity = Rarity.getRarity(nbt);
		double damageMultiplier = 1F;
		
		if (rarity != Rarity.DEFAULT)
		{
			int var1; // bonus experience chance
			int var2; // bonus experience amount
			int var3; // durability mitigation chance
			int var4; // durability mitigated amount
			
			// damage boosts and bonus experience
			switch (rarity)
			{
				// 5% chance of dealing 1.25x damage and 5% chance of gaining additional points of experience
				case UNCOMMON:
					var1 = (int) (Math.random() * 20);
					var2 = (int) (Math.random() * 1);
					damageMultiplier = Config.uncommonDamage;
					if (var1 == 0) Experience.setExperience(nbt, Experience.getExperience(nbt) + var2);
					// durability
					var3 = (int) (Math.random() * 20);
					var4 = (int) (Math.random() * 1);
					if (var3 == 0) stack.setItemDamage(stack.getItemDamage() - var4);
					break;
				// 7.7% chance of dealing 1.5x damage and 7.7% chance of gaining additional points of experience
				case RARE:
					var1 = (int) (Math.random() * 13);
					var2 = (int) (Math.random() * 2);
					damageMultiplier = Config.rareDamage;
					if (var1 == 0) Experience.setExperience(nbt, Experience.getExperience(nbt) + var2);
					// durability
					var3 = (int) (Math.random() * 13);
					var4 = (int) (Math.random() * 2);
					if (var3 == 0) stack.setItemDamage(stack.getItemDamage() - var4);
					break;
				// 10% chance of dealing 2x damage and 10% chance of gaining additional points of experience
				case ULTRA_RARE:
					var1 = (int) (Math.random() * 10);
					var2 = (int) (Math.random() * 3);
					damageMultiplier = Config.ultraRareDamage;
					if (var1 == 0) Experience.setExperience(nbt, Experience.getExperience(nbt) + var2);
					// durability
					var3 = (int) (Math.random() * 10);
					var4 = (int) (Math.random() * 3);
					if (var3 == 0) stack.setItemDamage(stack.getItemDamage() - var4);
					break;
				// 14% chance of dealing 2.5x damage and 14% chance of gaining additional points of experience
				case LEGENDARY:
					var1 = (int) (Math.random() * 7);
					var2 = (int) (Math.random() * 5);
					damageMultiplier = Config.legendaryDamage;
					if (var1 == 0) Experience.setExperience(nbt, Experience.getExperience(nbt) + var2);
					// durability
					var3 = (int) (Math.random() * 7);
					var4 = (int) (Math.random() * 5);
					if (var3 == 0) stack.setItemDamage(stack.getItemDamage() - var4);
					break;
				// 20% chance of dealing 3x damage and 20% chance of gaining additional points of experience
				case ARCHAIC:
					var1 = (int) (Math.random() * 5);
					var2 = (int) (Math.random() * 10);
					damageMultiplier = Config.archaicDamage;
					if (var1 == 0) Experience.setExperience(nbt, Experience.getExperience(nbt) + var2);
					// durability
					var3 = (int) (Math.random() * 5);
					var4 = (int) (Math.random() * 10);
					if (var3 == 0) stack.setItemDamage(stack.getItemDamage() - var4);
					break;
				default:
					break;
			}
			
			if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemAxe || stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemBow)
				event.setAmount((float) (event.getAmount() + (event.getAmount() * damageMultiplier)));
			else if (stack.getItem() instanceof ItemArmor)
				event.setAmount((float) (event.getAmount() / (1.0F + (damageMultiplier/5.2F))));
		}
	}
	
	/**
	 * Called everytime an enemy is hurt. Used to use the current abilities a weapon might have.
	 * @param event
	 * @param player
	 * @param enemy
	 * @param nbt
	 */
	private void useWeaponAbilities(LivingHurtEvent event, EntityPlayer player, EntityLivingBase enemy, NBTTagCompound nbt)
	{
		if (enemy != null)
		{
			// active
			if (Ability.FIRE.hasAbility(nbt) && (int) (Math.random() * Config.firechance) == 0)
			{
				double multiplier = Ability.FIRE.getMultiplier(Ability.FIRE.getLevel(nbt));
				enemy.setFire((int) (4 * multiplier));
			}
			
			if (Ability.FROST.hasAbility(nbt) && (int) (Math.random() * Config.frostchance) == 0)
			{
				double multiplier = Ability.FROST.getMultiplier(Ability.FROST.getLevel(nbt));
				enemy.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) (20 * (2 * multiplier)), 10));
			}
			
			if (Ability.POISON.hasAbility(nbt) && (int) (Math.random() * Config.poisonchance) == 0)
			{
				double multiplier = Ability.POISON.getMultiplier(Ability.POISON.getLevel(nbt));
				enemy.addPotionEffect(new PotionEffect(MobEffects.POISON, (int) (20 * (7 * multiplier)), Ability.POISON.getLevel(nbt)));
			}
			
			if (Ability.INNATE.hasAbility(nbt) && (int) (Math.random() * Config.innatechance) == 0)
			{
				double multiplier = Ability.INNATE.getMultiplier(Ability.INNATE.getLevel(nbt));
				enemy.addPotionEffect(new PotionEffect(MobEffects.WITHER, (int) (20 * (4 * multiplier)), Ability.INNATE.getLevel(nbt)));
			}

			if (Ability.BOMBASTIC.hasAbility(nbt) && (int) (Math.random() * Config.bombasticchance) == 0)
			{
				double multiplierD = Ability.BOMBASTIC.getMultiplier(Ability.BOMBASTIC.getLevel(nbt));
				float multiplier = (float)multiplierD;
				World world = enemy.getEntityWorld();
					
					if (enemy instanceof EntityLivingBase && !(enemy instanceof EntityAnimal))
					{
						world.createExplosion(enemy, enemy.lastTickPosX, enemy.lastTickPosY, enemy.lastTickPosZ, multiplier, true);
					}
			}
			
			if (Ability.VOID.hasAbility(nbt) && (int) (Math.random() * Config.voidachance) == 0)
			{
				float multiplier = 0F;
				
				if (Ability.VOID.getLevel(nbt) == 1) multiplier = 0.45F;
				else if (Ability.VOID.getLevel(nbt) == 2) multiplier = 0.65F;
				else if (Ability.VOID.getLevel(nbt) == 3) multiplier = 0.8F;

				float damage = enemy.getMaxHealth() * multiplier;
				event.setAmount(damage);
			}
			
			// passive
			if (Ability.ILLUMINATION.hasAbility(nbt) && (int) (Math.random() * Config.illuminationchance) == 0)
			{
				enemy.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, (int) (20 * 6), Ability.ILLUMINATION.getLevel(nbt)));
			}
			
			if (Ability.ETHEREAL.hasAbility(nbt) && (int) (Math.random() * Config.etherealchance) == 0)
			{
				float health = (float) (player.getHealth() + (event.getAmount() / 2));
				player.setHealth(health);
			}
		}
	}
	
	private void useArmorAbilities(LivingHurtEvent event, EntityPlayer player, EntityLivingBase enemy, NBTTagCompound nbt)
	{
		if (enemy != null)
		{
			// active
			if (Ability.MOLTEN.hasAbility(nbt) && (int) (Math.random() * Config.moltenchance) == 0)
			{
				double multiplier = Ability.MOLTEN.getMultiplier(Ability.MOLTEN.getLevel(nbt));
				enemy.setFire((int) (4 * multiplier));
			}
			
			if (Ability.FROZEN.hasAbility(nbt) && (int) (Math.random() * Config.frozenchance) == 0)
			{
				double multiplier = Ability.FROZEN.getMultiplier(Ability.FROZEN.getLevel(nbt));
				enemy.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) (20 * (2 * multiplier)), 10));
			}
			
			if (Ability.TOXIC.hasAbility(nbt) && (int) (Math.random() * Config.toxicchance) == 0)
			{
				double multiplier = Ability.TOXIC.getMultiplier(Ability.TOXIC.getLevel(nbt));
				enemy.addPotionEffect(new PotionEffect(MobEffects.POISON, (int) (20 * (7 * multiplier)), Ability.TOXIC.getLevel(nbt)));
			}
			
			if (Ability.ABSORB.hasAbility(nbt) && (int) (Math.random() * Config.absorbchance) == 0)
			{
				double multiplier = Ability.ABSORB.getMultiplier(Ability.ABSORB.getLevel(nbt));
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, (int) (20 * (5 * multiplier)), Ability.ABSORB.getLevel(nbt)));
			}

			// passive
			if (Ability.BEASTIAL.hasAbility(nbt))
			{
				if (player.getHealth() <= (player.getMaxHealth() * 0.2F))
					player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 10, 0));
			}
			
			if (Ability.ENLIGHTENED.hasAbility(nbt) && (int) (Math.random() * Config.enlightenedchance) == 0)
			{
				float health = (float) (player.getHealth() + (event.getAmount() / 2));
				player.setHealth(health);
			}
			
			if (Ability.HARDENED.hasAbility(nbt) && (int) (Math.random() * Config.hardenedchance) == 0)
			{
				event.setAmount(0F);
			}
		}
	}
	
	/**
	 * Called everytime an enemy is hurt. Used to check whether or not the weapon should level up.
	 * @param player
	 * @param stack
	 * @param nbt
	 */
	private void updateLevel(EntityPlayer player, ItemStack stack, NBTTagCompound nbt)
	{
		int level = Experience.getNextLevel(player, stack, nbt, Experience.getLevel(nbt), Experience.getExperience(nbt));
		Experience.setLevel(nbt, level);
		NBTHelper.saveStackNBT(stack, nbt);
	}
}
