package me.fallenbreath.tweakermore.trady.mixins.tweakmTrady;

import me.fallenbreath.tweakermore.trady.impl.TradyMerchantContainer;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.container.MerchantContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(MerchantContainer.class)
public abstract class MerchantContainerMixin implements TradyMerchantContainer
{
	@Unique
	private MerchantScreen merchantScreen;

	@Override
	public void setMerchantScreen(MerchantScreen merchantScreen)
	{
		this.merchantScreen = merchantScreen;
	}

	@Override
	public MerchantScreen getMerchantScreen()
	{
		return this.merchantScreen;
	}
}
