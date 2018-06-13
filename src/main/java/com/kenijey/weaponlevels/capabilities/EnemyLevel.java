package com.kenijey.weaponlevels.capabilities;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;

public class EnemyLevel implements IEnemyLevel
{
	private int level;
	@SuppressWarnings("unused")
	private final EntityLivingBase entity;
	
	public EnemyLevel(@Nullable EntityLivingBase entity)
	{
		this.entity = entity;
	}
	
	@Override
	public int getEnemyLevel() 
	{
		return level;
	}

	@Override
	public void setEnemyLevel(int level) 
	{
		this.level = level;
	}
}
