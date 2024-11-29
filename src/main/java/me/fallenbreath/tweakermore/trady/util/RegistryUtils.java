package me.fallenbreath.tweakermore.trady.util;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class RegistryUtils
{
	public static String getItemId(Item item)
	{
		return Registry.ITEM.getId(item).toString();
	}
}
