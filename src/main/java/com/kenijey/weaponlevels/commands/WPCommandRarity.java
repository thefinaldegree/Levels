package com.kenijey.weaponlevels.commands;

import java.util.List;

import com.google.common.collect.Lists;
import com.kenijey.weaponlevels.leveling.Rarity;
import com.kenijey.weaponlevels.util.NBTHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;

public class WPCommandRarity extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList("changerarity");

	@Override
	public String getName() {
		return "changerarity";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "rarity <rarityname>";
	}
	
	@Override
	public List<String> getAliases() {
		return aliases;
	}
	@Override
	public int getRequiredPermissionLevel()
    {
        return 3;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws UsageException
	{
		if((args.length < 1) || (args.length > 1)) throw new UsageException("Usage: /changerarity <id>");
		
		if((args.length < 1) || (args.length > 1)) return;
		
		if(!(isInteger(args[0]))) throw new UsageException("Enter an id! \n1:Basic\n2:Uncommon\n3:Rare\n4:Ultra Rare\n5:Legendary\n6:Archaic");
		
		if((sender instanceof EntityPlayer) && (isInteger(args[0])))
		{
			ChangeRarity((EntityPlayer)sender, args[0]);
		}
	}
	
	public static void ChangeRarity(EntityPlayer player, String rarityid) throws UsageException
	{
		int id = Integer.parseInt(rarityid);
		String raritynumber = Integer.toString(id);
		
		if((id < 1) || (id > 6)) throw new UsageException("Rarity ID must be 1, 2, 3, 4, 5 or 6!");
		
		if (((player.getHeldItemMainhand().getItem() instanceof ItemBow) || (player.getHeldItemMainhand().getItem() instanceof ItemSword) ||
			 (player.getHeldItemMainhand().getItem() instanceof ItemAxe) || (player.getHeldItemMainhand().getItem() instanceof ItemHoe) ||
			 (player.getHeldItemMainhand().getItem() instanceof ItemArmor)) && (id <7 && id > 0))
		{
		ItemStack item = player.getHeldItemMainhand();
		NBTTagCompound nbt = NBTHelper.loadStackNBT(item);
		Rarity.setRarity(nbt, raritynumber);
		NBTHelper.saveStackNBT(item, nbt);
		player.setHeldItem(EnumHand.MAIN_HAND, item);
		}
		
		if ( !((player.getHeldItemMainhand().getItem() instanceof ItemBow) ||
			  (player.getHeldItemMainhand().getItem() instanceof ItemSword) ||
			  (player.getHeldItemMainhand().getItem() instanceof ItemAxe) ||
			  (player.getHeldItemMainhand().getItem() instanceof ItemHoe) ||
			  (player.getHeldItemMainhand().getItem() instanceof ItemArmor))) throw new UsageException("Hold a weapon or an armor in your mainhand!");
	}
	
	public static boolean isInteger(String s)
	{
		return isInteger(s, 10);
	}
	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
