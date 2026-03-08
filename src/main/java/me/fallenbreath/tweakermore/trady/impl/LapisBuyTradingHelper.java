package me.fallenbreath.tweakermore.trady.impl;

import me.fallenbreath.tweakermore.trady.config.TradyConfig;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LapisBuyTradingHelper extends AbstractTradingHelper
{
	private static final ItemStack LAPIS_LAZULI_2x = new ItemStack(Items.LAPIS_LAZULI, 2);

	public LapisBuyTradingHelper(MerchantScreen merchantScreen)
	{
		super(merchantScreen);
	}

	@Override
	public boolean isEnabled()
	{
		return TradyConfig.TWEAKM_TRADY_BUY_LAPIS.getBooleanValue() && this.testProfession("entity.minecraft.villager.cleric");
	}

	@Override
	protected boolean shouldCloseContainerAfterTrade()
	{
		return true;
	}

	@Override
	public void checkOffer()
	{
	}
}
